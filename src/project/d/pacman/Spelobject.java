package project.d.pacman;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Ruben
 */
public abstract class Spelobject {

    public int grootte;
    public Vak vak;

    public Image afbeelding;

    public Spelobject[] spelobjecten;
    
    public Vak beginVak = null;

    public Spelobject(int grootte, Vak vak) {
        this.grootte = grootte;
        this.vak = vak;
    }
    
    public void objectGepakt(Graphics g){
        this.afbeelding = iconName("resources/zwart.jpg");
        g.drawImage(afbeelding, vak.getVakX(), vak.getVakY(), 0, 0, null);
    }
    
    public Image iconName(String icon){
        return new ImageIcon(this.getClass().getResource(icon)).getImage();
    }

    public abstract void render(Graphics g);

    public Vak getVak() {
        return vak;
    }

    public void setVak(Vak vak) {
        this.vak = vak;
    }
    public void setBeginVak(Vak beginVak){
        this.beginVak = beginVak;
    }
    public void goToBeginVak(){
        vak = beginVak;
    }
}
