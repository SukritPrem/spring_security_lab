package authenticate.pos.controller;


import java.time.Instant;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {
    @Autowired
    JwtEncoder encoder;

    public TokenController() {
    }

    @PostMapping({"/token"})
    public String token(Authentication authentication) {
        Instant now = Instant.now();
        long expiry = 36000L;
        String scope = (String)authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder().issuer("self").issuedAt(now).expiresAt(now.plusSeconds(expiry)).subject(authentication.getName()).claim("scope", scope).build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}

