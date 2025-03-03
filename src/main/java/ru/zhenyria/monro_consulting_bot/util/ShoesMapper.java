package ru.zhenyria.monro_consulting_bot.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.zhenyria.monro_consulting_bot.dto.response.ShoesDto;
import ru.zhenyria.monro_consulting_bot.model.Shoes;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ShoesMapper {

    @Mapping(target = "seasonName", source = "season.name")
    @Mapping(target = "modelName", source = "model.name")
    ShoesDto mapToDto(Shoes shoes);

    List<ShoesDto> mapToDtos(List<Shoes> shoes);
}
