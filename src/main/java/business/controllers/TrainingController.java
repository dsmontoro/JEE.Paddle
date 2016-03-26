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
import data.entities.Training;

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
    
    public void deleteTraining(int courtId, Calendar date){
        
    }
    
    public void deleteTrainingPlayer(int courtId, Calendar date, String username) {
        
    }  
    
    public List<TrainingWrapper> showTrainings(Calendar calendarDay){
        
        List<TrainingWrapper> trainingList = new ArrayList<>();
        
        for (Training training : trainingDao.findAvailableTrainingsByDate(calendarDay)) {
            trainingList.add(new TrainingWrapper(training.getInitDate(), training.getCourt().getId()));
        }
        
        return trainingList;
    }
    
    public void registerTraining (int courtId, Calendar date, String username) {
        
    }            
    
    public boolean rightTime(int hour) {
        return hour >= START_TIME && hour <= END_TIME;
    }
}
