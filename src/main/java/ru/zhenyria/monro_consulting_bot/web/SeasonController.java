package ru.zhenyria.monro_consulting_bot.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.zhenyria.monro_consulting_bot.dto.response.SeasonsDto;
import ru.zhenyria.monro_consulting_bot.service.SeasonService;

import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/seasons", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class SeasonController {
    private final SeasonService service;

    @GetMapping
    public SeasonsDto getAll() {
        return new SeasonsDto(service.getAll()
                                     .stream()
                                     .map(season -> new SeasonsDto.SeasonDto(season.getName(),
                                                                             season.getLocalizedName()))
                                     .collect(Collectors.toList()));
    }
}
