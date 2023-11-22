package walletapp.example.newwallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import walletapp.example.newwallet.entity.Account;
import walletapp.example.newwallet.entity.Transaction;
import walletapp.example.newwallet.repository.AccountRepository;
import walletapp.example.newwallet.repository.TransactionRepository;
import walletapp.example.newwallet.service.TransactionServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.*;
import java.util.stream.Collectors;

import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.bson.assertions.Assertions.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import walletapp.example.newwallet.controller.TransactionController;

public class newTransactionTest {
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;
    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllTransactions() {
        // Prepare mock data
        List<Transaction> mockTransactions = new ArrayList<>();
        mockTransactions.add(new Transaction("01", "0509", "4527", false, 100));


        // Mock the behavior of transactionRepository.findAll()
        when(transactionRepository.findAll()).thenReturn(mockTransactions);

        // Call the service method
        List<Transaction> result = transactionService.findalltransactions();

        // Verify the result
        assertEquals(1, result.size());

    }

    //test cases for find sender account number
    @Test
    void testFindBySenderAccountNumber() {
        // Prepare mock data
        String senderAccountNumber = "0509";

        Transaction transaction1 = new Transaction();
        transaction1.setId("1");
        transaction1.setSenderAccountNumber(senderAccountNumber);

        Transaction transaction2 = new Transaction();
        transaction2.setId("2");
        transaction2.setSenderAccountNumber(senderAccountNumber);

        List<Transaction> expectedTransactions = Arrays.asList(transaction1, transaction2);

        // Mock the behavior of transactionRepository.findBySenderAccountNumber()
        when(transactionRepository.findBySenderAccountNumber(senderAccountNumber)).thenReturn(expectedTransactions);

        // Call the service method
        List<Transaction> result = transactionService.findBySenderAccountNumber(senderAccountNumber);

        // Verify the behavior and assertions
        assertEquals(expectedTransactions.size(), result.size());
        assertEquals(expectedTransactions, result);

        // Verify that the repository method was called
        verify(transactionRepository, times(1)).findBySenderAccountNumber(senderAccountNumber);
    }

    @Test
    void testFindBySenderAccountNumber_NoTransactionsFound() {
        // Prepare mock data
        String senderAccountNumber = "0509";

        // Mock the behavior of transactionRepository.findBySenderAccountNumber()
        when(transactionRepository.findBySenderAccountNumber(senderAccountNumber)).thenReturn(Collections.emptyList());

        // Call the service method
        List<Transaction> result = transactionService.findBySenderAccountNumber(senderAccountNumber);

        // Verify the result
        assertTrue(result.isEmpty());

        // Verify that the repository method was called
        verify(transactionRepository, times(1)).findBySenderAccountNumber(senderAccountNumber);
    }


    @Test
    void testFindBySenderAccountNumber_RepositoryThrowsException() {
        // Prepare mock data
        String senderAccountNumber = "0509";

        // Mock the behavior of transactionRepository.findBySenderAccountNumber() to throw an exception
        when(transactionRepository.findBySenderAccountNumber(senderAccountNumber)).thenThrow(RuntimeException.class);

        // Call the service method
        assertThrows(RuntimeException.class, () -> transactionService.findBySenderAccountNumber(senderAccountNumber));

        // Verify that the repository method was called
        verify(transactionRepository, times(1)).findBySenderAccountNumber(senderAccountNumber);
    }
    // test cases for receiver account number

    @Test
    void testFindByReceiverAccountNumber_NoTransactionsFound() {
        // Prepare mock data
        String receiverAccountNumber = "4527";

        // Mock the behavior of transactionRepository.findByReceiverAccountNumber()
        when(transactionRepository.findByReceiverAccountNumber(receiverAccountNumber)).thenReturn(Collections.emptyList());

        // Call the service method
        List<Transaction> result = transactionService.findByReceiverAccountNumber(receiverAccountNumber);

        // Verify the result
        assertTrue(result.isEmpty());

        // Verify that the repository method was called
        verify(transactionRepository, times(1)).findByReceiverAccountNumber(receiverAccountNumber);
    }

    @Test
    void testFindByReceiverAccountNumber_NullReceiverAccountNumber() {
        // Call the service method with null receiver account number
        assertThrows(NullPointerException.class, () -> transactionService.findByReceiverAccountNumber(null));

        // Verify that the repository method was not called
        verify(transactionRepository, never()).findByReceiverAccountNumber(any());
    }

    @Test
    void testFindByReceiverAccountNumber_RepositoryThrowsException() {
        // Prepare mock data
        String receiverAccountNumber = "4527";

        // Mock the behavior of transactionRepository.findByReceiverAccountNumber() to throw an exception
        when(transactionRepository.findByReceiverAccountNumber(receiverAccountNumber)).thenThrow(RuntimeException.class);

        // Call the service method
        assertThrows(RuntimeException.class, () -> transactionService.findByReceiverAccountNumber(receiverAccountNumber));

        // Verify that the repository method was called
        verify(transactionRepository, times(1)).findByReceiverAccountNumber(receiverAccountNumber);
    }

    @Test
    void testFindTransaction() {
        // Create sample transactions
        Transaction transaction1 = new Transaction("01", "0509", "4527", false, 100);
        Transaction transaction2 = new Transaction("01", "0509", "4527", false, 100);
        Transaction transaction3 = new Transaction("01", "0509", "4527", false, 100);

        // Create a list of transactions for the sender account number
        List<Transaction> senderList = new ArrayList<>();
        senderList.add(transaction1);
        senderList.add(transaction2);

        // Create a list of transactions for the receiver account number
        List<Transaction> receiverList = new ArrayList<>();
        receiverList.add(transaction2);
        receiverList.add(transaction3);

        // Mock the repository method calls
        when(transactionRepository.findBySenderAccountNumber(anyString())).thenReturn(senderList);
        when(transactionRepository.findByReceiverAccountNumber(anyString())).thenReturn(receiverList);

        // Call the service method
        List<Transaction> result = transactionService.findTransaction("accountNumber");

        // Verify that the repository methods were called
        verify(transactionRepository).findBySenderAccountNumber("accountNumber");
        verify(transactionRepository).findByReceiverAccountNumber("accountNumber");

        // Verify the result
        List<Transaction> expected = senderList.stream()
                .filter(transaction -> !receiverList.contains(transaction))
                .collect(Collectors.toList());
        assertEquals(expected, result);

    }

    // test cases for add transaction
    public Transaction createSampleTransaction(String senderAccountNumber, String receiverAccountNumber) {
        Transaction transaction = new Transaction("01", "0509", "4527", false, 100);
        transaction.setId(UUID.randomUUID().toString().split("-")[0]);
        return transaction;
    }

    @Test
    void testAddTransaction_SuccessfulTransaction() {
        // Create sample transaction
        Transaction transaction = createSampleTransaction("0509", "4527");

        // Create sample sender and receiver accounts
        Account sender = new Account();
        sender.setAccountNumber("0509");
        sender.setBalance(100);
        Account receiver = new Account();
        receiver.setAccountNumber("4527");
        receiver.setBalance(50);

        // Mock the repository method calls
        when(accountRepository.findByAccountNumber("0509")).thenReturn(Optional.of(sender));
        when(accountRepository.findByAccountNumber("4527")).thenReturn(Optional.of(receiver));
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        // Call the service method
        Transaction result = transactionService.addTransaction(transaction);

        // Verify that the repository methods were called
        verify(accountRepository).findByAccountNumber("0509");
        verify(accountRepository).findByAccountNumber("4527");
        verify(accountRepository).save(sender);
        verify(accountRepository).save(receiver);
        verify(transactionRepository).save(transaction);

    }


    @Test
    void testAddTransaction_InsufficientBalance() {
        // Create sample transaction
        Transaction transaction = createSampleTransaction("0509", "4527");
        transaction.setAmount(200);

        // Create sample sender account
        Account sender = new Account();
        sender.setAccountNumber("0509");
        sender.setBalance(100);

        // Mock the repository method calls
        when(accountRepository.findByAccountNumber("0509")).thenReturn(Optional.of(sender));

        // Call the service method and verify that it throws an exception
        assertThrows(RuntimeException.class, () -> transactionService.addTransaction(transaction));

        // Verify that the repository methods were called
        verify(accountRepository).findByAccountNumber("0509");
//        verify(accountRepository, never()).findByAccountNumber("4527");
        verify(accountRepository, never()).save(sender);
        verify(accountRepository, never()).save(any(Account.class));
        verify(transactionRepository, never()).save(transaction);
    }

    @Test
    void testAddTransaction_AccountNotFound() {
        // Create sample transaction
        Transaction transaction = createSampleTransaction("0509", "4527");

        // Mock the repository method calls
        when(accountRepository.findByAccountNumber("0509")).thenReturn(Optional.empty());

        // Call the service method and verify that it throws an exception
        assertThrows(RuntimeException.class, () -> transactionService.addTransaction(transaction));

        // Verify that the repository methods were called
        verify(accountRepository).findByAccountNumber("0509");
        verify(accountRepository, never()).findByAccountNumber("4527");
        verify(accountRepository, never()).save(any(Account.class));
        verify(transactionRepository, never()).save(transaction);
    }
    //  TEST CASES for controller
//    test cases for create transaction

    @Test
    void testCreateTransaction_Success() {
        // Create a sample transaction
        Transaction transaction = new Transaction("01", "0509", "4527", false, 100);
        transaction.setId("123456");
        transaction.setAmount(1000);
        transaction.setSenderAccountNumber("0509");
        transaction.setReceiverAccountNumber("4527");

        // Create sample sender and receiver accounts
        Account sender = new Account();
        sender.setAccountNumber("0509");
        sender.setBalance(100);
        Account receiver = new Account();
        receiver.setAccountNumber("4527");
        receiver.setBalance(50);

        // Mock the repository method calls
        when(accountRepository.findByAccountNumber("0509")).thenReturn(Optional.of(sender));
        when(accountRepository.findByAccountNumber("4527")).thenReturn(Optional.of(receiver));
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        // Call the controller method
        ResponseEntity<Transaction> response = transactionController.createTransaction(transaction);

        // Verify that the repository methods were called
        verify(accountRepository).findByAccountNumber("0509");
        verify(accountRepository).findByAccountNumber("4527");
        verify(accountRepository).save(sender);
        verify(accountRepository).save(receiver);
        verify(transactionRepository).save(transaction);

        // Verify the response status code and body
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(transaction, response.getBody());
    }


    @Test
    void testCreateTransaction_NullTransaction() {
        // Call the controller method with a null transaction
        ResponseEntity<Transaction> response = transactionController.createTransaction(null);

        // Verify that the transaction service method was not called
        verifyNoInteractions(transactionService);

        // Verify the response status code
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}