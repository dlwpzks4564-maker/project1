package com.jun.notice.service;
import com.jun.notice.dto.NoticeRequest;
import com.jun.notice.dto.NoticeResponse;
import com.jun.notice.jundomain.Notice;
import com.jun.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Service
@RequiredArgsConstructor // 생성자주입 하지않으면 선언해줘야한다.
@Transactional(readOnly = true) //데이터변경이나 , 상세보기 할 항목에 증가해야할때 Transactional 를 사용.
public class NoticeService {
    private final NoticeRepository noticeRepository;

    //목록 추가
    public List<NoticeResponse> findAll() {
        //noticeRepository 를 map으로 변환 후 stream를 이용해서 NoticeResponse 를
        //from 이라는 객체를 이용해서 toList 로 반환해주겠다. 클론 두개 사용 기울기꼴 메서는 static 메서드
        return noticeRepository.findAll(org.springframework.data.domain.Sort.by
                        (Sort.Direction.DESC, "id"))//id 값을 도메인 값으로 받아서 내림차순(DESC)으로 받겠다.
                .stream().map(NoticeResponse::from).toList();
    }
    //상세보기 데이터변화가 있는 항목엔 @Transactional 파라미터 를 사용해줘라.
    @Transactional //데이터상세보기!
    public NoticeResponse findById(Long id) {
        Notice notice = noticeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(id + "공지사항을 찾을수없습니다.")
        );
        //읽은 횟수 증가(NoticeRepository)를 이용한 실제 DB, 엔티티 두개다)
        noticeRepository.increaseHits(id);
        notice.setHits(notice.getHits() + 1);
        //Notice => NoticeRepository 로 변환 데이터 단건 의 대해
        return NoticeResponse.from(notice);
        //
    }
    //글 등록
    @Transactional //변화가 있는 항목엔 @Transactional 파라미터 를 사용해줘라.
    public NoticeResponse create(NoticeRequest request){  //데이터베이스 에 데이터를 집어넣겠다.
        Notice notice = Notice.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .author(request.getAuthor())
                .hits(0L) // 0 의 값을 받을거면 넣어서 값을 저장해준다.
                .build();
        return NoticeResponse.from(noticeRepository.save(notice));  //return 를 사용하면 Notice 값을 받기때문에, notice NoticeResponse 반환 해줘야한다.

    }
    //글 수정 findById(id) * save(notice) 중요
    @Transactional //변화가 있는 항목엔 @Transactional 파라미터 를 사용해줘라.
    public NoticeResponse update(Long id, NoticeRequest request) {
        Notice notice = noticeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(id+"번글을 찾을수없습니다.")
        );
        //noticeRequest 타입을 => Notice 타입으로 반환
        notice.setTitle(request.getTitle());
        notice.setContent(request.getContent());
        notice.setAuthor(request.getAuthor());
        Notice n = noticeRepository.save(notice);
        //Notice = n 받은 값을 noticeResponse 로 변환 from 메서드를 이용해서
        return  NoticeResponse.from(n);
    }
    //글 삭제
    @Transactional //변화가 있는 항목엔 @Transactional 파라미터 를 사용해줘라.
    public void delete(Long id) { //메서드 이름은 자유지만 나중에 컨트롤러에서 같이 값을 받아야한다.
        noticeRepository.deleteById(id);
    }

    /* ---------------여기서부터는 DTO 없이 진행하는 메서드 ------------------------*/
    /* 글 목록 Dto(NoticeResponse) 쓰지않고 데이터 받는 방법
      public List<Notice> findNoticeAll() {
      return NoticeRepository.findAll();
      }
      글 목록 Dto(NoticeResponse) 쓰지않고 데이터 받는 방법
      public Notice findNoticeById(Long id) {
        return noticeRepository.findById(id).orElseThrow(
            () -> new IllegalArgumentException(id+"번 글이 없습니다.")
       );
      글 등록 - DTO(NoticeRequest, NoticeResponse) 사용하지 않고
       public Notice saveNotice(Notice notice) {
        return noticeRepository.save(notice);
       }
       글 수정 - DTO(NoticeRequest, NoticeResponse) 사용하지 않고
       public Notice updateNotice(Long id, Notice notice) {
          Notice noti = noticeRepository.findById(id).orElseThrow(
            () -> new IllegalArgumentException(id+"번 글이 없습니다.")
        );
        noti.setTitle(notice.getTitle());
        noti.setContent(notice.getContent());
        noti.setAuthor(notice.getAuthor());
        return noticeRepository.save(noti);
        }
        글 삭제 - DTO(NoticeRequest, NoticeResponse) 사용하지 않고
        public void delete(Long id) { //메서드 이름은 자유지만 나중에 컨트롤러에서 같이 값을 받아야한다.
        noticeRepository.deleteById(id);
     */
}
