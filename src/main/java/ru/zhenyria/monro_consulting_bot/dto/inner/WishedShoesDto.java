package ru.zhenyria.monro_consulting_bot.dto.inner;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class WishedShoesDto implements Serializable {
    Integer consultationRequestId;
    String name;
    String vendorCode;
}
