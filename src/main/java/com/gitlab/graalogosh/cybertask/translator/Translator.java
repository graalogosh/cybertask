package com.gitlab.graalogosh.cybertask.translator;

import com.gitlab.graalogosh.cybertask.task.Task;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;
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

        HashMap<String, DayOfWeek> weekdays = new HashMap<>();
        weekdays.put("(в понедельник)", DayOfWeek.MONDAY);
        weekdays.put("(во вторник)", DayOfWeek.TUESDAY);
        weekdays.put("(в среду)", DayOfWeek.WEDNESDAY);
        weekdays.put("(в четверг)", DayOfWeek.THURSDAY);
        weekdays.put("(в пятницу)", DayOfWeek.FRIDAY);
        weekdays.put("(в субботу)", DayOfWeek.SATURDAY);
        weekdays.put("(в воскресенье)", DayOfWeek.SUNDAY);

        for (Map.Entry<String, DayOfWeek> weekday : weekdays.entrySet()){
            Pattern pattern = Pattern.compile(weekday.getKey());
            m = pattern.matcher(input);
            if (m.find()) {
                return LocalDate.now().with(TemporalAdjusters.next(weekday.getValue()));
            }
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
