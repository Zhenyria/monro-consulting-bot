package ru.zhenyria.monro_consulting_bot.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * The entity represents shoes model
 */
@Entity
@Table(schema = "public", name = "shoes")
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
