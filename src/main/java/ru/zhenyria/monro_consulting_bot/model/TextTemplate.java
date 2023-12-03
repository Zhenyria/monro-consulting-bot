package ru.zhenyria.monro_consulting_bot.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
