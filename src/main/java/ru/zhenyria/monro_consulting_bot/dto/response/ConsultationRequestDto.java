package ru.zhenyria.monro_consulting_bot.dto.response;

import java.util.List;

public record ConsultationRequestDto(Integer id,
                                     String name,
                                     String lastName,
                                     String phoneNumber,
                                     Integer size,
                                     Integer volume,
                                     List<String> wishes,
                                     String createdAt) {
}
