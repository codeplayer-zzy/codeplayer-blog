package com.codeplayer.service;

import com.codeplayer.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @description: (user)表服务接口
* @author CodePlayer
* @date 2021/4/6 20:30
*/
@Service
public interface UserService {

    User queryById(Long userId);

    List<User> findByToken(String token);

    void updateUser(User user);

    void updateAdministrator(User user);

    void createOrUpdateGithub(User user);
}
