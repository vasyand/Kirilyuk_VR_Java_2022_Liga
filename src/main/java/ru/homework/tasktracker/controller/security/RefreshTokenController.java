package ru.homework.tasktracker.controller.security;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.homework.tasktracker.config.security.exception.RefreshTokenIsMissingException;
import ru.homework.tasktracker.config.security.jwt.JwtTokenGenerator;
import ru.homework.tasktracker.config.security.jwt.JwtTokenValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ru.homework.tasktracker.Constants.AUTHORIZATION_HEADER;
import static ru.homework.tasktracker.Constants.REFRESH_TOKEN_HEADER;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/refresh-token")
public class RefreshTokenController {
    private final JwtTokenValidator jwtTokenValidator;
    private final JwtTokenGenerator jwtTokenGenerator;

    @GetMapping
    public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response){
        String authHeader = request.getHeader(REFRESH_TOKEN_HEADER);
        if (authHeader == null) {
            throw new RefreshTokenIsMissingException();
        }

        Authentication authentication = jwtTokenValidator.extractAuthentication(authHeader);

        String refreshToken = jwtTokenGenerator.generateRefreshToken(authentication);
        String accessToken = jwtTokenGenerator.generateAccessToken(authentication);

        response.setHeader(REFRESH_TOKEN_HEADER, refreshToken);
        response.setHeader(AUTHORIZATION_HEADER, accessToken);

        return ResponseEntity.ok().build();

    }


}
