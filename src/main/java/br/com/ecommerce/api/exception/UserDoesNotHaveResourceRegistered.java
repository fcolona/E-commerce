package br.com.ecommerce.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;
import java.util.Set;
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@Getter
public class UserDoesNotHaveResourceRegistered extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;
    private Set<ErrorDetails.Field> fields;

    public UserDoesNotHaveResourceRegistered(Set<ErrorDetails.Field> fields) {
        super(fields.toString());
        this.fields = fields;
    }
}
