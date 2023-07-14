package sg.edu.nus.iss.paf_mongo2try.repo;

import java.util.Date;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

@Repository
public class ReviewRepo {
    
    @Autowired
    private MongoTemplate template;

    //db.game.find({gid: 1, users_rated: { $exists: true}})
    public Document findGameById(int gameId) {
        Query query = Query.query(Criteria.where("gid").is(gameId).and("users_rated").exists(true));
        Document game = template.findOne(query, Document.class, "game");
        return game;
    }

    //db.comments.insert()
    public Document insertReview(Document review) {
        Document newReview = template.insert(review, "comments");
        return newReview;
    }

    //db.comments.find({c_id: reviewId})
    public Document getReviewById(String reviewId) {
        Query query = Query.query(Criteria.where("c_id").is(reviewId));
        Document review = template.findOne(query, Document.class, "comments");
        return review;
    }

    // //db.comments.updateOne(
    //     {c_id: "reviewId"},
    //     {
                // $push: {edited: "updatedReview"}
    //     }
    // )
    public boolean updateReview(String reviewId, Document updatedReview) {
        Query query = Query.query(Criteria.where("c_id").is(reviewId));

        Update updateOps = new Update()
            .set("c_text", updatedReview.getString("comment"))
            .set("rating", updatedReview.getInteger("rating"))
            .set("posted", new Date())
            .push("edited", updatedReview);

         UpdateResult updateResult = template.updateFirst(query, updateOps, Document.class, "comments");

         return (updateResult.getModifiedCount() > 0);
    }

    public Document getLatestComment(Document review, Document game) {

        Document latestComment = new Document()
                                    .append("user", review.getString("user"))
                                    .append("rating", review.getInteger("rating"))
                                    .append("comment", review.getString("c_text"))
                                    .append("ID", review.get("gid"))
                                    .append("posted", review.getDate("posted"))
                                    .append("name", game.getString("name"));
        
        if (review.getList("edited", Document.class) != null) {
            latestComment.append("edited", true);
        } else {
            latestComment.append("edited", false);
        }
                                    latestComment.append("timestamp", new Date());
        
        return latestComment;
    }

}
