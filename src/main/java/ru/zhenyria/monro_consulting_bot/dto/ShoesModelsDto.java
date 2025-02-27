package ru.zhenyria.monro_consulting_bot.dto;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class ShoesModelsDto implements Serializable {
    List<ShoesModelDto> shoesModels;

    @Value
    public static class ShoesModelDto implements Serializable {
        String name;
        String localizedName;
    }
}
