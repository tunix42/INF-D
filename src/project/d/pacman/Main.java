package project.d.pacman;

/**
 *
 * @author Ruben
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Main extends JComponent implements Runnable {

    //grootte van het scherm in pixels
    private static final Dimension schermGrootte = new Dimension(672, 672);

    //(pixel)grootte vakjes      32 = normaal   134 = POC
    public static final int vakGrootte = 32;

    //scherm om op te tekenen
    private Image scherm;

    //speelbord
    private final Speelbord speelbord;

    private boolean pause = false;

    Timer timer;

    public Main() {
        setPreferredSize(schermGrootte);

        speelbord = new Speelbord(vakGrootte);

        KeyboardListener keyboardListener = new KeyboardListener((Pacman) speelbord.getSpelobject(1), this);
        addKeyListener(keyboardListener);
        timer = new Timer(95, new MainTimer());
        timer.start();
    }

    class MainTimer implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            render();
        }

    }

    public void init() {
        new Thread(this).start();
    }

    public void switchPause() {
        pause = !pause;                
    }

    public void render() {
        Graphics g = scherm.getGraphics();

        //teken achtergrond
        g.setColor(new Color(238, 232, 170));
        Image bg = new ImageIcon(this.getClass().getResource("resources/zwart.jpg")).getImage();

        g.drawImage(bg, 0, 0, schermGrootte.width, schermGrootte.height, null);

        speelbord.render(g);
        g = getGraphics();
        try {
            g.drawImage(scherm, 0, 0, schermGrootte.width, schermGrootte.height, 0, 0, schermGrootte.width, schermGrootte.height, null);
            g.dispose();
        } catch (NullPointerException ex) {

        }
        
        if(pause == true){
            speelbord.pause(true);
        }
        else{
            speelbord.pause(false);
            
        }
    }

    @Override
    public void run() {
        scherm = createVolatileImage(schermGrootte.width, schermGrootte.height);

        render();

    }

}
