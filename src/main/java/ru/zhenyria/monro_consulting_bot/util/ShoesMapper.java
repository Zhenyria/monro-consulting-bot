package ru.zhenyria.monro_consulting_bot.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Qualifier;
import ru.zhenyria.monro_consulting_bot.dto.response.ShoesDto;
import ru.zhenyria.monro_consulting_bot.model.Scale;
import ru.zhenyria.monro_consulting_bot.model.Shoes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ShoesMapper {

    @Mapping(target = "seasonName", source = "season.name")
    @Mapping(target = "modelName", source = "model.name")
    @Mapping(target = "sizes", source = "scales", qualifiedBy = SizesMapper.class)
    @Mapping(target = "volume", source = "scales", qualifiedBy = VolumeMapper.class)
    ShoesDto mapToDto(Shoes shoes);

    List<ShoesDto> mapToDtos(List<Shoes> shoes);

    @SizesMapper
    default List<Integer> mapToSizes(List<Scale> scales) {
        return Optional.ofNullable(scales)
                       .stream()
                       .flatMap(List::stream)
                       .map(Scale::getSize)
                       .sorted()
                       .toList();
    }

    @VolumeMapper
    default Integer mapToVolume(List<Scale> scales) {
        return Optional.ofNullable(scales)
                       .stream()
                       .flatMap(List::stream)
                       .map(Scale::getVolume)
                       .findFirst()
                       .orElse(null);
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.CLASS)
    @interface SizesMapper {
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.CLASS)
    @interface VolumeMapper {
    }
}
