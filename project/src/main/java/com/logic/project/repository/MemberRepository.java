package com.logic.project.repository;

import com.logic.project.domain.Member;
import com.logic.project.domain.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByLoginId(String loginId); //로그인(loginId) 조회
    Optional<Member> findByEmail(String email); //이메일 조회(email)
    boolean existsByLoginId(String loginId); //로그인아이디 loginId 존재여부
    boolean existsByEmail(String email); //이메일 email 존재여부
    List<Member> findAllByOrderByIdDesc(); //회원목록(회원번호의 내림차순 정렬하여 조회)
    List<Member> findByStatusOrderByIdDesc(MemberStatus status); //회원목록(상태별로 회원번호 내림차순 정렬하여 조회)
    List<Member> findTop5ByOrderByIdDesc();
}
