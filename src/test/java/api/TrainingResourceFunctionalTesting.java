package api;

import java.util.Calendar;

import org.junit.After;
import org.junit.Test;

import business.api.Uris;
import business.wrapper.TrainingWrapper;

public class TrainingResourceFunctionalTesting {
    
    RestService restService = new RestService();

    @Test
    public void testCreateTraining() {
        restService.createCourt("1");
        restService.createCourt("2");
        String token = restService.registerAndLoginTrainer();
        Calendar day = Calendar.getInstance();
        day.add(Calendar.DAY_OF_YEAR, 1);
        day.set(Calendar.HOUR_OF_DAY,12);
        new RestBuilder<String>(RestService.URL).path(Uris.TRAININGS).basicAuth(token, "").body(new TrainingWrapper(day,1)).post().build();
        day.set(Calendar.HOUR_OF_DAY,14);
        new RestBuilder<String>(RestService.URL).path(Uris.TRAININGS).basicAuth(token, "").body(new TrainingWrapper(day,2)).post().build();
    }
            
    @After
    public void deleteAll() {
        new RestService().deleteAll();
    }
}
