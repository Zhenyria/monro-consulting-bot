package ru.zhenyria.monro_consulting_bot.util;

import lombok.val;
import org.springframework.stereotype.Component;
import ru.zhenyria.monro_consulting_bot.dto.response.ScalesDto;
import ru.zhenyria.monro_consulting_bot.model.Scale;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
public class ScaleMapper {
    public ScalesDto mapToDto(List<Scale> scales) {
        var sizes = new ArrayList<Integer>();
        var volumes = new ArrayList<Integer>();

        var addedSizes = new HashSet<Integer>();
        var addedVolumes = new HashSet<Integer>();

        for (var scale : scales) {
            val size = scale.getSize();
            val volume = scale.getVolume();

            if (!addedSizes.contains(size)) {
                sizes.add(scale.getSize());
                addedSizes.add(size);
            }

            if (!addedVolumes.contains(volume)) {
                volumes.add(scale.getVolume());
                addedVolumes.add(volume);
            }
        }

        return new ScalesDto(sizes, volumes);
    }
}
