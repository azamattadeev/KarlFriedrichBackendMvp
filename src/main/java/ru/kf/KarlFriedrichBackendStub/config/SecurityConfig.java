package ru.kf.KarlFriedrichBackendStub.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import ru.kf.KarlFriedrichBackendStub.repositories.UserRepository;
import ru.kf.KarlFriedrichBackendStub.security.CustomTokenAuthenticationFilter;
import ru.kf.KarlFriedrichBackendStub.security.MapTokenStorage;
import ru.kf.KarlFriedrichBackendStub.security.TokenStorage;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserRepository userRepository;

    @Autowired
    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/confirm").permitAll()
                .anyRequest().authenticated()
                .and().addFilterBefore(new CustomTokenAuthenticationFilter(mapTokenStorage(), userRepository), BasicAuthenticationFilter.class)
                .csrf().disable();
    }

    @Bean
    public TokenStorage mapTokenStorage() {
        TokenStorage storage =  new MapTokenStorage();
        storage.addMapping("hardcodeToken", 4L);
        storage.addMapping("hardcodeToken2", 3L);
        return storage;
    }

}
