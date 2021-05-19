package com.codeplayer.mapper;
import com.codeplayer.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
* @description: (user)表数据库访问层
* @author CodePlayer
* @date 2021/4/6 20:26
*/
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE user_id = #{userId}")
    User findByUserId(@Param("userId") Long userId);

    @Select("SELECT * FROM user WHERE token = #{token}")
    List<User> findByToken(@Param("token") String token);

    @Select("SELECT * FROM user WHERE user_id = #{creator}")
    User findByCreatorId(@Param(value = "creator") Long creator);

    @Insert("INSERT INTO user (name, password,sex,github_account_id,address, email, birthday, phone, gmt_create, gmt_modified, token, avatar_url)" +
            "VALUES (#{name}, #{password}, #{sex}, #{githubAccountId}, #{address}, #{email}, #{birthday}, #{phone}, #{gmtCreate}, #{gmtModified}, #{token}, #{avatarUrl})")
    void insert(User user);

    @Select("<script>" +
            " SELECT * FROM user WHERE user_id IN " +
            "    <foreach item='commentator' index='index' collection='list' open='(' separator=',' close=')'>" +
            "       #{commentator}" +
            "    </foreach>" +
            "</script>")
    List<User> findByAllId(List<Long> userIds);

    @Select("SELECT * FROM user WHERE github_Account_id = #{githubAccountId}")
    List<User> findByAccountId(@Param(value = "githubAccountId") String githubAccountId);

    @Update("UPDATE user SET address = #{address},email = #{email},phone = #{phone},github_account_id = #{githubAccountId}," +
            "gmt_modified = #{gmtModified},token = #{token},avatar_url = #{avatarUrl},birthday = #{birthday},sex = #{sex} " +
            "WHERE user_id = #{userId}")
    void updateUser(User user1);

    @Update("UPDATE user SET name = #{name},github_account_id = #{githubAccountId}," +
            "gmt_modified = #{gmtModified},token = #{token},avatar_url = #{avatarUrl},email = #{email} " +
            "WHERE user_id = #{userId}")
    void updateGithubUser(User updateUser);
}

