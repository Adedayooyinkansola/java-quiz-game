
package Quiz;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.*;
import javax.swing.*;

/**
 *
 * @author Oyinkansola
 */
public class QuizUtilities 
{
    private static Clip musicClip;
    private static boolean isMusicOn = true;
    static Timer timer;
    static List<Integer> scoreHistory = new ArrayList<>();

    public static void setTimer(JLabel timeLabel, JLabel feedback, JButton button, JButton btn) {
        final int[] timeLeft = {300};
        timer = new Timer(1000, e -> {
            timeLeft[0]--;
            int minutes = timeLeft[0] / 60;
            int seconds = timeLeft[0] % 60;
            String feedbackText = feedback.getText();
            timeLabel.setText(String.format("Timer: %02d:%02d", minutes, seconds));
            if (timeLeft[0] <= 0) {
                ((Timer) e.getSource()).stop();
                timeLabel.setText("Time's up!");
                button.setEnabled(false);
                btn.setEnabled(false);
            }
            else if(feedbackText == "Your answer is correct. Click on the next button to proceed.") {
                ((Timer) e.getSource()).stop();
            }
        });
        timer.start();
    }
    
    public static void exitApp() {
        System.exit(0);
    }
    
    public static void setButtonMouseExited(JButton button) {
        button.setBackground(Color.white);
        button.setForeground(new Color(25, 25, 112));
    }
    
    public static void setButtonMouseEntered(JButton button) {
        button.setBackground(new Color(106, 50, 159));
        button.setForeground(Color.white);
    }
    
    public static void loadMusic(String filepath) {
       try {
           
            if (musicClip != null && musicClip.isOpen()) 
            {
                musicClip.stop();
                musicClip.close();
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(filepath));
            musicClip = AudioSystem.getClip();
            musicClip.open(audioIn);
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        } 
    }
    
    public static void toggleSound(JToggleButton toggleButton)
    {
        if(musicClip == null) 
        {
            return;
        }
        if (isMusicOn) {
            musicClip.stop();
            toggleButton.setIcon(new ImageIcon(QuizUtilities.class.getResource("assets/mute_icon.png")));
        }
        else {
            musicClip.setFramePosition(0);
            musicClip.start();
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
            toggleButton.setIcon(new ImageIcon(QuizUtilities.class.getResource("assets/sound_icon.png")));
        }
        
        isMusicOn = !isMusicOn;
    }

}
