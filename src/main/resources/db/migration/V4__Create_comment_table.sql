create table COMMENT
(
	ID BIGINT  auto_increment,
	PARENT_ID BIGINT not null,
	TYPE INTEGER not null,
	COMMENTATOR BIGINT not null,
	CONTENT VARCHAR(1024),
	COMMENT_COUNT INTEGER default 0,
	GMT_CREATE BIGINT,
	GMT_MODIFIED BIGINT,
	LIKE_COUNT INTEGER,
	constraint COMMENT_PK
		primary key (ID)
);

comment on column COMMENT.PARENT_ID is '父id';

comment on column COMMENT.TYPE is '类型';

comment on column COMMENT.COMMENTATOR is '评论人';

comment on column COMMENT.CONTENT is '评论内容';

comment on column COMMENT.COMMENT_COUNT is '评论数量';

comment on column COMMENT.GMT_CREATE is '创建时间';

comment on column COMMENT.GMT_MODIFIED is '修改时间';

