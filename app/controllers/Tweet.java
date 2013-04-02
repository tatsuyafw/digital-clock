package controllers;

import models.User;
import play.*;
import play.mvc.*;
import play.mvc.Http.Context;

@Security.Authenticated(Secured.class)
public class Tweet extends Controller {

  public static Result tweet() {
    User localUser = Application.getLocalUser(session());
    if (localUser == null) {
      final Context ctx = Context.current();
      ctx.flash().put(Application.FLASH_ERROR_KEY, "Sorry... Login with Twitter. And try again!!");
      return redirect(routes.Application.index());
    }
    
    return ok("Tweet! (TODO)");
  }

}
