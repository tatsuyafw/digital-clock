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
import providers.MyUsernamePasswordAuthProvider;
import providers.MyUsernamePasswordAuthProvider.MyLogin;
import providers.MyUsernamePasswordAuthProvider.MySignup;
import views.html.*;
import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.providers.password.UsernamePasswordAuthProvider;
import com.feth.play.module.pa.user.AuthUser;

public class Application extends Controller {

  public static final String FLASH_MESSAGE_KEY = "message";
  public static final String FLASH_ERROR_KEY = "error";
  public static final String USER_ROLE = "user";

  public static Result time() {
    Http.Request req = play.mvc.Http.Context.current().request();
    
    Map<String, String[]> queries  = req.queryString();
    String timezone = getTimeZone(queries);
    
    ObjectNode json = Json.newObject();
    json.put("dateTime", dateTimeStr(timezone));
    return ok(json);
  }

  public static Result index() {

    String msg  = "Hello, nicocale";
    return ok(index.render(msg, dateTimeStr()));
  }

  public static Result clock() {
    return ok(clock.render(dateTimeStr()));
  }

  public static Result login() {
    String msg = "Login";
    return ok(login.render(msg));
  }

    public static User getLocalUser(final Session session) {
    final AuthUser currentAuthUser = PlayAuthenticate.getUser(session);
    final User localUser = User.findByAuthUserIdentity(currentAuthUser);
    return localUser;
  }

  public static Result oAuth(final String provider) {
    Http.Request req = play.mvc.Http.Context.current().request();

    if (req.queryString().containsKey("denied")) {
      return oAuthDenied(provider);
    }

    return com.feth.play.module.pa.controllers.Authenticate.authenticate(provider);
  }

  public static Result oAuthDenied(final String providerKey) {
    com.feth.play.module.pa.controllers.Authenticate.noCache(response());
    flash(FLASH_ERROR_KEY,
          "You need to accept the OAuth connection in order to use this website!");
    return redirect(routes.Application.index());
  }

  public static Result signup() {
    return ok(signup.render(MyUsernamePasswordAuthProvider.SIGNUP_FORM));
  }

  public static Result doSignup() {
    com.feth.play.module.pa.controllers.Authenticate.noCache(response());
    final Form<MySignup> filledForm = MyUsernamePasswordAuthProvider.SIGNUP_FORM
      .bindFromRequest();
    if (filledForm.hasErrors()) {
      // User did not fill everything properly
      return badRequest(signup.render(filledForm));
    } else {
      // Everything was filled
      // do something with your part of the form before handling the user
      // signup
      return UsernamePasswordAuthProvider.handleSignup(ctx());
    }
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
