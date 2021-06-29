## CodePlayerBlog

## 实现功能
开源论坛系统，现有功能：提问、回复、通知、GitHub登录、搜索、热门标签、kaptcha 验证码、Gitee 图床。功能持续更新中...

## 增加功能
2021/5/21 ：增加了查看文章的最新、最热、待回复功能。  
2021/5/25 ：增加了文章草稿箱功能，删除文章功能，还完善了搜素功能以及profile页面。  
2021/5/26 ：增加了评论点赞功能，增加了草稿箱一键发布功能。  
2021/5/29 ：增加了底部。  

## 演示图片
[![](https://gitee.com/codeplayer-zzy/codeplayer-blog-table/raw/master//img/2021-05-20/1621489946035_cf4f81eddf3b453b997a10811d60409f.png)](http://https://gitee.com/zhangzuyi/codeplayer-blog-table/raw/master//img/2021-05-20/1621489946035_cf4f81eddf3b453b997a10811d60409f.png)
[![](https://gitee.com/codeplayer-zzy/codeplayer-blog-table/raw/master//img/2021-05-20/1621489973354_415ff14bcf6a49c6b0ebd78227b90fad.png)](http://https://gitee.com/zhangzuyi/codeplayer-blog-table/raw/master//img/2021-05-20/1621489973354_415ff14bcf6a49c6b0ebd78227b90fad.png)
[![](https://gitee.com/codeplayer-zzy/codeplayer-blog-table/raw/master//img/2021-05-20/1621490024016_935fea0eed57449fb90f7a0d007c719f.png)](http://https://gitee.com/zhangzuyi/codeplayer-blog-table/raw/master//img/2021-05-20/1621490024016_935fea0eed57449fb90f7a0d007c719f.png)
[![](https://gitee.com/codeplayer-zzy/codeplayer-blog-table/raw/master//img/2021-05-21/1621609840846_f5340d7fcde54e1499afdea5addedfcd.png)](http://https://gitee.com/zhangzuyi/codeplayer-blog-table/raw/master//img/2021-05-21/1621609840846_f5340d7fcde54e1499afdea5addedfcd.png)
[![](https://gitee.com/codeplayer-zzy/codeplayer-blog-table/raw/master//img/2021-05-20/1621490099345_b2f8c329ef1449449fc5b66c44f17e8e.png)](http://https://gitee.com/zhangzuyi/codeplayer-blog-table/raw/master//img/2021-05-20/1621490099345_b2f8c329ef1449449fc5b66c44f17e8e.png)
[![](https://gitee.com/codeplayer-zzy/codeplayer-blog-table/raw/master//img/2021-05-25/1621922007210_759d6e6e309c43558e36955932259813.png)](http://https://gitee.com/zhangzuyi/codeplayer-blog-table/raw/master//img/2021-05-25/1621922007210_759d6e6e309c43558e36955932259813.png)
[![](https://gitee.com/codeplayer-zzy/codeplayer-blog-table/raw/master//img/2021-05-25/1621922076336_ba65d09909e84c3b836a200a8246460a.png)](http://https://gitee.com/zhangzuyi/codeplayer-blog-table/raw/master//img/2021-05-25/1621922076336_ba65d09909e84c3b836a200a8246460a.png)

## 使用技术
| 后端技术 | 链接 |
| ------------ | ------------ |
| Java 11  | https://www.oracle.com/java/technologies/javase-downloads.html  |
|  Spring Boot 2.4.4 |  http://projects.spring.io/spring-boot/#quick-start  |
| MyBatis |  https://mybatis.org/mybatis-3/zh/index.html  |
| MySQL  | https://www.runoob.com/mysql/mysql-tutorial.html  |
| Lombok  | https://www.projectlombok.org  |
| GitHub OAuth  | https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/  |
| Editor.md  | https://pandao.github.io/editor.md/  |
| kaptcha  | https://github.com/penggle/kaptcha  |
| Elasticsearch 7.9.3 | https://www.elastic.co/cn/downloads/past-releases  |
| RabbitMQ 3.8.14 | https://github.com/rabbitmq/rabbitmq-server/releases  |

| 前端技术 | 链接 |
| ------------ | ------------ |
|  BootStrap v3 |  https://v3.bootcss.com/  |
|  jQuery |  https://jquery.com/download/  |
|  moment.js |  https://momentjs.com/  |
|  jQuery ajax |  https://www.jquery123.com/category/ajax/global-ajax-event-handlers/  |
|  Gitee 图床 |  https://gitee.com/zhangzuyi/codeplayer-blog-table |

## 本地运行手册
1. 安装必备工具
JDK 11 （安装JDK8也可运行，但是Elasticsearch不能运行）、IDEA或其他、MYSQL
2. 克隆代码到本地
```sh
git clone https://github.com/codeplayer-zzy/codeplayer-blog.git
````
3. 将resources目录下的codeplayer-blog.sql导入新创建的数据库
4. 将application.yml文件配置好
5. 将com.codeplayer.config目录下的GiteeImageBedConfig 类配置好（Gitee图床，可省略）
6. 运行项目
7. 访问项目
```
http://localhost:80
```

## 感谢
码匠笔记[https://www.mawen.co/](https://www.mawen.co/)
学习 java 推荐看 b站 搜：码匠笔记

## 联系我
作者非专业出身，目前大二，自学java，目前在简书发表了几篇文章，有兴趣的可以关注一波。  
简书搜：CodePr  
有任何问题可以加我QQ2775152443，同样也欢迎一起交流学习。



