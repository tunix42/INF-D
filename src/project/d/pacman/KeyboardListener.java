package project.d.pacman;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Ruben
 */
public class KeyboardListener implements KeyListener{
    
    private final Pacman pacman;
    private final Main main;
        
    public KeyboardListener(Pacman pacman, Main main) {
        this.pacman = pacman;
        this.main = main;        
    }

    
    

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_UP:
                if(!pacman.isPauze()){
                pacman.beweeg(Directie.omhoog);
                main.render();
                }
                
                break;
            case KeyEvent.VK_DOWN:
                if(!pacman.isPauze()){
                pacman.beweeg(Directie.omlaag);
                main.render();
                }
                break;
            case KeyEvent.VK_LEFT:
                if(!pacman.isPauze()){
                pacman.beweeg(Directie.links);
                main.render();
                }
                break;
            case KeyEvent.VK_RIGHT:
                if(!pacman.isPauze()){
                pacman.beweeg(Directie.rechts);
                main.render();
                }
                break;                   
        }
    }
    
}
