package com.example.cum.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.cum.entity.AppUser;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtUtil {
    @Value("${app.cum.jwt.jwt-secret}")
    private String jwtSecret;
    @Value("${app.cum.jwt.app-name}")
    private String appName ;
    @Value("${app.cum.jwt.expired}")
    private long jwtExpired ;

    private Algorithm algorithm;

    @PostConstruct
    private void initAlgorithm() {
        this.algorithm = Algorithm.HMAC256(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(AppUser appUser) {
        var jwt = JWT.create();
            jwt.withClaim("role", appUser.getRole());
        return jwt // bikin token jwt
                .withIssuer(appName)
                .withSubject(appUser.getId())
//                .withExpiresAt(Instant.now().plusSeconds(jwtExpired))
                .withExpiresAt(Instant.now().plus(1, ChronoUnit.HOURS))
                .withIssuedAt(Instant.now())
//                .withClaim("role", appUser.getRoles().stream().toList())
                .sign(algorithm);
    }

    public boolean verifyJwtToken(String token) {
//        verifikasi tokenya menggunakan algoritma dan secret key yg sudah ditentukan
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getIssuer().equals(appName);
    }

    public Map<String, String> getUserInfoByToken(String token) {
//        ambil user infonya
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("userId", decodedJWT.getSubject());
        userInfo.put("role", decodedJWT.getClaim("role").asString());
        return userInfo;
    }
}