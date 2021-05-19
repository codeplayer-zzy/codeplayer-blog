package com.codeplayer.cache;

import com.codeplayer.dto.TagsDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {
    public static List<TagsDTO> get() {
        List<TagsDTO> tagDTOS = new ArrayList<>();
        TagsDTO program = new TagsDTO();
        program.setCategoryName("开发语言");
        program.setTagsName(Arrays.asList("javascript", "php", "css", "html", "html5", "java", "node.js", "python", "c", "golang", "objective-c", "typescript", "shell", "swift", "c#", "sass", "ruby", "bash", "less", "asp.net", "lua", "scala", "coffeescript", "actionscript", "rust", "erlang", "perl"));
        tagDTOS.add(program);

        TagsDTO framework = new TagsDTO();
        framework.setCategoryName("平台框架");
        framework.setTagsName(Arrays.asList("laravel", "spring", "springboot", "django", "flask", "vue", "ruby-on-rails", "tornado", "springmvc", "struts"));
        tagDTOS.add(framework);

        TagsDTO server = new TagsDTO();
        server.setCategoryName("服务器");
        server.setTagsName(Arrays.asList("linux", "nginx", "docker", "apache", "ubuntu", "centos", "缓存 tomcat", "负载均衡", "unix", "hadoop", "windows-server"));
        tagDTOS.add(server);

        TagsDTO db = new TagsDTO();
        db.setCategoryName("数据库");
        db.setTagsName(Arrays.asList("mysql", "redis", "mongodb", "sql", "oracle", "nosql memcached", "sqlserver", "postgresql", "sqlite"));
        tagDTOS.add(db);

        TagsDTO tool = new TagsDTO();
        tool.setCategoryName("开发工具");
        tool.setTagsName(Arrays.asList("git", "github", "visual-studio-code", "vim", "sublime-text", "xcode intellij-idea", "eclipse", "maven", "ide", "svn", "visual-studio", "atom emacs", "textmate", "hg"));
        tagDTOS.add(tool);
        return tagDTOS;
    }

    public static String filterInvalid(String tags) {//找出无效的标签
        String[] split = StringUtils.split(tags, ",");
        List<TagsDTO> tagDTOS = get();
        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTagsName().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> StringUtils.isBlank(t) || !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }
}

