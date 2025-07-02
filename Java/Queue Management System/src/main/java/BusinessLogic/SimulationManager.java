package BusinessLogic;

import GUI.SimulationAppController;
import GUI.SimulationAppModel;
import GUI.SimulationFrame;
import Model.Server;
import Model.Task;

import java.io.FileWriter;
import java.util.*;

public class SimulationManager implements Runnable {

    private int timeLimit;
    private int minProcessingTime;
    private int maxProcessingTime;
    private int minArrivalTime;
    private int maxArrivalTime;
    private int numberOfServers;
    private int numberOfClients;
    private int mostCrowdedTime;
    private int maxNoTasks;
    private double averageWaitingTime;
    private double averageServiceTime;

    private SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;

    //entity responsible with queue management and client distribution
    private Scheduler scheduler;

    //frame for displaying simulation
    private SimulationFrame frame;
    private SimulationAppController controller;
    private final List<Task> generatedTasks;

    private boolean running = true;

    public SimulationManager() {
        SimulationAppModel model = new SimulationAppModel(this);
        frame = new SimulationFrame(model);
        SimulationAppController appController = new SimulationAppController(model, frame);
        generatedTasks = new ArrayList<>();
        mostCrowdedTime = 0;
        maxNoTasks = 0;
        averageWaitingTime = 0.0;
        averageServiceTime = 0.0;
    }

    public void initSimulation() {
        scheduler = new Scheduler(numberOfServers, numberOfClients);
        scheduler.changeStrategy(selectionPolicy);
        generatedTasks.clear();
        averageWaitingTime = 0.0;
        averageServiceTime = 0.0;
        mostCrowdedTime = 0;
        maxNoTasks = 0;
        generateNRandomTasks();
        frame.getSimulationTextArea().setText("");
        frame.getStatisticsTextArea().setText("");
        frame.getAverageWaitTimeTextField().setText("");
        frame.getAverageServiceTimeTextField().setText("");
        Thread[] threads = new Thread[numberOfServers];

        for (int i = 0; i < numberOfServers; ++i) {
            threads[i] = new Thread(scheduler.getServers().get(i));
            threads[i].start();
            scheduler.getServers().get(i).setRunning(running);

        }
    }
    public void generateNRandomTasks() {
        Random rand = new Random();
        int minArrivalTime = this.minArrivalTime, maxArrivalTime = this.maxArrivalTime, minServiceTime = this.minProcessingTime, maxServiceTime = this.maxProcessingTime;
        if( numberOfClients / numberOfServers > 8) {
            minArrivalTime = this.minArrivalTime / 5 + 1;
            maxArrivalTime = this.maxArrivalTime / 5 + 1;
            minServiceTime = this.minProcessingTime / 2 + 1;
            maxServiceTime = this.maxProcessingTime / 2 + 1;
        }
        for (int i = 0; i < numberOfClients; ++i) {
            int processingTime = rand.nextInt(maxServiceTime - minServiceTime + 1) + minServiceTime;
            int arrivalTime = rand.nextInt(maxArrivalTime - minArrivalTime + 1) + minArrivalTime;
            Task task = new Task(i + 1, arrivalTime, processingTime);
            generatedTasks.add(task);

        }
        Collections.sort(generatedTasks);
    }
    public synchronized void run() {
        int currentTime = 0;
        while (currentTime < timeLimit) {
            frame.getSimulationTextArea().append("Time " + currentTime + "\n");
            frame.getSimulationTextArea().append("Waiting clients : " + generatedTasks + "\n");
            frame.getSimulationTextArea().append(scheduler.toString() + "\n");
            frame.getStatisticsTextArea().append("Time " + currentTime + "\n");
            frame.getStatisticsTextArea().append("Waiting clients : " + generatedTasks + "\n");
            frame.getStatisticsTextArea().append(scheduler.toString() + "\n");
            synchronized (generatedTasks) {
                Iterator<Task> iter = generatedTasks.iterator();
                while (iter.hasNext()) {
                    Task task = iter.next();
                    if (task.getArrivalTime() == currentTime + 1) {
                        scheduler.dispatchTask(task);
                        iter.remove();
                    }
                }
            }

            for (Server server : scheduler.getServers()) {
                Task firstTask = server.getTasks().peek();
                if (firstTask != null && server.isRunning()) {
                    int firstTaskArrivalTime = firstTask.getArrivalTime();
                    int firstTaskServiceTime = firstTask.getServiceTime();
                    assert firstTaskServiceTime >= 0;
                    assert firstTaskArrivalTime >= 0;
                    if (firstTaskArrivalTime <= currentTime) {
                        firstTask.setServiceTime(firstTaskServiceTime - 1);
                    }
                }
            }
                averageWaitingTime(currentTime);
                averageServiceTime(currentTime);
                peakHour(currentTime + 1);
                writeTextToTestFile("Test", currentTime, timeLimit); //Real time queue evolution written in txt file
            if (generatedTasks.isEmpty()) {
                for (Server server : scheduler.getServers()) {
                    if (server.getTasks().isEmpty()) {
                        server.setRunning(false);
                    }

                }
            }
            try {
                Thread.sleep(1000L);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Simulation interrupted");
            }

            currentTime++;
        }
        if (frame.getAverageWaitTimeTextField().getText().isEmpty()) {
            frame.getAverageWaitTimeTextField().setText("0.0");
        }
        if(numberOfClients == 4 && numberOfServers == 2 && timeLimit == 60 && minArrivalTime == 2 && maxArrivalTime == 30 && minProcessingTime == 2 && maxProcessingTime == 4){
            writeTextToTestFile("Test_1", currentTime, timeLimit);
            System.out.println("Simulation 1 finished!");
        }
        else if(numberOfClients == 50 && numberOfServers == 5 && timeLimit == 60 && minArrivalTime == 2 && maxArrivalTime == 40 && minProcessingTime == 1 && maxProcessingTime == 7){
            writeTextToTestFile("Test_2", currentTime, timeLimit);
            System.out.println("Simulation 2 finished!");
        }
        else if(numberOfClients == 1000 && numberOfServers == 20 && timeLimit == 200 && minArrivalTime == 10 && maxArrivalTime == 100 && minProcessingTime == 3 && maxProcessingTime == 9){
            writeTextToTestFile("Test_3", currentTime, timeLimit);
            System.out.println("Simulation 3 finished!");
        }

    }
    public synchronized void averageWaitingTime(int currentTime) {
        int waitingTime = 0;
        int noTasks = 0;
        for (Server server : scheduler.getServers()) {
            if (!server.getTasks().toString().equals("Closed")) {
                noTasks += server.getTasks().size() - 1;
                Task firstTask = server.getTasks().peek();
                for (Task task : server.getTasks()) {
                    if(task == firstTask)continue;
                    waitingTime += task.getServiceTime();
                }
            }
        }
        if (waitingTime > 0 && noTasks > 0) {
            averageWaitingTime += (double) waitingTime / noTasks;
        }
        if(noTasks > 0) frame.getAverageWaitTimeTextField().setText(Double.toString(averageWaitingTime / currentTime));
    }
    public synchronized void averageServiceTime(int currentTime) {
        int serviceTime = 0;
        int noTasks = 0;
        for (Server server : scheduler.getServers()) {
            if (!server.toString().equals("Closed")) {
                noTasks += server.getTasks().size();
                for (Task task : server.getTasks()) {
                    serviceTime += task.getServiceTime();
                }
            }
        }
        if (serviceTime > 0 && noTasks > 0) {
            averageServiceTime += (double) serviceTime/noTasks ;
        }
        if(noTasks != 0) frame.getAverageServiceTimeTextField().setText(Double.toString(averageServiceTime / currentTime));

    }
    public synchronized void  peakHour(int currentTime) {
        int noTasks = 0;
        for (Server server : scheduler.getServers()) {
                noTasks += server.getTasks().size();

            }
        if (noTasks > maxNoTasks) {
            maxNoTasks = noTasks;
            mostCrowdedTime = currentTime;
        }
        frame.getPeakHourTextField().setText(Integer.toString(mostCrowdedTime));
    }
    public void writeTextToTestFile(String fileName, int currentTime, int timeLimit) {
        try{
            FileWriter file = new FileWriter(fileName);
            file.write(frame.getSimulationTextArea().getText());
            System.out.println(fileName + " successfully written to file");
            if(currentTime >= timeLimit - 1){
                file.write("Average waiting time : " + frame.getAverageWaitTimeTextField().getText() + "\n");
                file.write("Average service time : " + frame.getAverageServiceTimeTextField().getText() + "\n");
                file.write("Peak hour : " +frame.getPeakHourTextField().getText());
            }
            file.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getMaxProcessingTime() {
        return maxProcessingTime;
    }

    public void setMaxProcessingTime(int maxProcessingTime) {
        this.maxProcessingTime = maxProcessingTime;
    }

    public int getMinProcessingTime() {
        return minProcessingTime;
    }

    public void setMinProcessingTime(int minProcessingTime) {
        this.minProcessingTime = minProcessingTime;
    }

    public int getMinArrivalTime() {
        return minArrivalTime;
    }

    public void setMinArrivalTime(int minArrivalTime) {
        this.minArrivalTime = minArrivalTime;
    }

    public int getMaxArrivalTime() {
        return maxArrivalTime;
    }

    public void setMaxArrivalTime(int maxArrivalTime) {
        this.maxArrivalTime = maxArrivalTime;
    }

    public int getNumberOfServers() {
        return numberOfServers;
    }

    public void setNumberOfServers(int numberOfServers) {
        this.numberOfServers = numberOfServers;
    }

    public int getNumberOfClients() {
        return numberOfClients;
    }

    public void setNumberOfClients(int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    public SelectionPolicy getSelectionPolicy() {
        return selectionPolicy;
    }

    public void setSelectionPolicy(SelectionPolicy selectionPolicy) {
        this.selectionPolicy = selectionPolicy;
    }

    public SimulationFrame getFrame() {
        return frame;
    }

    public void setFrame(SimulationFrame frame) {
        this.frame = frame;
    }

    @Override
    public String toString() {
        return "Number of clients: " + numberOfClients + " Number of servers: " + numberOfServers + " Selection Policy: " + selectionPolicy
                + "\nMin arrival: " + minArrivalTime + " Max arrival: " + maxArrivalTime + "\nMin service "
                + minProcessingTime + " Max service " + maxProcessingTime + "\n";
    }

    public static void main(String[] args) {
        SimulationManager sim = new SimulationManager();
        System.out.println(sim);
    }
}