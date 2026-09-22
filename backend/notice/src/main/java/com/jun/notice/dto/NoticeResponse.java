package com.jun.notice.dto;
//데이터 보낼 목적
import com.jun.notice.jundomain.Notice;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NoticeResponse {
    private Long id;
    private String title;
    private String content;
    private String author;
    private Long hits;
    private LocalDateTime createdAt;

    //내부에서 Notice = > NoticeResponse(반환타입)
    public static NoticeResponse from(Notice notice) {
        return NoticeResponse.builder()    //빌드패던 => 나중에 from 값만 호출해서 사용
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .author(notice.getAuthor())
                .hits(notice.getHits())
                .createdAt(notice.getCreatedAt())
                .build();
    }
}
