package br.com.ecommerce.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Category {
    
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private long id;

    @NotBlank
    @Size(max = 255)
    private String name;
}
