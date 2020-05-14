package com.iioannou.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.iioannou.app.pojo.Person;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author ioannou
 */

public class TestJsonToPojo {

    private static ObjectMapper mapper;

    private static JsonObject jsonObject;

    private static final Logger logger =Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @BeforeClass
    public static void prepare() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);


        jsonObject = Json.createObjectBuilder().add("name", "Isidoros")
                         .add("age", 29)
                         .add("nationality", "Greek").build();

        logger.log(Level.INFO, jsonObject.toString());
    }

    @Test
    public void TestParse1() throws JsonProcessingException {

        Person person = mapper.readValue(jsonObject.toString(), Person.class);

        Assert.assertNotNull(person);
        Assert.assertEquals("Isidoros", person.getName());
    }


    @Test
    public void TestParse2() {

        String person = mapper.convertValue(jsonObject.toString(), String.class) ;
        Assert.assertNotNull(person);
    }

    @Test
    public void TestParse3() throws JsonProcessingException {

        Object person = mapper.readValue(jsonObject.toString(), Object.class) ;
        Assert.assertNotNull(person);
    }

    @Test
    public void TestParse4() {

        final Map<String, Object> person = mapper.convertValue(jsonObject, Map.class) ;
        Assert.assertNotNull(person);
        person.forEach( (k, v) -> logger.log(Level.INFO, person.get(k).toString()));
        Assert.assertNotEquals("Isidoros",  person.get("name"));
    }


    @Test
    public void TestParse5() throws JsonProcessingException {

        JsonNode node  = mapper.readTree(jsonObject.toString());
        Person person = mapper.convertValue(node, Person.class);
        Assert.assertNotNull(person);
        Assert.assertEquals("Isidoros",  person.getName());
    }
}
