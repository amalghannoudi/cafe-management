package com.cafe.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
/* les methodes necessaires pour token (extraire details du user, create token , verifier s'il token est valide ...*/
public class JwtUtil {

    private String secret ="amal";


    public String extractUserName(String token){
        return extractClaims(token , Claims::getSubject);
    }

    public Date extractExpiration (String token ){
        return extractClaims(token , Claims::getExpiration) ;
    }

    /* extraire les info specifique du claims */
    public <T> T extractClaims(String token , Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return  claimsResolver.apply(claims) ;
    }

  /* extraire tous les claims à partir du token en le decode en utilisant clé secret */
    public Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public String generateToken (String userName , String role)
    {
        Map<String,Object> claims = new HashMap<>();
        claims.put("role",role) ;
        return createToken(claims,userName) ;

    }

    private String createToken(Map<String,Object> claims , String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                /* token will be expired in 10 hours */
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 *10))
                .signWith(SignatureAlgorithm.HS256,secret).compact() ;
    }
    private Boolean isTokenExpried (String token){
        return extractExpiration(token).before(new Date());
    }

    /* valider si token de ce user et n'est pas expiré */
    public Boolean validateToken (String token, UserDetails userDetails){
        final String userName = extractUserName(token) ;
        return (userName.equals(userDetails.getUsername()) && !isTokenExpried(token));
    }
}
