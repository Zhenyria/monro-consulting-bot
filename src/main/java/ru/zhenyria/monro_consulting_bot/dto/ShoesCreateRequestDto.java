package ru.zhenyria.monro_consulting_bot.dto;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class ShoesCreateRequestDto implements Serializable {
    String vendorCode;
    String url;
    String name;
    String description;
    String imageUrl;
    List<SizeId> sizes;
    String seasonName;
    String modelName;

    @Value
    public static class SizeId implements Serializable {
        Integer size;
        Integer volume;
    }
}
