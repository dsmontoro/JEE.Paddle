package api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.junit.After;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

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
    
    @Test
    public void testCreateTrainingUnauthorized() {
        try {
            new RestBuilder<Object>(RestService.URL).path(Uris.TRAININGS).body(new TrainingWrapper(Calendar.getInstance(),1)).post().build();
            fail();
        } catch (HttpClientErrorException httpError) {
            assertEquals(HttpStatus.UNAUTHORIZED, httpError.getStatusCode());
            LogManager.getLogger(this.getClass()).info(
                    "testCreateTraining (" + httpError.getMessage() + "):\n    " + httpError.getResponseBodyAsString());
        }
    }
            
    @After
    public void deleteAll() {
        new RestService().deleteAll();
    }
}
