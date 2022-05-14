package br.com.ecommerce.api.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ecommerce.domain.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import static java.util.Arrays.stream;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.HttpStatus;

public class CustomAuthorizationFilter extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       if(request.getServletPath().equals("/login") || request.getServletPath().equals("/api/v1/user/refreshToken")){
           filterChain.doFilter(request, response);
       }else{
           String authorizationHeader = request.getHeader("Authorization");

           if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
               try {
                String token = authorizationHeader.substring("Bearer ".length());

                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);
                String username = decodedJWT.getSubject();
                long id = decodedJWT.getClaim("id").asLong();
                String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                stream(roles).forEach( role -> {
                    authorities.add(new SimpleGrantedAuthority(role));  
                });

                User user = new User();
                user.setEmail(username);
                user.setId(id);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                
                filterChain.doFilter(request, response);
               } catch (Exception e) {
                   response.setStatus(HttpStatus.FORBIDDEN.value());

                   Map<String, String> error = new HashMap<>();
                   error.put("error_message", e.getMessage());
                   response.setContentType("application/json");
                   new ObjectMapper().writeValue(response.getOutputStream(), error);
               }
           }else{
                filterChain.doFilter(request, response);
           }
       } 
    }
    
}
