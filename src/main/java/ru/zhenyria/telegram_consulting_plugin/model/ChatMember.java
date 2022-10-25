package ru.zhenyria.telegram_consulting_plugin.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;

/**
 * The entity for telegram char members
 */
@Entity
@Table(name = "chat_member")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public class ChatMember extends AbstractEntity {

    @NotNull
    @Column(name = "chat_member_id")
    BigInteger chatMemberId;

    @NotNull
    @Size(max = 50)
    @Column(name = "user_name")
    String userName;
}
