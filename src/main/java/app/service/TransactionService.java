package app.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import app.repository.TransactionRepository;
import app.repository.UserRepository;
import app.model.Transaction;
import app.model.User;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class TransactionService {
    
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final Queue<Transaction> transactionPool = new ConcurrentLinkedQueue<>();

    @Autowired 
    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public void addTransaction(String senderUsername, String receiverUsername, double amount) {
        User sender = userRepository.findByUsername(senderUsername).orElseThrow(() -> new RuntimeException("Sender not found"));
        userRepository.findByUsername(receiverUsername).orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (sender.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        if (senderUsername.equals(receiverUsername)) {
            throw new RuntimeException("Sender and receiver cannot be the same");
        }

        Transaction transaction = new Transaction();
        transaction.setSender(senderUsername);
        transaction.setReceiver(receiverUsername);
        transaction.setAmount(amount);

        transactionPool.add(transaction);
    }

    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public List<Transaction> getPendingTransactions() {
        return new ArrayList<>(transactionPool);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public void clearPool() {
        transactionPool.clear();
    }

    public void confirmTransaction(Transaction transaction) {
        User sender = userRepository.findByUsername(transaction.getSender()).orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findByUsername(transaction.getReceiver()).orElseThrow(() -> new RuntimeException("Receiver not found"));

        sender.setBalance(sender.getBalance() - transaction.getAmount());
        receiver.setBalance(receiver.getBalance() + transaction.getAmount());

        userRepository.save(sender);
        userRepository.save(receiver);

        transactionRepository.save(transaction);
    }
}
