package br.com.ecommerce.domain.service;

import br.com.ecommerce.api.exception.ErrorDetails;
import br.com.ecommerce.api.exception.ResourceNotFoundException;
import br.com.ecommerce.domain.model.User;
import br.com.ecommerce.domain.model.UserAddress;
import br.com.ecommerce.domain.repository.UserAddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserAddressService {
    private UserAddressRepository userAddressRepository;

    public UserAddress save(Long userId, UserAddress userAddress){
        User user = new User();
        user.setId(userId);
        userAddress.setUser(user);

        return userAddressRepository.save(userAddress);
    }

    public UserAddress update(Long addressId, UserAddress address) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserAddress existingAddress = userAddressRepository.findByIdAndUserId(addressId, user.getId()).orElseThrow( () -> {
            Set<ErrorDetails.Field> fields = new HashSet<>();
            fields.add(new ErrorDetails.Field("addressId", "Id given do not match"));
            throw new ResourceNotFoundException(fields);
        });

        //Check if the client have sent the propertie, if so, it is set in the existing address
        //if no, it just remains the same
        existingAddress.setAddressLine1(Objects.isNull(address.getAddressLine1()) ? existingAddress.getAddressLine1() : address.getAddressLine1());
        existingAddress.setAddressLine2(Objects.isNull(address.getAddressLine2()) ? existingAddress.getAddressLine2() : address.getAddressLine2());
        existingAddress.setCity(Objects.isNull(address.getCity()) ? existingAddress.getCity() : address.getCity());
        existingAddress.setPostalCode(Objects.isNull(address.getPostalCode()) ? existingAddress.getPostalCode() : address.getPostalCode());
        existingAddress.setCountry(Objects.isNull(address.getCountry()) ? existingAddress.getCountry() : address.getCountry());
        existingAddress.setMobile(Objects.isNull(address.getMobile()) ? existingAddress.getMobile() : address.getMobile());

        return userAddressRepository.save(existingAddress);
    }
}
