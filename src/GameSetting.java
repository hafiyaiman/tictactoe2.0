import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class GameSetting {

    private JFrame frame;
    private JButton close;
    private JComboBox CmbxBoardSize,CmbxGameMode;
    private int gameModeIndex, boardSizeIndex;
    private boolean mInfoTimer, mInfoBoard, mInfoWin;
    public boolean firstRunComboBoxItem = false;

    public GameSetting(){

        new DatabaseConnection();
        DatabaseConnection dbconn = new DatabaseConnection();
        gameModeIndex = dbconn.getGameModeIndex();
        boardSizeIndex = dbconn.getBoardSizeIndex();
        mInfoTimer = Boolean.parseBoolean(dbconn.getmInfoTimer());
        mInfoBoard = Boolean.parseBoolean(dbconn.getmInfoBoard());
        mInfoWin = Boolean.parseBoolean(dbconn.getmInfoWin());

        //Create comboBox item
        CmbxBoardSize = new JComboBox();
        CmbxBoardSize.setBackground(Color.WHITE);
        CmbxGameMode = new JComboBox();
        CmbxGameMode.setBackground(Color.WHITE);

        //create the combo box on the first run only to avoid items in combo box created many times
        if(!firstRunComboBoxItem){
            for(int i=3; i<7; i++){
                CmbxBoardSize.addItem(i + "x" + i);
            }
            for(int i=1; i<3; i++){
                CmbxGameMode.addItem(i + " Player");
            }
            firstRunComboBoxItem = true;
        }

        CmbxBoardSize.setSelectedIndex(boardSizeIndex);
        CmbxGameMode.setSelectedIndex(gameModeIndex);

        if(!Settings.firstRun){
            Settings.isTimerOn = mInfoTimer;
            Settings.isBoardInfoOn = mInfoBoard;
            Settings.isWinCountOn = mInfoWin;
            Settings.firstRun = true;
        }

        //Icon
        Icon exit = new ImageIcon("sources\\logout1.png");
        Icon timer = new ImageIcon("sources\\stopwatch.png");
        Icon boardIcon = new ImageIcon("sources\\square1.png");
        Icon win = new ImageIcon("sources\\trophy1.png");

        close = new JButton(exit);
        close.setBackground(new Color(238,238,238,255));
        close.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        frame = new JFrame("Tic-Tac-Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        //exit and save settings into database
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                //update setting into database
                try{
                    String updateQuery ="UPDATE tictactoesetting SET game_mode = ?, board_size = ?, minfo_timer = ?, minfo_board = ?, minfo_win = ? WHERE setting_id = 1 ";
                    PreparedStatement ps = DatabaseConnection.conn.prepareStatement(updateQuery);

                    ps.setInt(1,CmbxGameMode.getSelectedIndex());
                    ps.setInt(2,CmbxBoardSize.getSelectedIndex());
                    ps.setString(3, String.valueOf(Settings.isTimerOn));
                    ps.setString(4, String.valueOf(Settings.isBoardInfoOn));
                    ps.setString(5, String.valueOf(Settings.isWinCountOn));

                    int rs = ps.executeUpdate();

                    if(rs == 1){
                        System.out.println("Update Succesfully");
                    }

                }catch (Exception exc) {
                    System.out.println(exc);
                }

                frame.dispose();
                new MainInterface();
            }
        });


        CmbxGameMode.setPreferredSize(new Dimension(100, 22));
        CmbxBoardSize.setPreferredSize(new Dimension(100, 22));

        //radio button
        JRadioButton timerOn=new JRadioButton("On",true);
        timerOn.setBackground(Color.WHITE);
        JRadioButton timerOff=new JRadioButton("Off",false);
        timerOff.setBackground(Color.WHITE);

        JRadioButton boardInfo=new JRadioButton("On",true);
        boardInfo.setBackground(Color.WHITE);
        JRadioButton boardInfoOff=new JRadioButton("Off",false);
        boardInfoOff.setBackground(Color.WHITE);

        JRadioButton countWin=new JRadioButton("On",true);
        countWin.setBackground(Color.WHITE);
        JRadioButton countWinOff=new JRadioButton("Off",false);
        countWinOff.setBackground(Color.WHITE);

        ButtonGroup radioTimer = new ButtonGroup();
        radioTimer.add(timerOn);
        radioTimer.add(timerOff);

        ButtonGroup radioBoardInfo = new ButtonGroup();
        radioBoardInfo.add(boardInfo);
        radioBoardInfo.add(boardInfoOff);

        ButtonGroup radioWinCount = new ButtonGroup();
        radioWinCount.add(countWin);
        radioWinCount.add(countWinOff);

        timerOn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { Settings.isTimerOn = true;}
        });

        timerOff.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Settings.isTimerOn = false;
            }
        });

        boardInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Settings.isBoardInfoOn = true;
            }
        });

        boardInfoOff.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Settings.isBoardInfoOn = false;
            }
        });

        countWin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {Settings.isWinCountOn = true; }
        });

        countWinOff.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {Settings.isWinCountOn = false; }
        });

        if(Settings.isTimerOn){
            radioTimer.setSelected(timerOn.getModel(), true);
        }else{
            radioTimer.setSelected(timerOff.getModel(), true);
        }

        if(Settings.isBoardInfoOn){
            radioBoardInfo.setSelected(boardInfo.getModel(), true);
        }else{
            radioBoardInfo.setSelected(boardInfoOff.getModel(), true);
        }

        if(Settings.isWinCountOn){
            radioWinCount.setSelected(countWin.getModel(), true);
        }else{
            radioWinCount.setSelected(countWinOff.getModel(), true);
        }

        //panel setting component
        JPanel panel = new JPanel();
        JPanel board = new JPanel();
        JPanel mTimer = new JPanel();
        JPanel mTimerRadio = new JPanel();
        JPanel brdInfo = new JPanel();
        JPanel brdInfoRadio = new JPanel();
        JPanel wCount = new JPanel();
        JPanel wCountRadio = new JPanel();

        //panel for close btn and setting title
        JPanel space = new JPanel();
        JPanel generalPanel = new JPanel();
        JPanel mInfo = new JPanel();

        JPanel panelTitle = new JPanel();
        JLabel label = new JLabel("Settings");
        label.setFont(new Font("San-Serif", Font.BOLD, 20));
        JLabel general = new JLabel("General");
        JLabel m_info = new JLabel("Match Info");

        JLabel labelGm = new JLabel("Gamemode");
        labelGm.setFont(new Font("San-Serif", Font.PLAIN, 12));
        JLabel labelBoard = new JLabel("Board");
        labelBoard.setFont(new Font("San-Serif", Font.PLAIN, 12));
        JLabel mTimerLbl = new JLabel("   Match Timer",timer,0);
        mTimerLbl.setFont(new Font("San-Serif", Font.PLAIN, 12));
        JLabel brdInfoLbl = new JLabel("   Board info",boardIcon,0);
        brdInfoLbl.setFont(new Font("San-Serif", Font.PLAIN, 12));
        JLabel wCountLbl = new JLabel("   Win Counter",win,0);
        wCountLbl.setFont(new Font("San-Serif", Font.PLAIN, 12));

        generalPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,15));
        generalPanel.setPreferredSize(new Dimension(350, 50));
        generalPanel.setMaximumSize(new Dimension(350, 33));
        mInfo.setLayout(new FlowLayout(FlowLayout.LEFT,0,15));
        mInfo.setPreferredSize(new Dimension(350, 50));
        mInfo.setMaximumSize(new Dimension(350, 33));

        space.setLayout(new FlowLayout(FlowLayout.TRAILING,0,15));
        space.setPreferredSize(new Dimension(350, 50));
        space.setMaximumSize(new Dimension(350, 38));

        //Panel bawah
        panel.setLayout(new FlowLayout(FlowLayout.CENTER,77,16));
        panel.setPreferredSize(new Dimension(350, 50));
        panel.setMaximumSize(new Dimension(350, 60));

        board.setLayout(new FlowLayout(FlowLayout.CENTER,107,16));
        board.setPreferredSize(new Dimension(350, 50));
        board.setMaximumSize(new Dimension(350, 60));

        mTimer.setLayout(new FlowLayout(FlowLayout.CENTER,96,14));
        mTimer.setPreferredSize(new Dimension(350, 30));
        mTimer.setMaximumSize(new Dimension(350, 54));

        //Panel to hold radio timer
        mTimerRadio.setLayout(new GridLayout(2,1,1,3));
        mTimerRadio.setPreferredSize(new Dimension(50, 33));
        mTimerRadio.setMaximumSize(new Dimension(50, 33));
        mTimerRadio.setBackground(Color.WHITE);

        brdInfo.setLayout(new FlowLayout(FlowLayout.CENTER,107,12));
        brdInfo.setPreferredSize(new Dimension(370, 30));
        brdInfo.setMaximumSize(new Dimension(350, 54));

        //Panel to hold radio board info
        brdInfoRadio.setLayout(new GridLayout(2,1,1,3));
        brdInfoRadio.setPreferredSize(new Dimension(50, 32));
        brdInfoRadio.setMaximumSize(new Dimension(50, 32));
        brdInfoRadio.setBackground(Color.WHITE);

        wCount.setLayout(new FlowLayout(FlowLayout.CENTER,94,12));
        wCount.setPreferredSize(new Dimension(350, 30));
        wCount.setMaximumSize(new Dimension(350, 54));

        //Panel to hold radio win count
        wCountRadio.setLayout(new GridLayout(2,1,1,3));
        wCountRadio.setPreferredSize(new Dimension(50, 32));
        wCountRadio.setMaximumSize(new Dimension(50, 32));
        wCountRadio.setBackground(Color.WHITE);

        //panel for setting tile
        panelTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelTitle.setPreferredSize(new Dimension(350, 60));
        panelTitle.setMaximumSize(new Dimension(350, 35));

        //reset button panel
        JPanel resetBtnPanel= new JPanel();
        resetBtnPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetBtnPanel.setPreferredSize(new Dimension(350, 60));
        resetBtnPanel.setMaximumSize(new Dimension(350, 43));
        JButton resetSetting = new JButton("Reset to default");
        resetSetting.setPreferredSize(new Dimension(140, 35));
        resetSetting.setBackground(Color.WHITE);
        resetSetting.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        resetSetting.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                CmbxBoardSize.setSelectedIndex(0);
                CmbxGameMode.setSelectedIndex(0);
                timerOn.setSelected(true);
                boardInfo.setSelected(true);
                countWin.setSelected(true);

                Settings.isTimerOn = true;
                Settings.isBoardInfoOn = true;
                Settings.isWinCountOn = true;
            }
        });

        mTimerRadio.add(timerOn);
        mTimerRadio.add(timerOff);
        brdInfoRadio.add(boardInfo);
        brdInfoRadio.add(boardInfoOff);
        wCountRadio.add(countWin);
        wCountRadio.add(countWinOff);

        space.add(close);
        generalPanel.add(general);
        mInfo.add(m_info);
        panelTitle.add(label);
        panel.add(labelGm);
        panel.add(CmbxGameMode);
        board.add(labelBoard);
        board.add(CmbxBoardSize);
        mTimer.add(mTimerLbl);
        mTimer.add(mTimerRadio);
        brdInfo.add(brdInfoLbl);
        brdInfo.add(brdInfoRadio);
        wCount.add(wCountLbl);
        wCount.add(wCountRadio);
        resetBtnPanel.add(resetSetting);

        panel.setBackground(Color.WHITE);
        board.setBackground(Color.WHITE);
        mTimer.setBackground(Color.WHITE);
        brdInfo.setBackground(Color.WHITE);
        wCount.setBackground(Color.WHITE);

        frame.getContentPane().add(space);
        frame.getContentPane().add(panelTitle);
        frame.add(generalPanel);
        frame.add(Box.createRigidArea(new Dimension(0, 8)));
        frame.getContentPane().add(panel);
        frame.add(Box.createRigidArea(new Dimension(0, 8)));
        frame.getContentPane().add(board);
        frame.add(mInfo);
        frame.add(Box.createRigidArea(new Dimension(0, 8)));
        frame.getContentPane().add(mTimer);
        frame.add(Box.createRigidArea(new Dimension(0, 8)));
        frame.getContentPane().add(brdInfo);
        frame.add(Box.createRigidArea(new Dimension(0, 8)));
        frame.getContentPane().add(wCount);
        frame.add(Box.createRigidArea(new Dimension(0, 13)));
        frame.getContentPane().add(resetBtnPanel);
        frame.setSize(400, 570);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    public static void main(String[] args) {

        new GameSetting();
    }
}