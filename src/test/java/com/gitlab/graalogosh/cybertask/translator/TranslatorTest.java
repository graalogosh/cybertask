package com.gitlab.graalogosh.cybertask.translator;

import com.gitlab.graalogosh.cybertask.task.Task;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

import static org.junit.Assert.assertEquals;

/**
 * Created by Anton Mukovozov (graalogosh@gmail.com) on 04.02.2018.
 */
public class TranslatorTest {

    @Test

    public void getTaskFromString_time1() {
        String input = "Напомни мне покормить кота в 13:45";
        LocalTime expected = LocalTime.parse("13:45");
        Task task = Translator.getTaskFromString(input);

        LocalTime actual = task.getDate().toLocalTime();
        assertEquals(expected, actual);
    }

    @Test
    public void getTaskFromString_time2() {
        String input = "Напомни мне покормить кота в 13.45";
        LocalTime expected = LocalTime.parse("13:45");
        Task task = Translator.getTaskFromString(input);

        LocalTime actual = task.getDate().toLocalTime();
        assertEquals(expected, actual);
    }


    @Test
    //вторник
    public void getTaskFromString_tuesday() {
        String input = "Напомни мне покормить кота во вторник в 13:45";
        LocalDate expected = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
        Task task = Translator.getTaskFromString(input);
        LocalDate actual = task.getDate().toLocalDate();
        assertEquals(expected, actual);
    }

    @Test
    public void getTaskFromString_full() {
        String input = "Напомни мне пожалуйста покормить кота во вторник в 13.45";
        Task expected = Task.builder()
                .title("Покормить кота")
                .date(LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.TUESDAY)), LocalTime.of(13, 45)))
                .build();
        Task actual = Translator.getTaskFromString(input);
        assertEquals(expected, actual);
    }
}