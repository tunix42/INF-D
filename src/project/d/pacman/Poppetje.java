package project.d.pacman;

/**
 *
 * @author Ruben
 */
public abstract class Poppetje extends Spelobject{
    
    public Directie kijkRichting;
    
    public Poppetje(int grootte, Vak vak, Directie kijkRichting) {
        super(grootte, vak);
        this.kijkRichting = kijkRichting;
    }
    
    public void beweeg(Directie directie) {
        vak = vak.getBuur(directie);
    }
    
    public void setKijkRichting(Directie kijkrichting) {
        this.kijkRichting = kijkrichting;
    }
    
}
