import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class MainInterface {
    private JFrame f = new JFrame("Tic-Tac-Toe");
    private JButton btnPlay;
    private JPanel panel, logo, space;
    private JButton btnSetting;


    public MainInterface() {

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().setLayout(new BoxLayout(f.getContentPane(), BoxLayout.Y_AXIS));

        Icon logoGame = new ImageIcon("sources\\logo1.png");
        Icon playIcon = new ImageIcon("sources\\play1.png");
        Icon settingIcon = new ImageIcon("sources\\settings1.png");

        panel = new JPanel();
        logo = new JPanel();
        space = new JPanel();

        panel.setLayout(new FlowLayout(FlowLayout.CENTER,10,5));

        space.setLayout(new FlowLayout(FlowLayout.CENTER,10,5));
        space.setPreferredSize(new Dimension(100, 100));
        space.setMaximumSize(new Dimension(250,150));

        logo.setLayout(new FlowLayout(FlowLayout.CENTER,10,5));
        logo.setPreferredSize(new Dimension(150, 170));
        logo.setMaximumSize(new Dimension(250,10));


        JLabel game = new JLabel(logoGame);

        logo.add(game);
        btnPlay = new JButton("Play", playIcon);
        btnSetting = new JButton("Setting", settingIcon);

        btnPlay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                f.dispose();
                try {
                    new MultiplayerGame();
                } catch (UnsupportedAudioFileException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (LineUnavailableException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnSetting.setLayout(new FlowLayout(FlowLayout.CENTER,10,5));
        btnSetting.setPreferredSize(new Dimension(110, 50));
        btnSetting.setMaximumSize(new Dimension(210, 50));
        btnSetting.setBackground(Color.WHITE);
        btnSetting.setBorder(BorderFactory.createLineBorder(Color.WHITE,10));

        btnSetting.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                f.dispose();
                new GameSetting();
            }
        });

        btnPlay.setLayout(new FlowLayout(FlowLayout.CENTER,10,15));
        btnPlay.setPreferredSize(new Dimension(110, 50));
        btnPlay.setMaximumSize(new Dimension(210, 50));
        btnPlay.setBackground(Color.WHITE);
        btnPlay.setBorder(BorderFactory.createLineBorder(Color.WHITE,10));

        panel.add(btnPlay);
        panel.add(btnSetting);

        f.getContentPane().add(space);
        f.getContentPane().add(logo);
        f.add(Box.createRigidArea(new Dimension(0, 10)));
        f.add(panel, BorderLayout.CENTER);
        f.setSize(400,570);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainInterface();

            }
        });
    }
}