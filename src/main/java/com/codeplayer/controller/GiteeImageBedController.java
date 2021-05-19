package com.codeplayer.controller;

import com.alibaba.fastjson.JSONObject;
import com.codeplayer.dto.ResultDTO;
import com.codeplayer.utils.ImageBedUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.IOException;

@Controller
public class GiteeImageBedController {
    @Autowired
    private ResultDTO<String> resultDTO;
    /**
     * 文章上传图片到Gitee图床
     */
    @ResponseBody
    @PostMapping("/article")
    public String UploadMarkDown(@RequestParam(value = "editormd-image-file", required = false) MultipartFile file) throws IOException {
        String url = ImageBedUtils.upload(file);
        if (url != null){
            resultDTO.setUrl(url);
            resultDTO.setCode(1);
            resultDTO.setMessage("【成功】文章上传图片到Gitee图床，URL："+url);
        }else {
            resultDTO.setUrl(url);
            resultDTO.setCode(0);
            resultDTO.setMessage("【失败】文章上传图片到Gitee图床，URL："+url);
        }
        return JSONObject.toJSONString(resultDTO);
    }
}
