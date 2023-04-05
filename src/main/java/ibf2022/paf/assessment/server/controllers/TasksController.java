package ibf2022.paf.assessment.server.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ibf2022.paf.assessment.server.models.Task;
import ibf2022.paf.assessment.server.services.TodoService;

// TODO: Task 4, Task 8
@Controller
@RequestMapping
public class TasksController {

    @Autowired
    private TodoService todoSvc;

    @PostMapping(path = "/task")
    public ModelAndView postTask(@RequestBody MultiValueMap<String, String> form) throws ParseException {
        Integer taskCount = (form.size() - 1) / 3;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (int i = 0; i < taskCount; i++) {
                Task task = new Task();
                Date date = dateFormat.parse(form.getFirst("dueDate-" + i));
                task.setUsername(form.getFirst("username"));
                task.setDescription(form.getFirst("description-" + i));
                task.setPriority(Integer.parseInt(form.getFirst("priority-" + i)));
                task.setDueDate(date);
                todoSvc.upsertTask(task.getUsername(), task);
            }
            ModelAndView mav = new ModelAndView();
            mav.addObject("username", form.getFirst("username"));
            mav.addObject("taskCount", taskCount);
            mav.setStatus(HttpStatus.valueOf(200));
            mav.setViewName("result");
            return mav;
        } catch (Exception e) {
            ModelAndView mav = new ModelAndView();
            mav.setStatus(HttpStatus.valueOf(500));
            mav.setViewName("error");
            return mav;
        }
    }

}
