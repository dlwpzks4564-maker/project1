CREATE TABLE business ( 
id BIGINT PRIMARY KEY,
title VARCHAR(200)NOT NULL,
content TEXT NOT NULL,
author VARCHAR(50)NOT NULL,
hits BIGINT NOT NULL. DEFAULT 0,
created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP 
);


-- 모든 필드를 지정하여 데이터 추가
INSERT INTO notice (title, content, author, hits, created_at)
VALUES ('첫 번째 공지사항', '공지사항 내용입니다.', 'admin', 0, NOW());

INSERT INTO notice (title, content, author, hits, created_at)
VALUES ('두 번째 공지사항', '내용입니다.', DEFAULT, DEFAULT, NOW());

SELECT id, title, author, hits, created_at
FROM notice
ORDER BY created_at DESC;

SELECT id, title, content, author, hits, created_at
FROM notice
WHERE id = 1;

SELECT *
FROM notice
WHERE title LIKE '%공지%' OR content LIKE '%공지%';

UPDATE notice
SET title = '수정된 공지사항 제목',
    content = '수정된 공지사항 내용입니다.'
WHERE id = 1;

UPDATE notice
SET hits = hits + 1
WHERE id = 1;

-- 특정 ID의 공지사항 삭제
DELETE FROM notice
WHERE id = 1;

select * from notice;
select * from notice order by id DESC;
select id, title, content from notice;



