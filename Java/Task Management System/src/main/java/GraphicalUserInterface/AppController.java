package GraphicalUserInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class AppController {
    private AppModel model;
    private AppView view;

    public AppController(AppModel model, AppView view) {
        this.model = model;
        this.view = view;

        view.addEmployeeButtonListener(new AddEmployeeButtonListener());
        view.addViewEmployeeButtonListener(new ViewEmployeeButtonListener());
        view.addSimpleTaskButtonListener(new AddSimpleTaskButtonListener());
        view.addComplexTaskButtonListener(new AddComplexTaskButtonListener());
        view.addTaskToComplexButtonListener(new AddTaskToComplexButtonListener());
        view.addViewTaskButtonListener(new ViewTaskButtonListener());
        view.addAssignTaskButtonListener(new AssignTaskButtonListener());
        view.addWorkDurationButtonListener(new WorkDurationButtonListener());
        view.addModifyTaskButtonListener(new ModifyTaskButtonListener());
        view.addViewEmployeeAndTasksButtonListener(new ViewEmployeesAndTasksButtonListener());
        view.addFilterEmployeeButtonListener(new FilterButtonListener());
        view.addStatusReportButtonListener(new StatusReportButtonListener());
        view.addSerializeDataButtonListener(new SerializeButtonListener());
        view.addDeserializeDataButtonListener(new DeserializeButtonListener());

    }

    public class AddEmployeeButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                model.addEmployee(view.getEmployeeId().getText(), view.getEmployeeName().getText());
                JOptionPane.showMessageDialog(view, "Employee added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                view.getEmployeeId().setText("");;
                view.getEmployeeName().setText("");
            }catch(Exception ex){
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public class ViewEmployeeButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String employeeId = view.getViewEmployeeId().getText();
            //System.out.println(employeeId);
            try {
                String employeeDetails = model.viewEmployee(employeeId);
                //System.out.println(employeeDetails);
                view.getViewEmployee().setText(employeeDetails);
                view.getViewEmployeeId().setText("");
                //view.
            }catch(Exception ex){
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public class AddSimpleTaskButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                model.addSimpleTask(view.getTaskId().getText(), view.getTaskStatus().getText()
                        , view.getStartHour().getText(), view.getEndHour().getText());
                JOptionPane.showMessageDialog(view, "Simple task added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                view.getTaskId().setText("");
                view.getTaskStatus().setText("");
                view.getStartHour().setText("");
                view.getEndHour().setText("");
            }catch(Exception ex){
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public class AddComplexTaskButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                model.addComplexTask(view.getTaskId().getText(), view.getTaskStatus().getText());
                JOptionPane.showMessageDialog(view, "Complex task added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                view.getTaskId().setText("");
                view.getTaskStatus().setText("");
            }catch(Exception ex){
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public class AddTaskToComplexButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            try {
                model.addTaskToComplexTask(view.getAddTaskToComplexTaskId1().getText(), view.getAddTaskToComplexTaskId2().getText());
                JOptionPane.showMessageDialog(view, "Task added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                view.getAddTaskToComplexTaskId1().setText("");
                view.getAddTaskToComplexTaskId2().setText("");
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public class ViewTaskButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            String taskId = view.getViewTaskId().getText();
            try {
                view.getViewTaskTextArea().setText(model.viewTask(taskId));
                view.getViewTaskId().setText("");
            }catch(Exception ex){
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public class AssignTaskButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            String taskId = view.getAssignTaskId().getText();
            String idEmployee = view.getAssignEmployeeId().getText();
            try {
                model.assignTask(idEmployee, taskId);
                JOptionPane.showMessageDialog(view, "Task assigned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                view.getAssignTaskId().setText("");
                view.getAssignEmployeeId().setText("");
            }catch(Exception ex){
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public class WorkDurationButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            String employeeId = view.getEmployeeIdWorkDuration().getText();
            try {
                view.getViewWorkDurationTextArea().setText(Integer.toString(model.workDuration(employeeId)));
                view.getEmployeeIdWorkDuration().setText("");
            }catch(Exception ex){
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
    }
    public class ModifyTaskButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e){

            try {
                model.modifyTask(view.getEmployeeIdTaskToModify().getText(), view.getTaskIdToModify().getText());
                JOptionPane.showMessageDialog(view, "Task modified successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                view.getEmployeeIdTaskToModify().setText("");
                view.getTaskIdToModify().setText("");
            }catch(Exception ex){
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public class ViewEmployeesAndTasksButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
           view.getViewEmployeeTasksTextArea().setText(model.printEmployeesAndTasks().toString());
        }
    }
    public class FilterButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e){

            view.getFilterEmployeeTextArea().setText(model.sortEmployees().toString());
        }
    }
    public class StatusReportButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            view.getStatusReportTextArea().setText(model.countTasks());

        }
    }
    public class SerializeButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            try{
                model.serializeData();
                JOptionPane.showMessageDialog(view, "Save completed!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }catch(IOException ex){
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public class DeserializeButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            try{
                model.deserializeData();
                JOptionPane.showMessageDialog(view, "Load completed!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }catch(Exception ex){
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

}




