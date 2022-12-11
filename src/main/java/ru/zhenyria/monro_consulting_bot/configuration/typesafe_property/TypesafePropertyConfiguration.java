package ru.zhenyria.monro_consulting_bot.configuration.typesafe_property;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Configuration for TypeSafe configuration using
 */
@Configuration
@PropertySource(factory = TypesafePropertySourceFactory.class, value = "/telegram.conf")
public class TypesafePropertyConfiguration {
}
