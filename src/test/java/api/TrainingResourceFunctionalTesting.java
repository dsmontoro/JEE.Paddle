package api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.junit.After;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import business.api.Uris;
import business.wrapper.TokenWrapper;
import business.wrapper.TrainingWrapper;
import business.wrapper.UserWrapper;
import business.wrapper.UserWrapperBuilder;
import data.entities.Role;

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
    
    @Test
    public void testShowTrainings() {
        restService.createCourt("1");
        restService.createCourt("2");
        String tokenTrainer = restService.registerAndLoginTrainer();
        String tokenPlayer = restService.registerAndLoginPlayer();
        Calendar day = Calendar.getInstance();
        day.add(Calendar.DAY_OF_YEAR, 1);
        day.set(Calendar.HOUR_OF_DAY, 12);
        new RestBuilder<String>(RestService.URL).path(Uris.TRAININGS).basicAuth(tokenTrainer, "").body(new TrainingWrapper(day, 1)).post().build();
        day.set(Calendar.HOUR_OF_DAY, 14);
        new RestBuilder<String>(RestService.URL).path(Uris.TRAININGS).basicAuth(tokenTrainer, "").body(new TrainingWrapper(day, 2)).post().build();
        String day2 = "" + Calendar.getInstance().getTimeInMillis();
        List<TrainingWrapper> list = Arrays.asList(new RestBuilder<TrainingWrapper[]>(RestService.URL).path(Uris.TRAININGS).basicAuth(tokenPlayer, "")
                .param("day", day2).clazz(TrainingWrapper[].class).get().build());
        assertEquals(2, list.size());
    }
    
    @Test
    public void testRegisterTraining() {
        restService.createCourt("1");
        String tokenTrainer = restService.registerAndLoginTrainer();
        String tokenPlayer = restService.registerAndLoginPlayer();
        Calendar day = Calendar.getInstance();
        day.add(Calendar.DAY_OF_YEAR, 1);
        day.set(Calendar.HOUR_OF_DAY, 12);
        TrainingWrapper trainingWrapper = new TrainingWrapper(day,1);
        new RestBuilder<String>(RestService.URL).path(Uris.TRAININGS).basicAuth(tokenTrainer, "").body(trainingWrapper).post().build();
        String day2 = "" + Calendar.getInstance().getTimeInMillis();
        List<TrainingWrapper> list = Arrays.asList(new RestBuilder<TrainingWrapper[]>(RestService.URL).path(Uris.TRAININGS).basicAuth(tokenPlayer, "")
                .param("day", day2).clazz(TrainingWrapper[].class).get().build());
        new RestBuilder<String>(RestService.URL).path(Uris.TRAININGS).pathId(list.get(0).getTrainingId()).basicAuth(tokenPlayer, "").body(list.get(0)).put().build();
    }
    
    @Test
    public void testRegisterTrainingUnauthorized() {
        try {
            new RestBuilder<Object>(RestService.URL).path(Uris.TRAININGS).pathId(0).body(new TrainingWrapper(Calendar.getInstance(),1)).put().build();
            fail();
        } catch (HttpClientErrorException httpError) {
            assertEquals(HttpStatus.UNAUTHORIZED, httpError.getStatusCode());
            LogManager.getLogger(this.getClass()).info(
                    "testRegisterTraining (" + httpError.getMessage() + "):\n    " + httpError.getResponseBodyAsString());
        }
    }
    
    @Test
    public void testDeleteTraining() {
        restService.createCourt("1");
        restService.createCourt("2");
        String tokenTrainer = restService.registerAndLoginTrainer();
        String tokenPlayer = restService.registerAndLoginPlayer();
        Calendar day = Calendar.getInstance();
        day.add(Calendar.DAY_OF_YEAR, 1);
        day.set(Calendar.HOUR_OF_DAY, 12);
        new RestBuilder<String>(RestService.URL).path(Uris.TRAININGS).basicAuth(tokenTrainer, "").body(new TrainingWrapper(day, 1)).post().build();
        day.set(Calendar.HOUR_OF_DAY, 14);
        new RestBuilder<String>(RestService.URL).path(Uris.TRAININGS).basicAuth(tokenTrainer, "").body(new TrainingWrapper(day, 2)).post().build();
        String day2 = "" + Calendar.getInstance().getTimeInMillis();
        List<TrainingWrapper> list = Arrays.asList(new RestBuilder<TrainingWrapper[]>(RestService.URL).path(Uris.TRAININGS).basicAuth(tokenPlayer, "")
                .param("day", day2).clazz(TrainingWrapper[].class).get().build());
        new RestBuilder<String>(RestService.URL).path(Uris.TRAININGS).pathId(list.get(0).getTrainingId()).basicAuth(tokenTrainer, "").delete().build();
        List<TrainingWrapper> list2 = Arrays.asList(new RestBuilder<TrainingWrapper[]>(RestService.URL).path(Uris.TRAININGS).basicAuth(tokenPlayer, "")
                .param("day", day2).clazz(TrainingWrapper[].class).get().build());
        assertEquals(1, list2.size());
    }
    
    @Test
    public void testDeleteTrainingUnauthorized() {
        try {
            new RestBuilder<Object>(RestService.URL).path(Uris.TRAININGS).pathId(0).delete().build();
            fail();
        } catch (HttpClientErrorException httpError) {
            assertEquals(HttpStatus.UNAUTHORIZED, httpError.getStatusCode());
            LogManager.getLogger(this.getClass()).info(
                    "testDeleteTraining (" + httpError.getMessage() + "):\n    " + httpError.getResponseBodyAsString());
        }
    }
    
    @Test
    public void testDeleteTrainingPlayer() {
        restService.createCourt("1");
        String tokenTrainer = restService.registerAndLoginTrainer();
        String tokenPlayer = restService.registerAndLoginPlayer();
        Calendar day = Calendar.getInstance();
        day.add(Calendar.DAY_OF_YEAR, 1);
        day.set(Calendar.HOUR_OF_DAY, 12);
        new RestBuilder<String>(RestService.URL).path(Uris.TRAININGS).basicAuth(tokenTrainer, "").body(new TrainingWrapper(day, 1)).post().build();
        String day2 = "" + Calendar.getInstance().getTimeInMillis();
        List<TrainingWrapper> list = Arrays.asList(new RestBuilder<TrainingWrapper[]>(RestService.URL).path(Uris.TRAININGS).basicAuth(tokenPlayer, "")
                .param("day", day2).clazz(TrainingWrapper[].class).get().build());
        List<String> tokenPlayers = new ArrayList<>();
        List<UserWrapper> players = new ArrayList<>();
        for (int i = 2; i < 6; i++) {
            UserWrapper player = new UserWrapperBuilder(i, Role.PLAYER).build();
            new RestBuilder<Object>(RestService.URL).path(Uris.USERS).body(player).post().build();            
            tokenPlayers.add(new RestBuilder<TokenWrapper>(RestService.URL).path(Uris.TOKENS).basicAuth(player.getUsername(), player.getPassword()).clazz(TokenWrapper.class).post().build().getToken());
            players.add(player);
        }
        TrainingWrapper trainingWrapper = new TrainingWrapper(list.get(0).getTrainingId(), day, 1);
        for (int i = 0 ; i < 4; i++) {            
            new RestBuilder<String>(RestService.URL).path(Uris.TRAININGS).pathId(list.get(0).getTrainingId()).basicAuth(tokenPlayers.get(i), "").body(trainingWrapper).put().build();
        }
        
        trainingWrapper.setPlayerToDelete(players.get(0).getUsername());
        new RestBuilder<String>(RestService.URL).path(Uris.TRAININGS).pathId(list.get(0).getTrainingId()).path(Uris.PLAYER).basicAuth(tokenTrainer, "").body(trainingWrapper).delete().build();          
    }
    
    @Test
    public void testDeleteTrainingPlayerUnauthorized() {
        try {
            new RestBuilder<Object>(RestService.URL).path(Uris.TRAININGS).pathId(0).path(Uris.PLAYER).body(new TrainingWrapper(Calendar.getInstance(),1)).delete().build();
            fail();
        } catch (HttpClientErrorException httpError) {
            assertEquals(HttpStatus.UNAUTHORIZED, httpError.getStatusCode());
            LogManager.getLogger(this.getClass()).info(
                    "testDeleteTraining (" + httpError.getMessage() + "):\n    " + httpError.getResponseBodyAsString());
        }
    }
    
    @After
    public void deleteAll() {
        new RestService().deleteAll();
    }
}
