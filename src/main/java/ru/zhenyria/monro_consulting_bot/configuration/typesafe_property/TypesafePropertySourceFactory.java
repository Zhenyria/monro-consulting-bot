package ru.zhenyria.monro_consulting_bot.configuration.typesafe_property;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.val;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.util.Objects;
import java.util.Optional;

public class TypesafePropertySourceFactory implements PropertySourceFactory {

    @Override
    public @Nonnull PropertySource<?> createPropertySource(@Nullable String name, @Nonnull EncodedResource resource) {
        val fileName =
                Optional.of(resource)
                        .map(EncodedResource::getResource)
                        .map(Resource::getFilename)
                        .orElse(null);

        Objects.requireNonNull(fileName);

        Config config = ConfigFactory.load(fileName).resolve();

        val configName = name == null ? "typesafe" : name;
        return new TypesafeConfigPropertySource(configName, config);
    }
}
