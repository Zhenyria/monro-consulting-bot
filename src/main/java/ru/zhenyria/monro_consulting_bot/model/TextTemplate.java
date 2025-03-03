package ru.zhenyria.monro_consulting_bot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

/**
 * The entity for text templates
 */
@Entity
@Table(schema = "public", name = "text_template")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public class TextTemplate extends AbstractEntity implements Serializable {

    @NotBlank
    @Size(max = 50)
    @Column(name = "key_value")
    String key;

    @NotBlank
    @Size(max = 500)
    @Column(name = "text_value")
    String text;
}
