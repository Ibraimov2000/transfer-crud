package com.example.cbk.initializer;


import com.example.cbk.entity.*;
import com.example.cbk.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class DataInitializer {

    private final UserServiceImpl userService;
    private final CashService cashService;
    private final CurrencyService currencyService;
    private final AccountService accountService;

    @Autowired
    public DataInitializer(UserServiceImpl userService, CashService cashService, CurrencyService currencyService, AccountService accountService) {
        this.userService = userService;
        this.cashService = cashService;
        this.currencyService = currencyService;
        this.accountService = accountService;
    }

    @PostConstruct
    public void Init() {
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setRoles(Set.of(new Role("ROLE_ADMIN")));
        userService.createUser(admin);

        User user = new User();
        user.setUsername("user");
        user.setPassword("user");
        user.setRoles(Set.of(new Role("ROLE_USER")));
        userService.createUser(user);

        Account account = new Account();
        account.setName("Mirseit");
        account.setSurname("Ibraimov");
        account.setNumber("0776750817");
        accountService.create(account);

        Account account1 = new Account();
        account1.setName("Erlan");
        account1.setSurname("Abdizhamilov");
        account1.setNumber("0776750817");
        accountService.create(account1);

        Account account2 = new Account();
        account2.setName("Ernazar");
        account2.setSurname("Sydykov");
        account2.setNumber("0776750817");
        accountService.create(account2);

        Account account3 = new Account();
        account3.setName("Ilian");
        account3.setSurname("Damirov");
        account3.setNumber("0776750817");
        accountService.create(account3);

        Currency currency = new Currency();
        currency.setName("Рубль");
        currencyService.create(currency);

        Currency currency1 = new Currency();
        currency1.setName("Сом");
        currencyService.create(currency1);

        Currency currency2 = new Currency();
        currency2.setName("$");
        currencyService.create(currency2);

        Cash cash = new Cash();
        cash.setBalance(50000L);
        cashService.create(cash);

        Cash cash1 = new Cash();
        cash1.setBalance(100000L);
        cashService.create(cash1);

        Cash cash2 = new Cash();
        cash2.setBalance(150000L);
        cashService.create(cash2);

        Cash cash3 = new Cash();
        cash3.setBalance(2000000L);
        cashService.create(cash3);
    }
}