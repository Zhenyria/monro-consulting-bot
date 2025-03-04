package ru.zhenyria.monro_consulting_bot.util;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.zhenyria.monro_consulting_bot.dto.response.ShoesModelDto;
import ru.zhenyria.monro_consulting_bot.model.ShoesModel;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ShoesModelMapper {

    ShoesModelDto mapToDto(ShoesModel shoesModel);

    List<ShoesModelDto> mapToDtos(List<ShoesModel> shoesModel);
}
