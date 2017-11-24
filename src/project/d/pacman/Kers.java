package project.d.pacman;

import java.awt.Graphics;

/**
 *
 * @author Jelmer
 */
public class Kers extends Spelobject {
    
    public Kers(int grootte, Vak vak){
        super(grootte, vak);
        super.afbeelding = iconName("resources/kers.png");
        
    }
    
    @Override
    public void render(Graphics g){
        if(!vak.heeftKers()){
            super.objectGepakt(g);            
        }else{
            super.afbeelding = iconName("resources/kers.png");
            g.drawImage(afbeelding, vak.getVakX(), vak.getVakY(), grootte, grootte, null);
        }
    }
    
    
}
