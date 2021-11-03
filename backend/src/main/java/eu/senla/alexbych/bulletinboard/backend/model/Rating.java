package eu.senla.alexbych.bulletinboard.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;

@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "rate")
    @Max(5)
    private int rate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
