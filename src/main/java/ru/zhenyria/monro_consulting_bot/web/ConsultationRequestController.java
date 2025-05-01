package ru.zhenyria.monro_consulting_bot.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.zhenyria.monro_consulting_bot.service.ConsultationRequestService;

@Validated
@RestController
@RequestMapping(value = "/consultation-requests", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ConsultationRequestController {
    private final ConsultationRequestService service;

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        service.remove(id);
    }
}
