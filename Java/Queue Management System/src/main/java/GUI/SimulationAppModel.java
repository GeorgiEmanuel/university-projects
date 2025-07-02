package GUI;

import BusinessLogic.SelectionPolicy;
import BusinessLogic.SimulationManager;

import javax.swing.*;

public class SimulationAppModel {

    private SimulationManager simulationManager;

    public SimulationAppModel(SimulationManager simulationManager) {
        this.simulationManager = simulationManager;

    }

    public void setNumberOfClients(String numberOfClients) throws Exception{
        try{
            int clientsNumber = Integer.parseInt(numberOfClients);
            if(clientsNumber < 0) throw new Exception("Invalid number of clients!");
            simulationManager.setNumberOfClients(clientsNumber);


        }catch(Exception e){
            throw new Exception("Invalid number of clients format!");
        }

    }

    public void setNumberOfServers(String numberOfServers) throws Exception{
        try{
            int serversNumber = Integer.parseInt(numberOfServers);
            if(serversNumber < 0) throw new Exception("Invalid number of servers!");
            simulationManager.setNumberOfServers(serversNumber);
        }
        catch(Exception e){
            throw new Exception("Invalid number of servers format!");
        }
    }
    public void setTimeLimit(String interval) throws Exception{
        try{
            int timeLimit = Integer.parseInt(interval);
            if(timeLimit < 0) throw new Exception("Invalid time limit!");
            simulationManager.setTimeLimit(timeLimit);
        }catch(Exception e){
            throw new Exception("Invalid time limit format!");
        }
    }
    public void setMinArrivalTime(String minArrivalTime) throws Exception{
        try{
            int arrivalMinTime = Integer.parseInt(minArrivalTime);
            if(arrivalMinTime < 0) throw new Exception("Invalid minimum arrival time!");
            simulationManager.setMinArrivalTime(arrivalMinTime);
        }catch(Exception e){
            throw new Exception("Invalid minimum arrival time format!");
        }
    }
    public void setMaxArrivalTime(String maxArrivalTime) throws Exception{
        try{
            int arrivalMaxTime = Integer.parseInt(maxArrivalTime);
            if(arrivalMaxTime < 0) throw new Exception("Invalid maximum arrival time!");
            simulationManager.setMaxArrivalTime(arrivalMaxTime);
        }catch(Exception e){
            throw new Exception("Invalid maximum arrival time format!");
        }
    }
    public void setMinServiceTime(String minServiceTime) throws Exception{
        try{
            int serviceMinTime = Integer.parseInt(minServiceTime);
            if(serviceMinTime < 0) throw new Exception("Invalid minimum service time!");
            simulationManager.setMinProcessingTime(serviceMinTime);

        }catch(Exception e){
            throw new Exception("Invalid minimum service time format!");
        }
    }
    public void setMaxServiceTime(String maxServiceTime) throws Exception{
        try{
            int serviceMaxTime = Integer.parseInt(maxServiceTime);
            if(serviceMaxTime < 0) throw new Exception("Invalid maximum service time!");
            simulationManager.setMaxProcessingTime(serviceMaxTime);
        }catch(Exception e){
            throw new Exception("Invalid maximum service time format!");
        }
    }

    public void setSelectionPolicy(String selectionPolicy) throws Exception{
        try {
            SelectionPolicy selectionPolicyEnum = SelectionPolicy.valueOf(selectionPolicy);
            simulationManager.setSelectionPolicy(selectionPolicyEnum);
        } catch (IllegalArgumentException e) {
            throw new Exception("Invalid selection policy !");
        }
    }

    public SimulationManager getSimulationManager() {
        return simulationManager;
    }

    public void setSimulationManager(SimulationManager simulationManager) {
        this.simulationManager = simulationManager;
    }
    public void validateInput() throws Exception{
        int minArrivalTime = simulationManager.getMinArrivalTime();
        int maxArrivalTime = simulationManager.getMaxArrivalTime();
        int minServiceTime = simulationManager.getMinProcessingTime();
        int maxServiceTime = simulationManager.getMaxProcessingTime();
        if(minArrivalTime > maxArrivalTime) {
          throw new Exception("Simulation Aborted ! Minimum arrival time is greater than maximum arrival time! !");
        }
        if(minServiceTime > maxServiceTime) {
            throw new Exception("Simulation Aborted ! Minimum service time is greater than maximum service time! !");
        }
        if(minArrivalTime == 0 && maxArrivalTime == 0) {
            throw new Exception("Simulation Aborted ! Invalid arrival time !");
        }
        if(minServiceTime == 0 && maxServiceTime == 0) {
            throw new Exception("Simulation Aborted ! Invalid service time !");
        }

    }
    public void initSimulation(){
        simulationManager.initSimulation();
    }

}
