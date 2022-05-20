package br.com.ecommerce.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "user_address")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserAddress{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "address_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @JoinColumn(name = "address_line1")
    private String addressLine1;

    @JoinColumn(name = "address_line1")
    private String addressLine2;

    private String city;

    @JoinColumn(name = "postal_code")
    private String postalCode;

    private String country;

    private String mobile;
}
