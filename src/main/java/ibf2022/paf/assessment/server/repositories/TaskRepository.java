package ibf2022.paf.assessment.server.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ibf2022.paf.assessment.server.models.Task;
import ibf2022.paf.assessment.server.models.User;

@Repository
public class TaskRepository {

    @Autowired
    private JdbcTemplate jt;

    public Integer insertTask(User user, Task task) {
        String sql = """
            insert into task (description, priority, due_date, user_id) values (?, ?, ?, ?)
                """;
        jt.update(sql, task.getDescription(), task.getPriority(), task.getDueDate(), user.getUserId()); // getUserId is null when upserting!
        return jt.queryForObject("select last_insert_id()", Integer.class);
    }
}
