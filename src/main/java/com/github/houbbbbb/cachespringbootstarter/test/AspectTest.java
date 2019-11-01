package com.github.houbbbbb.cachespringbootstarter.test;

import com.github.houbbbbb.cachespringbootstarter.annotations.MapCacheAnnotation;
import org.springframework.stereotype.Component;


@Component
public class AspectTest {
    @MapCacheAnnotation(name = "name", key = "#me", value = true)
    public String mapCacheTest(String me) {
        return "hello good, this is " + me;
    }
}
