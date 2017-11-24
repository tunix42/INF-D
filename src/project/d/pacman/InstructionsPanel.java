/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.d.pacman;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

/**
 *
 * @author Ruben
 */
public class InstructionsPanel extends JComponent implements Runnable{
    private static final Dimension schermGrootte = new Dimension(672, 672);
    
    private Image scherm;
    
    public InstructionsPanel(){
        setPreferredSize(schermGrootte);
        
    }
    
    public void init() {
        new Thread(this).start();
    }    
    
    public void render() {
        
        Graphics g = scherm.getGraphics();
        
        Image bg = new ImageIcon(this.getClass().getResource("resources/instructions.png")).getImage();

        g.drawImage(bg, 0, 0, schermGrootte.width, schermGrootte.height, null);
        
        g = getGraphics();
        try {
            g.drawImage(scherm, 0, 0, schermGrootte.width, schermGrootte.height, 0, 0, schermGrootte.width, schermGrootte.height, null);
            g.dispose();
        } catch (NullPointerException ex) {

        }
        
    }

    @Override
    public void run() {
        scherm = createVolatileImage(schermGrootte.width, schermGrootte.height);
        while(true){
            render();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e);
                break;
            }
        }
    }
    
}
