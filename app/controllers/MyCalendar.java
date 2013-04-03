package controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import models.*;
import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
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
public class MyCalendar extends Controller {

  /**
   * @param year
   * @param month 1 - 12
   * If year or month is not specified, it's set current year or month.
   */
  public static Result calendar(int year, int month) {
    User localUser = Application.getLocalUser(session());
    if (localUser == null) {
      final Context ctx = Context.current();
      ctx.flash().put(Application.FLASH_ERROR_KEY, "Sorry... Login with Twitter. And try again!!");
      return redirect(routes.Application.index());
    }

    if (year == 0 || month == 0) {
      Calendar cal  = Calendar.getInstance();
      year  = cal.get(Calendar.YEAR);
      month = cal.get(Calendar.MONTH) + 1;
    } else if (year < 0 || month < 0){
      return badRequest("bad request");
    }

    int prevYear  = year;
    int prevMonth = month - 1;
    if (prevMonth == 0) {
      prevYear--;
      prevMonth = 12;
    }

    int nextYear  = year;
    int nextMonth = month + 1;
    if (nextMonth == 13) {
      nextYear++;
      nextMonth = 1;
    }

    Form<Tweet> tweetForm = form(Tweet.class);
    
    //return ok(calendar.render(tweetForm, prevYear, prevMonth, year, month, nextYear, nextMonth, cal(year, month)));
    return ok(calendar.render(localUser, tweetForm, prevYear, prevMonth, year, month, nextYear, nextMonth, cal(year, month)));
  }

  public static Result tweet() {
    User localUser = Application.getLocalUser(session());
    if (localUser == null) {
      final Context ctx = Context.current();
      ctx.flash().put(Application.FLASH_ERROR_KEY, "Sorry... Login with Twitter. And try again!!");
      return redirect(routes.Application.index());
    }
    
    Form<Tweet> tweetForm = form(Tweet.class).bindFromRequest();
    if (tweetForm.hasErrors()) {
      return badRequest("bad request");
    }

    Tweet created = tweetForm.get();
    localUser.tweets.add(created);    
    localUser.update();
    created.save();
    System.out.println("tweet(): " + created.created);
    Calendar cal = Calendar.getInstance();
    cal.setTime(created.created);
    int year  = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH) + 1;

    return redirect(routes.MyCalendar.calendar(year, month));
  }

  /**
   * @param year
   * @param month 1 - 12
   */
  private static List<List<String>> cal(int year, int month) {
    List<List<String>> all = new ArrayList<List<String>>();

    Calendar cal  = Calendar.getInstance();
    cal.set(year, month - 1, 1);
    Integer firstDay = cal.get(Calendar.DAY_OF_WEEK);
    Integer dayCount = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

    int daysOfWeek = 7;
    int weekCount = ((dayCount + firstDay - 1) / daysOfWeek) + 1;

    int day = 2 - firstDay;
    for (int i = 0; i < weekCount; i++) {
      List<String> week = new ArrayList<String>();
      all.add(week);

      for (int j = 0; j < daysOfWeek; j++) {
        if (day <= 0 || day > dayCount) {
          week.add("");
        } else {
          week.add(String.valueOf(day));
        }
        day++;
      }
    }

    return all;
  }

}
