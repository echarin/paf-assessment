package ibf2022.paf.assessment.server.repositories;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ibf2022.paf.assessment.server.models.Task;
import ibf2022.paf.assessment.server.models.User;

@Repository
public class TaskRepository {

    @Autowired
    private JdbcTemplate jt;

    public String insertTask(User user, Task task) {
        String sql = """
            insert into task (task_id, description, priority, due_date, user_id) values (?, ?, ?, ?, ?),
                """;
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        jt.update(sql, uuid, task.getDescription(), task.getPriority(), task.getDueDate(), user.getUserId()); // throws DataAccessException
        return uuid;
    }
}
