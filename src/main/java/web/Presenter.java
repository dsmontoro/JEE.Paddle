package web;

import java.text.SimpleDateFormat;
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
import business.wrapper.CourtState;
import data.entities.Court;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@SessionAttributes("name")
public class Presenter {

    @Autowired
    private ServletContext servletContext;
    
    @Autowired
    private CourtController courtController;

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

}
