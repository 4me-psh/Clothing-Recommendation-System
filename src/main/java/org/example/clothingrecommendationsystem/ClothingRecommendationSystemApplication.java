package org.example.clothingrecommendationsystem;

import org.example.clothingrecommendationsystem.model.person.IPersonOrchestrator;
import org.example.clothingrecommendationsystem.model.person.Person;
import org.example.clothingrecommendationsystem.model.pieceofclothes.IPieceOfClothesOrchestrator;
import org.example.clothingrecommendationsystem.model.pieceofclothes.PieceOfClothes;
import org.example.clothingrecommendationsystem.model.user.IUserOrchestrator;
import org.example.clothingrecommendationsystem.model.user.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

import static org.example.clothingrecommendationsystem.model.pieceofclothes.PieceOfClothes.PieceCategory.Innerlayer;
import static org.example.clothingrecommendationsystem.model.pieceofclothes.PieceOfClothes.Style.Casual;
import static org.example.clothingrecommendationsystem.model.pieceofclothes.PieceOfClothes.TemperatureCategory.Warm;

@SpringBootApplication
public class ClothingRecommendationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClothingRecommendationSystemApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(IUserOrchestrator userOrchestrator,
                                        IPersonOrchestrator personOrchestrator,
                                        IPieceOfClothesOrchestrator pieceOrchestrator) {
        return args -> {
            User john = userOrchestrator.create(new User(null, "John",
                    "password", "my.new@gmail.com"));
            User jane = userOrchestrator.create(new User(null, "Jane",
                    "real_password", "her.old@gmail.com"));
            User string = userOrchestrator.create(new User(null, "string",
                    "string", "string"));
            Person john_person = personOrchestrator.create(
                    new Person(null, john, Person.Gender.MALE, Person.SkinTone.MEDIUMLIGHT,
                    "коричневий", 185.2, 21));
            Person jane_person = personOrchestrator.create(
                    new Person(null, jane, Person.Gender.FEMALE, Person.SkinTone.MEDIUMDARK,
                            "блонд", 160.2, 35));
            Person string_person = personOrchestrator.create(
                    new Person(null, string, Person.Gender.MALE, Person.SkinTone.LIGHT,
                            "рижий", 190.2, 17));
            pieceOrchestrator.create(
                    new PieceOfClothes(null, string, "сорочка", "темно-синій", "бавовна",
                            List.of(Casual), "", "", Innerlayer, List.of(Warm),
                            List.of(" короткі рукава"), false));

            pieceOrchestrator.create(new PieceOfClothes(null, string, "футболка-оверсайз", "білий", "бавовна",
                    List.of(PieceOfClothes.Style.Casual, PieceOfClothes.Style.Sporty), "", "",
                    PieceOfClothes.PieceCategory.Innerlayer,
                    List.of(PieceOfClothes.TemperatureCategory.MildWarm,
                            PieceOfClothes.TemperatureCategory.Warm,
                            PieceOfClothes.TemperatureCategory.Hot), List.of("прямий крій"), false));

            pieceOrchestrator.create(new PieceOfClothes(null, string, "лонгслів", "чорний", "бавовна",
                    List.of(PieceOfClothes.Style.Sporty, PieceOfClothes.Style.Casual), "", "",
                    PieceOfClothes.PieceCategory.Innerlayer,
                    List.of(PieceOfClothes.TemperatureCategory.Cool,
                            PieceOfClothes.TemperatureCategory.MildWarm), List.of("сірі смужки"), false));

            pieceOrchestrator.create(new PieceOfClothes(null, string, "сорочка", "темно-синій", "бавовна",
                    List.of(PieceOfClothes.Style.Casual, PieceOfClothes.Style.Business), "", "",
                    PieceOfClothes.PieceCategory.Innerlayer,
                    List.of(PieceOfClothes.TemperatureCategory.MildWarm,
                            PieceOfClothes.TemperatureCategory.Warm), List.of("червоні ґудзики"), false));

            pieceOrchestrator.create(new PieceOfClothes(null, string, "водолазка", "бордовий", "шерсть",
                    List.of(PieceOfClothes.Style.Business, PieceOfClothes.Style.Casual), "", "",
                    PieceOfClothes.PieceCategory.Innerlayer,
                    List.of(PieceOfClothes.TemperatureCategory.Cool,
                            PieceOfClothes.TemperatureCategory.Cold,
                            PieceOfClothes.TemperatureCategory.ChillyFrost), List.of("тонка резинка","чорні вставки"), false));

            pieceOrchestrator.create(new PieceOfClothes(null, string, "світшот", "кремовий", "фліс",
                    List.of(PieceOfClothes.Style.Sporty, PieceOfClothes.Style.Casual), "", "",
                    PieceOfClothes.PieceCategory.Innerlayer,
                    List.of(PieceOfClothes.TemperatureCategory.Cool,
                            PieceOfClothes.TemperatureCategory.MildWarm), List.of("світло-бежеві рукави"), false));

            pieceOrchestrator.create(new PieceOfClothes(null, string, "кардиган", "оливковий", "шерсть",
                    List.of(PieceOfClothes.Style.Casual, PieceOfClothes.Style.Business), "", "",
                    PieceOfClothes.PieceCategory.Innerlayer,
                    List.of(PieceOfClothes.TemperatureCategory.Cold,
                            PieceOfClothes.TemperatureCategory.ChillyFrost,
                            PieceOfClothes.TemperatureCategory.Frost), List.of("коричневі ґудзики"), false));

            pieceOrchestrator.create(new PieceOfClothes(null, string, "худі", "сірий", "бавовна",
                    List.of(PieceOfClothes.Style.Sporty, PieceOfClothes.Style.Casual), "", "",
                    Innerlayer,
                    List.of(PieceOfClothes.TemperatureCategory.Cool,
                            PieceOfClothes.TemperatureCategory.MildWarm), List.of("чорний шнурок"), false));

            pieceOrchestrator.create(new PieceOfClothes(null, string, "плаття-футляр", "чорний", "віскоза",
                    List.of(PieceOfClothes.Style.Evening, PieceOfClothes.Style.Business), "", "",
                    PieceOfClothes.PieceCategory.Single,
                    List.of(PieceOfClothes.TemperatureCategory.MildWarm,
                            PieceOfClothes.TemperatureCategory.Warm), List.of("срібляста блискавка"), false));

            pieceOrchestrator.create(new PieceOfClothes(null, string, "джинси", "білий", "денім",
                    List.of(PieceOfClothes.Style.Casual), "", "",
                    PieceOfClothes.PieceCategory.Bottom,
                    List.of(PieceOfClothes.TemperatureCategory.Cool,
                            PieceOfClothes.TemperatureCategory.MildWarm,
                            PieceOfClothes.TemperatureCategory.Warm), List.of("сині нитки"), false));

            pieceOrchestrator.create(new PieceOfClothes(null, string, "штани", "темно-синій", "вовна",
                    List.of(PieceOfClothes.Style.Business, PieceOfClothes.Style.Casual), "", "",
                    PieceOfClothes.PieceCategory.Bottom,
                    List.of(PieceOfClothes.TemperatureCategory.Cool,
                            PieceOfClothes.TemperatureCategory.MildWarm), List.of("срібний лампас"), false));

            pieceOrchestrator.create(new PieceOfClothes(null, string, "спідниця-олівець", "бежевий", "льон",
                    List.of(PieceOfClothes.Style.Business), "", "",
                    PieceOfClothes.PieceCategory.Bottom,
                    List.of(PieceOfClothes.TemperatureCategory.MildWarm,
                            PieceOfClothes.TemperatureCategory.Warm), List.of("перлинний ремінь"), false));

            pieceOrchestrator.create(new PieceOfClothes(null, string, "легінси", "чорний", "еластан",
                    List.of(PieceOfClothes.Style.Sporty, PieceOfClothes.Style.Casual), "", "",
                    PieceOfClothes.PieceCategory.Bottom,
                    List.of(PieceOfClothes.TemperatureCategory.Cool,
                            PieceOfClothes.TemperatureCategory.Cold), List.of("сірі вставки"), false));

            pieceOrchestrator.create(new PieceOfClothes(null, string, "парка", "оливковий", "плащівка",
                    List.of(PieceOfClothes.Style.Casual, PieceOfClothes.Style.Sporty), "", "",
                    PieceOfClothes.PieceCategory.Outerlayer,
                    List.of(PieceOfClothes.TemperatureCategory.ChillyFrost,
                            PieceOfClothes.TemperatureCategory.Frost), List.of("помаранчевий шнурок"), false));

            pieceOrchestrator.create(new PieceOfClothes(null, string, "куртка", "синій", "денім",
                    List.of(PieceOfClothes.Style.Casual), "", "",
                    PieceOfClothes.PieceCategory.Outerlayer,
                    List.of(PieceOfClothes.TemperatureCategory.Cool,
                            PieceOfClothes.TemperatureCategory.MildWarm,
                            PieceOfClothes.TemperatureCategory.Warm), List.of("потертості"), false));

            pieceOrchestrator.create(new PieceOfClothes(null, string, "пальто", "темно-сірий", "вовна",
                    List.of(PieceOfClothes.Style.Business, PieceOfClothes.Style.Casual), "", "",
                    PieceOfClothes.PieceCategory.Outerlayer,
                    List.of(PieceOfClothes.TemperatureCategory.Cold,
                            PieceOfClothes.TemperatureCategory.ChillyFrost), List.of("чорний пояс"), false));

            pieceOrchestrator.create(new PieceOfClothes(null, string, "пуховик", "білий", "нейлон",
                    List.of(PieceOfClothes.Style.Sporty, PieceOfClothes.Style.Casual), "", "",
                    PieceOfClothes.PieceCategory.Outerlayer,
                    List.of(PieceOfClothes.TemperatureCategory.SevereFrost,
                            PieceOfClothes.TemperatureCategory.ExtremeCold), List.of("сині вставки"), false));

            pieceOrchestrator.create(new PieceOfClothes(null, string, "кросівки", "білий", "шкіра",
                    List.of(PieceOfClothes.Style.Sporty, PieceOfClothes.Style.Casual), "", "",
                    PieceOfClothes.PieceCategory.Footwear,
                    List.of(PieceOfClothes.TemperatureCategory.MildWarm,
                            PieceOfClothes.TemperatureCategory.Warm,
                            PieceOfClothes.TemperatureCategory.Hot), List.of("червоні смужки"), false));

            pieceOrchestrator.create(new PieceOfClothes(null, string, "дербі", "чорний", "шкіра",
                    List.of(PieceOfClothes.Style.Business), "", "",
                    PieceOfClothes.PieceCategory.Footwear,
                    List.of(PieceOfClothes.TemperatureCategory.Cool,
                            PieceOfClothes.TemperatureCategory.MildWarm), List.of("коричнева підошва"), false));

            pieceOrchestrator.create(new PieceOfClothes(null, string, "челсі", "коричневий", "замша",
                    List.of(PieceOfClothes.Style.Casual, PieceOfClothes.Style.Business), "", "",
                    PieceOfClothes.PieceCategory.Footwear,
                    List.of(PieceOfClothes.TemperatureCategory.Cold,
                            PieceOfClothes.TemperatureCategory.ChillyFrost), List.of("сірі еластичні вставки"), false));

            pieceOrchestrator.create(new PieceOfClothes(null, string, "гумові чоботи", "оливковий", "каучук",
                    List.of(PieceOfClothes.Style.Sporty, PieceOfClothes.Style.Casual), "", "",
                    PieceOfClothes.PieceCategory.Footwear,
                    List.of(PieceOfClothes.TemperatureCategory.LightFrost,
                            PieceOfClothes.TemperatureCategory.Cool), List.of("чорний кант"), false));

            pieceOrchestrator.create(new PieceOfClothes(null, string, "ремінь", "коричневий", "шкіра",
                    List.of(PieceOfClothes.Style.Casual, PieceOfClothes.Style.Business), "", "",
                    PieceOfClothes.PieceCategory.Accessories,
                    List.of(PieceOfClothes.TemperatureCategory.Cool,
                            PieceOfClothes.TemperatureCategory.MildWarm,
                            PieceOfClothes.TemperatureCategory.Warm), List.of("бронзова пряжка"), false));

            pieceOrchestrator.create(new PieceOfClothes(null, string, "хустка", "червоний", "шовк",
                    List.of(PieceOfClothes.Style.Evening, PieceOfClothes.Style.Casual), "", "",
                    PieceOfClothes.PieceCategory.Accessories,
                    List.of(PieceOfClothes.TemperatureCategory.MildWarm,
                            PieceOfClothes.TemperatureCategory.Warm), List.of("помаранчевий орнамент"), false));

            pieceOrchestrator.create(new PieceOfClothes(null, string, "годинник", "сріблястий", "метал",
                    List.of(PieceOfClothes.Style.Business), "", "",
                    PieceOfClothes.PieceCategory.Accessories,
                    List.of(PieceOfClothes.TemperatureCategory.MildWarm,
                            PieceOfClothes.TemperatureCategory.Warm,
                            PieceOfClothes.TemperatureCategory.Hot,
                            PieceOfClothes.TemperatureCategory.ExtremeHeat), List.of("чорний циферблат"), false));

            pieceOrchestrator.create(new PieceOfClothes(null, string, "рюкзак", "чорний", "нейлон",
                    List.of(PieceOfClothes.Style.Sporty, PieceOfClothes.Style.Casual), "", "",
                    PieceOfClothes.PieceCategory.Accessories,
                    List.of(PieceOfClothes.TemperatureCategory.Cool,
                            PieceOfClothes.TemperatureCategory.MildWarm,
                            PieceOfClothes.TemperatureCategory.Warm), List.of("білі блискавки"), false));

            pieceOrchestrator.create(new PieceOfClothes(null, string, "бейсболка", "синій", "бавовна",
                    List.of(PieceOfClothes.Style.Sporty, PieceOfClothes.Style.Casual), "", "",
                    PieceOfClothes.PieceCategory.Headwear,
                    List.of(PieceOfClothes.TemperatureCategory.Warm,
                            PieceOfClothes.TemperatureCategory.Hot,
                            PieceOfClothes.TemperatureCategory.ExtremeHeat), List.of("білий логотип"), false));

            pieceOrchestrator.create(new PieceOfClothes(null, string, "шапка-рибацька", "сірий", "акрил",
                    List.of(PieceOfClothes.Style.Casual), "", "",
                    PieceOfClothes.PieceCategory.Headwear,
                    List.of(PieceOfClothes.TemperatureCategory.Cold,
                            PieceOfClothes.TemperatureCategory.ChillyFrost,
                            PieceOfClothes.TemperatureCategory.Frost), List.of("чорний відворот"), false));

            pieceOrchestrator.create(new PieceOfClothes(null, string, "біні", "червоний", "шерсть",
                    List.of(PieceOfClothes.Style.Casual), "", "",
                    PieceOfClothes.PieceCategory.Headwear,
                    List.of(PieceOfClothes.TemperatureCategory.SevereFrost,
                            PieceOfClothes.TemperatureCategory.ExtremeCold), List.of("білий помпон"), false));

            pieceOrchestrator.create(new PieceOfClothes(null, string, "панама", "білий", "бавовна",
                    List.of(PieceOfClothes.Style.Sporty, PieceOfClothes.Style.Casual), "", "",
                    PieceOfClothes.PieceCategory.Headwear,
                    List.of(PieceOfClothes.TemperatureCategory.Hot,
                            PieceOfClothes.TemperatureCategory.ExtremeHeat), List.of("сині смуги"), false));

            pieceOrchestrator.create(new PieceOfClothes(null, string, "рукавички", "сірий", "вовна",
                    List.of(PieceOfClothes.Style.Casual, PieceOfClothes.Style.Sporty), "", "",
                    PieceOfClothes.PieceCategory.Accessories,
                    List.of(PieceOfClothes.TemperatureCategory.Frost,
                            PieceOfClothes.TemperatureCategory.SevereFrost,
                            PieceOfClothes.TemperatureCategory.ExtremeCold), List.of("чорні кнопки"), false));

            pieceOrchestrator.create(new PieceOfClothes(null, string, "балаклава", "чорний", "фліс",
                    List.of(PieceOfClothes.Style.Sporty, PieceOfClothes.Style.Casual), "", "",
                    PieceOfClothes.PieceCategory.Headwear,
                    List.of(PieceOfClothes.TemperatureCategory.ExtremeCold,
                            PieceOfClothes.TemperatureCategory.SevereFrost), List.of("сірі вставки"), false));

        };

    }

}
