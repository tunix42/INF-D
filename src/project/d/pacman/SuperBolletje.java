package project.d.pacman;

import java.awt.Graphics;

/**
 *
 * @author Ruben
 */
public class SuperBolletje extends Spelobject{

    public SuperBolletje(int grootte, Vak vak) {
        super(grootte, vak);
        super.afbeelding = iconName("resources/SuperBolletje.png");
    }
    
    @Override
    public void render(Graphics g) {
        
        if(!vak.heeftSuperBol()){
            super.objectGepakt(g);
        }else{
            g.drawImage(afbeelding, vak.getVakX(), vak.getVakY(), grootte, grootte, null);
        }
    }
}
