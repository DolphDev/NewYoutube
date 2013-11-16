# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table comment (
  id                        integer not null,
  content                   varchar(255),
  likes                     integer,
  dislikes                  integer,
  posted                    timestamp,
  author_id                 integer,
  video_id                  varchar(255),
  constraint pk_comment primary key (id))
;

create table tag (
  name                      varchar(255) not null,
  constraint pk_tag primary key (name))
;

create table user (
  id                        integer not null,
  username                  varchar(255),
  password                  varchar(255),
  email                     varchar(255),
  custom_css                varchar(255),
  created                   timestamp,
  constraint pk_user primary key (id))
;

create table video (
  id                        varchar(255) not null,
  title                     varchar(255),
  description               varchar(255),
  likes                     integer,
  dislikes                  integer,
  uploaded                  timestamp,
  uploader_id               integer,
  thumbnail_link            varchar(255),
  constraint pk_video primary key (id))
;

create table video_file (
  id                        integer not null,
  link                      varchar(255),
  mimetype                  varchar(255),
  video_id                  varchar(255),
  constraint pk_video_file primary key (id))
;


create table tag_video (
  tag_name                       varchar(255) not null,
  video_id                       varchar(255) not null,
  constraint pk_tag_video primary key (tag_name, video_id))
;
create sequence comment_seq;

create sequence tag_seq;

create sequence user_seq;

create sequence video_seq;

create sequence video_file_seq;

alter table comment add constraint fk_comment_author_1 foreign key (author_id) references user (id) on delete restrict on update restrict;
create index ix_comment_author_1 on comment (author_id);
alter table comment add constraint fk_comment_video_2 foreign key (video_id) references video (id) on delete restrict on update restrict;
create index ix_comment_video_2 on comment (video_id);
alter table video add constraint fk_video_uploader_3 foreign key (uploader_id) references user (id) on delete restrict on update restrict;
create index ix_video_uploader_3 on video (uploader_id);
alter table video_file add constraint fk_video_file_video_4 foreign key (video_id) references video (id) on delete restrict on update restrict;
create index ix_video_file_video_4 on video_file (video_id);



alter table tag_video add constraint fk_tag_video_tag_01 foreign key (tag_name) references tag (name) on delete restrict on update restrict;

alter table tag_video add constraint fk_tag_video_video_02 foreign key (video_id) references video (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists comment;

drop table if exists tag;

drop table if exists tag_video;

drop table if exists user;

drop table if exists video;

drop table if exists video_file;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists comment_seq;

drop sequence if exists tag_seq;

drop sequence if exists user_seq;

drop sequence if exists video_seq;

drop sequence if exists video_file_seq;

