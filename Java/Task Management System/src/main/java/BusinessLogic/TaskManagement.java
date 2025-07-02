package BusinessLogic;

import DataAccess.SerializationOperation;
import DataModel.ComplexTask;
import DataModel.Employee;
import DataModel.Task;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class TaskManagement implements Serializable {
    @Serial
    private static final long serialVersionUID = 5L;
    private Map<Employee, List<Task>> employeeTasks;


    public TaskManagement() {
        employeeTasks = new LinkedHashMap<>();
    }

    public Employee getEmployee(int idEmployee) {
        for (Employee employee : employeeTasks.keySet()) {
            if (employee.getIdEmployee() == idEmployee) {
                return employee;
            }

        }
        return null;
    }

    public List<Employee> getEmployeesList() {
        return new LinkedList<>(employeeTasks.keySet());
    }

    public void addEmployee(Employee employee) {
        employeeTasks.put(employee, new LinkedList<>());
    }

    public void assignTaskToEmployee(int idEmployee, Task task) {
        Employee employee = getEmployee(idEmployee);
        if (employee != null) {
            List<Task> assignedEmployeeTasks = employeeTasks.get(employee);
            if (!assignedEmployeeTasks.contains(task)) {
                employeeTasks.get(employee).add(task);
            }
        }

    }

    public Map<Employee, List<Task>> getEmployeeTasks() {
        return employeeTasks;
    }

    public void setEmployeeTasks(Map<Employee, List<Task>> employeeTasks) {
        this.employeeTasks = employeeTasks;
    }

    public int calculateEmployeeWorkDuration(int idEmployee) {
        int workDuration = 0;
        Employee employee = getEmployee(idEmployee);
        if (employee != null) {
            List<Task> assignedEmployeeTasks = employeeTasks.get(employee);
            for (Task task : assignedEmployeeTasks) {
                    workDuration += calculateTaskWorkDuration(task);
                }
        }
        return workDuration;
    }

    private int calculateTaskWorkDuration(Task task) {
        if (task.getClass().getSimpleName().equals("SimpleTask") && task.getStatusTask().equals("Completed")) {
            return task.estimateDuration();
        } else if (task.getClass().getSimpleName().equals("ComplexTask")) {
            int workDuration = 0;
            ComplexTask complexTask = (ComplexTask) task;
            for(Task taskIterator : complexTask.getTasks())
                workDuration += calculateTaskWorkDuration(taskIterator);
            return workDuration;
        }
        return 0;
    }

    public void modifyTaskStatus(int idEmployee, int idTask) {
        Employee employee = getEmployee(idEmployee);
        if (employee != null) {
            ComplexTask taskToBeModified = null;
            List<Task> assignedEmployeeTasks = employeeTasks.get(employee);

            for (Task task : assignedEmployeeTasks) {
                if (task.getIdTask() == idTask && task instanceof ComplexTask) {
                    taskToBeModified = (ComplexTask) task;
                    break;
                }
            }
            if (taskToBeModified != null) {
                List<Task> tasks = taskToBeModified.getTasks();
                boolean foundUncompletedTask = false;

                for (Task subTask : tasks) {
                    if (subTask.getStatusTask().equals("Uncompleted")) {
                        foundUncompletedTask = true;
                        break;
                    }
                }
                if(foundUncompletedTask) {
                    taskToBeModified.setStatusTask("Uncompleted");
                }
                else {
                    taskToBeModified.setStatusTask("Completed");
                }
            }
        }
    }



    public StringBuilder printEmployeesAndTasks() {
        StringBuilder result = new StringBuilder();
        for (Employee employee : employeeTasks.keySet()) {
            result.append(employee).append(":\n");
            for (Task task : employeeTasks.get(employee)) {
                result.append("   - ").append(task).append("\n");
            }
            result.append("\n");
        }
        return result;
    }

    public void printEmployeeTasks(int idEmployee) {
        Employee employee = getEmployee(idEmployee);
        if (employee != null) {
            System.out.println(employee + ": ");
            for (Task task : employeeTasks.get(employee)) {
                System.out.println(task);
            }
        }
        System.out.print("\n\n");

    }

    public void serialize() throws IOException {
        try {
            SerializationOperation.serialize("data.txt", this);
        } catch (IOException e) {
            throw new IOException();
        }
    }

    public void deserialize() throws Exception{
        try {
            TaskManagement data = (TaskManagement) SerializationOperation.deserialize("data.txt");
            this.employeeTasks = data.employeeTasks;
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception();
        }
    }


}

