package ru.zhenyria.monro_consulting_bot.dto.response;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class SeasonsDto implements Serializable {
    List<SeasonDto> seasons;

    @Value
    public static class SeasonDto implements Serializable {
        String name;
        String localizedName;
    }
}
