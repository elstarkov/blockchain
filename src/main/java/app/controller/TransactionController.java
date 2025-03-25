package app.controller;
import app.model.Transaction;
import app.service.TransactionService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@RestController
@RequestMapping("/transactions")
public class TransactionController {
    
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/add")
    public String addTransaction(@RequestBody Transaction transaction) {
        transactionService.addTransaction(transaction.getSender(), transaction.getReceiver(), transaction.getAmount());
        return "Transaction added!";
    }

    @GetMapping("/all")
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/pending")
    public List<Transaction> getPendingTransactions() {
        return transactionService.getPendingTransactions();
    }
}
