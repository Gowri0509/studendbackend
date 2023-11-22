//package walletapp.example.newwallet;
package walletapp.example.newwallet;
//
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.SpringRunner;
//import walletapp.example.newwallet.controller.TransactionController;
//import walletapp.example.newwallet.entity.Account;
//import walletapp.example.newwallet.entity.Transaction;
//import walletapp.example.newwallet.repository.AccountRepository;
//import walletapp.example.newwallet.repository.TransactionRepository;
//import walletapp.example.newwallet.service.TransactionServiceImpl;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//
//public class transactionTestCases {
//    @Autowired
//    private TransactionServiceImpl transactionService;
//
//    @Autowired
//    private TransactionController transactionController;
//    @Autowired
//    private AccountRepository accountRepository;
//    @Autowired
//    private TransactionRepository transactionRepository;
//
//    @Test
//    void testTransactionConstructor() {
//        String id = "1";
//        String senderAccountNumber = "1234567890";
//        String receiverAccountNumber = "9876543210";
//        boolean recharge = false;
//        int amount = 100;
//        Account account = new Account();
//        String type = "Transfer";
//
//        Transaction transaction = new Transaction(id, senderAccountNumber, receiverAccountNumber, recharge, amount, account, type);
//
//        assertEquals(id, transaction.getId());
//        assertEquals(senderAccountNumber, transaction.getSenderAccountNumber());
//        assertEquals(receiverAccountNumber, transaction.getReceiverAccountNumber());
//        assertEquals(recharge, transaction.isRecharge());
//        assertEquals(amount, transaction.getAmount());
//        assertEquals(account, transaction.getAccount());
//        assertEquals(type, transaction.getType());
//    }
//
//    @Test
//    void testTransactionGettersAndSetters() {
//        Transaction transaction = new Transaction();
//
//        String id = "1";
//        String senderAccountNumber = "1234567890";
//        String receiverAccountNumber = "9876543210";
//        boolean recharge = false;
//        int amount = 100;
//        Account account = new Account();
//        String type = "Transfer";
//
//        transaction.setId(id);
//        transaction.setSenderAccountNumber(senderAccountNumber);
//        transaction.setReceiverAccountNumber(receiverAccountNumber);
//        transaction.setRecharge(recharge);
//        transaction.setAmount(amount);
//        transaction.setAccount(account);
//        transaction.setType(type);

//        assertEquals(id, transaction.getId());
//        assertEquals(senderAccountNumber, transaction.getSenderAccountNumber());
//        assertEquals(receiverAccountNumber, transaction.getReceiverAccountNumber());
//        assertEquals(recharge, transaction.isRecharge());
//        assertEquals(amount, transaction.getAmount());
//        assertEquals(account, transaction.getAccount());
//        assertEquals(type, transaction.getType());
//    }

//    // Test Cases for Transaction Controller
//    @Test
//    void testCreateTransaction() {
//        Transaction transaction = new Transaction();
//        when(transactionService.addTransaction(transaction)).thenReturn(transaction);
//
//        ResponseEntity<Transaction> response = transactionController.createTransaction(transaction);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals(transaction, response.getBody());
//
//        verify(transactionService, times(1)).addTransaction(transaction);
//    }
//
//    @Test
//    void testGetTransaction() {
//        String senderAccountNumber = "1234567890";
//        List<Transaction> transactions = new ArrayList<>();
//        when(transactionService.findTransaction(senderAccountNumber)).thenReturn(transactions);
//
//        ResponseEntity<List<Transaction>> response = transactionController.getTransaction(senderAccountNumber);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(transactions, response.getBody());
//
//        verify(transactionService, times(1)).findTransaction(senderAccountNumber);
//    }
//
//    @Test
//    void testGetReceiverTransaction() {
//        String receiverAccountNumber = "9876543210";
//        List<Transaction> transactions = new ArrayList<>();
//        when(transactionService.findByReceiverAccountNumber(receiverAccountNumber)).thenReturn(transactions);
//
//        ResponseEntity<List<Transaction>> response = transactionController.getReceiverTransaction(receiverAccountNumber);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(transactions, response.getBody());
//
//        verify(transactionService, times(1)).findByReceiverAccountNumber(receiverAccountNumber);
//    }
//    // Test Cases Transaction services
//    @Test
//    void testAddTransaction() {
//        Transaction transaction = new Transaction();
//        transaction.setSenderAccountNumber("sender123");
//        transaction.setReceiverAccountNumber("receiver456");
//        transaction.setAmount(100);
//
//        Account sender = new Account();
//        sender.setBalance(200);
//
//        Account receiver = new Account();
//        receiver.setBalance(300);
//
//        when(accountRepository.findByAccountNumber("sender123")).thenReturn(java.util.Optional.of(sender));
//        when(accountRepository.findByAccountNumber("receiver456")).thenReturn(java.util.Optional.of(receiver));
//        when(transactionRepository.save(transaction)).thenReturn(transaction);
//
//        Transaction addedTransaction = transactionService.addTransaction(transaction);
//
//        assertEquals(transaction, addedTransaction);
//        assertEquals(100, sender.getBalance());
//        assertEquals(400, receiver.getBalance());
//
//        verify(accountRepository, times(1)).findByAccountNumber("sender123");
//        verify(accountRepository, times(1)).findByAccountNumber("receiver456");
//        verify(accountRepository, times(1)).save(sender);
//        verify(accountRepository, times(1)).save(receiver);
//        verify(transactionRepository, times(1)).save(transaction);
//    }
//
//    @Test
//    void testAddTransaction_ThrowRuntimeException() {
//        Transaction transaction = new Transaction();
//        transaction.setSenderAccountNumber("sender123");
//        transaction.setReceiverAccountNumber("receiver456");
//        transaction.setAmount(200);
//
//        Account sender = new Account();
//        sender.setBalance(-100);
//
//        when(accountRepository.findByAccountNumber("sender123")).thenReturn(java.util.Optional.of(sender));
//
//        assertThrows(RuntimeException.class, () -> transactionService.addTransaction(transaction));
//
//        verify(accountRepository, times(1)).findByAccountNumber("sender123");
//        verify(accountRepository, never()).findByAccountNumber("receiver456");
//        verify(accountRepository, never()).save(any());
//        verify(transactionRepository, never()).save(any());
//    }
//
//    @Test
//    void testFindByReceiverAccountNumber() {
//        String receiverAccountNumber = "receiver123";
//        List<Transaction> transactions = new ArrayList<>();
//        when(transactionRepository.findByReceiverAccountNumber(receiverAccountNumber)).thenReturn(transactions);
//
//        List<Transaction> result = transactionService.findByReceiverAccountNumber(receiverAccountNumber);
//
//        assertEquals(transactions, result);
//
//        verify(transactionRepository, times(1)).findByReceiverAccountNumber(receiverAccountNumber);
//    }
//
//    @Test
//    void testFindBySenderAccountNumber() {
//        String senderAccountNumber = "sender123";
//        List<Transaction> transactions = new ArrayList<>();
//        when(transactionRepository.findBySenderAccountNumber(senderAccountNumber)).thenReturn(transactions);
//
//        List<Transaction> result = transactionService.findBySenderAccountNumber(senderAccountNumber);
//
//        assertEquals(transactions, result);
//
//        verify(transactionRepository, times(1)).findBySenderAccountNumber(senderAccountNumber);
//    }
//
//    @Test
//    void testFindTransaction() {
//        String accountNumber = "account123";
//        List<Transaction> senderList = new ArrayList<>();
//        List<Transaction> receiverList = new ArrayList<>();
//        when(transactionRepository.findBySenderAccountNumber(accountNumber)).thenReturn(senderList);
//        when(transactionRepository.findByReceiverAccountNumber(accountNumber)).thenReturn(receiverList);
//
//        List<Transaction> result = transactionService.findTransaction(accountNumber);
//
//        assertEquals(senderList, result);
//
//        verify(transactionRepository, times(1)).findBySenderAccountNumber(accountNumber);
//        verify(transactionRepository, times(1)).findByReceiverAccountNumber(accountNumber);
//    }
//    // Test Cases for Transaction Repository
//    @Test
//    void testFindByReceiverAccountNumberinrepo() {
////        String receiverAccountNumber = "receiver123";
////        List<Transaction> transactions = new ArrayList<>();
////        when(transactionRepository.findByReceiverAccountNumber(receiverAccountNumber)).thenReturn(transactions);
////
////        List<Transaction> result = transactionService.findByReceiverAccountNumber(receiverAccountNumber);
////
////        assertEquals(transactions, result);
////
////        verify(transactionRepository, times(1)).findByReceiverAccountNumber(receiverAccountNumber);
//       String receiverAccountNumber = "receiver123";
//        List<Transaction> transactions = new ArrayList<>();
//        // Create a mock of the TransactionRepository
//        TransactionRepository transactionRepository = mock(TransactionRepository.class);
//
//// Configure the mock to return the expected result
//        when(transactionRepository.findByReceiverAccountNumber(receiverAccountNumber)).thenReturn(transactions);
//
//// Inject the mock repository into the transactionService
//        transactionService.addTransaction((Transaction) transactionRepository);
//
//// Perform the test
//        List<Transaction> result = transactionService.findByReceiverAccountNumber(receiverAccountNumber);
//
//// Assert the result
//        assertEquals(transactions, result);
//
//    }
//
//    @Test
//    void testFindBySenderAccountNumberinrepo() {
//        String senderAccountNumber = "0509";
//        List<Transaction> transactions = new ArrayList<>();
//        when(transactionRepository.findBySenderAccountNumber(senderAccountNumber)).thenReturn(transactions);
//
//        List<Transaction> result = transactionService.findBySenderAccountNumber(senderAccountNumber);
//
//        assertEquals(transactions, result);
//
//        verify(transactionRepository, times(1)).findBySenderAccountNumber(senderAccountNumber);
//    }
//
//}
//
