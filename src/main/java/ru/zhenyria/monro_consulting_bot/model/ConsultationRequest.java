package ru.zhenyria.monro_consulting_bot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "consultation_request", schema = "monro")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class ConsultationRequest extends AbstractEntity implements Serializable {

    @NotNull
    @Column(name = "customer_id")
    Long customerId;

    @ManyToMany
    @JoinTable(
            name = "consultation_requests_shoes",
            schema = "monro",
            joinColumns = @JoinColumn(name = "consultation_request_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "shoes_id", referencedColumnName = "id")
    )
    List<Shoes> shoes = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "create_datetime")
    LocalDateTime createDateTime;
}
