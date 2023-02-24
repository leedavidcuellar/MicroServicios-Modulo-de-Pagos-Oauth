package com.Microservice.ServerOauth2.Oauth2.Clients;

import com.Microservice.ServerOauth2.Oauth2.Models.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name ="service-users")
public interface UserFeignClient {
    @GetMapping("/api/user/findByMail/{mail}")
    public UserInfo findByMail(@PathVariable String mail);
}
