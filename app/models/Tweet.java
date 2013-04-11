package models;

import java.util.Calendar;
import java.util.Date;
import javax.persistence.*;

import play.data.validation.*;
import play.data.format.Formats;
import play.db.ebean.Model;

@Entity
@Table(name = "tweets")
public class Tweet extends Model {
  private static final long serialVersionUID = 1L;

  @Id
  public Long id;

  @Constraints.Required
  @Formats.NonEmpty
  public String content;

  @Constraints.Required
  @Formats.DateTime(pattern="yyyy-MM-dd")
  public Date created;

  @ManyToOne
  public User user;

  public static final Finder<Long, Tweet> find = new Finder<Long, Tweet>(Long.class, Tweet.class);

  public static Tweet findByDate(final User user, int year, int month, int day){
    Calendar cal = Calendar.getInstance();
    cal.set(year, month - 1, day - 1, 0, 0, 0);
    Date date1 = cal.getTime();
    cal.set(year, month - 1, day, 0, 0, 0);
    Date date2 = cal.getTime();

    return find.where().eq("user", user).between("created", date1, date2).findUnique();
  }
}