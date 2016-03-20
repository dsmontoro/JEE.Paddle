package data.entities;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Training {
    
    @Id
    @GeneratedValue
    private int id;
    
    @Column(nullable=false)
    private Calendar initDate;
    
    @Column(nullable=false)
    private Calendar finishDate;
    
    @ManyToOne
    @JoinColumn
    private Court court;
    
    @ManyToOne
    @JoinColumn
    private User trainer;
    
    @ManyToOne
    @JoinColumn
    private User player1;
    
    @ManyToOne
    @JoinColumn
    private User player2;
    
    @ManyToOne
    @JoinColumn
    private User player3;
    
    @ManyToOne
    @JoinColumn
    private User player4;
    
    @Override
    public String toString() {
        return "Training [id=" + id + ", initDate=" + initDate + ", finishDate=" + finishDate + ", trainer=" + trainer + "]";
    }

    public Training() {
        
    }
    
    public Training(Calendar initDate, User trainer, Court court) {
        assert initDate != null && trainer != null && court != null;
        this.initDate = initDate;
        Calendar finishDate = (Calendar) initDate.clone();
        finishDate.add(Calendar.HOUR, 1);
        this.finishDate = finishDate;
        this.trainer = trainer;
        this.court = court;
    }
    
    public int getId() {
        return id;
    }
    
    public Calendar getInitDate() {
        return initDate;
    }
    
    public Calendar getFinishDate() {
        return finishDate;
    }
    
    public User getTrainer() {
        return trainer;
    }
    
    public Court getCourt() {
        return court;
    }
    
    public User getPlayer1() {
        return player1;
    }
    
    public User getPlayer2() {
        return player2;
    }
    
    public User getPlayer3() {
        return player3;
    }
    
    public User getPlayer4() {
        return player4;
    }
    
    public void setPlayer1(User player) {
        this.player1= player;
    }
    
    public void setPlayer2(User player) {
        this.player2= player;
    }
    
    public void setPlayer3(User player) {
        this.player3= player;
    }
    
    public void setPlayer4(User player) {
        this.player4= player;
    }
        
    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (getClass() != obj.getClass()) {
            return false;
        } else {
            return id == ((Training) obj).id;
        }
    }    

}
