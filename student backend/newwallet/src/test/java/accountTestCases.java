import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import walletapp.example.newwallet.controller.AccountController;
import walletapp.example.newwallet.entity.Account;
import walletapp.example.newwallet.entity.LogInDto;
import walletapp.example.newwallet.repository.AccountRepository;
import walletapp.example.newwallet.service.AccountServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.bson.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class accountTestCases {
    //Test cases for entity

        @Mock
        private AccountRepository accountRepository;
        @Mock
        private Account account;
        @Mock
        private AccountServiceImpl accountService;

    @InjectMocks
    private AccountController accountController;


    @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
            account = new Account("1", "1234567890", "John Doe", "password", "123 Street", 1000);
        }

        @Test
        void testGetAccountNumber() {
            assertEquals("1234567890", account.getAccountNumber());
        }

        @Test
        void testGetName() {
            assertEquals("John Doe", account.getName());
        }

        @Test
        void testGetBalance() {
            assertEquals(1000, account.getBalance());
        }

        @Test
        void testUpdateBalance() {
            account.setBalance(500);
            assertEquals(1500, account.getBalance());
        }

        @Test
        void testSaveAccount() {
            when(accountRepository.save(account)).thenReturn(account);

            Account savedAccount = accountRepository.save(account);

            assertEquals(account, savedAccount);

    }
// Test cases for account controller

    @Test
    void testCreateAccount() {
        Account account = new Account("1", "1234567890", "John Doe", "password", "123 Street", 1000);
        when(accountService.addAccount(account)).thenReturn(account);

        ResponseEntity<Account> response = accountController.createAccount(account);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(account, response.getBody());
        verify(accountService, times(1)).addAccount(account);

    }
    @Test
    void testGetAccountByaccountId() {
        Account account = new Account("1", "1234567890", "John Doe", "password", "123 Street", 1000);
        String accountId = "1";
        when(accountService.getAccountById(accountId)).thenReturn(account);

        ResponseEntity<Account> response = accountController.getAccountById(accountId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(account, response.getBody());

        // Verify that the accountService.getAccountById() method was called with the correct account ID
        verify(accountService, times(1)).getAccountById(accountId);
    }



    @Test
    void testModifyWallet() {
        Account account = new Account("1", "1234567890", "John Doe", "password", "123 Street", 1000);
        String accountId = "1";
        when(accountService.modifyAccount(accountId, account)).thenReturn(account);

        ResponseEntity<Account> response = accountController.modifyWallet(accountId, account);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(account, response.getBody());
        verify(accountService, times(1)).modifyAccount(accountId, account);


}
    @Test
    void testDeleteWallet() {
        String accountId = "1";
        when(accountService.deleteAccount(accountId)).thenReturn("Account deleted");

        ResponseEntity<String> response = accountController.deleteWallet(accountId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Account deleted", response.getBody());
        verify(accountService, times(1)).deleteAccount(accountId);
    }
    @Test
    void testLogin() {
        LogInDto logInDto = new LogInDto("1234567890", "password");
        Account account = new Account("1", "1234567890", "John Doe", "password", "123 Street", 1000);
        when(accountRepository.findByAccountNumber(logInDto.getAccountNumber())).thenReturn(Optional.of(account));

        ResponseEntity<Account> response = accountController.login(logInDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(account, response.getBody());
        verify(accountRepository, times(1)).findByAccountNumber(logInDto.getAccountNumber());
    }
    // Test cases for account Services
//    void testAddAccount() {
//        Account account = new Account(senderAccountNumber, senderBalance);
//        account.setAccountNumber("1234567890");
//        account.setName("John Doe");
//        account.setPassword("password");
//        account.setAddress("123 Street");
//        account.setBalance(1000);
//
//        String generatedId = UUID.randomUUID().toString().split("-")[0];
//        account.setId(generatedId);
//
//        when(accountRepository.save(account)).thenReturn(account);
//
//        Account createdAccount = accountService.addAccount(account);
//
//        assertNotNull(createdAccount.getId());
//        assertEquals("1234567890", createdAccount.getAccountNumber());
//        assertEquals("John Doe", createdAccount.getName());
//        assertEquals("password", createdAccount.getPassword());
//        assertEquals("123 Street", createdAccount.getAddress());
//        assertEquals(1000, createdAccount.getBalance());
//
//        verify(accountRepository, times(1)).save(account);
//    }

    @Test
    void testFindAllAccounts() {
        List<Account> accountList = new ArrayList<>();
        accountList.add(new Account("1", "1234567890", "John Doe", "password", "123 Street", 1000));
        accountList.add(new Account("2", "0987654321", "Jane Smith", "password", "456 Avenue", 2000));

        when(accountRepository.findAll()).thenReturn(accountList);

        List<Account> foundAccounts = accountService.findAllAccounts();

        assertEquals(2, foundAccounts.size());
        assertEquals("1234567890", foundAccounts.get(0).getAccountNumber());
        assertEquals("John Doe", foundAccounts.get(0).getName());
        assertEquals("password", foundAccounts.get(0).getPassword());
        assertEquals("123 Street", foundAccounts.get(0).getAddress());
        assertEquals(1000, foundAccounts.get(0).getBalance());

        verify(accountRepository, times(1)).findAll();
    }

    @Test
    void testGetAccountById() {
        Account account = new Account("d8d0d019", "4527", "gowri", "gowri", "2205 Nele", 101097);
        String accountId = "d8d0d019";

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        Account foundAccount = accountService.getAccountById(accountId);

        assertNotNull(foundAccount);
        assertEquals("4527", foundAccount.getAccountNumber());
        assertEquals("gowri", foundAccount.getName());
        assertEquals("gowri", foundAccount.getPassword());
        assertEquals("2205 Nele", foundAccount.getAddress());
        assertEquals(101097, foundAccount.getBalance());

        verify(accountRepository, times(1)).findById(accountId);
    }

    @Test
    void testGetAccountByAccountNumber() {
        Account account = new Account("1", "1234567890", "John Doe", "password", "123 Street", 1000);
        String accountNumber = "1234567890";

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));

        Account foundAccount = accountService.getAccountByAccountNumber(accountNumber);

        assertNotNull(foundAccount);
        assertEquals("1", foundAccount.getId());
        assertEquals("John Doe", foundAccount.getName());
        assertEquals("password", foundAccount.getPassword());
        assertEquals("123 Street", foundAccount.getAddress());
        assertEquals(1000, foundAccount.getBalance());

        verify(accountRepository, times(1)).findByAccountNumber(accountNumber);
    }

    @Test
    void testModifyAccount() {
        Account account = new Account("1", "1234567890", "John Doe", "password", "123 Street", 1000);
        String accountId = "1";

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);

        Account modifiedAccount = accountService.modifyAccount(accountId, account);

        assertNotNull(modifiedAccount);
        assertEquals("1", modifiedAccount.getId());
        assertEquals("1234567890", modifiedAccount.getAccountNumber());
        assertEquals("John Doe", modifiedAccount.getName());
        assertEquals("password", modifiedAccount.getPassword());
        assertEquals("123 Street", modifiedAccount.getAddress());
        assertEquals(1000, modifiedAccount.getBalance());

        verify(accountRepository, times(1)).findById(accountId);
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void testDeleteAccount() {
        String accountId = "1";

        accountService.deleteAccount(accountId);

        verify(accountRepository, times(1)).deleteById(accountId);
    }
    // Test Cases for account Repository implementation
    void testFindByAccountNumber() {
        Account account = new Account("1", "1234567890", "John Doe", "password", "123 Street", 1000);
        String accountNumber = "1234567890";

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));

        Optional<Account> foundAccount = accountRepository.findByAccountNumber(accountNumber);

        assertEquals(account.getId(), foundAccount.get().getId());
        assertEquals(account.getAccountNumber(), foundAccount.get().getAccountNumber());
        assertEquals(account.getName(), foundAccount.get().getName());
        assertEquals(account.getPassword(), foundAccount.get().getPassword());
        assertEquals(account.getAddress(), foundAccount.get().getAddress());
        assertEquals(account.getBalance(), foundAccount.get().getBalance());

        verify(accountRepository, times(1)).findByAccountNumber(accountNumber);
    }

}