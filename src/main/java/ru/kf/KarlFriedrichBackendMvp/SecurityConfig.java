package ru.kf.KarlFriedrichBackendMvp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import ru.kf.KarlFriedrichBackendMvp.security.user.UserRepository;
import ru.kf.KarlFriedrichBackendMvp.security.CustomTokenAuthenticationFilter;
import ru.kf.KarlFriedrichBackendMvp.security.tokens.TokenService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Autowired
    public SecurityConfig(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/confirm").permitAll()
                .antMatchers("/menu").permitAll()
                .antMatchers("/menu-item/**").permitAll()
                .antMatchers("/refresh").permitAll()
                .anyRequest().authenticated()
                .and().addFilterBefore(new CustomTokenAuthenticationFilter(tokenService, userRepository), BasicAuthenticationFilter.class)
                .csrf().disable()
                .logout().disable();
    }

}
