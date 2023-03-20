package by.it_academy.user.web.controllers.filter;

import by.it_academy.user.core.dto.user.User;
import by.it_academy.user.core.dto.user.UserToken;
import by.it_academy.user.service.api.IAuthenticationService;
import by.it_academy.user.web.controllers.utils.JwtTokenHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final IAuthenticationService authenticationService;
    private final JwtTokenHandler jwtHandler;

    public JwtFilter(IAuthenticationService authenticationService, JwtTokenHandler jwtHandler) {
        this.authenticationService = authenticationService;
        this.jwtHandler = jwtHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        final String token = header.split(" ")[1].trim();
        if (!jwtHandler.validate(token)) {
            chain.doFilter(request, response);
            return;
        }

        User user = authenticationService.get(jwtHandler.getMail(token));
        UserToken userToken = new UserToken(user.getMail(), user.getRole().name(), user.getFio(), user.getUuid());


        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(
                userToken, null,
                userToken.getAuthorities());

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}