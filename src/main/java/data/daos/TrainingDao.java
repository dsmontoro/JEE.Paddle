package data.daos;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import data.entities.Training;
import data.entities.User;

public interface TrainingDao extends JpaRepository<Training, Integer>{

    @Query("SELECT t FROM Training t WHERE t.initDate > ?1 AND (t.player1 is null OR t.player2 is null OR t.player3 is null OR t.player4 is null)")
    List<Training> findAvailableTrainingsByDate(Calendar date); 

}
