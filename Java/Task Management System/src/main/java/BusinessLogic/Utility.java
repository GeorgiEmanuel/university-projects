package BusinessLogic;

import DataModel.Employee;
import DataModel.Task;

import java.util.*;

public class Utility {

    private TaskManagement taskManagement;

    public Utility(TaskManagement taskManagement) {
        this.taskManagement = taskManagement;
    }

    public List<Employee> sortEmployees() {
        List<Employee> allEmployees = taskManagement.getEmployeesList();
        List<Employee> filteredList = new ArrayList<>();
        for (Employee employee : allEmployees) {
            if (taskManagement.calculateEmployeeWorkDuration(employee.getIdEmployee()) > 40) {
                filteredList.add(employee);
            }
        }

        filteredList.sort(new WorkDurationComparator());
        return filteredList;

    }
    public TaskManagement getTaskManagement() {
        return taskManagement;
    }

    public void setTaskManagement(TaskManagement taskManagement) {
        this.taskManagement = taskManagement;
    }

    public Map<String, Map<String, Integer>> countTasks() {

        Map<String, Map<String, Integer>> employeeStatusTasks = new LinkedHashMap<>();
        Map<Employee, List<Task>> employeeTasks = taskManagement.getEmployeeTasks();

        for(Employee employee : employeeTasks.keySet()) {
            Map<String, Integer> statusTasks = new HashMap<>();
            Integer totalCompleted = 0;
            Integer totalUncompleted = 0;

            for(Task task : employeeTasks.get(employee)){
                if(task.getStatusTask().equals("Completed")){totalCompleted++;}
                    else totalUncompleted++;
            }
            statusTasks.put("Completed", totalCompleted);
            statusTasks.put("Uncompleted", totalUncompleted);
            employeeStatusTasks.put(employee.getName(), statusTasks);

        }

        return employeeStatusTasks;
    }

    public class WorkDurationComparator implements Comparator<Employee> {
        @Override
        public int compare(Employee e1, Employee e2) {
            return taskManagement.calculateEmployeeWorkDuration(e1.getIdEmployee()) -
                    taskManagement.calculateEmployeeWorkDuration(e2.getIdEmployee());
        }
    }
}

