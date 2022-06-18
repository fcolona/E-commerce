package br.com.ecommerce.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CartItem implements Serializable {
    @Serial
    private static final long serialVersionUID = 7156526077883281623L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private long cartItemId;

    @OneToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "cart_id")
    private long cartId;

    private int quantity;
}
