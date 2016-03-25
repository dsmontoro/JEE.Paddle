package data.entities;

import static org.junit.Assert.assertNotNull;

import java.util.Calendar;

import org.junit.Test;

import data.entities.User;
import data.entities.Training;

public class TrainingTest {
    
    @Test
    public void testTrainingNotNull() {
        Calendar initDate = Calendar.getInstance();
        User trainer = new User("t", "t@gmail.com", "p1", Calendar.getInstance());
        Court court = new Court(1);
        Training training = new Training(initDate, trainer, court);
        assertNotNull(training);
        assertNotNull(training.getTrainer());
        assertNotNull(training.getCourt());
        assertNotNull(training.getInitDate());
        
    }
    
    @Test
    public void testPlayersNotNull() {
        Calendar initDate = Calendar.getInstance();
        User trainer = new User("t", "t@gmail.com", "p", Calendar.getInstance());
        Court court = new Court(1);
        Training training = new Training(initDate, trainer, court);
        User player1 = new User("p1", "p1@gmail.com", "p1", Calendar.getInstance());
        User player2 = new User("p2", "p2@gmail.com", "p2", Calendar.getInstance());
        User player3 = new User("p3", "p3@gmail.com", "p3", Calendar.getInstance());
        User player4 = new User("p4", "p4@gmail.com", "p4", Calendar.getInstance());
        training.setPlayer1(player1);
        training.setPlayer2(player2);
        training.setPlayer3(player3);
        training.setPlayer4(player4);
        assertNotNull(training.getPlayer1());
        assertNotNull(training.getPlayer2());
        assertNotNull(training.getPlayer3());
        assertNotNull(training.getPlayer4());
    }
}
