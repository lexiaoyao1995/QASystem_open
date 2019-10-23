package com.lexiaoyao.handler;

import com.lexiaoyao.model.BusinessException;
import com.lexiaoyao.model.ErrorType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTFilter extends BasicAuthenticationFilter {
    public JWTFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    public static final String SING_KEY = "lexiaoyao";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("access-token");

        if (!StringUtils.isBlank(header)) {

            Claims body = null;
            try {
                body = Jwts.parser().setSigningKey(SING_KEY)
                        .parseClaimsJws(header.replace("Bearer ", ""))
                        .getBody();
            } catch (RuntimeException e) {
                throw new BusinessException(ErrorType.JWT_EXPIRED);
            }

            String username = body.getSubject();
            if (!StringUtils.isBlank(username)) {
                User user = new User(username, "", AuthorityUtils.createAuthorityList("admin"));
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, null);
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        super.doFilterInternal(request, response, chain);
    }
}
