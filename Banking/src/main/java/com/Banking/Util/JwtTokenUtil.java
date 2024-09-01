package com.Banking.Util;

import com.Banking.Exception.Classes.ResourceNotFoundException;
import com.Banking.Jwt.JwtHelper;
import com.Banking.Model.AppUser.AppUser;
import com.Banking.Repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenUtil {

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private AppUserUtil userUtil;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private HttpServletRequest request;

    public String getTokenFromHeader(){
        String header = request.getHeader("Authorization");
        if(header!=null && header.startsWith("Bearer ")) return header.substring(7);
        else throw new ResourceNotFoundException("Jwt Token is missing");
    }

    public void deleteTokenFromRepository(){
        String token = getTokenFromHeader();
        tokenRepository.deleteByToken(token);
    }

    public AppUser getAppUserFromToken(){
        String header = request.getHeader("Authorization");
        String token = "";
        if(header!=null && header.startsWith("Bearer ")){
            token = header.substring(7);
            return userUtil.findByEmail(this.jwtHelper.getUsernameFromToken(token));
        }else throw new RuntimeException("Jwt token is missing");
    }
}
