//package walletapp.example.newwallet;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.ResponseEntity;
//import walletapp.example.newwallet.controller.AccountController;
//import walletapp.example.newwallet.entity.Account;
//import walletapp.example.newwallet.repository.AccountRepository;
//import walletapp.example.newwallet.service.AccountService;
//import walletapp.example.newwallet.service.AccountServiceImpl;
//
//importmport static com.mongodb.assertions.Assertions.assertNull;
//import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.never;
//import static org.mockito.Mockito.when;
//import org.springframework.http.HttpStatus;
//
//public class accountTest {
//
//        @Mock
//        private AccountRepository accountRepository;
//
//        @Mock
//        private Account account;
//    @Mock
//    private AccountServiceImpl accountService;
//
//    @InjectMocks
//    private AccountController accountController;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        account = new Account("1", "1234567890", "John Doe", "password", "123 Street", 1000);
//    }
//
//    @Test
//    void testGetAccountNumber() {
//        assertEquals("1234567890", account.getAccountNumber());
//    }
//
//    @Test
//    void testGetName() {
//        assertEquals("John Doe", account.getName());
//    }
//
//    @Test
//    void testGetBalance() {
//        assertEquals(1000, account.getBalance());
//    }
//    @Test
//    void testSaveAccount() {
//        when(accountRepository.save(account)).thenReturn(account);
//
//        Account savedAccount = accountRepository.save(account);
//
//        assertEquals(account, savedAccount);
//
//    }
//    @Test
//    void testSetId() {
//        // Arrange
//        String initialId = "123";
//        Account account = new Account();
//        account.setId(initialId);
//
//        // Act
//        String newId = "456";
//        account.setId(newId);
//
//        // Assert
//        assertEquals(newId, account.getId());
//    }
//
//    @Test
//    void testSetAccountNumber() {
//        // Arrange
//        String initialAccountNumber = "123456";
//        Account account = new Account();
//        account.setAccountNumber(initialAccountNumber);
//
//        // Act
//        String newAccountNumber = "654321";
//        account.setAccountNumber(newAccountNumber);
//
//        // Assert
//        assertEquals(newAccountNumber, account.getAccountNumber());
//    }
//
//    @Test
//    void testSetName() {
//        // Arrange
//        String initialName = "John Doe";
//        Account account = new Account();
//        account.setName(initialName);
//
//        // Act
//        String newName = "Jane Smith";
//        account.setName(newName);
//
//        // Assert
//        assertEquals(newName, account.getName());
//    }
//
//    @Test
//    void testSetPassword() {
//        // Arrange
//        String initialPassword = "password123";
//        Account account = new Account();
//        account.setPassword(initialPassword);
//
//        // Act
//        String newPassword = "newpassword456";
//        account.setPassword(newPassword);
//
//        // Assert
//        assertEquals(newPassword, account.getPassword());
//    }
//
//    @Test
//    void testSetAddress() {
//        // Arrange
//        String initialAddress = "123 Main St";
//        Account account = new Account();
//        account.setAddress(initialAddress);
//
//        // Act
//        String newAddress = "456 Elm St";
//        account.setAddress(newAddress);
//
//        // Assert
//        assertEquals(newAddress, account.getAddress());
//    }
//
//    @Test
//    void testSetBalance() {
//        // Arrange
//        int initialBalance = 1000;
//        Account account = new Account();
//        account.setBalance(initialBalance);
//
//        // Act
//        int newBalance = 1500;
//        account.setBalance(newBalance);
//
//        // Assert
//        assertEquals(newBalance, account.getBalance());
//    }
//
////    @Test
////    void testCreateAccount() {
////        // Test case for successful account creation
////        // Arrange
////        Account account = new Account();
////        account.setId("123");
////        account.setAccountNumber("987654321");
////        // ... set other properties of the account
////
////        when(accountService.addAccount(any(Account.class))).thenReturn(account);
////
////        // Act
////        ResponseEntity<Account> responseEntity = accountController.createAccount(account);
////
////        // Assert
////        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
////        assertEquals(account, responseEntity.getBody());
////
////    }
////
////    @Test
////    void testCreateAccount_InvalidAccount() {
////        // Test case for creating an invalid account
////        // Arrange
////        Account account = new Account();
////        // ... set only partial or invalid account properties
////
////        // Act
////        ResponseEntity<Account> responseEntity = accountController.createAccount(account);
////
////        // Assert
////        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
////        assertNull(responseEntity.getBody());
////
////    }
////
////    @Test
////    void testCreateAccount_ExceptionThrown() {
////        // Test case for handling an exception during account creation
////        // Arrange
////        Account account = new Account();
////        // ... set valid account properties
////
////        when(accountService.addAccount(any(Account.class))).thenThrow(new RuntimeException("Failed to create account"));
////
////        // Act
////        ResponseEntity<Account> responseEntity = accountController.createAccount(account);
////
////        // Assert
////        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
////        assertNull(responseEntity.getBody());
////
////    }
//
//}
