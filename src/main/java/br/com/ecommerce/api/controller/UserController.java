package br.com.ecommerce.api.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import br.com.ecommerce.api.exception.ResourceAlreadyExistsException;
import br.com.ecommerce.api.model.response.UserWithRolesAndAddressesAndOrdersResponse;
import br.com.ecommerce.api.model.response.UserWithRolesResponse;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import br.com.ecommerce.api.assembler.UserAssembler;
import br.com.ecommerce.api.model.input.UserInput;
import br.com.ecommerce.domain.model.Role;
import br.com.ecommerce.domain.model.User;
import br.com.ecommerce.domain.repository.UserRepository;
import br.com.ecommerce.domain.service.UserService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserAssembler userAssembler;

    @Controller
    public class LoginController{
        @GetMapping("/login")
        public String login(){
            return "login";
        }
    }
    
    @GetMapping
    public List<UserWithRolesAndAddressesAndOrdersResponse> getAllUsers(){
        List<User> users = userRepository.findAll();
        return userAssembler.toCollectionAnyResponse(users, UserWithRolesAndAddressesAndOrdersResponse.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserWithRolesResponse createUser(@RequestBody @Valid UserInput userInput) throws ResourceAlreadyExistsException {
        User user = userAssembler.toEntity(userInput);

        User userSaved = userService.save(user); 
        return userAssembler.toAnyResponse(userSaved, UserWithRolesResponse.class);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userRepository.deleteFromLinkTable(userId);
        userRepository.deleteCartByUserId(userId);
        userRepository.deleteById(userId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String authorizationHeader = request.getHeader("Authorization");

           if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
               try {
                    String refreshToken = authorizationHeader.substring("Bearer ".length());

                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(refreshToken);
                    String username = decodedJWT.getSubject();

                    User user = userRepository.findByEmailAndRetrieveRoles(username).get();

                    int tenMinutes = 10 * 60 * 1000;

                    String accessToken = JWT.create()
                        .withSubject("" + user.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + tenMinutes))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getAuthority).collect(Collectors.toList()))
                        .sign(algorithm);

                    response.setHeader("access_token", accessToken);
                    response.setHeader("refresh_token", refreshToken);
               }catch (Exception e) {
                   response.sendError(HttpStatus.FORBIDDEN.value());

                   Map<String, String> error = new HashMap<>();
                   error.put("error_message", e.getMessage());
                   response.setContentType("application/json");
                   new ObjectMapper().writeValue(response.getOutputStream(), error);
               }
            }
    }
}
