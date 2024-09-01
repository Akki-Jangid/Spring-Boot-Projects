package com.Irctc.JwtSecurity;

import ch.qos.logback.classic.spi.IThrowableProxy;
import com.Irctc.Model.User.AppUser;
import com.Irctc.Repository.AppUserRepository;
import com.Irctc.ServiceImpl.AppUserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenDetails {

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private AppUserServiceImpl appUserService;

    @Autowired
    private HttpServletRequest request;

    public AppUser getAppUserFromToken(){

        String header = request.getHeader("Authorization");
        String token = "";

        if(header!=null && header.startsWith("Bearer ")){
            token = header.substring(7);
            return appUserService.findAppUserByEmail(this.jwtHelper.getUsernameFromToken(token));
        }else throw new RuntimeException("Jwt token is missing");
    }
}
