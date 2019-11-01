package com.github.houbbbbb.cachespringbootstarter.autoconfiguration;

import com.github.houbbbbb.cachespringbootstarter.aspects.MapCacheAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CacheAutoConfiguration {
    @Bean
    public MapCacheAspect getMapCacheAspect() {
        return new MapCacheAspect();
    }
}
