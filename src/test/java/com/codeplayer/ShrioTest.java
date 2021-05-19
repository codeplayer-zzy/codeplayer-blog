//package com.codeplayer;
//
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.UsernamePasswordToken;
//import org.apache.shiro.mgt.DefaultSecurityManager;
//import org.apache.shiro.realm.SimpleAccountRealm;
//import org.apache.shiro.subject.Subject;
//import org.junit.jupiter.api.Test;
///**
//* @description: Shrio测试
//* @author CodePlayer
//* @date 2021/4/7 17:26
//*/
//public class classShrioTest {
//    //简单的Realm类，还可以使用内置的IniRealm，JdbcRealm
//    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();
//
//    public void getSimpleAccountRealm() {
//        simpleAccountRealm.addAccount("codeplayer","123456","admin");
//    }
//
//    @Test
//    public void testAuthentication(){
//        getSimpleAccountRealm();
//
//        //构建SecurityManager环境
//        DefaultSecurityManager securityManager = new DefaultSecurityManager();
//        //设置Realm
//        securityManager.setRealm(simpleAccountRealm);
//
//        //主体提交认证请求
//        SecurityUtils.setSecurityManager(securityManager);
//        Subject subject = SecurityUtils.getSubject();
//
//         UsernamePasswordToken token = new UsernamePasswordToken("codeplayer","123456");
//         subject.login(token);
//
//         //检查是否认证成功
//        boolean authenticated = subject.isAuthenticated();
//        System.out.println(authenticated);
//
//        //检查授权
//        subject.checkRoles("admin");
//    }
//}
