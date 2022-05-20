package br.com.ecommerce.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    private ImplementsUserDetails userDetailsService;

   @Override
   protected void configure(HttpSecurity http) throws Exception {
       http.csrf().disable();
       http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
       http.authorizeRequests()
            .antMatchers(HttpMethod.GET,"/api/v1/user").hasRole("ADMIN")
            .antMatchers(HttpMethod.POST,"/api/v1/user").permitAll()
            .antMatchers(HttpMethod.GET,"/api/v1/cart").hasRole("USER")
            .antMatchers(HttpMethod.POST,"/api/v1/cart").hasRole("USER")
            .antMatchers(HttpMethod.DELETE,"/api/v1/cart").hasRole("USER")
            .antMatchers(HttpMethod.GET,"/api/v1/user-address").hasRole("USER")
            .antMatchers(HttpMethod.POST,"/api/v1/user-address").hasRole("USER")
            .antMatchers(HttpMethod.PUT,"/api/v1/user-address").hasRole("USER")
            .antMatchers(HttpMethod.DELETE,"/api/v1/user-address").hasRole("USER")
            .and()
            .formLogin().permitAll();
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
   } 

   @Bean
   @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
       return super.authenticationManagerBean();
   }

   @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/templates/**, /materialize/**", "/style/**, /swagger-resources/", "/webjars/").antMatchers(HttpMethod.OPTIONS, "/**");
    }
}
