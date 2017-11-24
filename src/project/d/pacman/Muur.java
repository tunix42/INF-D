package project.d.pacman;

import java.awt.Graphics;

/**
 *
 * @author Ruben
 */
public class Muur extends Spelobject{

    public Muur(int grootte, Vak vak) {
        super(grootte, vak);
        super.afbeelding = iconName("resources/muur.jpg");
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(afbeelding, vak.getVakX(), vak.getVakY(), grootte, grootte, null);
    }

    
    
}
