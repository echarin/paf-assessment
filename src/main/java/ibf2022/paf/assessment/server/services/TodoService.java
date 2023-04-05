package ibf2022.paf.assessment.server.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import ibf2022.paf.assessment.server.models.Task;
import ibf2022.paf.assessment.server.models.User;
import ibf2022.paf.assessment.server.repositories.TaskRepository;
import ibf2022.paf.assessment.server.repositories.UserRepository;

@Service
public class TodoService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TaskRepository taskRepo;

    @Transactional
    public void upsertTask(String username, List<Task> tasks) {
        Optional<User> optUser = userRepo.findUserByUsername(username);
        User user = null;

        if (optUser.isEmpty()) {
            user = new User();
            user.setUsername(username);
            userRepo.insertUser(user);
        }

        for (Task t : tasks) {
            taskRepo.insertTask(user, t);
        }
    }

    public List<Task> generateTasks(MultiValueMap<String, String> form) {
        List<String> descriptions = form.get("description");
        List<String> priorities = form.get("priority");
        List<String> dueDates = form.get("dueDate");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        List<Task> tasks = new LinkedList<>();

        for (int i = 0; i < descriptions.size(); i++) {
            Task task = new Task();
            task.setDescription(descriptions.get(i));

            task.setPriority(Integer.parseInt(priorities.get(i)));

            String date = dueDates.get(i);
            LocalDate localDate = LocalDate.parse(date, formatter);
            task.setDueDate(localDate);

            tasks.add(task);
        }

        return tasks;
    }
}
