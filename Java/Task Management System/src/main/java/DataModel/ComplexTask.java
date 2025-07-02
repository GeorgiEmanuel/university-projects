package DataModel;

import DataAccess.SerializationOperation;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public non-sealed class ComplexTask extends Task implements Serializable {

    @Serial
    private static final long serialVersionUID = 4L;
    private List<Task> tasks;


    public ComplexTask(int idTask, String statusTask) {
        super(idTask, statusTask);
        tasks = new LinkedList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);// if not exists
    }

    public void deleteTask(Task task) {
        tasks.remove(task);// if exists
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public int estimateDuration() {
        return tasks.stream().mapToInt(Task::estimateDuration).sum();
    }
    @Override
    public String toString() {
        return super.toString() + ", tasks: " + tasks;
    }

    public void serialize() throws IOException {
        try {
            SerializationOperation.serialize("ComplexTask", this);
        } catch (IOException e) {
            throw new IOException("ComplexTask serialization failed");
        }
    }

    public void deserialize() throws Exception{
        try {
            ComplexTask complexTask = (ComplexTask) SerializationOperation.deserialize("ComplexTask");
            this.tasks = complexTask.getTasks();

        } catch (IOException | ClassNotFoundException e) {
            throw new Exception();
        }
    }


}

