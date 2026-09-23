package com.logic.project.controller;

import com.logic.project.domain.Member;
import com.logic.project.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    public final MemberService memberService;

    //로그인 화면 로딩
    @GetMapping("/login")
    public String loginForm() {
        return "member/login"; //member 폴더안에 있는 login.html 를 검증하겠다.
    }

    //로그인 처리
    @PostMapping("/login")
    public String login(
            @RequestParam("loginId") String loginId,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) { //setAttribute 의 값을 추가대입
        try {
            Member member = memberService.login(loginId, password);
            //session 에 데이터 값을 저장하면 로그인처리 session 에 회원정보를 저장.
            //로그인 멤버 의 정보가 null 값이 아닐경우  session -> setAttribute 값을 대입
            session.setAttribute("loginMember", member);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "member/login"; //로그인의 실패하면 error 메세지를 띄우면서 member 에 있는 login 페이지로 리턴할거다.
        }
    }

    //로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    //약관 화면 로딩
    @GetMapping("/terms")
    public String terms() {
        return "member/terms";
    }

    //회원 가입 화면 로딩
    @GetMapping("/signup")
    public String signupForm() {
        return "member/signup";
    }

    //회원 가입 처리
    @PostMapping("/signup")
    public String signup(@ModelAttribute Member member, Model model) {
        try {
            memberService.signup(member);
           return "redirect:/members/login?signupSuccess=true";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "member/signup";
    }
}

    /*
     //마이 페이지
    @GetMapping("/mypage")
    public String mypage() {
    return "member/mypage";
        }

    //회원 정보 수정 폼 로딩
    @GetMapping("/edit")


    //회원 정보 수정 처리
    @PostMapping("/edit")


    //회원 탈퇴 화면 로딩
    @GetMapping("/delete")


    //회원 탈퇴 처리
    @PostMapping("/delete")
}
*/

