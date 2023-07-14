package sg.edu.nus.iss.paf_mongo2try.controller;

import java.util.Date;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.json.JsonObject;
import sg.edu.nus.iss.paf_mongo2try.model.Review;
import sg.edu.nus.iss.paf_mongo2try.service.ReviewService;

@Controller
@RequestMapping(path="/review")
public class ReviewController {

    @Autowired
    private ReviewService svc;

    @GetMapping
    public String getForm(ModelAndView model) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("review", new Review());
        
        return "review";
    }
    
    @PostMapping(consumes="application/x-www-form-urlencoded")
    public ResponseEntity<Document> insertReview(@RequestBody MultiValueMap<String, String> form) {
            int gameId = Integer.parseInt(form.getFirst("gid"));
            if (svc.getGameById(gameId) == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.status(HttpStatus.OK).body(svc.insertReview(form));
        
    }

    @PutMapping(path="/{reviewId}",
                consumes="application/json")
    public ResponseEntity<Boolean> updateReview(@PathVariable String reviewId, @RequestBody Document review) {
            if (svc.updateReview(reviewId, review)) {
                return ResponseEntity.ok().body(true);
            };

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    }

    @GetMapping(path="/{reviewId}")
    public ResponseEntity<Document> getLatest(@PathVariable String reviewId) {
        if ( null == svc.getLatestComment(reviewId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok().body(svc.getLatestComment(reviewId));
    }

    // @GetMapping(produces="applcation/json",
    //             path="/{reviewId}/history")
    // public JsonObject getCommentHistory() {
        

    // }
}
