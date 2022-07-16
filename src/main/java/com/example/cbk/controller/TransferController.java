package com.example.cbk.controller;

import com.example.cbk.entity.Account;
import com.example.cbk.entity.Cash;
import com.example.cbk.entity.Transfer;
import com.example.cbk.service.AccountService;
import com.example.cbk.service.CashService;
import com.example.cbk.service.CurrencyService;
import com.example.cbk.service.TransferService;
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
    private final AccountService accountService;
    private final CurrencyService currencyService;
    private final CashService cashService;

    @Autowired
    public TransferController(TransferService transferService, AccountService accountService, CurrencyService currencyService, CashService cashService) {
        this.transferService = transferService;
        this.accountService = accountService;
        this.currencyService = currencyService;
        this.cashService = cashService;
    }

    @GetMapping("/transfers")
    public String getTransfers(Model model, @Param("keyword") String keyword) {
        List<Transfer> transfers = transferService.search(keyword);
        model.addAttribute("transfers", transfers);
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
        List<Account> accounts = accountService.getAll();
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
        transfer.setUnicCode(Account.getCode());
        transferService.create(transfer);
        Account account = accountService.getById(transfer.getSender().getId());
        account.setUnicCode(transfer.getUnicCode());
        account.setTransferId(transfer.getId());
        accountService.update(account, transfer.getSender().getId());

        return "redirect:/";
    }

    @GetMapping("/getTransfer")
    public String getTransferPage(Model model) {
        model.addAttribute("transfer", new Transfer());
        List<Account> accounts = accountService.getAll();
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

        Account account = accountService.getById(transfer.getSender().getId());
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
