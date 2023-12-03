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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The entity for telegram chat members
 */
@Entity
@Table(schema = "public", name = "customer")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class Customer implements Serializable {

    @Id
    @NotNull
    @Column(name = "chat_member_id")
    Long chatMemberId;

    @NotNull
    @Size(max = 50)
    @Column(name = "user_name")
    String userName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "customer_scale",
            joinColumns = {
                    @JoinColumn(name = "customer_id", referencedColumnName = "chat_member_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "size", referencedColumnName = "size"),
                    @JoinColumn(name = "volume", referencedColumnName = "volume")
            }
    )
    Scale scale;

    @ManyToMany
    @JoinTable(
            name = "customer_shoes_wish",
            joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "chat_member_id"),
            inverseJoinColumns = @JoinColumn(name = "vendor_code", referencedColumnName = "vendor_code")

    )
    List<Shoes> wishedShoes = new ArrayList<>();
}
