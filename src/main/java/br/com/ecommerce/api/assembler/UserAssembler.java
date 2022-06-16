package br.com.ecommerce.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import br.com.ecommerce.domain.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.ecommerce.api.model.input.UserInput;
import br.com.ecommerce.api.model.response.UserResponse;
import br.com.ecommerce.domain.model.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class UserAssembler {
    private ModelMapper modelMapper;

    public User toEntity(UserInput userInput){
        return modelMapper.map(userInput, User.class);
    }

    public UserResponse toResponse(User user){
        return modelMapper.map(user, UserResponse.class);
    }

    public List<UserResponse> toCollectionResponse(List<User> users){

        return users.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public <T> T toAnyResponse(User entity, Class<T> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }
    public <T> List<T> toCollectionAnyResponse(List<User> users, Class<T> dtoClass){
        return users.stream()
                .map( user -> toAnyResponse(user, dtoClass))
                .collect(Collectors.toList());
    }
}

