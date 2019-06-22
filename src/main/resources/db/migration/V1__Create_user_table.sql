create table USER
(
	ID BIGINT primary key,
	ACCOUNT_ID VARCHAR(100),
	NAME VARCHAR(50),
	TOKEN VARCHAR(36),
	GMT_CREATE BIGINT,
	GMT_MODIFIED BIGINT,
	BIO VARCHAR(256),
	AVATAR_URL VARCHAR(100)
);

comment on column USER.ACCOUNT_ID is '账号id';

comment on column USER.NAME is '账号名称';

comment on column USER.TOKEN is 'token';

comment on column USER.GMT_CREATE is '创建';

comment on column USER.GMT_MODIFIED is '修改';

comment on column USER.BIO is '个性名称';

comment on column USER.AVATAR_URL is '头像url';

