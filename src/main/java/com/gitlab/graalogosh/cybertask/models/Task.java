package com.gitlab.graalogosh.cybertask.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

/**
 * Created by Anton Mukovozov (graalogosh@gmail.com) on 30.01.2018.
 */
@Data
public class Task {
    @Id
    private String id;

    private String title;
    private String content;

    private String where;

    private Date date;
    private Date completedDate;
    private List<Date> notificationDates;

    private List<Task> subtasks;
    private Boolean needToCompleteAllSubtasks;
}
