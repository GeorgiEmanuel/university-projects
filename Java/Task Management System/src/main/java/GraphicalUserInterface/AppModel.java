package GraphicalUserInterface;

import BusinessLogic.TaskManagement;
import BusinessLogic.Utility;
import DataModel.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AppModel {
    private TaskManagement taskManagement;
    private Utility utility;
    private List<Task> tasks = new LinkedList<>();

    public AppModel() {

        this.taskManagement = new TaskManagement();
        this.utility = new Utility(taskManagement);
    }

    private Task getTask(int idTask) {
        for (Task task : tasks) {
            if (task.getIdTask() == idTask) {
                return task;
            }
        }
        return null;
    }

    public void addEmployee(String idEmployee, String name) throws Exception {
        try{
            Employee employee = new Employee(Integer.parseInt(idEmployee), name);
            taskManagement.addEmployee(employee);
        } catch(NumberFormatException e){
            throw new Exception("Invalid employee ID format");

        }

    }

    public String viewEmployee(String idEmployee) throws Exception {
        try {
            Employee employee = taskManagement.getEmployee(Integer.parseInt(idEmployee));
            if(employee != null) {
                return employee.toString();
            } else {
                return "Employee not found";
            }
        } catch (NumberFormatException e) {
            throw new Exception("Invalid employee ID format");
        }
    }

    public void addSimpleTask(String idTask, String status, String startHour, String endHour) throws Exception {
        int id;
        int startHourInt;
        int endHourInt;
        try {
            id = Integer.parseInt(idTask);

        } catch (NumberFormatException e) {
            throw new Exception("Invalid task ID format");
        }

        if(!status.equals("Completed")) {
            if (!status.equals("Uncompleted")) {
                throw new Exception("Invalid task status");
            }
        }
        try {
            startHourInt = Integer.parseInt(startHour);
        } catch (NumberFormatException e) {
            throw new Exception("Invalid Start hour format");
        }
        try {
            endHourInt = Integer.parseInt(endHour);
        } catch (NumberFormatException e) {
            throw new Exception("Invalid End hour format");
        }
        Task task = new SimpleTask(id, status, startHourInt, endHourInt);
        tasks.add(task);
    }
    public void addComplexTask(String idTask, String status) throws Exception {
        int id;
        try{
            id = Integer.parseInt(idTask);
        }
        catch(NumberFormatException e){
            throw new Exception("Invalid task ID format");
        }
        if(!status.equals("Completed")) {
            if (!status.equals("Uncompleted")) {
                throw new Exception("Invalid task status");
            }
        }
        Task task = new ComplexTask(Integer.parseInt(idTask), status);
        tasks.add(task);
    }
    public void addTaskToComplexTask(String destTaskId, String sourceTaskId) throws Exception {
        int id1;
        int id2;
        try {
             id1 = Integer.parseInt(destTaskId);
        }catch (NumberFormatException e){
            throw new Exception("Invalid task ID 1 format");
        }
        try {
             id2 = Integer.parseInt(sourceTaskId);
        }catch (NumberFormatException e){
            throw new Exception("Invalid task ID 2 format");
        }
        Task task1 = getTask(id1);
        Task task2 = getTask(id2);
        Task task3 = getAssignedTask(id1);
        Task task4 = getAssignedTask(id2);

        Task actualTask1 = task1 != null ? task1 : task3;
        Task actualTask2 = task2 != null ? task2 : task4;

        if(actualTask1 == null && actualTask2 == null) {
            throw new Exception("Task IDs not found");
        }

        if(actualTask1 == null) {
            throw new Exception("Task ID not found");
        }
        if(actualTask2 == null) {
            throw new Exception("Task ID to be added not found");
        }
        if(!actualTask1.getClass().getSimpleName().equals("ComplexTask")) {
            throw new Exception("Task 1 is not Complex");
        }
        ((ComplexTask) actualTask1).addTask(actualTask2);
//        Map<Employee, List<Task>> employeeTasks = taskManagement.getEmployeeTasks();
//        for (Employee employee : employeeTasks.keySet()) {
//            List<Task> taskList = employeeTasks.get(employee);
//            for (Task taskIterator : taskList) {
//                if (taskIterator.getIdTask() == id1) {
//                    ((ComplexTask) taskIterator).addTask(actualTask2);
//                    //employee.
//                }
//            }
//        }
    }
    private Task getAssignedTask(int idTask) {
        Map<Employee, List<Task>> employeeTasks = taskManagement.getEmployeeTasks();
        for (Employee employee : employeeTasks.keySet()) {
            List<Task> taskList = employeeTasks.get(employee);
            for (Task task1 : taskList) {
                if (task1.getIdTask() == idTask) {
                    return task1;
                }

            }
        }
        return null;
    }
    public String viewTask(String idTask) throws Exception{
        Task task;
        try {
            int idTaskToView = Integer.parseInt(idTask);
            task = getAssignedTask(idTaskToView);
            if(task != null) {
                return task.toString();
            }
            task = getTask(idTaskToView);
            if(task != null) {
                 return task.toString();
             }
            return "No task found";
        }
        catch (NumberFormatException e){
            throw new Exception("Invalid task ID format");
        }
    }

    public void assignTask(String idEmployee, String idTask)throws Exception{
        int idTaskInt;
        int idEmployeeInt;
        try {
            idEmployeeInt = Integer.parseInt(idEmployee);
        }catch (NumberFormatException e){
            throw new Exception("Invalid employee ID format");
        }
        try {
            idTaskInt = Integer.parseInt(idTask);

        }catch (NumberFormatException e) {
            throw new Exception("Invalid task ID format");
        }
        Task task = getTask(idTaskInt);
        Task task2 = getAssignedTask(idTaskInt);

        Employee employee = taskManagement.getEmployee(idEmployeeInt);
        if(employee == null) {
            throw new Exception("Employee ID not found");
        }
        if(task == null && task2 == null) {
            throw new Exception("Task ID not found");
        };

        if(task != null) {
            taskManagement.assignTaskToEmployee(idEmployeeInt, task);
        }
        if(task2 != null) {
            taskManagement.assignTaskToEmployee(idEmployeeInt, task2);
        }

    }

    public int workDuration(String idEmployee)throws Exception {
        int idEmployeeInt;
        try{
            idEmployeeInt = Integer.parseInt(idEmployee);
        } catch (NumberFormatException e) {
            throw new Exception("Invalid employee ID format");
        }
        if(taskManagement.getEmployee(idEmployeeInt) == null) {
            throw new Exception("Employee ID not found");
        }
        return taskManagement.calculateEmployeeWorkDuration(idEmployeeInt);
    }

    public void modifyTask(String idEmployee, String idTask) throws Exception {
        int idTaskInt;
        int idEmployeeInt;
        try {
            idEmployeeInt = Integer.parseInt(idEmployee);
        }catch (NumberFormatException e){
            throw new Exception("Invalid employee ID format");
        }
        try {
            idTaskInt = Integer.parseInt(idTask);

        }catch (NumberFormatException e){
            throw new Exception("Invalid task ID format");
        }
        if(taskManagement.getEmployee(idEmployeeInt) == null) {
            throw new Exception("Employee ID not found");
        }

        Task task = getAssignedTask(idTaskInt);
        Task task2 = getTask(idTaskInt);
        Task actualTask = (task != null) ? task : task2;

        if (actualTask == null) {
            throw new Exception("Task ID not found");
        }

        if (!(actualTask instanceof ComplexTask)) {
            throw new Exception("Task is not Complex");
        }
        taskManagement.modifyTaskStatus(idEmployeeInt, idTaskInt);
    }

    public StringBuilder printEmployeesAndTasks() {
        StringBuilder employeesAndTasks = new StringBuilder();
        if(taskManagement.getEmployeeTasks().isEmpty()) {
            employeesAndTasks.append("No employee tasks found");
        }
        else employeesAndTasks.append(taskManagement.printEmployeesAndTasks());
        return employeesAndTasks;
    }

    public StringBuilder sortEmployees() {
        if(taskManagement.getEmployeeTasks().isEmpty()) {
            return new StringBuilder("No employee tasks found");
        }
        StringBuilder sortEmployeesResult = new StringBuilder();
        List<Employee> sortedEmployees = utility.sortEmployees();

        for(Employee employee : sortedEmployees) {
           sortEmployeesResult.append(employee.getName()).append(" ").append(taskManagement.calculateEmployeeWorkDuration(employee.getIdEmployee())).append('\n');
        }
        return sortEmployeesResult;

    }

    public String countTasks(){
        if(taskManagement.getEmployeeTasks().isEmpty()) {
            return "No employee tasks found";
        }
        Map<String, Map<String, Integer>> result = utility.countTasks();
        return result.toString();
    }


    public void serializeData() throws IOException {
        try {
            taskManagement.serialize();
        }catch (IOException e){
            throw new IOException("Data could not be saved");
        }
    }
    public void deserializeData() throws Exception {
        try{
            taskManagement.deserialize();
        }catch(IOException | ClassNotFoundException e){
            throw new Exception("Data could not be loaded");
        }

    }
}