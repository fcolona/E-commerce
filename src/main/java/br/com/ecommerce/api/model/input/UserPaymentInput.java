package br.com.ecommerce.api.model.input;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserPaymentInput {

    private String paymentType;

    private String provider;

    private Date expiry;
}
