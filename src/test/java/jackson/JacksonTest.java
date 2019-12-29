package jackson;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

class Person {
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private
    Date birthday;

    @JsonProperty("mails")
    private
    List<String> emails;

    @Override
    public String toString() {
        return "name:" + name + ", birthday: " + birthday + ", emails: " + emails;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    Date getBirthday() {
        return birthday;
    }

    void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    List<String> getEmails() {
        return emails;
    }

    void setEmails(List<String> emails) {
        this.emails = emails;
    }
}

public class JacksonTest {
    @Test
    public void testJsonNode() throws Exception {
        String jsonString = "{\"name\": \"hatlonely\" /* comment */, \"birthday\": \"2018-03-18 15:26:37\", \"mails\": [\"hatlonely@foxmail.com\", \"hatlonely@gmail.com\"]}";

        JsonFactory jsonFactory = new JsonFactory();
        jsonFactory.enable(Feature.ALLOW_COMMENTS);
        ObjectMapper objectMapper = new ObjectMapper(jsonFactory);
        JsonNode node = objectMapper.readTree(jsonString);

        assertThat(node.path("name").asText(), equalTo("hatlonely"));
        assertThat(node.path("birthday").asText(), equalTo("2018-03-18 15:26:37"));
        assertThat(node.path("mails").size(), equalTo(2));
        assertThat(node.path("mails").path(0).asText(), equalTo("hatlonely@foxmail.com"));
        assertThat(node.path("mails").path(1).asText(), equalTo("hatlonely@gmail.com"));
    }

    @Test
    public void testUnmarshal() throws Exception {
        String jsonString = "{\"name\": \"hatlonely\", \"birthday\": \"2018-03-18 15:26:37\", \"mails\": [\"hatlonely@foxmail.com\", \"hatlonely@gmail.com\"]}";

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Person person = objectMapper.readValue(jsonString, Person.class);

        assertThat(person.getName(), equalTo("hatlonely"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        assertThat(person.getBirthday(), equalTo(dateFormat.parse("2018-03-18 15:26:37")));
        assertThat(person.getEmails(), equalTo(Arrays.asList("hatlonely@foxmail.com", "hatlonely@gmail.com")));
    }

    @Test
    public void testMarshal() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        Person person = new Person();
        person.setName("hatlonely");
        person.setBirthday(dateFormat.parse("2018-03-18 15:26:37"));
        person.setEmails(Arrays.asList("hatlonely@foxmail.com", "hatlonely@gmail.com"));

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(person);

        assertThat(jsonString, equalTo(
                "{\"name\":\"hatlonely\",\"birthday\":\"2018-03-18 03:26:37\",\"mails\":[\"hatlonely@foxmail.com\",\"hatlonely@gmail.com\"]}"));
    }
}
