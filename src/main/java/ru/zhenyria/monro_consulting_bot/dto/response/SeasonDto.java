package ru.zhenyria.monro_consulting_bot.dto.response;

import java.io.Serializable;

public record SeasonDto(String name, String localizedName) implements Serializable {
}
