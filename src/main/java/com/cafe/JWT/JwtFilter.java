package com.cafe.JWT;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil ;

    @Autowired
    private CustomerUserDetailsService service ;

    Claims claims=null ;
    private String userName=null ;

    @Override
    /*filter pour verifier les chemin des requetes si les chemins sont vrai (login ,signup..)
    * laisse passer requete sinon pour les autres requetes il extraire userName et claims
    * s'il l'user doit s'authentifé on extrait les details et on test sur token s'il est valide
    * le filtre crée un objet d'auth avec des info   */
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      if (request.getServletPath().matches("/user/login|/user/signup|/user/forgotPass"))
      {
          filterChain.doFilter(request,response);
      }
      else {
          String authorizationHeader = request.getHeader("Authorization") ;
          String token = null ;

          if (authorizationHeader!= null && authorizationHeader.startsWith("Bearer ")){
              token = authorizationHeader.substring(7);
              userName = jwtUtil.extractUserName(token);
                claims = jwtUtil.extractAllClaims(token) ;
          }
          if (userName !=null && SecurityContextHolder.getContext().getAuthentication()==null){
              UserDetails userDetails =service.loadUserByUsername(userName) ;
                if (jwtUtil.validateToken(token,userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities()) ;
                    usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication((usernamePasswordAuthenticationToken));
                }
          }
          /* le filtre laisse passer la requete */
          filterChain.doFilter(request,response);

      }

    }
    public boolean isAdmin(){
        return  "admin".equalsIgnoreCase((String) claims.get("role")) ;
    }

    public boolean isUser(){
        return  "user".equalsIgnoreCase((String) claims.get("role")) ;
    }

    public String getCurretUser(){
        return userName ;
    }
}
