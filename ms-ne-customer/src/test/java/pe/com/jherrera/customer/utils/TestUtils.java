package pe.com.jherrera.customer.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class TestUtils {

    public static <T> T generateObject(String path, Class<T> clazz) throws IOException {
        return new ObjectMapper().readValue(
                new ClassPathResource(path).getInputStream(), clazz);
    }
}
