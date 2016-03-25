package data.daos;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.PersistenceConfig;
import config.TestsPersistenceConfig;
import data.entities.Training;
import data.entities.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class})
public class TrainingDaoITest {
            
    @Autowired
    private TrainingDao trainingDao;
    
    @Autowired
    private DaosService daosService;
    
    @Autowired
    private CourtDao courtDao;
    
    @Test
    public void testFindAvailableTrainingsByDate(){
        
        Calendar date = Calendar.getInstance();
        
        List<Training> trainingList1 = trainingDao.findAvailableTrainingsByDate(date);
                
        assertTrue(trainingList1.size() > 0);
               
        User player1 = (User) daosService.getMap().get("u0");
        User player2 = (User) daosService.getMap().get("u1");
        User player3 = (User) daosService.getMap().get("u2");
        User player4 = (User) daosService.getMap().get("u3");
        
        Training training = trainingList1.get(0);
        training.setPlayer1(player1);
        training.setPlayer2(player2);
        training.setPlayer3(player3);
        training.setPlayer4(player4);
        trainingDao.save(training);
        
        List<Training> trainingList2 = trainingDao.findAvailableTrainingsByDate(date);
        
        assertTrue(trainingList1.size() > trainingList2.size());
    }
    
    @Test
    public void testFindByCourtAndInitDate(){
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DAY_OF_YEAR, 1);
        date.set(Calendar.HOUR_OF_DAY, 14);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        assertNotNull(trainingDao.findByCourtAndInitDate(courtDao.findOne(1), date));
        assertNull(trainingDao.findByCourtAndInitDate(courtDao.findOne(2), date));
    }
}
