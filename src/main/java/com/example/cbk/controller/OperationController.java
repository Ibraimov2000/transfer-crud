package com.example.cbk.controller;

import com.example.cbk.entity.Transfer;
import com.example.cbk.service.TransferService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class OperationController {

    private final TransferService transferService;

    @Autowired
    public OperationController(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping("/operation")
    public String getOperationPage() {
        return "/useroperations/operation";
    }

    @GetMapping("/operation/send")
    public String getSendPage(Model model) {
        model.addAttribute("transfer", new Transfer());
        return "/useroperations/sendMoney";
    }

    @PostMapping("/sendMoney")
    public String addTransfer(@ModelAttribute("transfer") @Valid Transfer transfer, @NotNull BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "/admin/transfer/add";
        }
        transferService.create(transfer);
        return "redirect:/admin/transfer/transfers";
    }

    @GetMapping("/operation/take")
    public String getTakePage() {
        return "/useroperations/takeMoney";
    }
}
