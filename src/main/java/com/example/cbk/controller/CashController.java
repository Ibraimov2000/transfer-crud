package com.example.cbk.controller;

import com.example.cbk.entity.Cash;
import com.example.cbk.service.CashService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/cash")
public class CashController {

    private final CashService cashService;

    @Autowired
    public CashController(CashService cashService) {
        this.cashService = cashService;
    }

    @GetMapping("/cashes")
    public String getCashes(Model model) {
        model.addAttribute("cashes", cashService.getAll());
        return "/admin/cash/cashes";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("cash", cashService.getById(id));
        return "/admin/cash/cash";
    }

    @GetMapping("/add")
    public String addCashFrom(Model model) {
        model.addAttribute("cash", new Cash());
        return "/admin/cash/add";
    }

    @PostMapping("/addCash")
    public String addCash(@ModelAttribute("cash") @Valid Cash cash, @NotNull BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "/admin/cash/add";
        }
        cashService.create(cash);
        return "redirect:/cash/cashes";
    }

    @GetMapping("/{id}/delete")
    public String deleteCash(@PathVariable("id") Long id) {
        cashService.deleteById(id);
        return "redirect:/cash/cashes";
    }

    @GetMapping("/{id}/edit")
    public String editCash(@PathVariable("id") Long id, Model model) {
        model.addAttribute("cash", cashService.getById(id));
        return "/admin/cash/edit";
    }

    @PostMapping(value = "/{id}/update")
    public String update(@ModelAttribute("cash") @Valid Cash cash, BindingResult bindingResult, @PathVariable("id") Long id) {
        if(bindingResult.hasErrors()){
            return "/admin/cash/edit";
        }
        cashService.update(cash, id);
        return "redirect:/cash/cashes";
    }
}
