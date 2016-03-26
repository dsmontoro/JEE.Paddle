package business.wrapper;

import java.util.Calendar;

public class TrainingWrapper {
    
    private int trainingId;
    
    private Calendar initDate;
    
    private int courtId;
    
    private String playerToDelete;
    
    public TrainingWrapper(){        
    }
    
    public TrainingWrapper(Calendar initDate, int courtId) {
        
        this.initDate = initDate;
        this.courtId = courtId;
    }
    
    public TrainingWrapper(int trainingId, Calendar initDate, int courtId) {
        
        this.trainingId = trainingId;
        this.initDate = initDate;
        this.courtId = courtId;
    }

    public int getTrainingId() {
        return trainingId;
    }
    
    public int getCourtId() {
        return courtId;
    }

    public Calendar getInitDate() {
        return initDate;
    }
    
    public String getPlayerToDelete() {
        return playerToDelete;
    }
    
    public void setPlayerToDelete(String username) {
        this.playerToDelete = username;
    }
    
    @Override
    public String toString() {
        return "TrainingWrapper [trainingId=" + trainingId + ", initDate="+ initDate +", courtId=" + courtId + "]";
    }

}
