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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The entity for telegram chat members
 */
@Entity
@Table(name = "customer", schema = "monro")
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
            schema = "monro",
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
            schema = "monro",
            joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "chat_member_id"),
            inverseJoinColumns = @JoinColumn(name = "shoes_id", referencedColumnName = "id")

    )
    List<Shoes> wishedShoes = new ArrayList<>();
}
