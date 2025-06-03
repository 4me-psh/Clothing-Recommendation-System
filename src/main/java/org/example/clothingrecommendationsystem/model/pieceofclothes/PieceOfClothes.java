package org.example.clothingrecommendationsystem.model.pieceofclothes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.clothingrecommendationsystem.model.basemodel.BaseModel;
import org.example.clothingrecommendationsystem.model.user.User;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PieceOfClothes extends BaseModel {
    private Long id;
    private User user;
    private String name;
    private String color;
    private String material;
    private List<Style> styles;
    private String pathToPhoto;
    private String pathToRemovedBgPhoto;
    private PieceCategory pieceCategory;
    private List<TemperatureCategory> temperatureCategories;
    private List<String> characteristics;
    private Boolean useRemovedBg;

    public enum Style {
        Sporty, Casual, Business, Evening
    }

    public enum PieceCategory {
        Single, Outerlayer, Innerlayer, Bottom, Headwear, Footwear, Accessories
    }

    public enum TemperatureCategory {
        ExtremeHeat("30 and above"),
        Hot("25 to 29"),
        Warm("20 to 24"),
        MildWarm("15 to 19"),
        Cool("10 to 14"),
        Cold("5 to 9"),
        LightFrost("0 to 4"),
        ChillyFrost("-5 to -1"),
        Frost("-10 to -6"),
        SevereFrost("-19 to -11"),
        ExtremeCold("-20 and below");

        private final String range;

        TemperatureCategory(String range) {
            this.range = range;
        }

        public String getRange() {
            return range.toLowerCase();
        }
    }
}
