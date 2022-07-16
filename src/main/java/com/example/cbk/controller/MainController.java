package com.example.cbk.controller;

import com.example.cbk.service.CashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {

    private final CashService cashService;

    @Autowired
    public MainController(CashService cashService) {
        this.cashService = cashService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/")
    public String getAdminPage() {
        return "main/admin";
    }

}
