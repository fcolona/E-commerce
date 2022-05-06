package br.com.ecommerce.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserInput {

    @Email
    @NotBlank
    @Size(max = 255)
    private String email;

    @NotNull
    private String password;
}
