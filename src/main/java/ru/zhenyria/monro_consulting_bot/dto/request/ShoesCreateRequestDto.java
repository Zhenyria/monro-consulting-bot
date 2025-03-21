package ru.zhenyria.monro_consulting_bot.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;

public record ShoesCreateRequestDto(@NotBlank String vendorCode,
                                    @NotBlank String url,
                                    @NotBlank String name,
                                    @NotBlank String description,
                                    @NotBlank String imageUrl,
                                    List<@Valid @NotNull Scale> scales,
                                    @NotBlank String seasonName,
                                    @NotBlank String modelName) implements Serializable {

    public record Scale(@NotNull Integer size, @NotNull Integer volume) implements Serializable {
    }
}
