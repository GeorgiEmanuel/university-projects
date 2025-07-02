package Model;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {

    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private boolean running;

    public Server() {
        this.tasks = new LinkedBlockingQueue<>();
        this.waitingPeriod = new AtomicInteger(0);
    }

    public void addTask(Task newTask) {
        tasks.add(newTask);
        waitingPeriod.addAndGet(newTask.getServiceTime());
    }



    @Override
    public synchronized void run() {
        while (running) {
            try {
                Task task = tasks.peek();

                if (task != null) {
                    int serviceTime = task.getServiceTime();
                    Thread.sleep(serviceTime * 1000L);
                    waitingPeriod.getAndAdd(-serviceTime);
                    tasks.remove();
                } else {
                    waitingPeriod.set(0);
                    Thread.sleep(1000L);
                }
            } catch (InterruptedException | IllegalArgumentException e) {
                Thread.currentThread().interrupt();
                System.out.println(e.getMessage());
            }
        }
    }

    public BlockingQueue<Task> getTasks() {
        return tasks;
    }

    public void setTasks(BlockingQueue<Task> tasks) {
        this.tasks = tasks;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public void setWaitingPeriod(AtomicInteger waitingPeriod) {
        this.waitingPeriod = waitingPeriod;
    }

    public String toString() {
        if (tasks.isEmpty()) {
            return "Closed";
        }
        return tasks.toString() ;//+ "Waiting time: " + waitingPeriod.get();
    }
    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

}
