package com.codeplayer.controller;

import com.codeplayer.dto.AccessTokenDTO;
import com.codeplayer.dto.GithubUser;
import com.codeplayer.entity.User;
import com.codeplayer.provider.GithubProvider;
import com.codeplayer.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class SysAuthorizeController {
    @Value("${github.client.id}")
    private String githubClientId;

    @Value("${github.client.secret}")
    private String githubClientSecret;

    @Value("${github.redirect.url}")
    private String githubRedirectUrl;

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(SysAuthorizeController.class);

    @GetMapping("/callback")
    public String callbackGithub(@RequestParam(name = "code") String code,
                                 @RequestParam(name = "state") String state,
                                 HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(githubClientId);
        accessTokenDTO.setClient_secret(githubClientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(githubRedirectUrl);
        accessTokenDTO.setState(state);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null &&  githubUser.getId() != null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setGithubAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatarUrl());
            user.setEmail(githubUser.getEmail());
            userService.createOrUpdateGithub(user);
            response.addCookie(new Cookie("token", token));
            log.warn("callback get github warn,{}", githubUser);
            return "redirect:/index";
        }else {
            log.error("callback get github error,{}", githubUser);
            return "redirect:/index";
        }
    }
}
