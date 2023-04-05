package ibf2022.paf.assessment.server.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf2022.paf.assessment.server.models.Task;
import ibf2022.paf.assessment.server.models.User;
import ibf2022.paf.assessment.server.repositories.TaskRepository;
import ibf2022.paf.assessment.server.repositories.UserRepository;

// TODO: Task 7
@Service
public class TodoService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TaskRepository taskRepo;

    public Boolean upsertTask(String username, Task task) {
        // CHECK IF USER EXISTS
        Optional<User> userCheck = userRepo.findUserByUsername(username);
        if (!userCheck.isEmpty()) {
            // TRUE, CREATE TASK
            return taskRepo.insertTask(task, userCheck.get().getUserId());
        }
        // FALSE, CREATE USER THEN CREATE TASK
        else {
            User user = new User();
            user.setUsername(username);
            user.setName(username.toLowerCase());
            String created = userRepo.insertUser(user);
            if (!created.equalsIgnoreCase("Unsuccessful")) {
                return taskRepo.insertTask(task, user.getUserId());
            } else {
                return false;
            }
        }

    }
}
