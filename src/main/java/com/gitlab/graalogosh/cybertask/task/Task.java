package com.gitlab.graalogosh.cybertask.task;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Anton Mukovozov (graalogosh@gmail.com) on 30.01.2018.
 */
@Data
@Builder
public class Task {
    @Id
    private String id;

    private String title;
    private String content;

    private String where;

    private LocalDateTime date;
    private LocalDateTime completedDate;
    private List<LocalDateTime> notificationDates;
    private Boolean recurring;

    private List<Task> subtasks;
    private Boolean needToCompleteAllSubtasks;
}
