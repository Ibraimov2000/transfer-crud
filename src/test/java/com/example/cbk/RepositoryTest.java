package com.example.cbk;


import com.example.cbk.entity.*;
import com.example.cbk.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
public class RepositoryTest extends Assertions {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CashRepository cashRepository;

    private User userTest;

    private Transfer transferTest;

    private Role roleTest;

    private Currency currencyTest;

    private Cash cashTest;

    @BeforeEach
    public void setUpEntity() {

        roleTest = Role.builder()
                .name("ADMIN")
                .build();

        userTest = User.builder()
                .username("testUsername")
                .surname("testSurname")
                .password("testPassword")
                .number("0776750817")
//                .roles((Collection<Role>) roleTest)
                .unicCode(123456)
                .build();

        cashTest = Cash.builder()
                .balance(500L)
                .build();

        currencyTest = Currency.builder()
                .name("Сом")
                .build();

        transferTest = Transfer.builder()
                .amount(1L)
                .sender(userTest)
                .recipient(userTest)
                .cash(cashTest)
                .comment("asd")
                .status("СОЗДАН")
                .currency(currencyTest)
                .unicCode(User.getCode())
                .build();
    }

    @Test
    @Transactional
    void userTest() {

        //Сохранили тестового пользователя в БД
        userRepository.save(userTest);
        // Получили начначенный ID
        Long userTestId = userTest.getId();
        // Проверка на наличие тестового админа с полученным ID
        assertNotNull(userRepository.findById(userTestId));
        // Создали нового тестового пользователя и получили его из БД по ID
        User testUserDB = userRepository.findById(userTestId).get();

        //Проверили данные на совпадение
        assertEquals(testUserDB.getUsername(), userTest.getUsername());
        assertEquals(testUserDB.getSurname(), userTest.getSurname());
        assertEquals(testUserDB.getPassword(), userTest.getPassword());
        assertEquals(testUserDB.getRoles(), userTest.getRoles());
        assertEquals(testUserDB.getNumber(), userTest.getNumber());
        assertEquals(testUserDB.getUnicCode(), userTest.getUnicCode());
        assertEquals(testUserDB.getTransferId(), userTest.getTransferId());

        // Удалили тестового пользователя по ID
        userRepository.deleteById(userTestId);
        // Проверили что тестового пользователя больше нет в БД
        assertEquals(userRepository.findById(userTestId), Optional.empty());
    }

    @Test
    @Transactional
    void cashTest() {

        //Сохранили тестовой кассы в БД
        cashRepository.save(cashTest);
        // Получили начначенный ID
        Long cashTestId = cashTest.getId();
        // Проверка на наличие тестовой кассы с полученным ID
        assertNotNull(cashRepository.findById(cashTestId));
        // Создали новой тестовой кассы и получили его из БД по ID
        Cash testCashDB = cashRepository.findById(cashTestId).get();

        //Проверили данные на совпадение
        assertEquals(testCashDB.getBalance(), cashTest.getBalance());

        // Удалили тестовой валюты по ID
        cashRepository.deleteById(cashTestId);
        // Проверили что тестовой кассы больше нет в БД
        assertEquals(cashRepository.findById(cashTestId), Optional.empty());
    }

    @Test
    @Transactional
    void currencyTest() {

        //Сохранили тестовой валюты в БД
        currencyRepository.save(currencyTest);
        // Получили начначенный ID
        Long currencyTestId = currencyTest.getId();
        // Проверка на наличие тестовой валюты с полученным ID
        assertNotNull(currencyRepository.findById(currencyTestId));
        // Создали новой тестовой валюты и получили его из БД по ID
        Currency testCurrencyDB = currencyRepository.findById(currencyTestId).get();

        //Проверили данные на совпадение
        assertEquals(testCurrencyDB.getName(), currencyTest.getName());

        // Удалили тестовой валюты по ID
        currencyRepository.deleteById(currencyTestId);
        // Проверили что тестовой валюты больше нет в БД
        assertEquals(currencyRepository.findById(currencyTestId), Optional.empty());
    }

    @Test
    @Transactional
    void roleTest() {

        //Сохранили тестового роля в БД
        roleRepository.save(roleTest);
        // Получили начначенный ID
        Long roleTestId = roleTest.getId();
        // Проверка на наличие тестового роля с полученным ID
        assertNotNull(roleRepository.findById(roleTestId));
        // Создали новой тестового роля и получили его из БД по ID
        Role testRoleDB = roleRepository.findById(roleTestId).get();

        //Проверили данные на совпадение
        assertEquals(testRoleDB.getName(), roleTest.getName());

        // Удалили тестового роля по ID
        roleRepository.deleteById(roleTestId);
        // Проверили что тестового роля больше нет в БД
        assertEquals(roleRepository.findById(roleTestId), Optional.empty());
    }

    @Test
    @Transactional
    void transferTest() {

        //Сохранили тестового перевода в БД
        transferRepository.save(transferTest);
        // Получили начначенный ID
        Long transferTestId = transferTest.getId();
        // Проверка на наличие тестового перевода с полученным ID
        assertNotNull(transferRepository.findById(transferTestId));
        // Создали нового тестового перевода и получили его из БД по ID
        Transfer testTransferDB = transferRepository.findById(transferTestId).get();

        //Проверили данные на совпадение
        assertEquals(testTransferDB.getSender(), transferTest.getSender());
        assertEquals(testTransferDB.getRecipient(), transferTest.getRecipient());
        assertEquals(testTransferDB.getAmount(), transferTest.getAmount());
        assertEquals(testTransferDB.getUnicCode(), transferTest.getUnicCode());
        assertEquals(testTransferDB.getCash(), transferTest.getCash());
        assertEquals(testTransferDB.getCurrency(), transferTest.getCurrency());
        assertEquals(testTransferDB.getComment(), transferTest.getComment());
        assertEquals(testTransferDB.getStatus(), transferTest.getStatus());
        assertEquals(testTransferDB.getCreated(), transferTest.getCreated());

        // Удалили тестового пользователя по ID
        transferRepository.deleteById(transferTestId);
        // Проверили что тестового пользователя больше нет в БД
        assertEquals(transferRepository.findById(transferTestId), Optional.empty());
    }

}
