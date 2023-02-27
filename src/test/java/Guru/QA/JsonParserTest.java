package Guru.QA;

import static org.assertj.core.api.Assertions.assertThat;
import Guru.QA.model.ForDZJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.junit.jupiter.api.Test;


  class JsonParserTest {

    ClassLoader classLoader = JsonParserTest.class.getClassLoader();

    @Test
    void jsonParserTest() throws Exception {
      ObjectMapper mapper = new ObjectMapper();

      try (
              InputStream resource = classLoader.getResourceAsStream("test/glossary.json");
              InputStreamReader reader = new InputStreamReader(resource)
      ) {

        ForDZJson forDZJson = mapper.readValue(reader, ForDZJson.class);

        assertThat(forDZJson.name).isEqualTo("Bob");
        assertThat(forDZJson.salary).isEqualTo(100000);
        assertThat(forDZJson.married).isTrue();
        assertThat(forDZJson.hobbies[1]).isEqualTo("rock");

      }
    }
  }

