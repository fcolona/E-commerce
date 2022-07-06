package br.com.ecommerce.api.controller;

import br.com.ecommerce.api.model.input.UserInput;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.login.LoginException;
import java.util.List;

@RestController
public class LoginController {
    private final RestTemplate restTemplate;

    public LoginController (RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @PostMapping("/perform-login")
    public String performLogin(@RequestBody UserInput userInput) throws LoginException {
        String url = "http://localhost:8080/login";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("username", userInput.getEmail());
        map.add("password", userInput.getPassword());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<String> response = this.restTemplate.postForEntity( url, request , String.class );
        if(response.getStatusCode().equals(HttpStatus.OK)){
            return response.getHeaders().get("access_token").get(0);
        }
        throw new LoginException();
    }
}
