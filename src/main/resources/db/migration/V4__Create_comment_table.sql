create table COMMENT
(
	ID BIGINT,
	PARENT_ID BIGINT not null,
	TYPE INTEGER not null,
	COMMENTATOR BIGINT not null,
	CONTENT VARCHAR(1024),
	COMMENT_COUNT INTEGER default 0,
	constraint COMMENT_PK
		primary key (ID)
);

comment on column COMMENT.PARENT_ID is '父id';

comment on column COMMENT.COMMENT_COUNT is '评论数量';

