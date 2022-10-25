package ru.zhenyria.telegram_consulting_plugin.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Access(AccessType.FIELD)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(generator = "global_seq")
    Integer id;
}
