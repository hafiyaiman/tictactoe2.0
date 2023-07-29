import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Math;

public class singleplayer_game implements ActionListener {
    private JFrame frame;
    int boardSize = 3;
    private JPanel panel;
    private JButton[] buttons = new JButton[boardSize*boardSize];
    private JButton close = new JButton("Close");
    private boolean xTurn = true;

    public singleplayer_game() {

        frame = new JFrame("Tic-Tac-Toe");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                frame.dispose();
                new mainInterface();
            }
        });

        panel = new JPanel();
        panel.setLayout(new GridLayout(boardSize, boardSize));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        for (int i = 0; i < boardSize*boardSize; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 40));
            buttons[i].addActionListener(this);
            panel.add(buttons[i]);
        }

        frame.add(panel, BorderLayout.CENTER);
        frame.add(close, BorderLayout.NORTH);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (xTurn) {
            button.setText("X");
        } else {
            button.setText("O");
        }
        button.setEnabled(false);
        xTurn = !xTurn;

        checkForWinner();

        if (!xTurn) {
            // Computer's turn
            int computerMove;
            do {
                // Generate a random move for the computer
                computerMove = (int) (Math.random() * boardSize * boardSize);
            } while (!buttons[computerMove].isEnabled());

            // Update the game board with the computer's move
            buttons[computerMove].setText("O");
            buttons[computerMove].setEnabled(false);
            xTurn = !xTurn;

            checkForWinner();
        }
    }


    public void checkForWinner() {
        // Check rows
        for (int i = 0; i < boardSize*boardSize; i += boardSize) {
            if (buttons[i].getText().equals(buttons[i+1].getText()) && buttons[i].getText().equals(buttons[i+(boardSize-1)].getText()) && !buttons[i].isEnabled()) {
                JOptionPane.showMessageDialog(frame, buttons[i].getText() + " wins!");
                resetGame();
                return;
            }
        }

        // Check columns
        for (int i = 0; i < boardSize; i++) {
            if (buttons[i].getText().equals(buttons[i+boardSize].getText()) && buttons[i].getText().equals(buttons[i+(boardSize-1)*boardSize].getText()) && !buttons[i].isEnabled()) {
                JOptionPane.showMessageDialog(frame, buttons[i].getText() + " wins!");
                resetGame();
                return;
            }
        }

        // Check diagonals
        if (buttons[0].getText().equals(buttons[boardSize+1].getText()) && buttons[0].getText().equals(buttons[(boardSize-1)*boardSize+(boardSize-1)].getText()) && !buttons[0].isEnabled()) {
            JOptionPane.showMessageDialog(frame, buttons[0].getText() + " wins!");
            resetGame();
            return;
        }
        if (buttons[boardSize-1].getText().equals(buttons[(boardSize-1)*boardSize-(boardSize-1)].getText()) && buttons[boardSize-1].getText().equals(buttons[boardSize*boardSize-boardSize].getText()) && !buttons[boardSize-1].isEnabled()) {
            JOptionPane.showMessageDialog(frame, buttons[boardSize-1].getText() + " wins!");
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
            JOptionPane.showMessageDialog(frame, "Tie game!");
            resetGame();
        }
    }

    public void resetGame() {
        for (int i = 0; i < boardSize*boardSize; i++) {
            buttons[i].setText("");
            buttons[i].setEnabled(true);
        }
        xTurn = true;
    }

    public static void main(String[] args) {
        //new main_interface();
        new singleplayer_game();
    }
}