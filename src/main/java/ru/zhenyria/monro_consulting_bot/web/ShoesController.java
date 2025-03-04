package ru.zhenyria.monro_consulting_bot.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.zhenyria.monro_consulting_bot.dto.request.ShoesCreateRequestDto;
import ru.zhenyria.monro_consulting_bot.service.ShoesService;

@Validated
@RestController
@RequestMapping(value = "/shoes", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ShoesController {
    private final ShoesService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody @Valid ShoesCreateRequestDto createRequest) {
        service.create(createRequest);
    }

    @DeleteMapping("/{vendorCode}")
    public void delete(@PathVariable("vendorCode") String vendorCode) {
        service.delete(vendorCode);
    }
}
