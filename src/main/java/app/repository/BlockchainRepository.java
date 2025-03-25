package app.repository;

import app.model.Block;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface BlockchainRepository extends JpaRepository<Block, Long> {
    Optional<Block> findFirstByOrderByBlockIndexDesc();
}
