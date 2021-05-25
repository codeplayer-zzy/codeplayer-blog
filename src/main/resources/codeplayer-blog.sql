#article表
create table if not exists article
(
    article_id    bigint auto_increment comment '主键id'
        primary key,
    title         varchar(256) null comment '文章标题',
    content       longtext     null comment '文章内容',
    creator       bigint       null comment '文章发布人id',
    comment_count bigint       null comment '文章回答数',
    view_count    bigint       null comment '文章阅读数',
    like_count    bigint       null comment '文章点赞数',
    status        int          null comment '文章状态：0草稿 1发布 2回收站',
    tag           varchar(256) null comment '文章标签',
    gmt_create    datetime     null comment '文章创建时间',
    gmt_modified  datetime     null comment '文章最近编辑时间'
)
    comment '文章';

#comment表
create table if not exists comment
(
	comment_id bigint auto_increment comment '评论id'
		primary key,
	parent_id bigint not null comment '文章or评论的id',
	content text null comment '评论内容',
	gmt_create datetime null comment '创建时间',
	gmt_modified datetime null comment '修改时间',
	type int null comment '评论类型',
	like_count bigint null comment '点赞数',
	comment_count bigint null comment '评论数',
	commentator bigint null comment '评论人id'
)
    comment '评论';
#notification表
create table if not exists notification
(
	notification_id bigint auto_increment comment '消息id'
		primary key,
	notifier bigint not null comment '消息发送人',
	notifier_name varchar(50) null comment '消息人name',
	receiver bigint not null comment '消息接收者',
	outer_id bigint not null comment '消息回复的文章 笔记 评论的id',
	outer_title varchar(256) null comment '消息回复的文章 评论 笔记的title',
	type int not null comment '消息回复的是文章or评论or笔记的id',
	status int default 0 not null comment '已读 未读',
	gmt_create datetime null comment '创建时间'
)
    comment '通知';
#user表
create table if not exists user
(
	user_id bigint auto_increment comment '用户id'
		primary key,
	name varchar(50) null comment '名字',
	password varchar(50) null comment '密码',
	sex varchar(20) null comment '性别',
	address varchar(50) null comment '地址',
	email varchar(50) null comment '邮箱',
	birthday varchar(50) null comment '生日',
	phone varchar(20) null comment '电话号码',
	gmt_create datetime null comment '创建时间',
	gmt_modified datetime null comment '修改时间',
	token varchar(36) null comment '自定义token',
	avatar_url varchar(500) null comment '头像地址',
	github_account_id varchar(100) null comment 'github账号id'
)
    comment '用户';
# 管理员用户
INSERT INTO `codeplayer-blog`.user (user_id, name, password, sex, address, email, birthday, phone, gmt_create, gmt_modified, token, avatar_url, github_account_id) VALUES (1, 'codeplayer', '123456', '男', 'xxx', 'xxx', '1999.12.24', 'xxx', '2021-04-27 06:11:29', '2021-05-25 04:38:13', 'd3cae782-a3ea-430f-8326-fdc088c75419', 'https://avatars.githubusercontent.com/u/59161468?v=4', 'xxx');
#测试数据
INSERT INTO `codeplayer-blog`.article (article_id, title, content, creator, comment_count, view_count, like_count, status, tag, gmt_create, gmt_modified) VALUES (146, '测试11', '111111', 1, 0, 1, 0, 2, 'html', '2021-05-25 05:39:51', '2021-05-25 05:39:51');
INSERT INTO `codeplayer-blog`.article (article_id, title, content, creator, comment_count, view_count, like_count, status, tag, gmt_create, gmt_modified) VALUES (147, '测试22', '2222', 1, 0, 1, 0, 2, 'java', '2021-05-25 05:41:53', '2021-05-25 05:41:53');
INSERT INTO `codeplayer-blog`.article (article_id, title, content, creator, comment_count, view_count, like_count, status, tag, gmt_create, gmt_modified) VALUES (148, '测试33', '333333', 1, 0, 0, 0, 2, 'css', '2021-05-25 05:42:14', '2021-05-25 05:42:14');
INSERT INTO `codeplayer-blog`.article (article_id, title, content, creator, comment_count, view_count, like_count, status, tag, gmt_create, gmt_modified) VALUES (149, '测试44', '44444', 1, 0, 0, 0, 2, 'html', '2021-05-25 05:42:28', '2021-05-25 05:42:28');
INSERT INTO `codeplayer-blog`.article (article_id, title, content, creator, comment_count, view_count, like_count, status, tag, gmt_create, gmt_modified) VALUES (150, '测试55', '5555', 1, 0, 0, 0, 2, 'java,shell,swift', '2021-05-25 05:43:22', '2021-05-25 05:43:22');
INSERT INTO `codeplayer-blog`.article (article_id, title, content, creator, comment_count, view_count, like_count, status, tag, gmt_create, gmt_modified) VALUES (151, '测试66', '66666', 1, 0, 0, 0, 2, 'linux,nginx', '2021-05-25 05:50:25', '2021-05-25 05:50:25');
