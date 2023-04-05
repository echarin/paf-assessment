package ibf2022.paf.assessment.server.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

// TODO: Task 4, Task 8

@Controller
@RequestMapping
public class TasksController {

    
    @PostMapping("/task")
    public void postTasks(@RequestBody MultiValueMap<String, String> form) {
        String username = form.getFirst("username");
    }
}
