package br.com.ecommerce.api.model.input;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserAddressInput {

    @NonNull
    @Size(max = 255)
    private String addressLine1;

    @NonNull
    @Size(max = 255)
    private String addressLine2;

    @NonNull
    @Size(max = 255)
    private String city;

    @NonNull
    @Size(max = 255)
    private String postalCode;

    @NonNull
    @Size(max = 255)
    private String country;

    @NonNull
    @Size(min = 10, max = 17)
    private String mobile;
}
