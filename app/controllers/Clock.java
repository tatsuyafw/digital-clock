package controllers;

import org.codehaus.jackson.node.ObjectNode;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import java.util.Map;

import models.User;
import play.*;
import play.data.Form;
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

  public static Result time() {
    Http.Request req = play.mvc.Http.Context.current().request();

    Map<String, String[]> queries  = req.queryString();
    String timezone = getTimeZone(queries);

    ObjectNode json = Json.newObject();
    json.put("dateTime", dateTimeStr(timezone));
    return ok(json);
  }

  public static Result clock() {
    return ok(clock.render(dateTimeStr()));
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
