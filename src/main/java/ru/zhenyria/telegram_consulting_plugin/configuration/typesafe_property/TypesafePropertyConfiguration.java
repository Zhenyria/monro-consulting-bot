package ru.zhenyria.telegram_consulting_plugin.configuration.typesafe_property;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Configuration for TypeSafe configuration using
 */
@Configuration
@PropertySource(factory = TypesafePropertySourceFactory.class, value = "/telegram.conf")
public class TypesafePropertyConfiguration {
}
