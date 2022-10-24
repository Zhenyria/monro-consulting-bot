package ru.zhenyria.telegram_consulting_plugin.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * The entity form text templates
 */
@Entity
@Table(name = "text_template")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class TextTemplate {

  @Id
  @GeneratedValue(generator = "global_seq")
  @EqualsAndHashCode.Exclude
  Integer id;

  @NotBlank
  @Size(max = 50)
  @Column(name = "key_value")
  String key;

  @NotBlank
  @Size(max = 500)
  @Column(name = "text_value")
  String text;
}
