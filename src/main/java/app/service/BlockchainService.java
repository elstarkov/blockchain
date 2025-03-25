package app.service;

import app.model.Block;
import app.model.Transaction;
import app.repository.BlockchainRepository;
import app.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlockchainService {

    private final BlockchainRepository blockchainRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionService transactionService;
    private final int difficulty = 4;

    @Autowired
    public BlockchainService(BlockchainRepository blockchainRepository , TransactionService transactionService, TransactionRepository transactionRepository) {
        this.blockchainRepository = blockchainRepository;
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;


        if (blockchainRepository.findFirstByOrderByBlockIndexDesc().isEmpty()) {
            Block genesisBlock = new Block(0, "0", new ArrayList<>());
            blockchainRepository.save(genesisBlock);
        }
    }

    public Block getLatestBlock() {
        return blockchainRepository.findFirstByOrderByBlockIndexDesc()
                .orElseThrow(() -> new RuntimeException("Blockchain is empty"));
    }

    public Block mineBlock() {
        List<Transaction> transactions = transactionService.getPendingTransactions();
        if (transactions.isEmpty()) {
            throw new RuntimeException("No transactions to mine");
        }

        Block latestBlock = getLatestBlock();
        Block newBlock = new Block(latestBlock.getBlockIndex() + 1, latestBlock.getHash(), new ArrayList<>());


        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!newBlock.getHash().substring(0, difficulty).equals(target)) {
            newBlock.incrementNonce();
            newBlock.setHash(newBlock.calculateHash());
        }

        newBlock = blockchainRepository.save(newBlock);

        for (Transaction transaction : transactions) {
            transaction.setBlockId(newBlock.getId());

            transactionService.confirmTransaction(transaction);
        }
        
        List<Transaction> savedTransactions = transactionRepository.findByBlockId(newBlock.getId());
        newBlock.setTransactions(savedTransactions);

        transactionService.clearPool();

        return newBlock;
    }

    public List<Block> getChain() {
        return blockchainRepository.findAll();
    }
}