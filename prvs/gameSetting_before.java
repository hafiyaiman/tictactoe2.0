import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class gameSetting_before implements ActionListener {

    private JFrame frame;

    private JPanel panel, mainPanel;
    private JButton close = new JButton("Close");
    public gameSetting_before(){
        frame = new JFrame("Tic-Tac-Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3,1));

        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                frame.dispose();
                new mainInterface();
            }
        });

        panel = new JPanel();
        mainPanel = new JPanel();

        panel.setBackground(Color.yellow);
        panel.setLayout(new FlowLayout(FlowLayout.LEADING,0,15));

        mainPanel.setPreferredSize(new Dimension(400,500));
        mainPanel.setBackground(Color.green);
        mainPanel.setLayout(new GridLayout(5,1));

        panel.add(new JLabel("   General"));

        mainPanel.add(new JButton("1"));
        mainPanel.add(new JButton("1"));
        mainPanel.add(new JButton("1"));
        mainPanel.add(new JButton("1"));

        frame.add(close, BorderLayout.NORTH);
        frame.add(panel);
        frame.add(mainPanel);
        frame.setSize(400, 500);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) {
        //new main_interface();
        new gameSetting_before();
    }
}