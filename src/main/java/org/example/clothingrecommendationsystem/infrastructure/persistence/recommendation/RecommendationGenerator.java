package org.example.clothingrecommendationsystem.infrastructure.persistence.recommendation;

import lombok.NonNull;
import org.example.clothingrecommendationsystem.infrastructure.external.weather.WeatherCacheService;
import org.example.clothingrecommendationsystem.model.pieceofclothes.PieceOfClothes;
import org.example.clothingrecommendationsystem.model.pieceofclothes.IPieceOfClothesRepository;
import org.example.clothingrecommendationsystem.model.recommendation.IRecommendationGenerator;
import org.example.clothingrecommendationsystem.model.recommendation.Recommendation;
import org.example.clothingrecommendationsystem.model.weather.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.example.clothingrecommendationsystem.dictionaries.StyleDictionary.UA_STYLE_MAP;
import static org.example.clothingrecommendationsystem.dictionaries.ColorDictionary.*;

@Service
public class RecommendationGenerator implements IRecommendationGenerator {

    private final IPieceOfClothesRepository repo;
    private final WeatherCacheService weather;

    @Autowired
    public RecommendationGenerator(IPieceOfClothesRepository repo,
                                   WeatherCacheService weather) {
        this.repo = repo;
        this.weather = weather;
    }

    private static final Random RNG = ThreadLocalRandom.current();

    private static final Set<String> FIRST_LAYER  = Set.of("майка","топ");
    private static final Set<String> SECOND_LAYER = Set.of("сорочка","футболка","теніска","лонгслів","вишиванка");

    @Override
    public Recommendation generateRecommendation(@NonNull String prompt,
                                                 @NonNull Long userId) {

        ParsedRequest req = ensureRandom(parseGenerationCharacteristics(prompt.toLowerCase(Locale.ROOT)));

        boolean useStrictStyle = req.style != null;

        Weather w = weather.get(userId);
        List<PieceOfClothes> wardrobe = repo.getAllByUserId(userId);

        PieceOfClothes.TemperatureCategory now = mapTemperature(w.getFeelsLikeTemperature());
        Map<PieceOfClothes.PieceCategory,List<PieceOfClothes>> byCat = buildTempBuckets(wardrobe, now);

        List<PieceOfClothes> outfit = new ArrayList<>();
        Set<String> palette = new HashSet<>();

        PieceOfClothes single = pickBest(byCat.get(PieceOfClothes.PieceCategory.Single), req, useStrictStyle, true, now, palette);
        if (single!=null && RNG.nextDouble()<0.5) addAndLearn(outfit, single, palette);

        if (outfit.isEmpty()) {
            buildInnerLayers(byCat.get(PieceOfClothes.PieceCategory.Innerlayer), outfit, req,
                    w.getFeelsLikeTemperature(), now, palette, useStrictStyle);
            addBestUnique(outfit, byCat.get(PieceOfClothes.PieceCategory.Bottom), req, useStrictStyle,true, now, palette);
        }
        addBestUnique(outfit, byCat.get(PieceOfClothes.PieceCategory.Footwear), req, useStrictStyle,true, now, palette);

        boolean precip = Optional.ofNullable(w.getWeatherCondition())
                .map(String::toLowerCase)
                .map(s -> s.contains("rain")||s.contains("snow"))
                .orElse(false);

        boolean needOuter = w.getFeelsLikeTemperature()<10;

        boolean randomOuterAllowed = switch (now){
            case Warm,Hot,ExtremeHeat      -> false;
            case MildWarm                   -> innerCount(outfit)<3;
            default                         -> true;
        };

        List<PieceOfClothes> outers = byCat.get(PieceOfClothes.PieceCategory.Outerlayer);
        if (outers!=null && !outers.isEmpty()){
            if (needOuter){
                addBestUnique(outfit,outers,req,useStrictStyle,true,now,palette);
            }else if(randomOuterAllowed && RNG.nextDouble()<0.5){
                List<PieceOfClothes> strict = outers.stream()
                        .filter(o->o.getTemperatureCategories().contains(now))
                        .toList();
                addBestUnique(outfit,strict,req,useStrictStyle,true,now,palette);
            }
        }

        maybeAddAccessories(byCat.get(PieceOfClothes.PieceCategory.Accessories),
                outfit, precip, now, req, palette);
        maybeAddHeadwear(byCat.get(PieceOfClothes.PieceCategory.Headwear),
                outfit, precip, w.getFeelsLikeTemperature(), now, req, palette);

        Recommendation r = new Recommendation();
        r.setRecommendedClothes(outfit);
        return r;
    }

    private Map<PieceOfClothes.PieceCategory,List<PieceOfClothes>> buildTempBuckets(List<PieceOfClothes> wardrobe,
                                                                                    PieceOfClothes.TemperatureCategory now){
        Map<PieceOfClothes.PieceCategory,List<PieceOfClothes>> byCat = new EnumMap<>(PieceOfClothes.PieceCategory.class);
        int radius=0;
        while(byCat.values().stream().allMatch(List::isEmpty) && radius<=5){
            Set<PieceOfClothes.TemperatureCategory> allowed = expandTemperature(now,radius);
            Predicate<PieceOfClothes> tempOk = pc -> pc.getTemperatureCategories().stream().anyMatch(allowed::contains);
            byCat = wardrobe.stream().filter(Objects::nonNull).filter(tempOk)
                    .collect(Collectors.groupingBy(PieceOfClothes::getPieceCategory,
                            ()->new EnumMap<>(PieceOfClothes.PieceCategory.class),
                            Collectors.toList()));
            radius++;
        }
        return byCat;
    }

    private void buildInnerLayers(List<PieceOfClothes> inners,
                                  List<PieceOfClothes> outfit,
                                  ParsedRequest req,
                                  float t,
                                  PieceOfClothes.TemperatureCategory now,
                                  Set<String> palette, boolean useStrictStyle){
        if(inners==null || inners.isEmpty()) return;

        List<PieceOfClothes> tier1 = inners.stream().filter(pc->FIRST_LAYER .stream().anyMatch(k->pc.getName().toLowerCase().contains(k))).toList();
        List<PieceOfClothes> tier2 = inners.stream().filter(pc->SECOND_LAYER.stream().anyMatch(k->pc.getName().toLowerCase().contains(k))).toList();

        List<PieceOfClothes> basePool = new ArrayList<>(tier1); basePool.addAll(tier2);
        if(basePool.isEmpty()) basePool.addAll(inners);

        PieceOfClothes base = pickBest(basePool, req, useStrictStyle, true, now, palette);

        addAndLearn(outfit,base,palette);

        Set<Tier> usedTiers = EnumSet.of(tierOf(base));

        int min,max;
        if(t<10){           min=3;max=3;}
        else if(t<15){      min=2;max=3;}
        else if(t<20){      min=1;max=3;}
        else if(t<25){      min=1;max=2;}
        else {              min=1;max=2;}

        List<PieceOfClothes> pool = new ArrayList<>(inners);
        pool.removeIf(pc -> tierOf(pc) == tierOf(base));
        Collections.shuffle(pool,RNG);

        while(innerCount(outfit)<min && !pool.isEmpty()){
            PieceOfClothes cand = pickBest(pool, req, useStrictStyle, true, now, palette);
            pool.remove(cand);
            if(usedTiers.add(tierOf(cand))) {
                if(addAndLearn(outfit,cand,palette)){
                    pool.removeIf(pc -> tierOf(pc) == tierOf(cand));
                }
            }
        }
        while (innerCount(outfit) < max && !pool.isEmpty()) {
            PieceOfClothes cand = pickBest(pool, req, useStrictStyle, true, now, palette);
            pool.remove(cand);
            if (RNG.nextDouble() < 0.5 && usedTiers.add(tierOf(cand)))
                if(addAndLearn(outfit,cand,palette)){
                    pool.removeIf(pc -> tierOf(pc) == tierOf(cand));
                }
        }
    }

    private static long innerCount(List<PieceOfClothes> list){
        return list.stream().filter(p->p.getPieceCategory()==PieceOfClothes.PieceCategory.Innerlayer).count();
    }

    private void maybeAddAccessories(List<PieceOfClothes> acc,
                                     List<PieceOfClothes> outfit,
                                     boolean precip,
                                     PieceOfClothes.TemperatureCategory now,
                                     ParsedRequest req,
                                     Set<String> palette){

        if(acc==null || acc.isEmpty()) return;

        acc.stream().filter(a->precip &&
                        Optional.ofNullable(a.getName()).map(String::toLowerCase)
                                .filter(n->n.contains("парасолька")||n.contains("umbrella")).isPresent())
                .findFirst().ifPresent(a->addAndLearn(outfit,a,palette));

        List<PieceOfClothes> pool = acc.stream()
                .filter(a->styleOk(a, req.style))
                .collect(Collectors.toCollection(ArrayList::new));
        pool.removeAll(outfit);
        Collections.shuffle(pool,RNG);

        int added=0;
        for (PieceOfClothes pc: pool){
            if(RNG.nextDouble()<0.5 && addAndLearn(outfit,pc,palette) && ++added==2) break;
        }
    }

    private void maybeAddHeadwear(List<PieceOfClothes> head,
                                  List<PieceOfClothes> outfit,
                                  boolean precip,
                                  float t,
                                  PieceOfClothes.TemperatureCategory now,
                                  ParsedRequest req,
                                  Set<String> palette){
        if(head==null || head.isEmpty()) return;
        boolean must = precip || t<10;
        if(RNG.nextDouble() < (must?1.0:0.5))
            addAndLearn(outfit, head.get(RNG.nextInt(head.size())), palette);
    }

    private ParsedRequest ensureRandom(ParsedRequest r) {
        String col = r.color;
        PieceOfClothes.Style s = r.style;

        if (s == null && col == null) {
            s = List.copyOf(UA_STYLE_MAP.values()).get(RNG.nextInt(UA_STYLE_MAP.size()));
            col = new ArrayList<>(BASE_PALETTE.keySet()).get(RNG.nextInt(BASE_PALETTE.size()));
        }

        if (col == null) {
            col = new ArrayList<>(BASE_PALETTE.keySet()).get(RNG.nextInt(BASE_PALETTE.size()));
        }

        return new ParsedRequest(s, col);
    }


    private ParsedRequest parseGenerationCharacteristics(String txt){
        PieceOfClothes.Style style = UA_STYLE_MAP.entrySet().stream()
                .filter(e->txt.contains(e.getKey()))
                .map(Map.Entry::getValue).findFirst().orElse(null);
        String col = SHADE_TO_BASE.entrySet().stream()
                .filter(e->txt.contains(e.getKey()))
                .map(Map.Entry::getValue).findFirst().orElse(null);
        return new ParsedRequest(style,col);
    }

    private PieceOfClothes.TemperatureCategory mapTemperature(float t){
        if(t>=30) return PieceOfClothes.TemperatureCategory.ExtremeHeat;
        if(t>=25) return PieceOfClothes.TemperatureCategory.Hot;
        if(t>=20) return PieceOfClothes.TemperatureCategory.Warm;
        if(t>=15) return PieceOfClothes.TemperatureCategory.MildWarm;
        if(t>=10) return PieceOfClothes.TemperatureCategory.Cool;
        if(t>=5)  return PieceOfClothes.TemperatureCategory.Cold;
        if(t>=0)  return PieceOfClothes.TemperatureCategory.LightFrost;
        if(t>=-5) return PieceOfClothes.TemperatureCategory.ChillyFrost;
        if(t>=-10)return PieceOfClothes.TemperatureCategory.Frost;
        if(t>=-19)return PieceOfClothes.TemperatureCategory.SevereFrost;
        return PieceOfClothes.TemperatureCategory.ExtremeCold;
    }
    private Set<PieceOfClothes.TemperatureCategory> expandTemperature(PieceOfClothes.TemperatureCategory base,int radius){
        PieceOfClothes.TemperatureCategory[] v = PieceOfClothes.TemperatureCategory.values();
        int idx = Arrays.asList(v).indexOf(base);
        Set<PieceOfClothes.TemperatureCategory> res = EnumSet.of(base);
        for(int r=1;r<=radius;r++){
            if(idx-r>=0)   res.add(v[idx-r]);
            if(idx+r<v.length) res.add(v[idx+r]);
        }
        return res;
    }

    private PieceOfClothes pickBest(List<PieceOfClothes> items,
                                    ParsedRequest req,
                                    boolean strictStyle,
                                    boolean strictColor,
                                    PieceOfClothes.TemperatureCategory now,
                                    Set<String> palette){
        if(items==null || items.isEmpty()) return null;

        if(strictStyle && req.style!=null){
            List<PieceOfClothes> styleMatched = items.stream()
                    .filter(pc->pc.getStyles()!=null && pc.getStyles().contains(req.style))
                    .toList();
            if(!styleMatched.isEmpty()){
                items = styleMatched;
                strictStyle = false;
            }
        }

        if(strictColor && req.color!=null){
            List<PieceOfClothes> exact = items.stream()
                    .filter(pc->collectColors(pc).contains(req.color))
                    .toList();
            if(!exact.isEmpty()) items = exact;
        }

        TreeMap<Integer,List<PieceOfClothes>> bucket = new TreeMap<>();
        for (PieceOfClothes pc: items){
            int score = 0;

            int dist = pc.getTemperatureCategories().stream()
                    .mapToInt(c->Math.abs(c.ordinal()-now.ordinal()))
                    .min().orElse(5);
            score += dist*30;

            if(strictStyle && req.style!=null &&
                    (pc.getStyles()==null || !pc.getStyles().contains(req.style)))
                score += 300;

            if(req.color!=null){
                List<String> pal = BASE_PALETTE.getOrDefault(req.color,List.of(req.color));
                Set<String> all  = collectColors(pc);
                boolean exact = pal.stream().anyMatch(all::contains);
                if(!exact){
                    boolean near = COMPATIBLE_BASE.getOrDefault(req.color,List.of()).stream()
                            .map(BASE_PALETTE::get).filter(Objects::nonNull)
                            .anyMatch(lst->lst.stream().anyMatch(all::contains));
                    score += near ? 50 : 150;
                }
            }

            if(!palette.isEmpty()){
                boolean comp = palette.stream()
                        .anyMatch(c->collectColors(pc).stream()
                                .anyMatch(cl->cl.equals(c)||
                                        COMPATIBLE_BASE.getOrDefault(c,List.of()).contains(cl)));
                if(!comp) score += 100;
            }

            bucket.computeIfAbsent(score,__->new ArrayList<>()).add(pc);
        }
        List<PieceOfClothes> best = bucket.firstEntry().getValue();

        System.out.println(best);

        System.out.println("_----------------------------------------------------------------------------");

        return best.get(RNG.nextInt(best.size()));
    }

    private Set<String> collectColors(PieceOfClothes pc){
        Set<String> s = new HashSet<>();
        Optional.ofNullable(pc.getColor())
                .ifPresent(c->s.add(SHADE_TO_BASE.getOrDefault(c.toLowerCase(),c.toLowerCase())));
        Optional.ofNullable(pc.getCharacteristics()).orElse(List.of())
                .forEach(ch->{
                    String low = ch.toLowerCase();
                    String base= SHADE_TO_BASE.get(low);
                    if(base!=null) s.add(base);
                    else if(BASE_PALETTE.containsKey(low)) s.add(low);
                });
        return s;
    }
    private boolean addAndLearn(List<PieceOfClothes> outfit, PieceOfClothes cand, Set<String> palette){
        if(addUnique(outfit,cand)){
            palette.addAll(collectColors(cand));
            return true;
        }
        return false;
    }
    private static boolean addUnique(List<PieceOfClothes> outfit, PieceOfClothes cand){
        String n = Optional.ofNullable(cand.getName()).orElse("").trim().toLowerCase();
        boolean exists = outfit.stream().anyMatch(p->{
            String m = Optional.ofNullable(p.getName()).orElse("").trim().toLowerCase();
            return m.contains(n)||n.contains(m);
        });
        if(!exists) outfit.add(cand);
        return !exists;
    }
    private void addBestUnique(List<PieceOfClothes> outfit, List<PieceOfClothes> pool,
                               ParsedRequest req, boolean strictStyle, boolean strictColor,
                               PieceOfClothes.TemperatureCategory now, Set<String> palette){
        PieceOfClothes best = pickBest(pool,req,strictStyle,strictColor,now,palette);
        if(best!=null) addAndLearn(outfit,best,palette);
    }

    private record ParsedRequest(PieceOfClothes.Style style, String color){}

    enum Tier {FIRST,SECOND,OTHER}
    private Tier tierOf(PieceOfClothes pc){
        String name = Optional.ofNullable(pc.getName()).orElse("").toLowerCase(Locale.ROOT);
        if(FIRST_LAYER .stream().anyMatch(name::contains)) return Tier.FIRST;
        if(SECOND_LAYER.stream().anyMatch(name::contains)) return Tier.SECOND;
        return Tier.OTHER;
    }

    private boolean styleOk(PieceOfClothes pc, PieceOfClothes.Style need){
        if(need==null) return true;
        return pc.getStyles()!=null && pc.getStyles().contains(need);
    }
}
