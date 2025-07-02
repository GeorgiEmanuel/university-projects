package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.List;

public class ConcreteStrategyQueue implements Strategy {

    public void addTask(List<Server> servers, Task t) {
        //TODO Auto-generated method stub
        int minNumberOfTasks = servers.getFirst().getTasks().size();
        Server bestServer = servers.getFirst();
        for (Server s : servers) {
            int numberOfTasks = s.getTasks().size();
            if (numberOfTasks < minNumberOfTasks) {
                minNumberOfTasks = numberOfTasks;
                bestServer = s;
            }
        }
        bestServer.addTask(t);

    }
}
