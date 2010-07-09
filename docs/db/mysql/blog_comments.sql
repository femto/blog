DROP TABLE IF EXISTS comments;

CREATE TABLE comments (
  id int(11) NOT NULL auto_increment,
  commenter  varchar(255),
  body       text,
  post_id    int(11),
  created_at timestamp,
  updated_at timestamp,
  PRIMARY KEY  (id)
) ENGINE=InnoDB;