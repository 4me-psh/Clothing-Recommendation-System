package org.example.clothingrecommendationsystem.infrastructure.persistence.recommendation;

import org.example.clothingrecommendationsystem.model.pieceofclothes.PieceOfClothes;
import org.example.clothingrecommendationsystem.model.pieceofclothes.IPieceOfClothesRepository;
import org.example.clothingrecommendationsystem.model.recommendation.IRecommendationGenerator;
import org.example.clothingrecommendationsystem.model.recommendation.Recommendation;
import org.example.clothingrecommendationsystem.model.weather.IWeatherOrchestrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationGenerator implements IRecommendationGenerator {

    private final IPieceOfClothesRepository pieceOfClothesRepository;
    private final IWeatherOrchestrator weatherOrchestrator;

    @Autowired
    public RecommendationGenerator(IPieceOfClothesRepository pieceOfClothesRepository,
                                   IWeatherOrchestrator weatherOrchestrator) {
        this.pieceOfClothesRepository = pieceOfClothesRepository;
        this.weatherOrchestrator = weatherOrchestrator;
    }

    @Override
    public Recommendation generateRecommendation(String generationCharacteristics, Long userId) {
        String city = "Kyiv";
        float feelsLikeTemp = weatherOrchestrator.getByCity(city).getFeelsLikeTemperature();

        // Завантажуємо увесь гардероб (далі можна змінити на userId)
        List<PieceOfClothes> wardrobe = pieceOfClothesRepository.getAllByUserId(userId);

        // Фільтрація речей за температурними категоріями
        List<PieceOfClothes> suitable = wardrobe.stream()
                // Фільтрація за погодою
                .filter(pc -> matchesWeather(pc, feelsLikeTemp))
                // Фільтрація за кольором, якщо в рядку є вказівка "color:" або "palette:"
                .filter(pc -> {
                    String gen = generationCharacteristics.toLowerCase();
                    boolean hasPalette = gen.contains("color:") || gen.contains("palette:");
                    if (!hasPalette) return true;
                    String color = pc.getColor().toLowerCase();
                    return gen.contains(color);
                })
                .toList();

        // Групування за категоріями
        Map<String, List<PieceOfClothes>> byCat = suitable.stream()
                .collect(Collectors.groupingBy(pc -> pc.getPieceCategory().toLowerCase()));

        List<PieceOfClothes> selected = new ArrayList<>();
        Random rand = new Random();

        // Одинарний одяг з імовірністю 50%
        if (byCat.containsKey("single") && rand.nextBoolean()) {
            selected.add(randomFrom(byCat.get("single"), rand));
            optionalAdd(byCat, "outerlayer", selected, rand);
        } else {
            // Багатошаровість внутрішнього шару
            List<PieceOfClothes> inner = byCat.getOrDefault("innerlayer", Collections.emptyList());
            int layers = determineLayers(feelsLikeTemp);
            selected.addAll(selectRandomItems(inner, layers, rand));

            optionalAdd(byCat, "bottom", selected, rand);
            if (feelsLikeTemp < 18) optionalAdd(byCat, "outerlayer", selected, rand);
            if (feelsLikeTemp < 10) optionalAdd(byCat, "headwear", selected, rand);
        }

        // Взуття
        optionalAdd(byCat, "footwear", selected, rand);
        // Аксесуари (до 2)
        selected.addAll(selectAccessories(byCat.getOrDefault("accessories", Collections.emptyList()), rand));

        // Повертаємо рекомендацію
        Recommendation rec = new Recommendation();
        rec.setUserPrompt(generationCharacteristics);
        rec.setRecommendedClothes(selected);
        return rec;
    }

    private boolean matchesWeather(PieceOfClothes pc, float temp) {
        return pc.getTemperatureCategories().stream().anyMatch(tc -> {
            String range = tc.getRange().toLowerCase();
            if (range.contains("andabove")) {
                float low = Float.parseFloat(range.split("andabove")[0].trim());
                return temp >= low;
            } else if (range.contains("andbelow")) {
                float high = Float.parseFloat(range.split("andbelow")[0].trim());
                return temp <= high;
            } else if (range.contains("to")) {
                String[] parts = range.split("to");
                float low = Float.parseFloat(parts[0].trim());
                float high = Float.parseFloat(parts[1].trim());
                return temp >= low && temp <= high;
            }
            return false;
        });
    }

    private PieceOfClothes randomFrom(List<PieceOfClothes> list, Random rand) {
        return list.get(rand.nextInt(list.size()));
    }

    private void optionalAdd(Map<String, List<PieceOfClothes>> byCat, String cat,
                             List<PieceOfClothes> selected, Random rand) {
        List<PieceOfClothes> list = byCat.getOrDefault(cat, Collections.emptyList());
        if (!list.isEmpty() && rand.nextBoolean()) {
            selected.add(randomFrom(list, rand));
        }
    }

    private int determineLayers(float temp) {
        if (temp >= 20) return 1;
        if (temp >= 15) return 2;
        if (temp >= 10) return 2;
        if (temp >= 5) return 3;
        return 3;
    }

    private List<PieceOfClothes> selectRandomItems(List<PieceOfClothes> items, int count, Random rand) {
        if (items.size() <= count) return new ArrayList<>(items);
        List<PieceOfClothes> copy = new ArrayList<>(items);
        Collections.shuffle(copy, rand);
        return copy.subList(0, count);
    }

    private List<PieceOfClothes> selectAccessories(List<PieceOfClothes> accessories, Random rand) {
        Collections.shuffle(accessories, rand);
        int count = Math.min(2, accessories.size());
        return new ArrayList<>(accessories.subList(0, count));
    }
}
