package controllers;

import play.*;
import play.mvc.*;

import views.html.*;
import com.feth.play.module.pa.PlayAuthenticate;

public class Application extends Controller {

  public static final String FLASH_MESSAGE_KEY = "message";
  public static final String FLASH_ERROR_KEY = "error";

  public static Result index() {
    return ok(index.render("Hello, nicocale"));
  }
  public static Result oAuthDenied(final String providerKey) {
    com.feth.play.module.pa.controllers.Authenticate.noCache(response());
    flash(FLASH_ERROR_KEY,
        "You need to accept the OAuth connection in order to use this website!");
    return redirect(routes.Application.index());
  }

}
