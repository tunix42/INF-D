package project.d.pacman;

import java.util.Random;

/**
 *
 * @author Ruben
 */
public class DronkenSpook extends Spook {

    private Pacman pacman;

    public DronkenSpook(int grootte, Vak vak, Spelobject[] spelobjecten, int color, Pacman pacman, int level) {
        super(grootte, vak, spelobjecten, color, pacman, level);
        this.pacman = pacman;
    }

    public static int randInt() {
        Random rand = new Random();
        int randomNum = rand.nextInt((3 - 0) + 1) + 0;
        return randomNum;
    }

    @Override
    public void beweeg(Directie d) {
        if (!pacman.isPauze()) {
            //Valideren of Pacman maar 1 richting op kan bewegen, als dat zo is zal hij die kant op gaan
            boolean rechts = beweegEenRichting(Directie.rechts);
            boolean links = beweegEenRichting(Directie.links);
            boolean omhoog = beweegEenRichting(Directie.omhoog);
            boolean omlaag = beweegEenRichting(Directie.omlaag);

            //Als Pacman meerdere keuzes heeft zal hij random een directie op gaan die niet gelijk is aan de directie waar hij vandaan komt
            if (!rechts || !links || !omhoog || !omlaag) {
                int rand = DronkenSpook.randInt();
                switch (rand) {
                    case 0:
                        nonSpagettieBeweegRandom(Directie.omlaag, Directie.omhoog);
                        break;
                    case 1:
                        nonSpagettieBeweegRandom(Directie.omhoog, Directie.omlaag);
                        break;
                    case 2:
                        nonSpagettieBeweegRandom(Directie.links, Directie.rechts);
                        break;
                    case 3:
                        nonSpagettieBeweegRandom(Directie.rechts, Directie.links);
                        break;
                }
            }
        }
    }

    public void nonSpagettieBeweegRandom(Directie beweegrichting, Directie muurCheck) {
        if (beweegRichting != beweegrichting && !vak.getBuur(muurCheck).isMuur()) {
            beweegRichting = muurCheck;
            super.beweeg(beweegRichting);
            kijkRichting = beweegrichting;
        } else {
            Directie d = null;
            beweeg(d);
        }
    }

    public boolean beweegEenRichting(Directie richting) {
        boolean muur = true;
        for (Directie d : Directie.values()) {
            if (d != richting) {
                muur = vak.getBuur(d).isMuur();
                if (!muur) {
                    return false;
                }
            }
        }
        super.beweeg(richting);
        return muur;
    }
}
