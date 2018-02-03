package com.gitlab.graalogosh.cybertask.user;

import com.gitlab.graalogosh.cybertask.task.Task;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * Created by Anton Mukovozov (graalogosh@gmail.com) on 30.01.2018.
 */
@Data
public class User {
    @Id
    private String id;

    private String username;

    private String email;
    private String firstName;

    //TODO change to hash and unique salt
    private String password;

    private String telegramId;

    private List<Task> tasks;

}
