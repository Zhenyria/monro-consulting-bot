package ru.zhenyria.monro_consulting_bot.util;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.zhenyria.monro_consulting_bot.dto.response.SeasonDto;
import ru.zhenyria.monro_consulting_bot.model.Season;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SeasonMapper {

    SeasonDto mapToDto(Season season);

    List<SeasonDto> mapToDtos(List<Season> seasons);
}
