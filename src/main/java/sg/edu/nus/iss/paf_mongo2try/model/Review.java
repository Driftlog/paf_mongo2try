package sg.edu.nus.iss.paf_mongo2try.model;

import java.util.Date;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class Review{

        private String name;
        @Min(value=0)
        @Max(value=10)
        private int rating;
        private int gameId;
        private Date posted;
}
