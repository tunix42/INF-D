package project.d.pacman;

import static java.lang.Math.abs;
import java.util.ArrayList;

/**
 *
 * @author Ruben
 */
public class SlimSpook extends Spook {

    private AStar aStar = new AStar();

    public SlimSpook(int grootte, Vak vak, Spelobject[] spelobjecten, int color, Pacman pacman, int level) {
        super(grootte, vak, spelobjecten, color, pacman, level);
    }
    
    @Override
    public void beweeg(Directie d) {
        if(this.leeft){
            Directie dirAStar = aStar.zoekPad(this.getVak(), spelobjecten[1].getVak());
            super.beweeg(dirAStar);
        }
    }
}
