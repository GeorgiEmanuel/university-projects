package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationFrame extends JFrame {

    private SimulationAppModel model;

    private JPanel contentPane;
    private JMenu simulationMenu;
    private JMenu statisticsMenu;
    private JMenuBar menuBar;
    private CardLayout cardLayout;


    private JPanel simulationPanel;
    private JPanel statisticsPanel;

    //private JLabel

    private JTextField numberOfClientsTextField;
    private JTextField numberOfServersTextField;
    private JTextField simulationTimeTextField;
    private JTextField minArrivalTimeTextField;
    private JTextField maxArrivalTimeTextField;
    private JTextField minServiceTimeTextField;
    private JTextField maxServiceTimeTextField;
    private JTextField averageWaitTimeTextField;
    private JTextField averageServiceTimeTextField;
    private JTextField peakHourTextField;


    private JButton numberOfClientsButton;
    private JButton numberOfServersButton;
    private JButton simulationTimeButton;
    private JButton minArrivalTimeButton;
    private JButton maxArrivalTimeButton;
    private JButton minServiceTimeButton;
    private JButton maxServiceTimeButton;
    private JButton startSimulationButton;

    private JTextArea simulationTextArea;
    private JTextArea statisticsTextArea;

    private JComboBox<String> strategySelection;


    public SimulationFrame(SimulationAppModel model) {
        this.model = model;
        this.prepareGui();


    }

    private void prepareGui() {
        this.setTitle("Queues Management Application");
        this.setSize(1400, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        this.cardLayout = new CardLayout();
        this.contentPane = new JPanel(cardLayout);
        this.setContentPane(contentPane);

        this.prepareMenu();
        this.preparePanel();
        this.setJMenuBar(menuBar);
        this.setVisible(true);

    }

    private void prepareMenu() {
        this.menuBar = new JMenuBar();
        this.simulationMenu = new JMenu("Simulation");
        this.statisticsMenu = new JMenu("Statistics");

        JMenuItem simulationItem = new JMenuItem("Simulation");
        JMenuItem statisticsItem = new JMenuItem("Statistics");

        simulationMenu.add(simulationItem);
        statisticsMenu.add(statisticsItem);

        this.menuBar.add(simulationMenu);
        this.menuBar.add(statisticsMenu);

        simulationItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPane, "Simulation");
            }

        });
        statisticsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPane, "Statistics");
            }
        });


    }

    private void preparePanel() {
        simulationPanel = new JPanel(new BorderLayout());

        JPanel labelPanel = getLabelPanel();
        labelPanel.setBackground(Color.WHITE);

        JPanel textFieldsPanel = new JPanel();
        textFieldsPanel.setLayout(new GridLayout(7, 1, 5, 5));
        textFieldsPanel.add(numberOfClientsTextField = new JTextField(10));
        textFieldsPanel.add(numberOfServersTextField = new JTextField(10));
        textFieldsPanel.add(simulationTimeTextField = new JTextField(10));
        textFieldsPanel.add(minArrivalTimeTextField = new JTextField(10));
        textFieldsPanel.add(maxArrivalTimeTextField = new JTextField(10));
        textFieldsPanel.add(minServiceTimeTextField = new JTextField(10));
        textFieldsPanel.add(maxServiceTimeTextField = new JTextField(10));



        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 7, 5, 5));
        buttonPanel.add(numberOfClientsButton = new JButton("Set Number of Clients"));
        buttonPanel.add(numberOfServersButton = new JButton("Set Number of Servers"));
        buttonPanel.add(simulationTimeButton = new JButton("Set Simulation Time"));
        buttonPanel.add(minArrivalTimeButton = new JButton("Set Minimum Arrival Time"));
        buttonPanel.add(maxArrivalTimeButton = new JButton("Set Maximum Arrival Time"));
        buttonPanel.add(minServiceTimeButton = new JButton("Set Minimum Service Time"));
        buttonPanel.add(maxServiceTimeButton = new JButton("Set Maximum Service Time"));

        numberOfClientsButton.setBackground(Color.LIGHT_GRAY);
        numberOfServersButton.setBackground(Color.LIGHT_GRAY);
        simulationTimeButton.setBackground(Color.LIGHT_GRAY);
        minArrivalTimeButton.setBackground(Color.LIGHT_GRAY);
        maxArrivalTimeButton.setBackground(Color.LIGHT_GRAY);
        minServiceTimeButton.setBackground(Color.LIGHT_GRAY);
        maxServiceTimeButton.setBackground(Color.LIGHT_GRAY);


        JPanel inputSubPane = new JPanel(new FlowLayout());
        inputSubPane.setBackground(Color.WHITE);
        inputSubPane.add(labelPanel);
        inputSubPane.add(textFieldsPanel);

        JPanel inputPane = new JPanel(new BorderLayout());
        inputPane.add(inputSubPane, BorderLayout.NORTH);
        inputPane.add(buttonPanel, BorderLayout.CENTER);

        Border border1 = BorderFactory.createLineBorder(Color.black, 3);
        TitledBorder strategyBorder = BorderFactory.createTitledBorder(border1, "Select Strategy");
        strategyBorder.setTitleFont(new Font("Arial", Font.BOLD, 14));
        strategyBorder.setTitleColor(Color.DARK_GRAY);

        strategySelection = new JComboBox<>(new String[] { "SHORTEST_QUEUE", "SHORTEST_TIME" });
        strategySelection.setBorder(strategyBorder);
        inputPane.add(strategySelection, BorderLayout.SOUTH);

        simulationTextArea = new JTextArea();
        simulationTextArea.setEditable(false);
        simulationTextArea.setLineWrap(true);

        Border border2 = BorderFactory.createLineBorder(Color.black, 3);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(border2, "Real Time Queue Evolution");
        titledBorder.setTitleFont(new Font("Arial", Font.BOLD, 14));
        titledBorder.setTitleColor(Color.DARK_GRAY);

        JScrollPane simulationScrollPane = new JScrollPane(simulationTextArea);
        simulationScrollPane.setPreferredSize(new Dimension(800, 600));
        simulationScrollPane.setBorder(titledBorder);
        simulationScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        simulationScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        startSimulationButton = new JButton("Start Simulation");

        simulationPanel.add(inputPane, BorderLayout.NORTH);
        simulationPanel.add(simulationScrollPane, BorderLayout.CENTER);
        simulationPanel.add(startSimulationButton = new JButton("Start Simulation"), BorderLayout.SOUTH);




        statisticsPanel = new JPanel(new BorderLayout());
        Border statisticsBorder = BorderFactory.createLineBorder(Color.black, 3);
        TitledBorder statisticsTitle = BorderFactory.createTitledBorder(statisticsBorder, "Simulation Statistics");
        statisticsTitle.setTitleFont(new Font("Arial", Font.BOLD, 14));
        statisticsTitle.setTitleColor(Color.DARK_GRAY);

        statisticsPanel.setBorder(statisticsTitle);

        JPanel statisticsSubPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        statisticsSubPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        statisticsSubPanel.add(new JLabel("Average waiting time:"));
        statisticsSubPanel.add(averageWaitTimeTextField = new JTextField(15));
        statisticsSubPanel.add(new JLabel("Average service time:"));
        statisticsSubPanel.add(averageServiceTimeTextField = new JTextField(15));
        statisticsSubPanel.add(new JLabel("Peak hour:"));
        statisticsSubPanel.add(peakHourTextField = new JTextField(15));

        averageServiceTimeTextField.setEditable(false);
        averageWaitTimeTextField.setEditable(false);
        peakHourTextField.setEditable(false);


        averageWaitTimeTextField.setBackground(Color.WHITE);
        averageServiceTimeTextField.setBackground(Color.WHITE);
        peakHourTextField.setBackground(Color.WHITE);

        statisticsTextArea = new JTextArea();
        statisticsTextArea.setEditable(false);
        statisticsTextArea.setLineWrap(true);

        JScrollPane simulationScrollPane2 = new JScrollPane(statisticsTextArea);
        simulationScrollPane2.setPreferredSize(new Dimension(800, 600));
        simulationScrollPane2.setBorder(titledBorder);
        simulationScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        simulationScrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        simulationScrollPane2.setBackground(Color.WHITE);
        statisticsTextArea.setBackground(Color.WHITE);

        statisticsSubPanel.setBackground(Color.WHITE);
        statisticsPanel.add(statisticsSubPanel, BorderLayout.NORTH);
        statisticsPanel.add(simulationScrollPane2, BorderLayout.CENTER);

        simulationPanel.setBackground(Color.WHITE);
        statisticsPanel.setBackground(Color.WHITE);
        contentPane.setBackground(Color.WHITE);

        contentPane.add(simulationPanel, "Simulation");
        contentPane.add(statisticsPanel, "Statistics");
    }

    private JPanel getLabelPanel() {
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(7, 1, 5, 5));  // Align items vertically

        JLabel numberOfClientsLabel = new JLabel("Number of Clients:      ");
        numberOfClientsLabel.setFont(new Font("Arial", Font.PLAIN, 14));  // Increase font size

        JLabel numberOfServersLabel = new JLabel("Number of Servers:      ");
        numberOfServersLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel simulationTimeLabel = new JLabel("Simulation Time:      ");
        simulationTimeLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel minArrivalTimeLabel = new JLabel("Minimum Arrival Time:      ");
        minArrivalTimeLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel maxArrivalTimeLabel = new JLabel("Maximum Arrival Time:      ");
        maxArrivalTimeLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel minServiceTimeLabel = new JLabel("Minimum Service Time:      ");
        minServiceTimeLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel maxServiceTimeLabel = new JLabel("Maximum Service Time:      ");
        maxServiceTimeLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        labelPanel.add(numberOfClientsLabel);
        labelPanel.add(numberOfServersLabel);
        labelPanel.add(simulationTimeLabel);
        labelPanel.add(minArrivalTimeLabel);
        labelPanel.add(maxArrivalTimeLabel);
        labelPanel.add(minServiceTimeLabel);
        labelPanel.add(maxServiceTimeLabel);
        return labelPanel;
    }

    public SimulationAppModel getModel() {
        return model;
    }

    public void setModel(SimulationAppModel model) {
        this.model = model;
    }

    public JTextField getNumberOfClientsTextField() {
        return numberOfClientsTextField;
    }

    public void setNumberOfClientsTextField(JTextField numberOfClientsTextField) {
        this.numberOfClientsTextField = numberOfClientsTextField;
    }

    public JTextField getNumberOfServersTextField() {
        return numberOfServersTextField;
    }

    public void setNumberOfServersTextField(JTextField numberOfServersTextField) {
        this.numberOfServersTextField = numberOfServersTextField;
    }

    public JTextField getSimulationTimeTextField() {
        return simulationTimeTextField;
    }

    public void setSimulationTimeTextField(JTextField simulationTimeTextField) {
        this.simulationTimeTextField = simulationTimeTextField;
    }

    public JTextField getMinArrivalTimeTextField() {
        return minArrivalTimeTextField;
    }

    public void setMinArrivalTimeTextField(JTextField minArrivalTimeTextField) {
        this.minArrivalTimeTextField = minArrivalTimeTextField;
    }

    public JTextField getMaxArrivalTimeTextField() {
        return maxArrivalTimeTextField;
    }

    public void setMaxArrivalTimeTextField(JTextField maxArrivalTimeTextField) {
        this.maxArrivalTimeTextField = maxArrivalTimeTextField;
    }

    public JTextField getMinServiceTimeTextField() {
        return minServiceTimeTextField;
    }

    public void setMinServiceTimeTextField(JTextField minServiceTimeTextField) {
        this.minServiceTimeTextField = minServiceTimeTextField;
    }

    public JTextField getMaxServiceTimeTextField() {
        return maxServiceTimeTextField;
    }

    public void setMaxServiceTimeTextField(JTextField maxServiceTimeTextField) {
        this.maxServiceTimeTextField = maxServiceTimeTextField;
    }

    public JButton getNumberOfClientsButton() {
        return numberOfClientsButton;
    }

    public void setNumberOfClientsButton(JButton numberOfClientsButton) {
        this.numberOfClientsButton = numberOfClientsButton;
    }

    public JButton getNumberOfServersButton() {
        return numberOfServersButton;
    }

    public void setNumberOfServersButton(JButton numberOfServersButton) {
        this.numberOfServersButton = numberOfServersButton;
    }

    public JButton getSimulationTimeButton() {
        return simulationTimeButton;
    }

    public void setSimulationTimeButton(JButton simulationTimeButton) {
        this.simulationTimeButton = simulationTimeButton;
    }

    public JButton getMinArrivalTimeButton() {
        return minArrivalTimeButton;
    }

    public void setMinArrivalTimeButton(JButton minArrivalTimeButton) {
        this.minArrivalTimeButton = minArrivalTimeButton;
    }

    public JButton getMaxArrivalTimeButton() {
        return maxArrivalTimeButton;
    }

    public void setMaxArrivalTimeButton(JButton maxArrivalTimeButton) {
        this.maxArrivalTimeButton = maxArrivalTimeButton;
    }

    public JButton getMinServiceTimeButton() {
        return minServiceTimeButton;
    }

    public void setMinServiceTimeButton(JButton minServiceTimeButton) {
        this.minServiceTimeButton = minServiceTimeButton;
    }

    public JButton getMaxServiceTimeButton() {
        return maxServiceTimeButton;
    }

    public void setMaxServiceTimeButton(JButton maxServiceTimeButton) {
        this.maxServiceTimeButton = maxServiceTimeButton;
    }

    public JButton getStartSimulationButton() {
        return startSimulationButton;
    }

    public void setStartSimulationButton(JButton startSimulationButton) {
        this.startSimulationButton = startSimulationButton;
    }

    public JTextArea getSimulationTextArea() {
        return simulationTextArea;
    }

    public void setSimulationTextArea(JTextArea simulationTextArea) {
        this.simulationTextArea = simulationTextArea;
    }

    public JComboBox<String> getStrategySelection() {
        return strategySelection;
    }

    public void setStrategySelection(JComboBox<String> strategySelection) {
        this.strategySelection = strategySelection;
    }

    public JTextField getAverageWaitTimeTextField() {
        return averageWaitTimeTextField;
    }

    public void setAverageWaitTimeTextField(JTextField averageWaitTimeTextField) {
        this.averageWaitTimeTextField = averageWaitTimeTextField;
    }

    public JTextField getAverageServiceTimeTextField() {
        return averageServiceTimeTextField;
    }

    public void setAverageServiceTimeTextField(JTextField averageServiceTimeTextField) {
        this.averageServiceTimeTextField = averageServiceTimeTextField;
    }

    public JTextField getPeakHourTextField() {
        return peakHourTextField;
    }

    public void setPeakHourTextField(JTextField peakHourTextField) {
        this.peakHourTextField = peakHourTextField;
    }

    public void addNumberOfClientsButtonActionListener(ActionListener actionListener){
        this.numberOfClientsButton.addActionListener(actionListener);
    }
    public void addNumberOfServersButtonActionListener(ActionListener actionListener){
        this.numberOfServersButton.addActionListener(actionListener);
    }
    public void addSimulationTimeButtonActionListener(ActionListener actionListener){
        this.simulationTimeButton.addActionListener(actionListener);
    }
    public void addMinArrivalTimeButtonActionListener(ActionListener actionListener){
        this.minArrivalTimeButton.addActionListener(actionListener);
    }
    public void addMaxArrivalTimeButtonActionListener(ActionListener actionListener){
        this.maxArrivalTimeButton.addActionListener(actionListener);
    }
    public void addMinServiceTimeButtonActionListener(ActionListener actionListener){
        this.minServiceTimeButton.addActionListener(actionListener);
    }
    public void addMaxServiceTimeButtonActionListener(ActionListener actionListener){
        this.maxServiceTimeButton.addActionListener(actionListener);
    }
    public void addStartSimulationButtonActionListener(ActionListener actionListener){
        this.startSimulationButton.addActionListener(actionListener);
    }
    public void addStrategySelectionActionListener(ActionListener actionListener){
        this.strategySelection.addActionListener(actionListener);
    }

    public JTextArea getStatisticsTextArea() {
        return statisticsTextArea;
    }

    public void setStatisticsTextArea(JTextArea statisticsTextArea) {
        this.statisticsTextArea = statisticsTextArea;
    }
}

