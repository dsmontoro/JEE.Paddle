package data.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import data.entities.Token;
import data.entities.User;

public interface TokenDao extends JpaRepository<Token, Integer> {

    Token findByUser(User user);
    
    @Query("SELECT t FROM Token t WHERE t.user = ?1 AND t.expiredDate > NOW()")
    Token findNotExpiredByUser(User user);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM Token t WHERE t.expiredDate < NOW()")    
    void deleteExpiredTokens();
}
