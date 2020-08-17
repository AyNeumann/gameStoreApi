package com.aymeric.gamestore.configuration;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomCacheEventLogger implements CacheEventListener<Object, Object> {
    
    /** Logback logger reference. */
    private static final Logger logger = LoggerFactory.getLogger(CustomCacheEventLogger.class);

    @Override
    public void onEvent(CacheEvent<?, ?> cacheEvent) {
        logger.error("**** EVENT ON CACHE ****");
        logger.error("custom Caching event {} {} {} {} ", cacheEvent.getType(),cacheEvent.getKey(),cacheEvent.getOldValue(),cacheEvent.getNewValue());
    }

}
