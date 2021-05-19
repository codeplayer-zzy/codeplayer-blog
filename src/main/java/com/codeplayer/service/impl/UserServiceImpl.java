package com.codeplayer.service.impl;

import com.codeplayer.entity.User;
import com.codeplayer.service.BaseService;
import com.codeplayer.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @description: (user)表服务实现类
* @author CodePlayer
* @date 2021/4/6 20:33
*/
@Service("userService")
public class UserServiceImpl extends BaseService implements UserService {

    @Override
    public User queryById(Long userId) {
        User user = userMapper.findByUserId(userId);
        return user;
    }

    @Override
    public List<User> findByToken(String token) {
        List<User> user = userMapper.findByToken(token);
        return user;
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    /**
     * 管理员用户
     * @param user
     */
    @Override
    public void updateAdministrator(User user) {
        //更新
        User user1 = new User();
        BeanUtils.copyProperties(user, user1);
        user1.setGmtModified(new Date());
        userMapper.updateUser(user1);
    }

    /**
     * github用户登录
     * @param user
     */
    @Override
    public void createOrUpdateGithub(User user) {
        List<User> githubUsers = userMapper.findByAccountId(user.getGithubAccountId());
        if (githubUsers.size() == 0) {
            // 插入
            user.setGmtCreate(new Date());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else {
            //更新
            User dbUser = githubUsers.get(0);
            User updateUser = new User();
            BeanUtils.copyProperties(user,updateUser);
            updateUser.setUserId(dbUser.getUserId());
            updateUser.setGmtModified(new Date());
            userMapper.updateGithubUser(updateUser);
        }
    }
}
