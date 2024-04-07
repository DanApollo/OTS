package data.shows;

import domain.shows.IShow;

import java.util.Date;

public class Show implements IShow {
    private String title;
    private Date date;

    public Show(String title, Date date) {
        this.title = title;
        this.date = date;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Date getDate() {
        return date;
    }
}
