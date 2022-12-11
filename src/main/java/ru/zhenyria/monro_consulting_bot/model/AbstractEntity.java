package ru.zhenyria.monro_consulting_bot.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

@MappedSuperclass
@Access(AccessType.FIELD)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public abstract class AbstractEntity {
    static final int START_SEQ = 100;

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", initialValue = START_SEQ, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    Integer id;
}
