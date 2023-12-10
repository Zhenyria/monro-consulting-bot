package ru.zhenyria.monro_consulting_bot.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.val;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The entity represents shoes scale <i>(size and girth)</i>
 */
@Entity
@Table(schema = "public", name = "scale")
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
