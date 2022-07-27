package com.example.cbk.controller;

import com.example.cbk.entity.*;
import com.example.cbk.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;


@Controller
public class MainController {

    private final CashService cashService;
    private final UserServiceImpl userService;
    private final TransferService transferService;
    private final RoleService roleService;
    private final CurrencyService currencyService;

    @Autowired
    public MainController(CashService cashService, UserServiceImpl userService, TransferService transferService, RoleService roleService, CurrencyService currencyService) {
        this.cashService = cashService;
        this.userService = userService;
        this.transferService = transferService;
        this.roleService = roleService;
        this.currencyService = currencyService;
    }


    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/")
    public String getAdminPage(Model model, @Param("keyword") Long keyword) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("authorizedUser", userDetails);
        model.addAttribute("newUser", new User());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("transfers", transferService.search(keyword));
        model.addAttribute("newTransfer", new Transfer());
        model.addAttribute("cashes", cashService.getAll());
        model.addAttribute("newCash", new Cash());
        model.addAttribute("currencies", currencyService.getAll());
        model.addAttribute("newCurrency", new Currency());
        model.addAttribute("keyword", keyword);
        return "admin";
    }


    //TRANSFER


    @PostMapping("/transfer/addTransfer")
    public String addTransfer(@ModelAttribute("transfer") Transfer transfer, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "admin";
        }

        transfer.setStatus("СОЗДАН");
        transfer.setUnicCode(User.getCode());
        transferService.create(transfer);
        User user = userService.getById(transfer.getSender().getId());
        user.setUnicCode(transfer.getUnicCode());
        user.setTransferId(transfer.getId());
        userService.update(user, transfer.getSender().getId());

        return "redirect:/";
    }

    @PostMapping("/transfer/update")
    public String updateTransfer(@ModelAttribute Transfer transfer) {
        transferService.update(transfer, transfer.getId());
        return "redirect:/";
    }

    @PostMapping("/transfer/getTransfer")
    public String getTransfer(@ModelAttribute Transfer transfer) {

        User account = userService.getById(transfer.getSender().getId());
        Transfer transfer1 = transferService.getById(account.getTransferId());

        if (transfer.getSender() == transfer1.getSender() &&
                transfer.getRecipient() == transfer1.getRecipient() &&
                Objects.equals(transfer.getAmount(), transfer1.getAmount()) &&
                transfer.getCurrency() == transfer1.getCurrency() &&
                Objects.equals(transfer.getUnicCode(), transfer1.getUnicCode())) {

            Cash cash = cashService.getById(transfer1.getCash().getId());
            long balance = (cash.getBalance() - transfer.getAmount());
            cash.setBalance(balance);
            cashService.update(cash, transfer1.getCash().getId());

            Cash cash1 = cashService.getById(transfer.getCash().getId());
            balance = (cash1.getBalance() + transfer.getAmount());
            cash1.setBalance(balance);
            cashService.update(cash1, transfer.getCash().getId());
            transfer1.setStatus("ВЫДАН");
            transferService.update(transfer1, account.getTransferId());
            return "redirect:/";
        } else {
            return "/";
        }
    }

    @PostMapping("/transfer/delete/{id}")
    public String deleteTransfer(@PathVariable("id") Long id) {
        transferService.deleteById(id);
        return "redirect:/";
    }


    //USER

    @PostMapping("/user/update")
    public String updateUser(@ModelAttribute User user) {
        userService.update(user, user.getId());
        return "redirect:/";
    }

    @PostMapping("/user/addUser")
    public String addUser(@ModelAttribute User user) {
        userService.createUser(user);
        return "redirect:/";
    }

    @PostMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/";
    }


    //CASH

    @PostMapping("/cash/addCash")
    public String addCash(@ModelAttribute Cash cash) {
        cashService.create(cash);
        return "redirect:/";
    }

    @PostMapping("/cash/delete/{id}")
    public String deleteCash(@PathVariable("id") Long id) {
        cashService.deleteById(id);
        return "redirect:/";
    }


    @PostMapping(value = "/cash/update")
    public String updateCash(@ModelAttribute Cash cash) {
        cashService.update(cash, cash.getId());
        return "redirect:/";
    }

    //CURRENCY

    @PostMapping("/currency/addCurrency")
    public String addCurrency(@ModelAttribute Currency currency) {
        currencyService.create(currency);
        return "redirect:/";
    }

    @PostMapping("/currency/delete/{id}")
    public String deleteCurrency(@PathVariable("id") Long id) {
        currencyService.deleteById(id);
        return "redirect:/";
    }


    @PostMapping(value = "/currency/update")
    public String updateCurrency(@ModelAttribute Currency currency) {
        currencyService.update(currency, currency.getId());
        return "redirect:/";
    }
}
