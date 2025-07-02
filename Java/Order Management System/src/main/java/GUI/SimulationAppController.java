package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationAppController {

    private SimulationAppModel model;
    private SimulationFrame view;

    public SimulationAppController(SimulationAppModel model, SimulationFrame view){
        this.model = model;
        this.view = view;

        view.addNumberOfClientsButtonActionListener(new AddNumberOfClientsListener());
        view.addNumberOfServersButtonActionListener(new AddNumberOfQueuesListener());
        view.addSimulationTimeButtonActionListener(new AddTimeLimitListener());
        view.addMinArrivalTimeButtonActionListener(new AddMinArrivalTimeListener());
        view.addMaxArrivalTimeButtonActionListener(new AddMaxArrivalTimeListener());
        view.addMinServiceTimeButtonActionListener(new AddMinServiceTimeListener());
        view.addMaxServiceTimeButtonActionListener(new AddMaxServiceTimeListener());
        view.addStartSimulationButtonActionListener(new StartSimulationListener());
        view.addStrategySelectionActionListener(new addSelectionPolicyListener());

    }

    public class AddNumberOfClientsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            try {
                model.setNumberOfClients(view.getNumberOfClientsTextField().getText());
                JOptionPane.showMessageDialog(view, "Client added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public class AddNumberOfQueuesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            try{
                model.setNumberOfServers((view.getNumberOfServersTextField().getText()));
                JOptionPane.showMessageDialog(view, "Servers added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }catch(Exception ex){
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public class AddTimeLimitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            try{
                model.setTimeLimit(view.getSimulationTimeTextField().getText());
                JOptionPane.showMessageDialog(view, "Time limit added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            }catch(Exception ex){
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public class AddMinArrivalTimeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            try{
                model.setMinArrivalTime(view.getMinArrivalTimeTextField().getText());
                JOptionPane.showMessageDialog(view, "Minimum arrival time added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public class AddMaxArrivalTimeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            try{
                model.setMaxArrivalTime(view.getMaxArrivalTimeTextField().getText());
                JOptionPane.showMessageDialog(view, "Maximum arrival time added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public class AddMinServiceTimeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            try{
                model.setMinServiceTime(view.getMinServiceTimeTextField().getText());
                JOptionPane.showMessageDialog(view, "Minimum service time added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public class AddMaxServiceTimeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            try{
                model.setMaxServiceTime(view.getMaxServiceTimeTextField().getText());
                JOptionPane.showMessageDialog(view, "Maximum service time added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public class StartSimulationListener implements ActionListener  {
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                model.validateInput();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try{
                model.initSimulation();
                JOptionPane.showMessageDialog(view, "Simulation started!", "Success", JOptionPane.INFORMATION_MESSAGE);
                Thread t = new Thread( model.getSimulationManager());
                t.start();
           } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

            }

        }
    }
    public class addSelectionPolicyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String strategy = (String)view.getStrategySelection().getSelectedItem();
                model.setSelectionPolicy(strategy);
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }




}
