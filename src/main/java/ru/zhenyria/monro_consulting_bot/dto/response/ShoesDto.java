package ru.zhenyria.monro_consulting_bot.dto.response;

import java.io.Serializable;
import java.util.List;

public record ShoesDto(Integer id,
                       String vendorCode,
                       String url,
                       String name,
                       String description,
                       String imageUrl,
                       List<Integer> sizes,
                       Integer volume,
                       String seasonName,
                       String modelName) implements Serializable {
}
