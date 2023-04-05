package ibf2022.paf.assessment.server.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import ibf2022.paf.assessment.server.models.Task;
import ibf2022.paf.assessment.server.models.User;
import ibf2022.paf.assessment.server.repositories.TaskRepository;
import ibf2022.paf.assessment.server.repositories.UserRepository;

// TODO: Task 7

public class TodoService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    TaskRepository taskRepo;

    public Boolean upsertTask(String username, Task task, User user) {
        // CHECK IF USER EXISTS
        Optional<User> userCheck = userRepo.findUserByUsername(username);
        if (userCheck.isPresent()) {
            // TRUE, CREATE TASK
            return taskRepo.insertTask(task, user.getUserId());
        }
        // FALSE, CREATE USER THEN CREATE TASK
        else {
            String created = userRepo.insertUser(user);
            if (!created.equalsIgnoreCase("Unsuccessful")) {
                return taskRepo.insertTask(task, user.getUserId());
            } else {
                return false;
            }
        }

    }
}
