package business.api;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import business.api.exceptions.InvalidCourtTrainingException;
import business.api.exceptions.InvalidDateException;
import business.api.exceptions.NotFoundTrainingIdException;
import business.controllers.CourtController;
import business.controllers.TrainingController;
import business.wrapper.TrainingWrapper;

@RestController
@RequestMapping(Uris.SERVLET_MAP + Uris.TRAININGS)
public class TrainingResource {
    
    private TrainingController trainingController;
    
    private CourtController courtController;
    
    @Autowired
    public void setTrainingController(TrainingController trainingController) {
        this.trainingController = trainingController;
    }
    
    @Autowired
    public void setCourtController(CourtController courtController) {
        this.courtController = courtController;
    }
    
    private void validateDay(Calendar day) throws InvalidDateException {
        Calendar calendarDay = Calendar.getInstance();
        calendarDay.add(Calendar.DAY_OF_YEAR, -1);
        if (calendarDay.after(day)) {
            throw new InvalidDateException("La fecha no puede ser un día pasado");
        }
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public void createTraining(@AuthenticationPrincipal User activeUser, @RequestBody TrainingWrapper trainingWrapper) 
            throws InvalidCourtTrainingException, InvalidDateException {
        if (!courtController.exist(trainingWrapper.getCourtId())) {
            throw new InvalidCourtTrainingException("" + trainingWrapper.getCourtId());
        }
        Calendar date = trainingWrapper.getInitDate();
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        if (!trainingController.rightTime(date.get(Calendar.HOUR_OF_DAY))) {
            throw new InvalidCourtTrainingException(date.get(Calendar.HOUR_OF_DAY) + " fuera de rango");
        }
        this.validateDay(date);
        if (!trainingController.createTraining(trainingWrapper.getCourtId(), date, activeUser.getUsername())) {
            throw new InvalidCourtTrainingException(trainingWrapper.getCourtId() + "-" + trainingWrapper.getInitDate());
        }
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public List<TrainingWrapper> showTrainings(@AuthenticationPrincipal User activeUser, @RequestParam(required = false) Long day) throws InvalidDateException {
        Calendar calendarDay = Calendar.getInstance();
        if (day != null) {
            calendarDay.setTimeInMillis(day);
            this.validateDay(calendarDay);
        }
        calendarDay.set(Calendar.HOUR, 0);
        calendarDay.set(Calendar.MINUTE, 0);
        calendarDay.set(Calendar.SECOND, 0);
        calendarDay.set(Calendar.MILLISECOND, 0);
        
        return trainingController.showTrainings(calendarDay);
    }
    
    @RequestMapping(value = Uris.ID, method = RequestMethod.PUT)
    public void registerTraining(@AuthenticationPrincipal User activeUser, @PathVariable int id) throws NotFoundTrainingIdException {
        if (!trainingController.registerTraining(id, activeUser.getUsername()))
            throw new NotFoundTrainingIdException();
    }
    
    @RequestMapping(value = Uris.ID, method = RequestMethod.DELETE)
    public void deleteTraining(@AuthenticationPrincipal User activeUser, @PathVariable int id) throws NotFoundTrainingIdException {
        if (!trainingController.deleteTraining(id))
            throw new NotFoundTrainingIdException();
    }
    
    @RequestMapping(value = Uris.ID + Uris.PLAYER, method = RequestMethod.DELETE)
    public void deleteTrainingPlayer(@AuthenticationPrincipal User activeUser, @PathVariable int id, @RequestBody TrainingWrapper trainingWrapper) throws NotFoundTrainingIdException {
        if (!trainingController.deleteTrainingPlayer(id, trainingWrapper.getPlayerToDelete()))
            throw new NotFoundTrainingIdException();
    }
}
