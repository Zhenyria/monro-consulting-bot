package ru.zhenyria.monro_consulting_bot.dto.response;

import java.io.Serializable;
import java.util.List;

public record ScalesDto(List<Integer> sizes, List<Integer> volumes) implements Serializable {
}
