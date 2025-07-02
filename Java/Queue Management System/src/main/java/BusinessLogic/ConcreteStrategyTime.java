package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcreteStrategyTime implements Strategy {

    public  void addTask(List<Server> servers, Task t) {
        int minWaitingPeriod = servers.getFirst().getWaitingPeriod().get();
        Server bestServer = servers.getFirst();
        for (Server server : servers) {
            int waitingPeriod = server.getWaitingPeriod().get();
            if (waitingPeriod < minWaitingPeriod) {
                minWaitingPeriod = waitingPeriod;
                bestServer = server;
            }
        }
       if(bestServer != null){
           bestServer.addTask(t);

       }


    }
}
