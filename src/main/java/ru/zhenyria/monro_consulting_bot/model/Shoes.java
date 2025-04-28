package ru.zhenyria.monro_consulting_bot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

/**
 * The entity represents shoes model
 */
@Entity
@Table(name = "shoes", schema = "monro")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class Shoes implements Serializable {

    @Id
    @NotNull
    @Column(name = "vendor_code")
    String vendorCode;

    @NotBlank
    @Column(name = "url")
    String url;

    @NotBlank
    @Column(name = "name")
    String name;

    @NotNull
    @Column(name = "description")
    String description;

    @NotEmpty
    @Column(name = "image_url")
    String imageUrl;

    @ManyToMany
    @JoinTable(
            name = "shoes_scales",
            schema = "monro",
            joinColumns = {
                    @JoinColumn(name = "shoes_vendor_code", referencedColumnName = "vendor_code")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "size", referencedColumnName = "size"),
                    @JoinColumn(name = "volume", referencedColumnName = "volume")
            }
    )
    List<Scale> scales;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "season_name", referencedColumnName = "name_val")
    Season season;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "model_name", referencedColumnName = "name_val")
    ShoesModel model;
}
