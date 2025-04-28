package ru.zhenyria.monro_consulting_bot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.val;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The entity represents shoes scale <i>(size and girth)</i>
 */
@Entity
@Table(name = "scale", schema = "monro")
@IdClass(ScaleId.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Scale implements Serializable, Comparable<Scale> {

    @Id
    @NotNull
    @Column(name = "size")
    Integer size;

    @Id
    @NotNull
    @Column(name = "volume")
    Integer volume;

    @NotNull
    @Column(name = "foot_length")
    BigDecimal footLength;

    @NotNull
    @Column(name = "foot_girth")
    BigDecimal footGirth;

    @Override
    public int compareTo(Scale scale) {
        val sizesComparingResult = this.getSize().compareTo(scale.getSize());
        return sizesComparingResult == 0 ? this.getVolume().compareTo(scale.getVolume()) : sizesComparingResult;
    }
}
