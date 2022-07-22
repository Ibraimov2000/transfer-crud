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

    @Autowired
    public DataInitializer(UserServiceImpl userService, CashService cashService, CurrencyService currencyService) {
        this.userService = userService;
        this.cashService = cashService;
        this.currencyService = currencyService;
    }

    @PostConstruct
    public void Init() {

        User admin = new User();
        admin.setUsername("admin");
        admin.setSurname("admin");
        admin.setPassword("admin");
        admin.setRoles(Set.of(new Role("ADMIN")));
        userService.createUser(admin);

        User user = new User();
        user.setUsername("user");
        user.setSurname("user");
        user.setPassword("user");
        user.setRoles(Set.of(new Role("USER")));
        userService.createUser(user);

        User account = new User();
        account.setUsername("Mirseit");
        account.setSurname("Ibraimov");
        account.setPassword("Mirseit");
        account.setNumber("0776750817");
        account.setRoles(Set.of(new Role("USER")));
        userService.createUser(account);

        User account1 = new User();
        account1.setUsername("Erlan");
        account1.setSurname("Abdizhamilov");
        account1.setPassword("Erlan");
        account1.setNumber("0776750817");
        account1.setRoles(Set.of(new Role("USER")));
        userService.createUser(account1);

        User account2 = new User();
        account2.setUsername("Ernazar");
        account2.setSurname("Sydykov");
        account2.setPassword("Ernazar");
        account2.setNumber("0776750817");
        account2.setRoles(Set.of(new Role("USER")));
        userService.createUser(account2);

        User account3 = new User();
        account3.setUsername("Ilian");
        account3.setSurname("Damirov");
        account3.setPassword("Ilian");
        account3.setNumber("0776750817");
        account3.setRoles(Set.of(new Role("USER")));
        userService.createUser(account3);

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