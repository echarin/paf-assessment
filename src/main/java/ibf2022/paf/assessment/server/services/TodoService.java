package ibf2022.paf.assessment.server.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        } else {
            user = optUser.get();
        }

        for (Task t : tasks) {
            taskRepo.insertTask(user, t);
        }
    }

    public List<Task> generateTasks(MultiValueMap<String, String> form) {
        List<List<String>> formFields = form.values().stream().collect(Collectors.toList());
        Integer formSize = formFields.size();
        

        // Task fields are located in every 3rd field
        Integer startRange = 1;
        Integer endRange = formSize;

        List<String> descriptions = filterEveryNthElement(startRange, endRange, 1, formFields);
        List<String> priorities = filterEveryNthElement(startRange, endRange, 2, formFields);
        List<String> dueDates = filterEveryNthElement(startRange, endRange, 0, formFields);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
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

    public List<String> filterEveryNthElement(Integer startInclusive, Integer endExclusive, Integer moduloByThree, List<List<String>> listToFilter) {
        IntStream stream = IntStream.range(startInclusive, endExclusive);

        // Since each form field is a List<String> with only one value, we shall call get(0) to get that value
        return stream.filter(i -> i % 3 == moduloByThree).mapToObj(i -> listToFilter.get(i).get(0)).collect(Collectors.toList());
    }
}
