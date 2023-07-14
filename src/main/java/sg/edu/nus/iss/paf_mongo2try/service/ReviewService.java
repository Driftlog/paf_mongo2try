package sg.edu.nus.iss.paf_mongo2try.service;

import java.util.Date;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import jakarta.json.JsonObject;
import sg.edu.nus.iss.paf_mongo2try.repo.ReviewRepo;

@Service
public class ReviewService {
    
    @Autowired
    private ReviewRepo repo;

    public Document getGameById(int gameId) {
        return repo.findGameById(gameId);
    }

    public Document insertReview(MultiValueMap<String, String> form) {
        
        Document review = new Document()
            .append("user", form.getFirst("name"))
            .append("rating", form.getFirst("rating"))
            .append("comment", form.getFirst("comment"))
            .append("ID", form.getFirst("gid"))
            .append("user", new Date())
            .append("gname", form.getFirst("gname"));
        
        return repo.insertReview(review);

    }

    public Boolean updateReview(String commentId, Document review) {
        if (repo.getReviewById(commentId) == null) {
            System.out.println("failed");
            return false;
        }

        Document generatedReview = new Document().append("comment", review.getString("name"))
                                                .append("rating", review.getInteger("rating"))
                                                .append("posted", new Date());

        return repo.updateReview(commentId, generatedReview);
        
    }

    public Document getLatestComment(String reviewId) {
       Document review = repo.getReviewById(reviewId);

       if (review == null) {
            return null;
       }

       Document game = repo.findGameById(review.getInteger("gid"));
       
       return repo.getLatestComment(review, game);
       
    }
    
}
