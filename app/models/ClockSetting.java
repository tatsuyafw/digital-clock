package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.persistence.*;

import play.db.ebean.Model;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.validation.NotNull;

@Entity
//@Table(name = "")
public class ClockSetting extends Model {
  private static final long serialVersionUID = 1L;

  @Id
  public Long id;

  public String timezone;

  @CreatedTimestamp
  public Date createdAt;

  @Version
  public Date updatedAt;

  public static ClockSetting create() {
    final ClockSetting clockSetting = new ClockSetting();
    clockSetting.timezone = "Asia/Tokyo";
    return clockSetting;
  }

  @Override
  public String toString() {
    return "ClockSetting: " + timezone;
  }
}
