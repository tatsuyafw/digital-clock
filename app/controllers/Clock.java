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
import play.mvc.Http.Context;
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

  public static final String FLASH_ERROR_KEY = "error";

  //final static Form<ClockSetting> clockSettingForm = form(ClockSetting.class);

  // time json , GET /time
  public static Result time() {
    Http.Request req = Context.current().request();

    Map<String, String[]> queries  = req.queryString();
    String timezone = getTimeZone(queries);

    ObjectNode json = Json.newObject();
    json.put("dateTime", dateTimeStr(timezone));
    return ok(json);
  }

  // Clock page, GET /clock
  public static Result clock() {
    User user = Application.getLocalUser(session());

    // when not log in
    if (user == null) {
      final Context ctx = Context.current();
      ctx.flash().put(Application.FLASH_ERROR_KEY, "Sorry... Login with Twitter. And try again!!");
      return redirect(routes.Application.index());
    }

    Form<ClockSetting> clockSettingForm = form(ClockSetting.class);
    String timezone = user.clockSetting.timezone;
    return ok(clock.render(dateTimeStr(timezone), clockSettingForm, timezone));
  }

  // save setting, POST /clock
  public static Result saveClockSetting() {
    Form<ClockSetting> filledForm = form(ClockSetting.class).bindFromRequest();
    User user = Application.getLocalUser(session());

    if (filledForm.hasErrors()) {
      System.out.println("Invalid form!!");
      return badRequest(clock.render(dateTimeStr(), filledForm, user.clockSetting.timezone));
    }

    ClockSetting created = filledForm.get();
    user.clockSetting.timezone = created.timezone;
    user.clockSetting.update();
    String timezone = user.clockSetting.timezone;
    return ok(clock.render(dateTimeStr(timezone), filledForm, timezone));
  }

  private static String dateTimeStr(String timezone) {
    DateTime dt = null;
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
}
