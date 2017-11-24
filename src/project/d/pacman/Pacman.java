package project.d.pacman;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 *
 *
 * @author Ruben
 */
public class Pacman extends Poppetje {

    private int onsterfelijkCounter = 0;
    public int punten = 0;
    private int animation = 0;
    private String direction = "rechts";
    private boolean onstervelijk = false;
    private Timer timer;
    private boolean pauze = false;
    private boolean gameover = false;
    private int levens = 3;
    private Speelbord speelbord;
    private int huidigLevel = 0;

    public Pacman(int grootte, Vak vak, Spelobject[] spelobjecten, Speelbord speelbord) {
        super(grootte, vak, Directie.rechts);

        super.afbeelding = iconName("resources/pacmanRechtsOpen.png");
        super.spelobjecten = spelobjecten;
        this.speelbord = speelbord;

        PacmanListener listener = new PacmanListener();
        timer = new Timer(95, listener);
        timer.start();
    }

    class PacmanListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (isPauze() == false) {
                animation++;
                notOnstervelijk();
            }
        }

    }

    @Override
    public void render(Graphics g) {

        switch (direction) {
            case "rechts":
                if (animation == 1) {
                    super.afbeelding = iconName("resources/pacmanRechtsOpen.png");
                } else if (animation == 2) {
                    super.afbeelding = iconName("resources/pacmanRechtsHalf.png");
                } else if (animation == 3) {
                    super.afbeelding = iconName("resources/pacmanDicht.png");
                } else if (animation == 4) {
                    super.afbeelding = iconName("resources/pacmanRechtsHalf.png");
                    animation = 0;
                }
                break;
            case "links":
                if (animation == 1) {
                    super.afbeelding = iconName("resources/pacmanLinksOpen.png");
                } else if (animation == 2) {
                    super.afbeelding = iconName("resources/pacmanLinksHalf.png");
                } else if (animation == 3) {
                    super.afbeelding = iconName("resources/pacmanDicht.png");
                } else if (animation == 4) {
                    super.afbeelding = iconName("resources/pacmanLinksHalf.png");
                    animation = 0;
                }
                break;
            case "omhoog":
                if (animation == 1) {
                    super.afbeelding = iconName("resources/pacmanOmhoogOpen.png");
                } else if (animation == 2) {
                    super.afbeelding = iconName("resources/pacmanOmhoogHalf.png");
                } else if (animation == 3) {
                    super.afbeelding = iconName("resources/pacmanDicht.png");
                } else if (animation == 4) {
                    super.afbeelding = iconName("resources/pacmanOmhoogHalf.png");
                    animation = 0;
                }
                break;
            case "omlaag":
                if (animation == 1) {
                    super.afbeelding = iconName("resources/pacmanBenedenOpen.png");
                } else if (animation == 2) {
                    super.afbeelding = iconName("resources/pacmanBenedenHalf.png");
                } else if (animation == 3) {
                    super.afbeelding = iconName("resources/pacmanDicht.png");
                } else if (animation == 4) {
                    super.afbeelding = iconName("resources/pacmanBenedenHalf.png");
                    animation = 0;
                }
                break;

        }
        if (!gameover) {
            g.drawImage(afbeelding, vak.getVakX(), vak.getVakY(), grootte, grootte, null);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Stencil", Font.PLAIN, 24));

            g.drawString("Score: " + punten, 10, 25);
            g.drawString("Lives: " + levens, 10, 665);
        }
        if (speelbord.isPauze() == true) {
            setPauze(true);
        } else {
            setPauze(false);
        }
        levelOmhoog();
    }

    @Override
    public void beweeg(Directie directie) {
        if (!isPauze()) {
            switch (directie) {
                case omhoog:
                    direction = "omhoog";
                    break;
                case omlaag:
                    direction = "omlaag";
                    break;
                case links:
                    direction = "links";
                    break;
                case rechts:
                    direction = "rechts";
                    break;
            }

            Vak buurvak = vak.getBuur(directie);
            if (!buurvak.isMuur()) {
                if (buurvak.heeftBol()) {
                    paktBol();
                } else if (buurvak.heeftSuperBol()) {
                    paktSuperBol();
                }
                if (buurvak.heeftKers()) {
                    paktKers();
                }
                super.beweeg(directie);

            }
        }
    }

    public void switchPauze() {
        pauze = !pauze;
    }

    public boolean isPauze() {
        return pauze;
    }

    public void setPauze(boolean set) {
        pauze = set;
    }

    public boolean isOnstervelijk() {
        return onstervelijk;
    }

    public void setOnstervelijk(boolean set) {
        onstervelijk = set;
    }

    public void paktBol() {
        punten = punten + 10;

        speelbord.maakKers();
        speelbord.bollenOpgepakt();

    }

    public void paktSuperBol() {
        setOnstervelijk(true);
        onsterfelijkCounter = 0;
    }

    public void paktKers() {
        punten = punten + 100;

    }

    private void notOnstervelijk() {
        if (onstervelijk) {
            onsterfelijkCounter++;
        }

        if (onsterfelijkCounter == 106) {
            onsterfelijkCounter = 0;
            setOnstervelijk(false);
        }
    }

    public int getCounter() {
        return onsterfelijkCounter;
    }

    public void eatSpook() {
        punten = punten + 200;

    }

    public int getScore() {
        return punten;
    }

    public boolean getGameover() {
        return gameover;
    }

    public void setGameover(boolean set) {
        gameover = set;
    }

    public void levenOmlaag() {
        levens--;
        spelobjecten[1].goToBeginVak();
        spelobjecten[2].goToBeginVak();
        spelobjecten[3].goToBeginVak();
        spelobjecten[4].goToBeginVak();
        spelobjecten[5].goToBeginVak();

    }

    public int getLevens() {
        return levens;
    }

    private void levelOmhoog() {
        int level = speelbord.getHuidigLevel();
        if (huidigLevel != level) {
            huidigLevel = level;
            onsterfelijkCounter = 0;
            setOnstervelijk(false);
        }
    }
}
