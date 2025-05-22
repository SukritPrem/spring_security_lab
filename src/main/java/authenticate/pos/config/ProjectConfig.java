package authenticate.pos.config;


import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import java.io.PrintStream;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ProjectConfig {
    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;
    @Value("${jwt.public.key}")
    RSAPublicKey key;
    @Value("${jwt.private.key}")
    RSAPrivateKey priv;

    public ProjectConfig() {
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = (AuthenticationManagerBuilder)http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(this.customAuthenticationProvider);
        return (AuthenticationManager)authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        HttpSecurity var10000 = http.authorizeHttpRequests((authorize) -> {
            ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)authorize.anyRequest()).authenticated();
        }).httpBasic(Customizer.withDefaults()).csrf((csrf) -> {
            csrf.ignoringRequestMatchers(new String[]{"token"});
        }).oauth2ResourceServer((oauth2) -> {
            oauth2.jwt(Customizer.withDefaults());
        }).sessionManagement((session) -> {
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });
        PrintStream var10001 = System.out;
        Objects.requireNonNull(var10001);
        var10000.exceptionHandling(var10001::print).exceptionHandling((exceptions) -> {
            exceptions.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint()).accessDeniedHandler(new BearerTokenAccessDeniedHandler());
        });
        return (SecurityFilterChain)http.build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.key).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = (new RSAKey.Builder(this.key)).privateKey(this.priv).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }
}
