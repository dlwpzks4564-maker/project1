package com.jun.notice.repository;

import com.jun.notice.jundomain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
                                     //JpaRepository 기본타입
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    @Modifying //기존메서드를 내가 수정해서 쓰겠다. 수정자 엔티티 이름을 넣어야 하기에 Notice 가 들어감.
    //엔티티 이름을 넣어야 하므로 Notice 선언 파라미터 값 을 앞에 선언해준 값에 id 값으로 들어가야한다.
    @Query("update Notice n set n.hits = n.hits + 1 where n.id = :id")
    int increaseHits(@Param("id")Long id);

}
