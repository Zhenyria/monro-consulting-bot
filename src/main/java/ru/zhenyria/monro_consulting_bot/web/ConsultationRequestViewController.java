package ru.zhenyria.monro_consulting_bot.web;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.zhenyria.monro_consulting_bot.dto.inner.WishedShoesDto;
import ru.zhenyria.monro_consulting_bot.dto.response.ConsultationRequestDto;
import ru.zhenyria.monro_consulting_bot.model.ConsultationRequest;
import ru.zhenyria.monro_consulting_bot.model.Customer;
import ru.zhenyria.monro_consulting_bot.model.Scale;
import ru.zhenyria.monro_consulting_bot.service.ConsultationRequestService;
import ru.zhenyria.monro_consulting_bot.service.CustomerService;
import ru.zhenyria.monro_consulting_bot.service.ShoesService;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/consultation-requests")
@RequiredArgsConstructor
public class ConsultationRequestViewController {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final ConsultationRequestService service;
    private final CustomerService customerService;
    private final ShoesService shoesService;

    @GetMapping
    public String getPage(Model model) {
        var consultationRequests = service.getAll();
        consultationRequests.sort(Comparator.comparing(ConsultationRequest::getCreateDateTime));

        val customersIds = consultationRequests.stream()
                                               .map(ConsultationRequest::getCustomerId)
                                               .toList();

        var customersByIds = customerService.getAllByIds(customersIds)
                                            .stream()
                                            .collect(Collectors.toMap(Customer::getChatMemberId, Function.identity()));

        val consultationRequestsIds = consultationRequests.stream()
                                                          .map(ConsultationRequest::getId)
                                                          .toList();

        var shoesByConsultationRequestsIds = shoesService.getAllByConsultationRequests(consultationRequestsIds)
                                                         .stream()
                                                         .collect(Collectors.groupingBy(
                                                                 WishedShoesDto::getConsultationRequestId
                                                         ));

        var entries = new ArrayList<ConsultationRequestDto>();
        for (var consultationRequest : consultationRequests) {
            var customer = customersByIds.get(consultationRequest.getCustomerId());

            val wishes = shoesByConsultationRequestsIds.getOrDefault(consultationRequest.getId(),
                                                                     Collections.emptyList())
                                                       .stream()
                                                       .map(wishedShoes -> "%s %s".formatted(wishedShoes.getName(),
                                                                                             wishedShoes.getVendorCode()))
                                                       .toList();

            val size = Optional.ofNullable(customer.getScale())
                               .map(Scale::getSize)
                               .orElse(null);

            val volume = Optional.ofNullable(customer.getScale())
                                 .map(Scale::getVolume)
                                 .orElse(null);

            var entry = new ConsultationRequestDto(consultationRequest.getId(),
                                                   customer.getFirstName(),
                                                   customer.getLastName(),
                                                   customer.getPhoneNumber(),
                                                   size,
                                                   volume,
                                                   wishes,
                                                   DATE_TIME_FORMATTER.format(consultationRequest.getCreateDateTime()));

            entries.add(entry);
        }

        model.addAttribute("consultationRequests", entries);
        return "consultationRequests";
    }
}
