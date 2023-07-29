import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.swing.plaf.metal.MetalButtonUI;
import java.util.Timer;
import java.util.TimerTask;

public class MultiplayerGame implements ActionListener {
    private JFrame frame;
    private JPanel panel;
    int boardSize, boardSizeIndex, gameModeIndex, brdCount, playerWinCount, botWinCount, p1WinCount, p2WinCount;
    private JButton[] buttons;
    public JButton close, volume, volumeOff, time, board, player1Win, player2Win, playerWin, botWin;
    private boolean xTurn = true;
    JTextField turnLbl;
    static int interval;
    static Timer timer;
    String secs, temp_brdCount, temp_playerWinCount, temp_botWinCount, temp_p1WinCount, temp_p2WinCount;

    public MultiplayerGame() throws UnsupportedAudioFileException, IOException, LineUnavailableException  {

        //Icon section
        Icon exit = new ImageIcon("sources\\logout1.png");
        Icon vol = new ImageIcon("sources\\volumeoff.png");
        Icon reload = new ImageIcon("sources\\reload.png");
        Icon volOff = new ImageIcon("sources\\volume.png");
        Icon matchTimeIcon = new ImageIcon("sources\\stopwatch.png");
        Icon brdCountIcon = new ImageIcon("sources\\square1.png");
        Icon playerIcon = new ImageIcon("sources\\player.png");
        Icon botIcon = new ImageIcon("sources\\robot.png");
        Icon p1Icon = new ImageIcon("sources\\user1.png");
        Icon p2Icon = new ImageIcon("sources\\user2.png");

        //set setting by following database on first run
        new DatabaseConnection();
        DatabaseConnection dbconn = new DatabaseConnection();

        //set the setting by following the database on the first run
        if(!Settings.firstRun){
            Settings.isTimerOn = Boolean.parseBoolean(dbconn.getmInfoTimer());
            Settings.isBoardInfoOn = Boolean.parseBoolean(dbconn.getmInfoBoard());
            Settings.isWinCountOn = Boolean.parseBoolean(dbconn.getmInfoWin());;
            Settings.firstRun = true;
        }

        //Game timer
        secs = "1";

        int delay = 1000;
        int period = 1000;

        timer = new Timer();
        interval = Integer.parseInt(secs);
        System.out.println(secs);
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                //System.out.println(setInterval());
                time.setText(" "+setInterval());
            }

        }, delay, period);

        //close button
        close = new JButton(exit);
        close.setBackground(new Color(238,238,238,255));
        close.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        //volume on button
        volume = new JButton(vol);
        volume.setBackground(new Color(238,238,238,255));
        volume.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        //volume off button
        volumeOff = new JButton(volOff);
        volumeOff.setBackground(new Color(238,238,238,255));
        volumeOff.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        //Insert audio file
        File file = new File("sources\\lobby-time.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);

        clip.loop(5);

        volume.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                clip.start();
                volume.setVisible(false);
                volumeOff.setVisible(true);
            }
        });

        volumeOff.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                clip.stop();
                volumeOff.setVisible(false);
                volume.setVisible(true);
            }
        });

        volume.doClick();

        //Match info button, match info components sit in JPanel named space.
        time = new JButton(" 0",matchTimeIcon);
        time.setBackground(new Color(238,238,238,255));
        time.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        board = new JButton(" 0",brdCountIcon);
        board.setBackground(new Color(238,238,238,255));
        board.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        player1Win = new JButton(" 0",p1Icon);
        player1Win.setBackground(new Color(238,238,238,255));
        player1Win.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        player2Win = new JButton(" 0",p2Icon);
        player2Win.setBackground(new Color(238,238,238,255));
        player2Win.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        playerWin = new JButton(" 0",playerIcon);
        playerWin.setBackground(new Color(238,238,238,255));
        playerWin.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        botWin = new JButton(" 0",botIcon);
        botWin.setBackground(new Color(238,238,238,255));
        botWin.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        //Match info setting
        if(Settings.isTimerOn){
            time.setEnabled(true);
        }else{
            time.setEnabled(false);
        }

        if(Settings.isBoardInfoOn){
            board.setEnabled(true);
        }else{
            board.setEnabled(false);
        }

        if(Settings.isWinCountOn){
            player1Win.setEnabled(true);
            player2Win.setEnabled(true);
            playerWin.setEnabled(true);
            botWin.setEnabled(true);
        }else{
            player1Win.setEnabled(false);
            player2Win.setEnabled(false);
            playerWin.setEnabled(false);
            botWin.setEnabled(false);
        }

        //board size and game mode retrieve from database
        boardSizeIndex = dbconn.getBoardSizeIndex();
        gameModeIndex = dbconn.getGameModeIndex();

        //combo box board size index
        if (boardSizeIndex == -1){
            boardSize = 3;
        }else if (boardSizeIndex == 0){
            boardSize = 3;
        }else if (boardSizeIndex == 1){
            boardSize = 4;
        } else if (boardSizeIndex == 2){
            boardSize = 5;
        }else if (boardSizeIndex == 3){
            boardSize = 6;
        }

        //combo box game mode index
        if (gameModeIndex == 1){
            //2 player
            playerWin.setVisible(false);
            botWin.setVisible(false);
        } else if (gameModeIndex == 0 || gameModeIndex == -1) {
            //1 player
            player1Win.setVisible(false);
            player2Win.setVisible(false);
        }

        System.out.println("multiplayer board: "+ boardSize);

        //Create frame
        frame = new JFrame("Tic-Tac-Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        buttons = new JButton[boardSize*boardSize];

        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                timer.cancel();
                clip.stop();
                frame.dispose();
                new MainInterface();
            }
        });

        JPanel space = new JPanel();
        space.setLayout(new FlowLayout(FlowLayout.CENTER,35,10));
        space.setMaximumSize(new Dimension(500, 40));

        JPanel topBtn = new JPanel();
        topBtn.setLayout(new FlowLayout(FlowLayout.TRAILING,10,10));
        topBtn.setPreferredSize(new Dimension(350, 30));
        topBtn.setMaximumSize(new Dimension(360, 30));

        JPanel belowBtn = new JPanel();
        JButton replay = new JButton(" Play Again", reload);
        replay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                clip.setMicrosecondPosition(0);
                resetGame();
            }
        });

        replay.setBackground(Color.WHITE);
        replay.setBorder(BorderFactory.createLineBorder(Color.white, 8));

        belowBtn.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        belowBtn.setPreferredSize(new Dimension(400, 45));
        belowBtn.setMaximumSize(new Dimension(450, 45));

        JPanel wonLbl = new JPanel();
        wonLbl.setLayout(new FlowLayout(FlowLayout.CENTER,0,5));
        wonLbl.setPreferredSize(new Dimension(400, 50));
        wonLbl.setMaximumSize(new Dimension(450, 50));

        if (gameModeIndex == 0 || gameModeIndex == -1){
            turnLbl = new JTextField("Your Turn");
        }else{
            turnLbl = new JTextField("Player 1 Turn");
        }

        turnLbl.setBackground(new Color(238,238,238,255));
        turnLbl.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        turnLbl.setFont(new Font("Arial", Font.BOLD, 22));
        turnLbl.setMaximumSize(new Dimension(300, 10));

        panel = new JPanel();
        panel.setLayout(new GridLayout(boardSize, boardSize,8,8));
        panel.setMaximumSize(new Dimension(500, 500));

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //create board using button
        for (int i = 0; i < boardSize*boardSize; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("Arial", Font.BOLD, 35));
            buttons[i].addActionListener(this);
            panel.add(buttons[i]);
            buttons[i].setBackground(Color.WHITE);
            buttons[i].setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        }

        space.add(time);
        space.add(board);
        space.add(player1Win);
        space.add(player2Win);
        space.add(playerWin);
        space.add(botWin);
        topBtn.add(volumeOff);
        topBtn.add(volume);
        topBtn.add(close);
        wonLbl.add(turnLbl);
        belowBtn.add(replay);

        frame.getContentPane().add(topBtn);
        frame.getContentPane().add(wonLbl);
        frame.getContentPane().add(space);
        frame.getContentPane().add(panel);
        frame.getContentPane().add(belowBtn);
        frame.add(Box.createRigidArea(new Dimension(0, 5)));
        frame.setSize(400, 570);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static final int setInterval() {
        if(Settings.isTimerOn == true){
            return interval++;
        }
        return 0;
    }

    public void actionPerformed(ActionEvent e) {
        JLabel x = new JLabel("X");
        JLabel o = new JLabel("O");

        JButton button = (JButton) e.getSource();
        time.setVisible(false);
        if (xTurn) {
            button.setText("X");
            button.setUI(new MetalButtonUI() {
                protected Color getDisabledTextColor() {
                    return Color.decode("#FE6862");
                }
            });
            button.setBorder(BorderFactory.createLineBorder(Color.decode("#FE6862"),4,true));
        } else {
            button.setText("O");
            button.setUI(new MetalButtonUI() {
                protected Color getDisabledTextColor() {
                    return Color.decode("#0492C2");
                }
            });
            button.setBorder(BorderFactory.createLineBorder(Color.decode("#0492C2"),4,true));
        }
        button.setEnabled(false);
        xTurn = !xTurn;

        if (xTurn) {
            turnLbl.setText("Player 1 Turn");
        } else {
            turnLbl.setText("Player 2 Turn");
        }

        checkForWinner();

        if (gameModeIndex == 0 || gameModeIndex == -1){
            if (!xTurn) {
                turnLbl.setText("Your Turn");
                // Computer's turn
                int computerMove;
                do {
                    // Generate a random move for the computer
                    computerMove = (int) (Math.random() * boardSize * boardSize);
                } while (!buttons[computerMove].isEnabled());

                // Update the game board with the computer's move
                buttons[computerMove].setText("O");
                buttons[computerMove].setUI(new MetalButtonUI() {
                    protected Color getDisabledTextColor() {
                        return Color.decode("#0492C2");
                    }
                });
                buttons[computerMove].setBorder(BorderFactory.createLineBorder(Color.decode("#0492C2"),4,true));
                buttons[computerMove].setEnabled(false);
                xTurn = !xTurn;

                checkForWinner();
            }
        }
        //Match info win count
        if(Settings.isWinCountOn){
            temp_botWinCount = String.valueOf(botWinCount);
            temp_p1WinCount = String.valueOf(p1WinCount);
            temp_p2WinCount = String.valueOf(p2WinCount);
            temp_playerWinCount = String.valueOf(playerWinCount);

            playerWin.setText(" "+temp_playerWinCount);
            player1Win.setText(" "+temp_p1WinCount);
            player2Win.setText(" "+temp_p2WinCount);
            botWin.setText(" "+temp_botWinCount);
        }

    }

    public void checkForWinner() {

        //add already clicked board count
        if (brdCount<boardSize*boardSize){
            if(Settings.isBoardInfoOn){
                brdCount++;
                temp_brdCount = String.valueOf(brdCount);
                board.setText(" "+ temp_brdCount);
            }
        }

        //set match info visible
        board.setVisible(true);
        time.setVisible(true);

        // Check rows
        for (int i = 0; i < boardSize * boardSize; i += boardSize) {
            boolean sameText = true;
            String buttonText = buttons[i].getText();
            for (int j = i + 1; j < i + boardSize; j++) {
                if (!buttonText.equals(buttons[j].getText())) {
                    sameText = false;
                    break;
                }
            }
            if (sameText && !buttons[i].isEnabled()) {
                // Winner found
                if (gameModeIndex == 1){
                    if (buttons[i].getText() == "O") {
                        time.setVisible(false);
                        board.setVisible(false);
                        playerWin.setVisible(false);
                        player2Win.setVisible(false);
                        player1Win.setVisible(false);
                        botWin.setVisible(false);
                        turnLbl.setText("Player 2 Won!");
                        p2WinCount++;
                        JOptionPane.showMessageDialog(frame," Good game !");
                    }else if (buttons[i].getText() == "X"){
                        time.setVisible(false);
                        board.setVisible(false);
                        playerWin.setVisible(false);
                        player2Win.setVisible(false);
                        player1Win.setVisible(false);
                        botWin.setVisible(false);
                        turnLbl.setText("Player 1 Won!");
                        p1WinCount++;
                        JOptionPane.showMessageDialog(frame," Good game !");
                    }
                }else if (gameModeIndex == -1 || gameModeIndex == 0){
                    if (buttons[i].getText() == "O"){
                        time.setVisible(false);
                        board.setVisible(false);
                        playerWin.setVisible(false);
                        player2Win.setVisible(false);
                        player1Win.setVisible(false);
                        botWin.setVisible(false);
                        turnLbl.setText("You Lost!");
                        botWinCount++;
                        JOptionPane.showMessageDialog(frame," Try Harder !");
                    } else if (buttons[i].getText() == "X"){
                        time.setVisible(false);
                        board.setVisible(false);
                        playerWin.setVisible(false);
                        player2Win.setVisible(false);
                        player1Win.setVisible(false);
                        botWin.setVisible(false);
                        turnLbl.setText("You Won!");
                        playerWinCount++;
                        JOptionPane.showMessageDialog(frame," Good Job !");
                    }
                }
                resetGame();
                return;
            }
        }

        // Check columns
        for (int i = 0; i < boardSize; i++) {
            boolean sameText = true;
            String buttonText = buttons[i].getText();
            for (int j = i + boardSize; j < boardSize * boardSize; j += boardSize) {
                if (!buttonText.equals(buttons[j].getText())) {
                    sameText = false;
                    break;
                }
            }
            if (sameText && !buttons[i].isEnabled()) {
                // Winner found
                if (gameModeIndex == 1){
                    if (buttons[i].getText() == "O") {
                        time.setVisible(false);
                        board.setVisible(false);
                        playerWin.setVisible(false);
                        player2Win.setVisible(false);
                        player1Win.setVisible(false);
                        botWin.setVisible(false);
                        turnLbl.setText("Player 2 Won!");
                        p2WinCount++;
                        JOptionPane.showMessageDialog(frame," Good game !");
                    }else if (buttons[i].getText() == "X"){
                        time.setVisible(false);
                        board.setVisible(false);
                        playerWin.setVisible(false);
                        player2Win.setVisible(false);
                        player1Win.setVisible(false);
                        botWin.setVisible(false);
                        turnLbl.setText("Player 1 Won!");
                        p1WinCount++;
                        JOptionPane.showMessageDialog(frame," Good game !");
                    }
                }else if (gameModeIndex == -1 || gameModeIndex == 0){
                    if (buttons[i].getText() == "O"){
                        time.setVisible(false);
                        board.setVisible(false);
                        playerWin.setVisible(false);
                        player2Win.setVisible(false);
                        player1Win.setVisible(false);
                        botWin.setVisible(false);
                        turnLbl.setText("You Lost!");
                        botWinCount++;
                        JOptionPane.showMessageDialog(frame," Try Harder !");
                    } else if (buttons[i].getText() == "X"){
                        time.setVisible(false);
                        board.setVisible(false);
                        playerWin.setVisible(false);
                        player2Win.setVisible(false);
                        player1Win.setVisible(false);
                        botWin.setVisible(false);
                        turnLbl.setText("You Won!");
                        playerWinCount++;
                        JOptionPane.showMessageDialog(frame," Good Job !");
                    }
                }
                resetGame();
                return;
            }
        }

        // Check diagonal (top-left to bottom-right)
        boolean mainDiagonalWin = true;
        for (int i = boardSize + 1; i < boardSize * boardSize; i += boardSize + 1) {
            if (!buttons[0].getText().equals(buttons[i].getText()) || buttons[0].isEnabled()) {
                mainDiagonalWin = false;
                break;
            }
        }
        if (mainDiagonalWin) {
            // Handle the winning condition

            if (gameModeIndex == 1){
                if (buttons[0].getText() == "O") {
                    time.setVisible(false);
                    board.setVisible(false);
                    playerWin.setVisible(false);
                    player2Win.setVisible(false);
                    player1Win.setVisible(false);
                    botWin.setVisible(false);
                    turnLbl.setText("Player 2 Won!");
                    p2WinCount++;
                    JOptionPane.showMessageDialog(frame," Good game !");
                }else if (buttons[0].getText() == "X"){
                    time.setVisible(false);
                    board.setVisible(false);
                    playerWin.setVisible(false);
                    player2Win.setVisible(false);
                    player1Win.setVisible(false);
                    botWin.setVisible(false);
                    turnLbl.setText("Player 1 Won!");
                    p1WinCount++;
                    JOptionPane.showMessageDialog(frame," Good game !");
                }
            }else if (gameModeIndex == -1 || gameModeIndex == 0){
                if (buttons[0].getText() == "O"){
                    time.setVisible(false);
                    board.setVisible(false);
                    playerWin.setVisible(false);
                    player2Win.setVisible(false);
                    player1Win.setVisible(false);
                    botWin.setVisible(false);
                    turnLbl.setText("You Lost!");
                    botWinCount++;
                    JOptionPane.showMessageDialog(frame," Try Harder !");
                } else if (buttons[0].getText() == "X"){
                    time.setVisible(false);
                    board.setVisible(false);
                    playerWin.setVisible(false);
                    player2Win.setVisible(false);
                    player1Win.setVisible(false);
                    botWin.setVisible(false);
                    turnLbl.setText("You Won!");
                    playerWinCount++;
                    JOptionPane.showMessageDialog(frame," Good Job !");
                }
            }
            resetGame();
            return;
        }

        // Check diagonal (top-right to bottom-left)
        boolean secondaryDiagonalWin = true;
        for (int i = boardSize - 1; i <= boardSize * (boardSize-1); i += (boardSize - 1)) {
            System.out.println("index = "+i);
            if (!buttons[boardSize - 1].getText().equals(buttons[i].getText()) || buttons[boardSize - 1].isEnabled()) {
                secondaryDiagonalWin = false;
                break;
            }
        }
        if (secondaryDiagonalWin ) {
            // Handle the winning condition

            if (gameModeIndex == 1){
                if (buttons[boardSize - 1].getText() == "O") {
                    time.setVisible(false);
                    board.setVisible(false);
                    playerWin.setVisible(false);
                    player2Win.setVisible(false);
                    player1Win.setVisible(false);
                    botWin.setVisible(false);
                    turnLbl.setText("Player 2 Won!");
                    p2WinCount++;
                    JOptionPane.showMessageDialog(frame," Good game !");
                }else if (buttons[boardSize - 1].getText() == "X"){
                    time.setVisible(false);
                    board.setVisible(false);
                    playerWin.setVisible(false);
                    player2Win.setVisible(false);
                    player1Win.setVisible(false);
                    botWin.setVisible(false);
                    turnLbl.setText("Player 1 Won!");
                    p1WinCount++;
                    JOptionPane.showMessageDialog(frame," Good game !");
                }
            }else if (gameModeIndex == -1 || gameModeIndex == 0){
                if (buttons[boardSize - 1].getText() == "O"){
                    time.setVisible(false);
                    board.setVisible(false);
                    playerWin.setVisible(false);
                    player2Win.setVisible(false);
                    player1Win.setVisible(false);
                    botWin.setVisible(false);
                    turnLbl.setText("You Lost!");
                    botWinCount++;
                    JOptionPane.showMessageDialog(frame," Try Harder !");
                } else if (buttons[boardSize - 1].getText() == "X"){
                    time.setVisible(false);
                    board.setVisible(false);
                    playerWin.setVisible(false);
                    player2Win.setVisible(false);
                    player1Win.setVisible(false);
                    botWin.setVisible(false);
                    turnLbl.setText("You Won!");
                    playerWinCount++;
                    JOptionPane.showMessageDialog(frame," Good Job !");
                }
            }
            resetGame();
            return;
        }

        // Check for tie
        boolean tie = true;
        for (int i = 0; i < boardSize*boardSize; i++) {
            if (buttons[i].isEnabled()) {
                tie = false;
                break;
            }
        }
        if (tie) {
            time.setVisible(false);
            board.setVisible(false);
            playerWin.setVisible(false);
            player2Win.setVisible(false);
            player1Win.setVisible(false);
            botWin.setVisible(false);
            turnLbl.setText("Game Tie");
            JOptionPane.showMessageDialog(frame, "Draw!");
            resetGame();
        }
    }

    public void resetGame() {
        brdCount = 0;
        interval = 1;

        //make match info visible
        time.setVisible(true);
        board.setVisible(true);
        if (gameModeIndex == 1){
            player1Win.setVisible(true);
            player2Win.setVisible(true);
        } else if (gameModeIndex == 0 || gameModeIndex == -1) {
            playerWin.setVisible(true);
            botWin.setVisible(true);
        }
        board.setText(" 0");

        for (int i = 0; i < boardSize*boardSize; i++) {
            buttons[i].setText("");
            buttons[i].setEnabled(true);
            buttons[i].setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        }
        xTurn = true;

        if (gameModeIndex == -1 || gameModeIndex == 0){
            turnLbl.setText("Your Turn");
        } else if (gameModeIndex ==1){
            turnLbl.setText("Player 1 Turn");
        }
    }

    public static void main(String[] args)  throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        //new main_interface();
        new MultiplayerGame();
    }
}