package ru.zhenyria.monro_consulting_bot.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.zhenyria.monro_consulting_bot.dto.ShoesModelsDto;
import ru.zhenyria.monro_consulting_bot.service.ShoesModelService;

import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/shoes-models", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ShoesModelController {
    private final ShoesModelService service;

    @GetMapping
    public ShoesModelsDto getAll() {
        return new ShoesModelsDto(service.getAll()
                                         .stream()
                                         .map(shoesModel -> new ShoesModelsDto.ShoesModelDto(
                                                 shoesModel.getName(),
                                                 shoesModel.getLocalizedName()
                                         ))
                                         .collect(Collectors.toList()));
    }
}
