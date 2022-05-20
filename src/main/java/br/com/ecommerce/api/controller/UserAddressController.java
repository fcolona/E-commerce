package br.com.ecommerce.api.controller;

import br.com.ecommerce.api.assembler.UserAddressAssembler;
import br.com.ecommerce.api.exception.ErrorDetails;
import br.com.ecommerce.api.exception.ResourceNotFoundException;
import br.com.ecommerce.api.exception.UserDoesNotHaveResourceRegistered;
import br.com.ecommerce.api.model.input.UserAddressInput;
import br.com.ecommerce.domain.model.User;
import br.com.ecommerce.domain.model.UserAddress;
import br.com.ecommerce.domain.repository.UserAddressRepository;
import br.com.ecommerce.domain.repository.UserRepository;
import br.com.ecommerce.domain.service.UserAddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/user-address")
@AllArgsConstructor
public class UserAddressController {
    private UserAddressRepository userAddressRepository;

    private UserAddressService userAddressService;

    private UserRepository userRepository;

    private UserAddressAssembler userAddressAssembler;

    @GetMapping
    public List<UserAddress> getUserAddress(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<UserAddress> addresses = userAddressRepository.findByUserId(user.getId());
        if(addresses.isEmpty()){
            Set<ErrorDetails.Field> fields = new HashSet<>();
            fields.add(new ErrorDetails.Field("address", "User does not have a registered address"));
            throw new UserDoesNotHaveResourceRegistered(fields);
        }
        return addresses;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserAddress createUserAddress(@RequestBody UserAddressInput userAddressInput){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserAddress userAddress = userAddressAssembler.toEntity(userAddressInput);

        return userAddressService.save(user.getId(), userAddress);
    };

    @PutMapping("/{addressId}")
    public UserAddress updateUserAddress(@PathVariable long addressId, @RequestBody UserAddressInput userAddressInput){
        UserAddress userAddress = userAddressAssembler.toEntity(userAddressInput);

        return userAddressService.update(addressId, userAddress);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteUserAddress(@PathVariable long addressId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userAddressRepository.deleteByIdAndUserId(addressId, user.getId());

        return ResponseEntity.noContent().build();
    }
}
