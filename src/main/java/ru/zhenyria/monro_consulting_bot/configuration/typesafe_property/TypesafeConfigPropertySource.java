package ru.zhenyria.monro_consulting_bot.configuration.typesafe_property;

import com.typesafe.config.Config;
import org.springframework.core.env.PropertySource;
import org.springframework.lang.Nullable;

import java.util.Objects;

public class TypesafeConfigPropertySource extends PropertySource<Config> {
    public TypesafeConfigPropertySource(String name, Config source) {
        super(name, source);
    }

    @Override
    public Object getProperty(@Nullable String path) {
        if (Objects.isNull(path) || path.contains("[") || path.contains(":")) {
            return null;
        }

        return super.source.hasPath(path)
                ? super.source.getAnyRef(path)
                : null;
    }
}
