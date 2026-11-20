package nl.fontys.restaurantreservations.bll.services;

import nl.fontys.restaurantreservations.bll.domains.UserDomain;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;

    public JwtService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(UserDomain userDomain) {
        Instant now = Instant.now();

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .subject(String.valueOf(userDomain.getId()))
                .issuer("restaurant-reservations-be")
                .audience(List.of("restaurant-reservations-be"))
                .issuedAt(now)
                .expiresAt(now.plus(10, ChronoUnit.HOURS)) //should be lower when refresh tokens are implemented
                .build();

        JwsHeader header = JwsHeader.with(
                MacAlgorithm.HS256  // surely this is fine right copium
        ).build();

        return jwtEncoder.encode(JwtEncoderParameters.from(header, claimsSet)).getTokenValue();
    }
}
