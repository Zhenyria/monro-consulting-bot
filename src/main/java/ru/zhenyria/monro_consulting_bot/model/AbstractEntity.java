package ru.zhenyria.monro_consulting_bot.model;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@MappedSuperclass
@Access(AccessType.FIELD)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public abstract class AbstractEntity {
    static final int START_SEQ = 100;

    @Id
    @SequenceGenerator(
            name = "global_seq",
            sequenceName = "global_seq",
            schema = "monro",
            initialValue = START_SEQ,
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    @Column(name = "id")
    Integer id;
}
