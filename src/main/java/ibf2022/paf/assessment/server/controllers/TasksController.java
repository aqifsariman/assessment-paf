package ibf2022.paf.assessment.server.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import ibf2022.paf.assessment.server.models.Task;

// TODO: Task 4, Task 8

@Controller
@RequestMapping()
public class TasksController {

    @PostMapping(path = "/task")
    public String postTask(@RequestBody MultiValueMap<String, String> form) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(form.getFirst("dueDate-0"));

        Task task = new Task();
        task.setUsername(form.getFirst("username"));
        task.setDescription(form.getFirst("description-0"));
        task.setPriority(Integer.parseInt(form.getFirst("priority-0")));
        task.setDueDate(date);

        return "result";
    }

}
