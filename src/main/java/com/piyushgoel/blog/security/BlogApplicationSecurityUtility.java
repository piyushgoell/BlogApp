package com.piyushgoel.blog.security;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.piyushgoel.blog.dataTransferObject.Auth;
import com.piyushgoel.blog.enums.RoleType;
import com.piyushgoel.blog.model.User;

@Component
public class BlogApplicationSecurityUtility {

	public static final long JWT_TOKEN_VALIDITY = 15 * 60 * 60 * 100;
	
    @Value("${jwt.secret}")
    private  String SECRET_KEY;
    
    public static final Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
    
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token,"sub").asString();
    }
    
    public String getUserIdFromToken(String token) {
        return getClaimFromToken(token,"jti").asString();
    }


    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, "exp").asDate();
    }
    
    public RoleType[] getClaimsFromToken(String token) {
        return getClaimFromToken(token, "claims").asArray(RoleType.class);
    }
    
    public Claim getClaimFromToken(String token, String claim) {
        return getAllClaimsFromToken(token).getClaim(claim);
    }

    private DecodedJWT getAllClaimsFromToken(String token) {
    	return JWT.require(algorithm).build().verify(token);
    }

    public Auth generateToken(User user,String issuer) {
        return doGenerateToken(user,issuer);
    }


    private Auth doGenerateToken(User user, String issuer) {
    	
    	String JWTId = UUID.randomUUID().toString();
    	String subject = user.getUsername();
    	Map<String,String> clientid = Map.of("clientid",user.getId().toString());
    	Date issued_at = new Date(System.currentTimeMillis());
		Date expires_at = new Date (issued_at.getTime() + JWT_TOKEN_VALIDITY);
		
		Builder jwtBuilder = JWT.create()
				.withJWTId(JWTId)
				.withSubject(subject)
				.withPayload(clientid)
				.withIssuedAt(issued_at)
				.withExpiresAt(expires_at)
				.withIssuer(issuer);
				
		String access_token = jwtBuilder
				.withClaim("claims", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algorithm);
		
		String refresh_token = jwtBuilder
				.sign(algorithm);
		
		return new Auth(access_token,refresh_token,issued_at,expires_at,"bearer");
    
    }


    public Boolean validateToken(String token) {
        return (JWT.require(algorithm).build().verify(token).getExpiresAt().before(new Date()));
    }
    
}
