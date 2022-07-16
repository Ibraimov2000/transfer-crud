package com.example.cbk.controller;

import com.example.cbk.entity.Account;
import com.example.cbk.service.AccountService;
import com.example.cbk.service.RoleService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final RoleService roleService;

    @Autowired
    public AccountController(AccountService accountService, RoleService roleService) {
        this.accountService = accountService;
        this.roleService = roleService;
    }

    @GetMapping("/accounts")
    public String getAccounts(Model model) {
        model.addAttribute("accounts", accountService.getAll());
        return "admin/account/accounts";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("account", accountService.getById(id));
        return "admin/account/account";
    }

    @GetMapping("/add")
    public String addUserFrom(Model model) {
        model.addAttribute("account", new Account());
        return "admin/account/add";
    }

    @PostMapping("/addAccount")
    public String addAccount(@ModelAttribute("account") @Valid Account account, @NotNull BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "admin/account/add";
        }
        accountService.create(account);
        return "redirect:/account/accounts";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        accountService.deleteById(id);
        return "redirect:/account/accounts";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("account", accountService.getById(id));
        return "admin/account/edit";
    }

    @PostMapping("/{id}/update")
    public String update(@ModelAttribute("account") @Valid Account account, BindingResult bindingResult, @PathVariable("id") Long id) {
        if(bindingResult.hasErrors()){
            return "admin/account/edit";
        }
        accountService.update(account, id);
        return "redirect:/account/accounts";
    }
}
