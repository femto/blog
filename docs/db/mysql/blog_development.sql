DROP DATABASE IF EXISTS blog_development;

CREATE DATABASE blog_development DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE blog_development;

DROP TABLE IF EXISTS posts;

create table users (
id int(11) not null auto_increment,
name varchar(255),
password varchar(255),
email varchar(255),
is_admin boolean,
created_at timestamp,
  updated_at timestamp,
  PRIMARY KEY  (id)
) ENGINE=InnoDB;

CREATE TABLE posts (
  id int(11) NOT NULL auto_increment,
  name       varchar(255),
  title      varchar(255),
  content    text,
  --user_id    int(11),
  created_at timestamp,
  updated_at timestamp,
  PRIMARY KEY  (id)
) ENGINE=InnoDB;

alter table posts add column
(user_id int(11));

CREATE TABLE tags (
  id int(11) NOT NULL auto_increment,
  name       varchar(255) not null,
  created_at timestamp,
  updated_at timestamp,
  PRIMARY KEY  (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `post_tags` (
  `Post_id` bigint(20) NOT NULL,
  `tag_id` bigint(20) NOT NULL,
  PRIMARY KEY (`Post_id`,`tag_id`),
  KEY `FK30FE237B222C70F7` (`tag_id`),
  KEY `FK30FE237B388562DE` (`Post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `post_tag`
--


--
-- Constraints for dumped tables
--

--
-- Constraints for table `post_tag`
--
ALTER TABLE `post_tags`
  ADD CONSTRAINT `FK30FE237B388562DE` FOREIGN KEY (`Post_id`) REFERENCES `post` (`id`),
  ADD CONSTRAINT `FK30FE237B222C70F7` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`);