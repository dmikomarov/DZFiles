import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.open;

public class ParamTest {
  @BeforeAll
  static void beforeAll() {
    Configuration.browserSize = "1920x1080";
    Configuration.timeout = 10000;
    open("https://aquaterra.pro/");
  }

  @ValueSource (strings = {
          "Клапан nelson" , "Бустерный насос", "Клапан Nelson 800V"
  })
  @ParameterizedTest (name = "Наличие в результатах поиска по ключу: {0}")
  void differentValues(String arg) {
    $("[id=search_input]").setValue(arg).pressEnter();
    $$(".product-title").first().
            shouldHave(text(arg));
  }

  @CsvFileSource(resources = "/testData.csv")
  @ParameterizedTest(name = "В результате поиска продукции бренда {0} должен появиться раздел {1}")
  @Tags({@Tag("CRITICAL"), @Tag("UI_TEST")})
  void categorySearchTest(String productBrandName, String productCategory) {
    $("[id=search_input]").setValue(productBrandName).pressEnter();
    $(".ty-product-filters__item-more").shouldHave(text(productCategory));
  }

}
