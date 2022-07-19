package com.example.cbk.controller;

import com.example.cbk.entity.Cash;
import com.example.cbk.entity.Transfer;
import com.example.cbk.entity.User;
import com.example.cbk.service.*;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/transfer")
public class TransferController {

    private final TransferService transferService;
    private final UserServiceImpl userService;
    private final CurrencyService currencyService;
    private final CashService cashService;

    @Autowired
    public TransferController(TransferService transferService, UserServiceImpl userService, CurrencyService currencyService, CashService cashService) {
        this.transferService = transferService;
        this.userService = userService;
        this.currencyService = currencyService;
        this.cashService = cashService;
    }

    @GetMapping("/transfers/{id}")
    public String getTransfers(Model model, @Param("keyword") String keyword, @PathVariable("id") Long id) {
        List<Transfer> transfers = transferService.search(keyword);
        model.addAttribute("transfers", transfers);
        model.addAttribute("id", id);
        return "admin/transfer/transfers";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("transfer", transferService.getById(id));
        return "admin/transfer/transfer";
    }

    @GetMapping("/add")
    public String addTransferFrom(Model model) {
        model.addAttribute("transfer", new Transfer());
        List<User> accounts = userService.getAllUsers();
        model.addAttribute("accounts", accounts);
        model.addAttribute("currencies", currencyService.getAll());
        model.addAttribute("cashes", cashService.getAll());
        return "admin/transfer/add";
    }

    @PostMapping("/addTransfer")
    public String addTransfer(@ModelAttribute("transfer") @Valid Transfer transfer, @NotNull BindingResult bindingResult) {
        if(bindingResult.hasErrors() || (transfer.getAmount() > transfer.getCash().getBalance())) {
            return "admin/transfer/add";
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

    @GetMapping("/getTransfer")
    public String getTransferPage(Model model) {
        model.addAttribute("transfer", new Transfer());
        List<User> accounts = userService.getAllUsers();
        model.addAttribute("accounts", accounts);
        model.addAttribute("currencies", currencyService.getAll());
        model.addAttribute("cashes", cashService.getAll());
        return "admin/transfer/getTransfer";
    }

    @PostMapping("/getTransfer")
    public String getTransfer(@ModelAttribute("transfer") @Valid Transfer transfer, @NotNull BindingResult bindingResult) {
        if(bindingResult.hasErrors() || (transfer.getAmount() > transfer.getCash().getBalance())) {
            return "admin/transfer/add";
        }

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
            return "admin/transfer/add";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteTransfer(@PathVariable("id") Long id) {
        transferService.deleteById(id);
        return "redirect:/transfer/transfers";
    }

    @GetMapping("/{id}/edit")
    public String editTransfer(@PathVariable("id") Long id, Model model) {
        model.addAttribute("transfer", transferService.getById(id));
        return "admin/cash/edit";
    }

    @PostMapping("/{id}/update")
    public String update(@ModelAttribute("transfer") @Valid Transfer transfer, BindingResult bindingResult, @PathVariable("id") Long id) {
        if(bindingResult.hasErrors()){
            return "admin/transfer/edit";
        }
        transferService.update(transfer, id);
        return "redirect:/transfer/transfers";
    }
}
