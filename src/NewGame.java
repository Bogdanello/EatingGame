import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

import java.util.*;
public class NewGame extends JFrame implements ActionListener{
    private JFrame frame;
    private JPanel buttonPane, fieldsPanel;
    private JLabel botsNoLabel, mazeSizeLabel, levelLabel;
    private JTextField  mazeSizeField;
    private JButton start, cancel;
    private String mazeSizeData, botsNoData, lv;
    private JComboBox levelList, botsNoList;
    private String[] levels = {"Easy", "Medium", "Hard"};
    private String[] botsNo = {"1","2","3","4"};
    public NewGame() {
        frame = new JFrame("Game");
        buttonPane = new JPanel();
        fieldsPanel = new JPanel();
        botsNoLabel = new JLabel("Bots number");
        mazeSizeLabel = new JLabel("Maze size");
        levelLabel = new JLabel("Select level");
        mazeSizeField = new JTextField("");
        start = new JButton("Start");
        start.addActionListener(this);
        cancel = new JButton("Cancel");
        
        levelList = new JComboBox(levels);
        levelList.setSelectedIndex(0);
        levelList.addActionListener(this);
        
        botsNoList = new JComboBox(botsNo);
        botsNoList.setSelectedIndex(0);
        botsNoList.addActionListener(this);
        
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.PAGE_AXIS));
        buttonPane.setLayout(new FlowLayout());

        fieldsPanel.add(botsNoLabel);
        fieldsPanel.add(botsNoList);
        fieldsPanel.add(levelLabel);
        fieldsPanel.add(levelList);
        fieldsPanel.add(mazeSizeLabel);
        fieldsPanel.add(mazeSizeField);
        buttonPane.add(start);
        buttonPane.add(cancel);
        frame.add(fieldsPanel, BorderLayout.PAGE_START);
        frame.add(buttonPane, BorderLayout.PAGE_END);
        
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    public synchronized void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == start) {
           // botsNoData = .getText(); //perform your operation
        	botsNoData = (String) botsNoList.getSelectedItem();
            mazeSizeData = mazeSizeField.getText();
            lv = (String) levelList.getSelectedItem();
//            System.out.println(botsNoData);
//            System.out.println(mazeSizeData);
           // System.out.println(lv);
            frame.dispose();
            
            
            
        }
    }
    public String getMazeSize(){
    	return mazeSizeData;
    }
    public String getBotsNo(){
    	return botsNoData;
    }
    public String getLevel(){
    	return lv;
    }
//    public static void main(String args[]) {
//        NewGame ng = new NewGame();
//        int n = Integer.parseInt(mazeSizeData);
//        //start game btn, nb of bots, maze size, dificulty lvl buttons/dropdown
//        
//        Maze maze = new Maze(20);
//        maze.setPlayerPosition(1, 1);
//        
//        //StdDraw.enableDoubleBuffering();
//        maze.draw();
//        maze.startGame();
//        
//        //maze.solve();
//    }
}