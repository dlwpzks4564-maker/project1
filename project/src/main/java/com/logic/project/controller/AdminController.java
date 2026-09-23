package com.logic.project.controller;

import com.logic.project.domain.Member;
import com.logic.project.domain.MemberRole;
import com.logic.project.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final MemberService memberService;

    //관리자 여부 확인
    private boolean isAdmin(HttpSession session) {
        Member member = (Member) session.getAttribute("loginMember"); //강제형변환
        return member != null && member.getRole() == MemberRole.ADMIN; //세션값 이
    }

    @GetMapping("/")//index.html admin(관리자)대시보드
    public String adminIndex(HttpSession session, Model model) {
        if (!isAdmin(session)) {
            return "redirect:/";
        }
        return "admin/index";
    }

    //회원 목록
    @GetMapping("/members")
    public String list(HttpSession session, Model model) {
        if (!isAdmin(session)) {
            return "redirect:/";
        }
        model.addAttribute("members", memberService.findAll());
        return "members/member/list";
    }

    //회원 상세
    @GetMapping("/{id}")
    public String Detail(@PathVariable("id") Long id, HttpSession session, Model model) {
        if (!isAdmin(session)) {
            return "redirect:/";
        }
        model.addAttribute("member", memberService.findById(id));
        return "admin/member/detail";
    }
    //회원 활성화
    @PostMapping("/{id}/activate")
    public String activate(@PathVariable("id")Long id, HttpSession session) {
        if(!isAdmin(session)) {
            return "redirect:/";
        }
        memberService.activate(id);
        return "redirect:/admin/"+id;
    }
    //회원 비활성화
    @PostMapping("/{id}/deactivate")
    public String deactivate(@PathVariable("id")Long id, HttpSession session){
        if (!isAdmin(session)) {
            return "redirect:/";
        }
        memberService.deactivate(id);
        return "redirect:/admin/"+id;
    }
    //회원 강퇴
    @PostMapping("/{id}/kick")
    public String kick(@PathVariable("id") Long id, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/";
        }
        Member target = memberService.findById(id);
        if (target.getRole() == MemberRole.ADMIN) {
            return "redirect:/admin/member/" + id + "error=admin";
        }
        //관리자 계정은 강퇴 방지
        memberService.kickoff(id);
        return "redirect:/admin/member/" + id;
    }
}








