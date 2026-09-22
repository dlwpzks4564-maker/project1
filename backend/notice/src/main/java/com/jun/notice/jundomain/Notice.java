package com.jun.notice.jundomain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="notice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notice {  //데이터 베이스 내용 설계
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @Column(nullable = false, length = 200)
    private String title;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    @Column(nullable = false, length = 50)
    @Builder.Default  // 빌드 패턴 쓸때 초기화
    private String author = "admin";
    @Column(nullable = false)
    @Builder.Default
    private Long hits = 0L; //글 읽은횟수
    //createdAt => created_At / 데이터베이스 내용안의 기제
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @PrePersist
    void prePersist() {
        if(createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if(hits == null) { //hits 의 값이 0일때 널값을 대입해라.
            hits = 0L;
        }
        if(author == null){   // author 값이 널값이라면 author 는 admin 이다.
            author = "admin";
        }
    }
}
