package Guru.QA;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SelenideFilesTest {



  @Test
  void selenideDownloadTest() throws Exception {
    open("https://github.com/junit-team/junit5/blob/main/README.md");
    File downloadedFile = $("#raw-url").download();
    try (InputStream is = new FileInputStream(downloadedFile)) {
      byte[] bytes = is.readAllBytes();
      String textContent = new String(bytes, StandardCharsets.UTF_8);
      assertThat(textContent).contains("This repository is the home of the next generation of JUnit, _JUnit 5_");
    }
  }

  @Test
  void selenideuploadFile() {
    open("https://fineuploader.com/demos.html");
    $("input[type='file']").uploadFromClasspath("test/podpis.png");
    $("div.qq-file-info").shouldHave(text("podpis.png"));
  }
}

