create table NOTIFICATION
(
	ID BIGINT  IDENTITY(1,1),
	NOTIFIER BIGINT not null,
	RECEIVER BIGINT not null,
	OUTER_ID BIGINT not null,
	TYPE INTEGER not null,
	STATUS INTEGER default 0 not null,
	NOTIFIER_NAME VARCHAR(100),
	OUTER_TITLE VARCHAR(256),
	GMT_CREATE BIGINT,
	GMT_MODIFIED BIGINT,
	constraint NOTIFICATION_PK
		primary key (ID)
);

comment on column NOTIFICATION.ID is '主键';

comment on column NOTIFICATION.NOTIFIER is '通知者';

comment on column NOTIFICATION.RECEIVER is '接收者';

comment on column NOTIFICATION.OUTER_ID is '外部id';

comment on column NOTIFICATION.TYPE is '通知类型';

comment on column NOTIFICATION.STATUS is '状态';

