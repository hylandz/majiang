create table QUESTION
(
	ID BIGINT IDENTITY,
	TITLE VARCHAR(50),
	DESCRIPTION TEXT,
	COMMENT_COUNT INTEGER default 0,
	VIEW_COUNT INTEGER default 0,
	LIKE_COUNT INTEGER default 0,
	TAG VARCHAR(256),
	CREATOR BIGINT,
	GMT_CREATE BIGINT,
	GMT_MODIFIED BIGINT,
	constraint QUESTION_PK
		primary key (ID)
);

comment on column QUESTION.ID is '主键';

comment on column QUESTION.TITLE is '标题';

comment on column QUESTION.DESCRIPTION is '描述';

comment on column QUESTION.COMMENT_COUNT is '评论数';

comment on column QUESTION.VIEW_COUNT is '阅览数';

comment on column QUESTION.LIKE_COUNT is '点赞数';

comment on column QUESTION.TAG is '标签';

