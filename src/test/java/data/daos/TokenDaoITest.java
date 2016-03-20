package data.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.PersistenceConfig;
import config.TestsPersistenceConfig;
import data.entities.Token;
import data.entities.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class})
public class TokenDaoITest {

    @Autowired
    private TokenDao tokenDao;

    @Autowired
    private DaosService daosService;

    @Test
    public void testFindByUser() {
        Token token = (Token) daosService.getMap().get("tu1");
        User user = (User) daosService.getMap().get("u4");
        assertEquals(token, tokenDao.findByUser(token.getUser()));
        assertNull(tokenDao.findByUser(user));
    }
        
    @Test
    public void testFindNotExpiredTokenByUser() {
        Token token1 = (Token) daosService.getMap().get("tu0");
        Calendar expiredDate = Calendar.getInstance();
        expiredDate.add(Calendar.HOUR, -1);
        token1.setExpiredDate(expiredDate);
        tokenDao.save(token1);
        assertNull(tokenDao.findNotExpiredByUser(token1.getUser()));
        
        Token token2 = (Token) daosService.getMap().get("tu1");
        assertNotNull(tokenDao.findNotExpiredByUser(token2.getUser()));
    }
    
    @Test
    public void testDeleteExpiredTokens() {
        Token token = (Token) daosService.getMap().get("tu0");
        Calendar expiredDate = Calendar.getInstance();
        expiredDate.add(Calendar.HOUR, -1);
        token.setExpiredDate(expiredDate);
        tokenDao.save(token);        
        int numberOfTokensBefore = (int) tokenDao.count();
        tokenDao.deleteExpiredTokens();
        int numberOfTokensAfter = (int) tokenDao.count();
        assertTrue(numberOfTokensBefore > numberOfTokensAfter);
        assertTrue(numberOfTokensAfter > 0);
    }

}
