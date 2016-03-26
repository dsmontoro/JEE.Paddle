package business.wrapper;

import java.util.Calendar;

public class TrainingWrapper {
    
    private Calendar initDate;
    
    private int courtId;
        
    public TrainingWrapper(){        
    }
    
    public TrainingWrapper(Calendar initDate, int courtId) {
        
        this.initDate = initDate;
        this.courtId = courtId;
    }

    public int getCourtId() {
        return courtId;
    }

    public Calendar getInitDate() {
        return initDate;
    }
    
    @Override
    public String toString() {
        return "TrainingWrapper [initDate="+ initDate +", courtId=" + courtId + "]";
    }

}
