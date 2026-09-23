package com.logic.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    //index.html 리졸빙
    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("massage","메세지를 입력하세요.");
        return "index";
    }

}
