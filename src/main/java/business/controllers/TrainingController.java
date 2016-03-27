package business.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import business.wrapper.TrainingWrapper;
import data.daos.CourtDao;
import data.daos.TrainingDao;
import data.daos.UserDao;
import data.entities.Court;
import data.entities.Training;
import data.entities.User;

@Controller
public class TrainingController {

    private static final int START_TIME = 9;

    private static final int END_TIME = 23;
    
    private TrainingDao trainingDao;
    
    private CourtDao courtDao;
    
    private UserDao userDao;
    
    @Autowired
    public void setTrainingDao(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
    }
    
    @Autowired
    public void setCourtDao(CourtDao courtDao) {
        this.courtDao = courtDao;
    }
    
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    
    public boolean createTraining(int courtId, Calendar date, String username) {
        Training training = new Training(date, userDao.findByUsernameOrEmail(username), courtDao.findOne(courtId));
        if (trainingDao.findByCourtAndInitDate(training.getCourt(), training.getInitDate()) != null) {
            return false;
        }       
        trainingDao.save(training);
        return true;
    }
    
    public boolean deleteTraining(int id){
        Training training = trainingDao.findOne(id);
        if (training == null) {
            return false;
        }
        else {
            trainingDao.delete(training);
            return true;
        }
    }
    
    public boolean deleteTrainingPlayer(int id, String username) {
        Training training = trainingDao.findOne(id);
        if (training == null) {
            return false;
        }
        else {
            User user = userDao.findByUsernameOrEmail(username);
            if (user.equals(training.getPlayer1())) {
                training.setPlayer1(null);
            }
            else if (user.equals(training.getPlayer2())) {
                training.setPlayer2(null);
            }
            else if (user.equals(training.getPlayer3())) {
                training.setPlayer3(null);
            }
            else if (user.equals(training.getPlayer4())) {
                training.setPlayer4(null);
            }
            
            trainingDao.save(training);
            
            return true;
        }
        
    }  
    
    public List<TrainingWrapper> showTrainings(Calendar calendarDay){
        
        List<TrainingWrapper> trainingList = new ArrayList<>();
        
        for (Training training : trainingDao.findAvailableTrainingsByDate(calendarDay)) {
            trainingList.add(new TrainingWrapper(training.getId(), training.getInitDate(), training.getCourt().getId()));
        }
        
        return trainingList;
    }
    
    public boolean registerTraining (int id, String username) {
        Training training = trainingDao.findOne(id);
        if (training == null) {
            return false;
        }
        else {
            User player = userDao.findByUsernameOrEmail(username);
            if (training.getPlayer1() == null) {
                training.setPlayer1(player);
            }
            else if (training.getPlayer2() == null) {
                training.setPlayer2(player);
            }
            else if (training.getPlayer3() == null) {
                training.setPlayer3(player);
            }
            else if (training.getPlayer4() == null) {
                training.setPlayer4(player);
            }
            trainingDao.save(training);
            return true;
        }        
    }            
    
    public boolean rightTime(int hour) {
        return hour >= START_TIME && hour <= END_TIME;
    }

    public boolean exist(int courtId, Calendar date) {
        Court court = courtDao.findOne(courtId);
        return trainingDao.findByCourtAndInitDate(court, date)!=null;
    }
}
