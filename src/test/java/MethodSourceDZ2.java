import Data.Locale;
import com.codeborne.selenide.CollectionCondition;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class MethodSourceDZ2 {

  static Stream<Arguments> localDataProvider() {
    return Stream.of(
            Arguments.of(Locale.RU, Arrays.asList("Продукт\n" +
                    "Решения\n" +
                    "Ресурсы\n" +
                    "Предприятия\n" +
                    "Тарифы")),
            Arguments.of(Locale.EN, Arrays.asList("Product\n" +
                    "Solutions\n" +
                    "Resources\n" +
                    "Enterprise\n" +
                    "Pricing"))
    );
  }

  @MethodSource("localDataProvider")
  @ParameterizedTest(name = "Для локали {0} отображаются кнопки {1}")
  @Tag("BLOCKER")
  void localetest2(
          Locale locale,
          List<String> buttons
  ) {
    open("https://miro.com/ru/");
    $(".ghharZ").click();
    $("[data-locale="+locale.getDesc()+"]").click();
    $$(".fSddvK").shouldHave(CollectionCondition.texts(buttons));
  }

}



