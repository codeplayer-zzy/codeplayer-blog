package com.codeplayer.config;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * 码云博客图床的常量类
 */
public interface GiteeImageBedConfig {
    /**
     * 码云分配的私人令牌Token
     */
    String ACCESS_TOKEN = "f5e7eb62c07bab59e2c9697dd2116071";

    /**
     * 码云用户名
     */
    String OWNER = "zhangzuyi";

    /**
     * 仓库名称
     */
    String REPO_NAME = "codeplayer-blog-table";

    /**
     * 上传图片的message
     */
    String CREATE_REPOS_MESSAGE = "add img";

    /**
     * 文件前缀
     */
    String IMG_FILE_DEST_PATH = "/img/" + DateUtil.format(new Date(), "yyyy-MM-dd") + "/";

    /**
     * 新建文件URL
     * 第一个 %s =>仓库所属空间地址(owner)
     * 第二个 %s => 仓库路径(repo)
     * 第三个 %s => 文件的路径(path)
     */
    String CREATE_REPOS_URL = "https://gitee.com/api/v5/repos/%s/%s/contents/%s";

    /**
     * GitPage请求路径
     */
    String GITPAGE_REQUEST_URL = "https://gitee.com/"+OWNER+"/"+REPO_NAME+"/raw/master/";

}
