package com.fluxapp.todoflux.service;

import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import static com.fluxapp.todoflux.controller.AuthController.log;

@Service
public class TokenService {

    private final JwtEncoder jwtEncoder;

    public TokenService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(Authentication authentication) {
        try {
            // Generate Authentication
//            Authentication authentication = generateAuthenticationFromUserDetails(userLoginRequest);

            // Define token claims
            Instant now = Instant.now();
            String scope = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(" ")); // Ensure space-separated scopes

            JwtClaimsSet claims = JwtClaimsSet.builder()
                    .issuer("self")
                    .issuedAt(now)
                    .expiresAt(now.plus(1, ChronoUnit.HOURS))
                    .subject(authentication.getName())
                    .claim("scope", scope)
                    .build();

            return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        } catch (AuthenticationException ex) {
            log.error("Authentication failed for user {}: {}", authentication.getPrincipal().toString(), ex.getMessage());
            throw new BadCredentialsException("Invalid username or password", ex);
        }
    }

    public Authentication generateAuthenticationFromUserDetails(UserLoginRequest userLoginRequest) {
        // Create Authentication token with username and password
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userLoginRequest.getUsername(), userLoginRequest.getPassword());

        // Perform authentication
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // Store the authentication context (optional, depending on your needs)
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }

}
