package ru.zhenyria.monro_consulting_bot.dto.response;

import java.io.Serializable;
import java.util.List;

public record ShoesDto(String vendorCode,
                       String url,
                       String name,
                       String description,
                       String imageUrl,
                       List<Scale> scales,
                       String seasonName,
                       String modelName) implements Serializable {

    public record Scale(Integer size, Integer volume) implements Serializable {
    }
}
