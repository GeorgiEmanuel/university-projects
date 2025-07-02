package DataModel;

import DataAccess.SerializationOperation;

import java.io.*;

public sealed abstract class Task implements Serializable permits SimpleTask, ComplexTask  {

    @Serial
    private static final long serialVersionUID = 2L;
    private int idTask;
    private String statusTask;


    public abstract int estimateDuration();

    public Task(int idTask, String statusTask) {
        this.idTask = idTask;
        this.statusTask = statusTask;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public String getStatusTask() {
        return statusTask;
    }

    public void setStatusTask(String statusTask) {
        this.statusTask = statusTask;
    }
    @Override
    public String toString(){
        return this.getClass().getSimpleName() + ", Task id: " + this.getIdTask() + ", Status: " + this.getStatusTask();
    }
    public void serialize(Task task, DataOutputStream out) throws IOException {
        try{
            SerializationOperation.serialize("Task", this);
        } catch (IOException e) {
            throw new IOException("Task serialization failed");
        }
    }
    public void deserialize() throws Exception {

        try{
           Task data = (Task) SerializationOperation.deserialize("Task");
           this.idTask = data.getIdTask();
           this.statusTask = data.getStatusTask();

        }catch (IOException | ClassNotFoundException e) {
            throw new Exception("Task deserialization failed");
        }

    }
}
