package web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import business.controllers.CourtController;
import business.controllers.UserController;
import business.wrapper.CourtState;
import business.wrapper.UserWrapper;
import data.entities.Court;
import data.entities.User;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@SessionAttributes("name")
public class Presenter {

    @Autowired
    private ServletContext servletContext;
    
    @Autowired
    private CourtController courtController;
    
    @Autowired
    private UserController userController;
    
    public Presenter() {
    }

    @ModelAttribute("now")
    public String now() {
        return new SimpleDateFormat("EEEE, d MMM yyyy HH:mm:ss").format(new Date());
    }

    @RequestMapping("/home")
    public String home(Model model) {
        return "home";
    }
    
    @RequestMapping("/show-courts")
    public ModelAndView showCourts() {
    	ModelAndView modelAndView = new ModelAndView("/courtList");
        modelAndView.addObject("courtList", courtController.showCourts());
        return modelAndView;
    }
    
    @RequestMapping(value = "/create-court", method = RequestMethod.GET)
    public String createCourt(Model model) {
        model.addAttribute("court", new Court());
        return "createCourt";
    }
    
    @RequestMapping(value = "/create-court", method = RequestMethod.POST)
    public String createCourtSubmit(@Valid Court court, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {         
            if (courtController.createCourt(court.getId())) {
                model.addAttribute("id", court.getId());
                return "createCourtSuccess";
            } else {
                bindingResult.rejectValue("id", "error.court", "Pista ya existente");
            }
        }
        return "createCourt";
    }
    
    @RequestMapping(value = "/register-user", method = RequestMethod.GET)
    public String registerUser(Model model) {
        model.addAttribute("user", new UserWrapper());
        return "registerUser";
    }

    @RequestMapping(value = "/register-user", method = RequestMethod.POST)
    public String registerUserSubmit(@Valid UserWrapper user, String birthdate, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            int year = Integer.parseInt(birthdate.substring(0,4));
            int month = Integer.parseInt(birthdate.substring(5, 7));
            int day = Integer.parseInt(birthdate.substring(8, 10));
            Calendar date = Calendar.getInstance();
            date.set(year, month - 1, day);
            user.setBirthDate(date);
            if (userController.registration(user)) {
                model.addAttribute("user", user);
                return "registerUserSuccess";
            } else {
                bindingResult.rejectValue("username", "error.user", "Nombre de Usuario ya existente");
            }
        }        
        return "registerUser";
    }

}
