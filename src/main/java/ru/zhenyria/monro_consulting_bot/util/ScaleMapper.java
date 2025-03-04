package ru.zhenyria.monro_consulting_bot.util;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.zhenyria.monro_consulting_bot.dto.response.ScaleDto;
import ru.zhenyria.monro_consulting_bot.model.Scale;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ScaleMapper {

    ScaleDto mapToDto(Scale scale);

    List<ScaleDto> mapToDtos(List<Scale> scales);
}
