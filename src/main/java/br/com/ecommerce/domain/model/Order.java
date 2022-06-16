package br.com.ecommerce.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "`order`")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Order {
    public enum StatusEnum {
        IN_STORAGE, SENT_FOR_SHIPPING, DELIVERED;

        @Override
        public String toString(){
            return this.name();
        }
    }

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "order", fetch = FetchType.LAZY)
    private Set<OrderItem> orderItems;

    @Column(name = "user_id")
    private long userId;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private UserAddress userAddress;

    @Column(name = "stripe_id")
    private String stripeId;

    @Enumerated(value = EnumType.STRING)
    private StatusEnum status;
}
