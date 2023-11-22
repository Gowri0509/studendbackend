package walletapp.example.newwallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import walletapp.example.newwallet.controller.TransactionController;
import walletapp.example.newwallet.entity.Account;
import walletapp.example.newwallet.entity.Transaction;
import walletapp.example.newwallet.repository.AccountRepository;
import walletapp.example.newwallet.repository.TransactionRepository;
import walletapp.example.newwallet.service.TransactionServiceImpl;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class transactionTest {



        @Mock
        private TransactionServiceImpl transactionService;

        @InjectMocks
        private TransactionController transactionController;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private TransactionRepository transactionRepository;


    @BeforeEach
        public void setUp() {
            MockitoAnnotations.initMocks(this);
        }
    @Test
    void testTransactionConstructor() {
        String id = "1";
        String senderAccountNumber = "1234567890";
        String receiverAccountNumber = "9876543210";
        boolean recharge = false;
        int amount = 100;
        Account account = new Account();
        String type = "Transfer";

        Transaction transaction = new Transaction(id, senderAccountNumber, receiverAccountNumber, recharge, amount, account, type);

        assertEquals(id, transaction.getId());
        assertEquals(senderAccountNumber, transaction.getSenderAccountNumber());
        assertEquals(receiverAccountNumber, transaction.getReceiverAccountNumber());
        assertEquals(recharge, transaction.isRecharge());
        assertEquals(amount, transaction.getAmount());
        assertEquals(account, transaction.getAccount());
        assertEquals(type, transaction.getType());
    }

    @Test
    void testTransactionGettersAndSetters() {
        Transaction transaction = new Transaction();

        String id = "1";
        String senderAccountNumber = "1234567890";
        String receiverAccountNumber = "9876543210";
        boolean recharge = false;
        int amount = 100;
        Account account = new Account();
        String type = "Transfer";

        transaction.setId(id);
        transaction.setSenderAccountNumber(senderAccountNumber);
        transaction.setReceiverAccountNumber(receiverAccountNumber);
        transaction.setRecharge(recharge);
        transaction.setAmount(amount);
        transaction.setAccount(account);
        transaction.setType(type);

        assertEquals(id, transaction.getId());
        assertEquals(senderAccountNumber, transaction.getSenderAccountNumber());
        assertEquals(receiverAccountNumber, transaction.getReceiverAccountNumber());
        assertEquals(recharge, transaction.isRecharge());
        assertEquals(amount, transaction.getAmount());
        assertEquals(account, transaction.getAccount());
        assertEquals(type, transaction.getType());
    }
        @Test
        public void testCreateTransaction_Success() {
            // Prepare test data
            Transaction transaction = new Transaction();
            // Set up mock behavior
            when(transactionService.addTransaction(any(Transaction.class))).thenReturn(transaction);

            // Perform the API call
            ResponseEntity<Transaction> response = transactionController.createTransaction(transaction);

            // Verify the response
            verify(transactionService, times(1)).addTransaction(any(Transaction.class));
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals(transaction, response.getBody());
        }

        @Test
        public void testCreateTransaction_Failure() {
            // Prepare test data
            Transaction transaction = new Transaction();
            // Set up mock behavior
            when(transactionService.addTransaction(any(Transaction.class))).thenThrow(new RuntimeException("Account not found"));

            // Perform the API call
            assertThrows(RuntimeException.class, () -> transactionController.createTransaction(transaction));

            // Verify the behavior
            verify(transactionService, times(1)).addTransaction(any(Transaction.class));
        }
    @Test
    public void testCreateTransaction_Recharge_Success() {
        // Prepare test data
        Transaction transaction = new Transaction();
        transaction.setRecharge(true);
        // Set up mock behavior
        when(transactionService.addTransaction(any(Transaction.class))).thenReturn(transaction);

        // Perform the API call
        ResponseEntity<Transaction> response = transactionController.createTransaction(transaction);

        // Verify the response
        verify(transactionService, times(1)).addTransaction(any(Transaction.class));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(transaction, response.getBody());
    }

    @Test
    public void testCreateTransaction_Recharge_SameAccount() {
        // Prepare test data
        Transaction transaction = new Transaction();
        transaction.setRecharge(true);
        transaction.setSenderAccountNumber("123");
        transaction.setReceiverAccountNumber("123");
        // Set up mock behavior
        when(transactionService.addTransaction(any(Transaction.class))).thenThrow(new RuntimeException("Invalid transaction"));

        // Perform the API call
        assertThrows(RuntimeException.class, () -> transactionController.createTransaction(transaction));

        // Verify the behavior
        verify(transactionService, times(1)).addTransaction(any(Transaction.class));
    }

    @Test
    void testCreateTransaction_ReturnsCreatedStatus() {
        // Arrange
        Transaction transaction = new Transaction(/* provide necessary transaction details */);
        when(transactionService.addTransaction(transaction)).thenReturn(transaction);

        // Act
        ResponseEntity<Transaction> responseEntity = transactionController.createTransaction(transaction);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(transaction, responseEntity.getBody());
        verify(transactionService, times(1)).addTransaction(transaction);
    }

    @Test
    void testCreateTransaction_InvalidTransaction_ThrowsException() {
        // Arrange
        Transaction transaction = new Transaction(/* provide necessary transaction details */);
        when(transactionService.addTransaction(transaction)).thenThrow(new RuntimeException("Invalid transaction"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> {
            transactionController.createTransaction(transaction);
        });
        verify(transactionService, times(1)).addTransaction(transaction);
    }

    @Test
    void testGetTransaction_Success() {
        // Arrange
        String senderAccountNumber = "123456";
        List<Transaction> expectedTransactions = Arrays.asList(
                new Transaction("123456", "789012", 100.0),
                new Transaction("123456", "345678", 50.0)
        );

        when(transactionService.findTransaction(senderAccountNumber)).thenReturn(expectedTransactions);

        // Act
        ResponseEntity<List<Transaction>> response = transactionController.getSenderTransaction(senderAccountNumber);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedTransactions, response.getBody());
    }

    @Test
    void testGetTransaction_NoTransactionsFound() {
        // Arrange
        String senderAccountNumber = "123456";
        List<Transaction> emptyList = Arrays.asList();

        when(transactionService.findTransaction(senderAccountNumber)).thenReturn(emptyList);

        // Act
        ResponseEntity<List<Transaction>> response = transactionController.getSenderTransaction(senderAccountNumber);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(emptyList, response.getBody());
    }
    @Test
    void testGetTransaction_NullSenderAccountNumber() {
        // Arrange
        String senderAccountNumber = null;

        // Act
        ResponseEntity<List<Transaction>> response = transactionController.getSenderTransaction(senderAccountNumber);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void testGetTransaction_EmptySenderAccountNumber() {
        // Arrange
        String senderAccountNumber = "";

        // Act
        ResponseEntity<List<Transaction>> response = transactionController.getSenderTransaction(senderAccountNumber);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void testGetReceiverTransaction_WithExistingReceiverAccountNumber() {
        // Mock the data
        String receiverAccountNumber = "receiver123";
        List<Transaction> mockTransactions = new ArrayList<>();
        // Add some mock transactions to the list

        // Mock the service method
        when(transactionService.findByReceiverAccountNumber(receiverAccountNumber))
                .thenReturn(mockTransactions);

        // Call the controller method
        ResponseEntity<List<Transaction>> responseEntity = transactionController.getReceiverTransaction(receiverAccountNumber);

        // Verify the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockTransactions, responseEntity.getBody());
    }

    @Test
    public void testGetReceiverTransaction_WithNonExistingReceiverAccountNumber() {
        // Mock the data
        String receiverAccountNumber = "nonExistingReceiver";

        // Mock the service method to return an empty list
        when(transactionService.findByReceiverAccountNumber(receiverAccountNumber))
                .thenReturn(Collections.emptyList());

        // Call the controller method
        ResponseEntity<List<Transaction>> responseEntity = transactionController.getReceiverTransaction(receiverAccountNumber);

        // Verify the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
    }
    @Test
    public void testFindTransaction_WithValidAccountNumber() {
        String accountNumber = "123456789";
        List<Transaction> senderList = Arrays.asList(new Transaction(), new Transaction());
        List<Transaction> receiverList = Collections.emptyList();

        when(transactionRepository.findBySenderAccountNumber(accountNumber)).thenReturn(senderList);
        when(transactionRepository.findByReceiverAccountNumber(accountNumber)).thenReturn(receiverList);

        List<Transaction> result = transactionService.findTransaction(accountNumber);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindTransaction_WithInvalidAccountNumber() {
        String accountNumber = "987654321";
        List<Transaction> senderList = Collections.emptyList();
        List<Transaction> receiverList = Collections.emptyList();

        when(transactionRepository.findBySenderAccountNumber(accountNumber)).thenReturn(senderList);
        when(transactionRepository.findByReceiverAccountNumber(accountNumber)).thenReturn(receiverList);

        List<Transaction> result = transactionService.findTransaction(accountNumber);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindTransaction_WithNullAccountNumber() {
        // Arrange
        String accountNumber = null;

        // Act
        List<Transaction> transactionList = transactionService.findTransaction(accountNumber);

        // Assert
        assertEquals(0, transactionList.size());
        verify(transactionRepository, never()).findBySenderAccountNumber(anyString());
        verify(transactionRepository, never()).findByReceiverAccountNumber(anyString());
    }

    @Test
    public void testFindTransaction_WithEmptyAccountNumber() {
        // Arrange
        String accountNumber = "";

        // Act
        List<Transaction> transactionList = transactionService.findTransaction(accountNumber);

        // Assert
        assertEquals(0, transactionList.size());
        verify(transactionRepository, never()).findBySenderAccountNumber(anyString());
        verify(transactionRepository, never()).findByReceiverAccountNumber(anyString());
    }


    @Test
    public void testFindTransaction_WithNoTransactions() {
        // Arrange
        String accountNumber = "valid_account_number";

        List<Transaction> senderList = new ArrayList<>();
        List<Transaction> receiverList = new ArrayList<>();

        when(transactionRepository.findBySenderAccountNumber(accountNumber)).thenReturn(senderList);
        when(transactionRepository.findByReceiverAccountNumber(accountNumber)).thenReturn(receiverList);

        // Act
        List<Transaction> transactionList = transactionService.findTransaction(accountNumber);

        // Assert
        assertEquals(0, transactionList.size());
        verify(transactionRepository, times(1)).findBySenderAccountNumber(accountNumber);
        verify(transactionRepository, times(1)).findByReceiverAccountNumber(accountNumber);
    }

    @Test
    public void testFindTransaction_WithMatchingSenderAndReceiver() {
        // Arrange
        String accountNumber = "valid_account_number";

        // Create sample transactions
        Transaction transaction1 = new Transaction();
        transaction1.setSenderAccountNumber(accountNumber);
        transaction1.setReceiverAccountNumber(accountNumber);

        List<Transaction> senderList = new ArrayList<>();
        senderList.add(transaction1);

        List<Transaction> receiverList = new ArrayList<>();
        receiverList.add(transaction1);

        when(transactionRepository.findBySenderAccountNumber(accountNumber)).thenReturn(senderList);
        when(transactionRepository.findByReceiverAccountNumber(accountNumber)).thenReturn(receiverList);

        // Act
        List<Transaction> transactionList = transactionService.findTransaction(accountNumber);

        // Assert
        assertEquals(1, transactionList.size());
        assertEquals(transaction1, transactionList.get(0));
        verify(transactionRepository, times(1)).findBySenderAccountNumber(accountNumber);
        verify(transactionRepository, times(1)).findByReceiverAccountNumber(accountNumber);
    }


}





