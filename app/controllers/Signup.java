package controllers;

import models.TokenAction;
import models.TokenAction.Type;
import models.User;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import providers.MyLoginUsernamePasswordAuthUser;
import providers.MyUsernamePasswordAuthProvider;
import providers.MyUsernamePasswordAuthProvider.MyIdentity;
import providers.MyUsernamePasswordAuthUser;
import views.html.*;

import com.feth.play.module.pa.PlayAuthenticate;

import static play.data.Form.form;

public class Signup extends Controller {

  /**
   * Returns a token object if valid, null if not
   *
   * @param token
   * @param type
   * @return
   */
  private static TokenAction tokenIsValid(final String token, final Type type) {
    TokenAction ret = null;
    if (token != null && !token.trim().isEmpty()) {
      final TokenAction ta = TokenAction.findByToken(token, type);
      if (ta != null && ta.isValid()) {
        ret = ta;
      }
    }

    return ret;
  }

  public static Result oAuthDenied(final String getProviderKey) {
    com.feth.play.module.pa.controllers.Authenticate.noCache(response());
    return ok(oAuthDenied.render(getProviderKey));
  }
}
