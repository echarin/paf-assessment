package ibf2022.paf.assessment.server.controllers;

import java.util.List;

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

@Controller
@RequestMapping
public class TasksController {

    @Autowired
    private TodoService todoSvc;

    @PostMapping("/task")
    public ModelAndView postTasks(@RequestBody MultiValueMap<String, String> form) {
        String username = form.getFirst("username");
        List<Task> tasks = todoSvc.generateTasks(form);

        ModelAndView mav = new ModelAndView();
        
        try { 
            todoSvc.upsertTask(username, tasks);
            mav.setViewName("result.html");
            mav.addObject("taskCount", tasks.size());
            mav.addObject("username", username);
            mav.setStatus(HttpStatus.OK);
            return mav;
        } catch (Exception ex) {
            mav.setViewName("error.html");
            mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return mav;
        }
    }
}
