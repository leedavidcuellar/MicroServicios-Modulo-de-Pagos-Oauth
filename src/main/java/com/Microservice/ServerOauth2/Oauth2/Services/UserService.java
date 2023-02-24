package com.Microservice.ServerOauth2.Oauth2.Services;

import com.Microservice.ServerOauth2.Oauth2.Clients.UserFeignClient;
import com.Microservice.ServerOauth2.Oauth2.Models.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserFeignClient client;

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        UserInfo userInfo = client.findByMail(mail);
        if(userInfo ==null){
            log.error("MS Authentification: User NO Found, check data");
            throw new UsernameNotFoundException("Error to login, User no exits "+mail+" in the system");
        }

        List<GrantedAuthority> authorities = userInfo.getRoles()
                .stream()
                .map(role->new SimpleGrantedAuthority(role.getName()))
			    .peek(authority -> log.info(" MS Autentification Role: "+ authority.getAuthority()))
			    .collect(Collectors.toList());
        log.info(" MSAutentification: User authentificate: "+mail);
        return new User(userInfo.getMail(), userInfo.getPassword(), userInfo.getActive(),true,true,true,authorities);
    }
}
