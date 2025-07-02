package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<Server> servers = new ArrayList<Server>(); // queues
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategy;
    public Scheduler(int maxNoServers, int maxTasksPerServer) {
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;
        for (int i = 0; i < maxNoServers; ++i) {
            Server server = new Server(); //  create the server
            servers.add(server); // add the server with the allocated thread to the list
        }

    }

    public void changeStrategy(SelectionPolicy policy) {
        if (policy == SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new ConcreteStrategyQueue();
        } else if (policy == SelectionPolicy.SHORTEST_TIME) {
            strategy = new ConcreteStrategyTime();
        }
    }

    public void dispatchTask(Task task) {

         strategy.addTask(servers, task);
    }

    public List<Server> getServers() {
        return servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

    public int getMaxNoServers() {
        return maxNoServers;
    }

    public void setMaxNoServers(int maxNoServers) {
        this.maxNoServers = maxNoServers;
    }

    public int getMaxTasksPerServer() {
        return maxTasksPerServer;
    }

    public void setMaxTasksPerServer(int maxTasksPerServer) {
        this.maxTasksPerServer = maxTasksPerServer;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    private String printQueues(){
        StringBuilder queues= new StringBuilder();
        for(Server server : servers){
            queues.append("Queue ").append(servers.indexOf(server) + 1).append(": ").append(server.toString()).append("\n");
        }
        return queues.toString();
    }
    public String toString(){
        if(servers.isEmpty()){
            return "Closed";
        }
        else return printQueues();


    }
}
