package sg.edu.nus.iss.paf_mongo2try.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.nus.iss.paf_mongo2try.model.Review;

@Controller
public class FormController {
    
        @GetMapping
    public String getForm(ModelAndView model) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("review", new Review());
        
        return "review";
    }
}
