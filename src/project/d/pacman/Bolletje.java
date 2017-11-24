package project.d.pacman;

import java.awt.Graphics;

/**
 *
 * @author Ruben
 */
public class Bolletje extends Spelobject{

    public Bolletje(int grootte, Vak vak) {
        super(grootte, vak);
        super.afbeelding = iconName("resources/Bolletje.png");
    }

    @Override
    public void render(Graphics g) {
        
        if(!vak.heeftBol()){
            super.objectGepakt(g);
        }else{
            g.drawImage(afbeelding, vak.getVakX(), vak.getVakY(), grootte, grootte, null);
        }
    }
    
    
    
}
