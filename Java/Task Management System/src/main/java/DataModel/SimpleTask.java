package DataModel;

import BusinessLogic.TaskManagement;
import DataAccess.SerializationOperation;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

public non-sealed class SimpleTask extends Task implements Serializable{

    @Serial
    private static final long serialVersionUID = 3L;
    private int startHour;
    private int endHour;

    public SimpleTask(int idTask, String statusTask, int startHour, int endHour) {
        super(idTask, statusTask);
        this.startHour = startHour;
        this.endHour = endHour;
    }

    @Override
    public int estimateDuration() {
        return endHour - startHour;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    @Override
    public String toString(){
        return super.toString() + ", start hour: " + startHour + ", end hour: " + endHour;
    }

    public void serialize() throws IOException {
        try {
            SerializationOperation.serialize("SimpleTask", this);
        } catch (IOException e) {
            throw new IOException("SimpleTask serialization failed");
        }
    }

    public void deserialize() throws Exception{
        try {
            SimpleTask simpleTask = (SimpleTask) SerializationOperation.deserialize("SimpleTask");
            this.startHour = simpleTask.getStartHour();
            this.endHour = simpleTask.getEndHour();

        } catch (IOException | ClassNotFoundException e) {
            throw new Exception();
        }
    }

}
