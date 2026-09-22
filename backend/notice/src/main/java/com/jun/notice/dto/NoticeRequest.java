package com.jun.notice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//공지사항 받을 목적
@Getter
@Setter
@NoArgsConstructor  //비어있는 객체(데이터)가 던져주겠다.
@AllArgsConstructor //모든 데이터 안에 해당하는
public class NoticeRequest {
    @NotBlank(message="제목입력하세요.")
    @Size(max = 200, message = "제목은200자이내이여야 합니다.")
    private String title;
    @NotBlank(message = "내용을입력하세요.")
    private String content;
    @NotBlank(message = "작성자입력하세요.")
    @Size(max = 50, message = "작성자는 최소 50자이내 입력하셔야합니다.")
    private String author;
}
