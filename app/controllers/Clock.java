package controllers;

import org.codehaus.jackson.node.ObjectNode;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import java.util.List;
import java.util.Map;

import models.*;
import play.*;
import play.data.*;
import static play.data.Form.*;
import play.libs.Json;
import play.mvc.*;
import play.mvc.Http.Session;
import play.mvc.Security;
import providers.MyUsernamePasswordAuthProvider;
import providers.MyUsernamePasswordAuthProvider.MyLogin;
import providers.MyUsernamePasswordAuthProvider.MySignup;
import views.html.*;
import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.providers.password.UsernamePasswordAuthProvider;
import com.feth.play.module.pa.user.AuthUser;

@Security.Authenticated(Secured.class)
public class Clock extends Controller {

  final static Form<ClockSetting> clockSettingForm = form(ClockSetting.class);

  public static Result time() {
    Http.Request req = play.mvc.Http.Context.current().request();

    Map<String, String[]> queries  = req.queryString();
    String timezone = getTimeZone(queries);

    ObjectNode json = Json.newObject();
    json.put("dateTime", dateTimeStr(timezone));
    return ok(json);
  }

  public static Result clock() {
    return ok(clock.render(dateTimeStr(), getTimeZones(), clockSettingForm));
  }

  public static Result saveClockSetting() {
    return ok("saveClockSetting");
  }

  private static String dateTimeStr(String timezone) {
    ;    DateTime dt = null;
    if (timezone == null) {
      dt = new DateTime(DateTimeZone.forID("Asia/Tokyo"));
    } else {
      dt = new DateTime(DateTimeZone.forID(timezone));
    }
    return dt.toString("yyyy/MM/dd HH:mm:ss");
  }

  private static String dateTimeStr() {
    return dateTimeStr(null);
  }

  private static String getTimeZone(Map<String, String[]> queries) {
    String[] timezones = queries.get("timezone");
    if (timezones.length == 0) {
      return null;
    }
    return timezones[0];
  }

  private static List<String> getTimeZones() {
    String[] timezones = {
      "UTC",
      "WET",
      "Europe/Rome",
      "CET",
      "Africa/Cairo",
      "Africa/Lusaka",
      "Europe/Istanbul",
      "Europe/Moscow",
      "Asia/Baghdad",
      "Asia/Tehran",
      "Asia/Karachi",
      "Asia/Calcutta",
      "Asia/Bangkok",
      "Asia/Hong_Kong",
      "Asia/Tokyo",
      "Pacific/Guam",
      "Pacific/Noumea",
      "Pacific/Fiji",
      "Pacific/Auckland",
      "Pacific/Tongatapu",
      "Pacific/Kiritimati",
      "Pacific/Pago_Pago",
      "Pacific/Honolulu",
      "Pacific/Gambier",
      "US/Alaska",
      "America/Los_Angeles",
      "Canada/Mountain",
      "America/Merida",
      "America/Winnipeg",
      "America/New_York",
      "Atlantic/Stanley",
      "Brazil/DeNoronha",
      "Atlantic/Cape_Verde"
    };
    return java.util.Arrays.asList(timezones);
  }
}
