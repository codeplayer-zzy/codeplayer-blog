package com.codeplayer.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Kaptcha工具类
 *
 * @author codeplayer
 */
public class KaptchaUtil {
    /**
     * 将获取到的前端参数转为string类型
     * @param request
     * @param key
     * @return
     */
    public static String getString(HttpServletRequest request, String key) {
        try {
            String result = request.getParameter(key);
            if(result != null) {
                result = result.trim();
            }
            if(result == "") {
                result = null;
            }
            return result;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 验证码校验
     * @param request
     * @return
     */
    public static boolean checkVerifyCode(HttpServletRequest request) {
        //获取生成的验证码
        String verifyCodeExpected = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        //获取用户输入的验证码
        String validateCode = KaptchaUtil.getString(request, "validateCode");
        if(validateCode == null || verifyCodeExpected.equals(validateCode)) {
            return false;
        }
        return true;
    }
}

