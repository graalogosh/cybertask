package com.gitlab.graalogosh.cybertask.translator;

import com.gitlab.graalogosh.cybertask.task.Task;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Anton Mukovozov (graalogosh@gmail.com) on 04.02.2018.
 */
public class Translator {
    static Task getTaskFromString(String input) {
        /*TODO проверять сначала время, потом дату, если общий объект меньше текущего времени, то либо прибавлять один день,
               либо искать следующий, такой же как сегодня, день недели*/

        //date
        LocalDate date = getDateFromString(input);

        //time
        LocalTime time = getTimeFromString(input);

        //combine date and time
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime resultDateTime = LocalDateTime.of(date, time);
        if (resultDateTime.isBefore(now)) {
            resultDateTime = resultDateTime.plusDays(1);
        }


        //subject


        return Task.builder().date(resultDateTime).build();
    }

    static private LocalDate getDateFromString(String input) {
        Pattern todayPattern = Pattern.compile("(сегодня)");
        Matcher m = todayPattern.matcher(input);
        if (m.find()) {
            return LocalDate.now();
        }

        Pattern tomorrowPattern = Pattern.compile("(завтра)");
        m = tomorrowPattern.matcher(input);
        if (m.find()) {
            return LocalDate.now().plusDays(1);
        }

        Pattern monday = Pattern.compile("(в понедельник)");
        m = monday.matcher(input);
        if (m.find()) {
            return LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        }

        Pattern tuesday = Pattern.compile("(во вторник)");
        m = tuesday.matcher(input);
        if (m.find()) {
            return LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.TUESDAY));

        }

        Pattern wednesday = Pattern.compile("(в среду)");
        m = wednesday.matcher(input);
        if (m.find()) {
            return LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));

        }

        Pattern thursday = Pattern.compile("(в четверг)");
        m = thursday.matcher(input);
        if (m.find()) {
            return LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.THURSDAY));

        }

        Pattern friday = Pattern.compile("(в пятницу)");
        m = friday.matcher(input);
        if (m.find()) {
            return LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.FRIDAY));

        }

        Pattern saturday = Pattern.compile("(в субботу)");
        m = saturday.matcher(input);
        if (m.find()) {
            return LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY));

        }

        Pattern sunday = Pattern.compile("(в воскресенье)q");
        m = sunday.matcher(input);
        if (m.find()) {
            return LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY));

        }

        //else
        return LocalDate.now();
    }

    static private LocalTime getTimeFromString(String input) {
        Pattern timePattern = Pattern.compile("(([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9])");
        Matcher matcher = timePattern.matcher(input);
        LocalTime time = null;
        if (matcher.find()) {
            String timeString = matcher.group(1);
            input.replace(timeString, "");
            time = LocalTime.parse(timeString);
        }
        return time;
    }
}
