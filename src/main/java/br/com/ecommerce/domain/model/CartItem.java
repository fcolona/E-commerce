package br.com.ecommerce.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long cartItemId;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "cart_id")
    private long cartId;

    private int quantity;
}
