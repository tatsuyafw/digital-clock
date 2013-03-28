package controllers;

import models.User;
import be.objectify.deadbolt.java.actions.Restrict;
import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.SubjectPresent;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.user.AuthUser;

import play.data.Form;
import play.data.format.Formats.NonEmpty;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import providers.MyUsernamePasswordAuthProvider;
import providers.MyUsernamePasswordAuthUser;
import views.html.account.*;

import static play.data.Form.form;

public class Account extends Controller {

  public static class Accept {

    @Required
      @NonEmpty
      public Boolean accept;

    public Boolean getAccept() {
      return accept;
    }

    public void setAccept(Boolean accept) {
      this.accept = accept;
    }

  }

  public static class PasswordChange {
    @MinLength(5)
      @Required
      public String password;

    @MinLength(5)
      @Required
      public String repeatPassword;

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }

    public String getRepeatPassword() {
      return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
      this.repeatPassword = repeatPassword;
    }

    public String validate() {
      if (password == null || !password.equals(repeatPassword)) {
        return Messages
          .get("playauthenticate.change_password.error.passwords_not_same");
      }
      return null;
    }
  }

  private static final Form<Accept> ACCEPT_FORM = form(Accept.class);
  private static final Form<Account.PasswordChange> PASSWORD_CHANGE_FORM = form(Account.PasswordChange.class);

  @SubjectPresent
    public static Result link() {
    com.feth.play.module.pa.controllers.Authenticate.noCache(response());
    return ok(link.render());
  }
}
