package ru.zhenyria.monro_consulting_bot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

/**
 * The entity represents model of shoes
 */
@Entity
@Table(name = "shoes_model", schema = "monro")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class ShoesModel implements Serializable {

    @Id
    @NotBlank
    @Column(name = "name_val")
    String name;

    @NotBlank
    @Column(name = "localized_name")
    String localizedName;
}
