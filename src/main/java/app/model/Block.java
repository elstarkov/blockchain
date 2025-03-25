package app.model;

import jakarta.persistence.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor
@Entity
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    private int blockIndex;
    private String timestamp;
    private String previousHash;
    private String hash;
    private int nonce;

    @Transient
    private List<Transaction> transactions;

    public Block(int blockIndex, String previousHash, List<Transaction> transactions) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        this.blockIndex = blockIndex;
        this.previousHash = previousHash;
        this.transactions = (transactions != null) ? transactions : List.of();
        this.hash = calculateHash();
        this.nonce = 0;
        this.timestamp = dateFormat.format(new Date());
    }

    public void incrementNonce() {
        this.nonce++;
    }

    public String getTimeStamp() {
        return timestamp;
    }

    public String calculateHash() {
        String transactionData = transactions.stream()
        .map(t -> t.getSender() + t.getReceiver() + t.getAmount())
        .reduce("", String::concat);

        String input = blockIndex + getTimeStamp() + previousHash + nonce + transactionData;
        return applySHA256(input);
    }
    

    private String applySHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    } 
}
