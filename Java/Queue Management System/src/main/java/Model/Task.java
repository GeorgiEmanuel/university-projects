package Model;

public class Task implements Comparable<Task>{
    private int arrivalTime;
    private int serviceTime;
    private int ID;


    public Task(int ID, int arrivalTime, int serviceTime)  {
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String toString(){
        return "(" + ID + ", " +  arrivalTime + ", " + serviceTime + ")";
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    public int compareTo(Task o) {
        return this.arrivalTime - o.arrivalTime;
    }
}
