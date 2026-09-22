package com.jun.notice.controller;

import com.jun.notice.dto.NoticeRequest;
import com.jun.notice.dto.NoticeResponse;
import com.jun.notice.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeRestController {
    private final NoticeService noticeService;
    //생성시에는 : 201 => HttpStatus.CREATED
    //삭제시에는 : 204 => HttpStatus.NO_CONTENT
    //조회/수정시에는 : 200 => HttpStatus.OK
    //글 목록 (GET) : http://localhost:8081/api/notices")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NoticeResponse> list() {
        return noticeService.findAll();
    }
    /*
    // 글 목록 (GET) : http://localhost:8081/api/notices/v2")
     @GetMapping("/v2")
     public List<Notice> list2() {
         return noticeService.findNoticeAll();
      }
*/
    //글 상세보기 (GET) http://localhost:8081/api/notices/1
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NoticeResponse detail(@PathVariable Long id) {
        return noticeService.findById(id);
    }
    /*
    // 글 상세보기 (GET) http://localhost:8081/api/notices/V2/1
     @GetMapping("/v2/{id}")
     public Notice detail2(@PathVariable Long id) {
         return noticeService.findById(id);
       }
     */
    //글 등록 (POST) http://localhost:8081/api/notices/ form { "title : "제목", "content":"내용1", "author"
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NoticeResponse create(@Valid @RequestBody NoticeRequest request) {//폼 검증
        return noticeService.create(request);

    }
    /*
     @PostMapping("/v2/{id}")
     public NoticeResponse create2(@Valid @RequestBody NoticeRequest request)
         return noticeService.create(request);
       }
     */
    //글 수정
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NoticeResponse update(@PathVariable("id")Long id,
                                 @Valid @RequestBody NoticeRequest request) {
        return noticeService.update(id, request);
}
    /*
    @PutMapping("/v2/{id}")
    public NoticeResponse update(@PathVariable("id")Long id,
                                 @Valid @RequestBody NoticeRequest request) {
        return noticeService.update(id, request);
     }
      */
    //글 삭제
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id){
        noticeService.delete(id);
    }
    /*
      //글 삭제
    @DeleteMapping("/v2/{id}")
    public void delete(@PathVariable("id") Long id){
        noticeService.delete(id);
    }
     */
}
