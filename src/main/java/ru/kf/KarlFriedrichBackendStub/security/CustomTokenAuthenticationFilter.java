package ru.kf.KarlFriedrichBackendStub.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import ru.kf.KarlFriedrichBackendStub.entities.User;
import ru.kf.KarlFriedrichBackendStub.repositories.UserRepository;
import ru.kf.KarlFriedrichBackendStub.services.TokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class CustomTokenAuthenticationFilter extends GenericFilterBean {
    private final TokenService tokenService;
    private final UserRepository userRepository;

    public CustomTokenAuthenticationFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String accessToken = httpRequest.getHeader("Authorization");
        if (accessToken == null || accessToken.length() == 0){
            chain.doFilter(request, response);
            return;
        }

        Long id = tokenService.getIdByAccessToken(accessToken);
        User user = (id != null) ? userRepository.findById(id).orElse(null) : null;
        if (user != null) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    user, accessToken, AuthorityUtils.createAuthorityList(user.getRole())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            SecurityContextHolder.getContext().setAuthentication(null);
        }

        chain.doFilter(request, response);
    }

}
