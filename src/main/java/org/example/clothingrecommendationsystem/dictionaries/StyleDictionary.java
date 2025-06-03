package org.example.clothingrecommendationsystem.dictionaries;

import lombok.experimental.UtilityClass;
import org.example.clothingrecommendationsystem.model.pieceofclothes.PieceOfClothes;

import java.util.Map;

@UtilityClass
public class StyleDictionary {

    public static final Map<String, PieceOfClothes.Style> UA_STYLE_MAP =
            Map.ofEntries(
                    Map.entry("повсякденний",  PieceOfClothes.Style.Casual),
                    Map.entry("повсякденного", PieceOfClothes.Style.Casual),
                    Map.entry("повсякденному", PieceOfClothes.Style.Casual),
                    Map.entry("повсякденним",  PieceOfClothes.Style.Casual),
                    Map.entry("буденний",      PieceOfClothes.Style.Casual),
                    Map.entry("буденного",     PieceOfClothes.Style.Casual),
                    Map.entry("буденному",     PieceOfClothes.Style.Casual),
                    Map.entry("щоденний",      PieceOfClothes.Style.Casual),
                    Map.entry("щоденного",     PieceOfClothes.Style.Casual),
                    Map.entry("кежуал",        PieceOfClothes.Style.Casual),
                    Map.entry("casual",        PieceOfClothes.Style.Casual),

                    Map.entry("спортивний",    PieceOfClothes.Style.Sporty),
                    Map.entry("спортивного",   PieceOfClothes.Style.Sporty),
                    Map.entry("спортивному",   PieceOfClothes.Style.Sporty),
                    Map.entry("спортивним",    PieceOfClothes.Style.Sporty),
                    Map.entry("спорт",         PieceOfClothes.Style.Sporty),
                    Map.entry("атлетичний",    PieceOfClothes.Style.Sporty),
                    Map.entry("атлетичного",   PieceOfClothes.Style.Sporty),
                    Map.entry("тренувальний",  PieceOfClothes.Style.Sporty),

                    Map.entry("бізнес",        PieceOfClothes.Style.Business),
                    Map.entry("діловий",       PieceOfClothes.Style.Business),
                    Map.entry("ділового",      PieceOfClothes.Style.Business),
                    Map.entry("діловому",      PieceOfClothes.Style.Business),
                    Map.entry("формальний",    PieceOfClothes.Style.Business),
                    Map.entry("формального",   PieceOfClothes.Style.Business),
                    Map.entry("офіційний",     PieceOfClothes.Style.Business),
                    Map.entry("офіційного",    PieceOfClothes.Style.Business),
                    Map.entry("класичний",     PieceOfClothes.Style.Business),
                    Map.entry("костюмний",     PieceOfClothes.Style.Business),
                    Map.entry("строгий",       PieceOfClothes.Style.Business),

                    Map.entry("вечірній",      PieceOfClothes.Style.Evening),
                    Map.entry("вечірнього",    PieceOfClothes.Style.Evening),
                    Map.entry("вечірньому",    PieceOfClothes.Style.Evening),
                    Map.entry("вечірнім",      PieceOfClothes.Style.Evening),
                    Map.entry("коктейльний",   PieceOfClothes.Style.Evening),
                    Map.entry("нарядний",      PieceOfClothes.Style.Evening),
                    Map.entry("святковий",     PieceOfClothes.Style.Evening),
                    Map.entry("вихідний",      PieceOfClothes.Style.Evening),
                    Map.entry("evening",       PieceOfClothes.Style.Evening)
            );


}
