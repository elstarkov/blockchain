package app.controller;

import app.model.Block;
import app.service.BlockchainService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blockchain")
public class BlockchainController {

    private final BlockchainService blockchainService;

    public BlockchainController(BlockchainService blockchainService) {
        this.blockchainService = blockchainService;
    }

    @GetMapping("/chain")
    public List<Block> getBlockchain() {
        return blockchainService.getChain();
    }

    @PostMapping("/mine")
    public Block mineBlock() {
        return blockchainService.mineBlock();
    }
}
