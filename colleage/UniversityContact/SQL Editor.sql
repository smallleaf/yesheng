create database colleageassistant;
use colleageassistant;
create table user
(
       username varchar(20) PRIMARY KEY ,
       password varchar(200) not null,
       university varchar(200),
       major varchar(100),
       job varchar(100),
       address varchar(300),
       hobby1 varchar(50),
       hobby2 varchar(50),
       hobby3 varchar(50),
       associatition varchar(200) 
)

create table hot_news
(
       id int AUTO_INCREMENT,
       title varchar(100) not null,
       colleage varchar(100) not null,
       publicTime varchar(20) not null,
       commentCounts int not null,
       rightpicture blob,
       picture1 blob,
       picture2 blob,
       picture3 blob,
       news_id int not null,
       public_user_kind int not null,
       PRIMARY Key(id)
)

alter table hot_news add content varchar(2000);
drop table hot_news;


create table news_kind(
       id int primary key,
       name varchar(20) not null
)

drop table news_kind;

insert into  news_kind VALUES (0,'推荐');
insert into  news_kind VALUES (1,'热点');
insert into  news_kind VALUES (2,'学习');
insert into  news_kind VALUES (3,'课程');
insert into  news_kind VALUES (4,'活动');
insert into  news_kind VALUES (5,'新闻');
insert into  news_kind VALUES (6,'社团');
insert into  news_kind VALUES (7,'娱乐');


create table user_public_news(
       id int AUTO_INCREMENT,
       title varchar(30) not null,
       content varchar(2000) not null,
       news_kind_id int NOT null,
       picture1 blob ,
       picture2 blob,
       picture3 blob,
       PRIMARY Key(id),
      CONSTRAINT news_id_FK FOREIGN Key (news_kind_id) REFERENCES news_kind(id)
)
drop table user_public_news;
alter table user_public_news add public_time varchar(40) not null;
create TABLE store_file
(
       id int PRIMARY key AUTO_INCREMENT,
       name varchar(200),
       CONTENTS blob
)

/*用户发布还是系统推送*/
create table public_user_kind
(
       id int  PRIMARY key,
       name varchar(20) not null
)
insert into public_user_kind VALUES(0,'系统');
insert into public_user_kind VALUES(1,'用户');

/*用户发表评论*/
create table public_comment_table(
        id int AUTO_INCREMENT,
        username varchar(20) not null,
        comment_time varchar(30) not null,
        comment_content varchar(3000) not null,
        new_id int not null,
        PRIMARY key(id),
        CONSTRAINT comment_news_id_FK FOREIGN Key (new_id) REFERENCES hot_news(id)
);
drop table public_comment_table;

create table comment_reply_table(
       id int AUTO_INCREMENT,
       username varchar(20) not null,
       content varchar(3000) not null,
       comment_id int not null,
       comment_reply_id int DEFAULT 0,
       PRIMARY key (id),
       CONSTRAINT comment_Reply_id_FK FOREIGN Key (comment_id) REFERENCES public_comment_table(id)
)