package com.codeplayer.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.codeplayer.config.GiteeImageBedConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ImageBedUtils工具类
 *
 * @author codeplayer
 */
public class ImageBedUtils {
    /**
     * 上传图片到Gitee
     */
    public static String upload(MultipartFile file)throws IOException {
        String trueFileName = file.getOriginalFilename();
        assert trueFileName != null;
        String suffix = trueFileName.substring(trueFileName.lastIndexOf("."));

        String fileName = System.currentTimeMillis() + "_" + IdUtil.simpleUUID() + suffix;
        String paramImgFile = Base64.encode(file.getBytes());
        //转存到gitee
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("access_token", GiteeImageBedConfig.ACCESS_TOKEN);
        paramMap.put("message", GiteeImageBedConfig.CREATE_REPOS_MESSAGE);
        paramMap.put("content", paramImgFile);

        String targetDir = GiteeImageBedConfig.IMG_FILE_DEST_PATH + fileName;

        String requestUrl = String.format(GiteeImageBedConfig.CREATE_REPOS_URL, GiteeImageBedConfig.OWNER,
                GiteeImageBedConfig.REPO_NAME, targetDir);

        String resultJson = HttpUtil.post(requestUrl, paramMap);

        System.out.println("resultJson = " + resultJson);

        JSONObject jsonObject = JSONUtil.parseObj(resultJson);
        if (jsonObject.getObj("commit") != null) {
            return GiteeImageBedConfig.GITPAGE_REQUEST_URL + targetDir;
        }
        return "";



    }

    public static boolean checkFileSize(Long len, int size, String unit) {
        double fileSize = 0;
        if ("B".equals(unit.toUpperCase())) {
            fileSize = (double) len;
        } else if ("K".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1024;
        } else if ("M".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1048576;
        } else if ("G".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1073741824;
        }
        if (fileSize > size) {
            return false;
        }
        return true;
    }
}
