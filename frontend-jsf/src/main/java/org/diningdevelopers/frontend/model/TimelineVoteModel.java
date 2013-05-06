package org.diningdevelopers.frontend.model;

import java.util.Date;

import org.dozer.Mapping;

public class TimelineVoteModel {

    private TimelineLocationModel location;
    @Mapping("vote")
    private int points;
    private Date date;

    public TimelineLocationModel getLocation() {
        return location;
    }

    public Date getDate() {
        return date;
    }

    public int getPoints() {
        return points;
    }

    public void setLocation(TimelineLocationModel location) {
        this.location = location;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return getLocation() + "=" + points;
    }
}
