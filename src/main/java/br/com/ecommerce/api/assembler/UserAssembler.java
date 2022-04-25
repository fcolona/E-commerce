package br.com.ecommerce.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

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
        UserResponse res = modelMapper.map(user, UserResponse.class);

        return res;
    }

    public List<UserResponse> toCollectionResponse(List<User> users){

        List<UserResponse> res = users.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        return res;
    }
}
