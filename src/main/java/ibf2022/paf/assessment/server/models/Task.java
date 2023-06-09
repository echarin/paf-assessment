package ibf2022.paf.assessment.server.models;

import java.time.LocalDate;

public class Task {
    private String taskId;
    private String description;
    private Integer priority;
    private LocalDate dueDate;

    public Task() {
    }
    
    public String getTaskId() {
        return taskId;
    }
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getPriority() {
        return priority;
    }
    public void setPriority(Integer priority) {
        this.priority = priority;
    }
    public LocalDate getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "Task [taskId=" + taskId + ", description=" + description + ", priority=" + priority + ", dueDate="
                + dueDate + "]";
    }
}
