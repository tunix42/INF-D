package project.d.pacman;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author Ruben
 */
public abstract class Spook extends Poppetje {

    private final int color;
    private Pacman pacman;
    protected boolean leeft;
    private boolean gameover = false;
    Directie beweegRichting = Directie.omlaag;
    Directie kijkRichting = Directie.omlaag;
    private Timer timer;

    public Spook(int grootte, Vak vak, Spelobject[] spelobjecten, int color, Pacman pacman, int level) {
        super(grootte, vak, Directie.rechts);
        super.spelobjecten = spelobjecten;
        this.color = color;
        this.pacman = pacman;
        leeft = true;
        
        if(level == 1){
            timer = new Timer(285, new Ticker());
        }if(level == 2){
            timer = new Timer(230, new Ticker());
        }if(level == 3){
            timer = new Timer(180, new Ticker());
        }
        timer.start();
    }
    
    class Ticker implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            if(!pacman.isPauze()){
                Directie d = null;
                beweeg(d);
            }
        }
    }
    
    @Override
    public void render(Graphics g) {
        detectPacman();
        if (!leeft) {
            super.afbeelding = iconName("resources/zwart.jpg");
            grootte = 0;
        }

        if (gameover) {
            pacman.setPauze(true);
            super.afbeelding = iconName("resources/gameOverZwart.png");
            g.drawImage(afbeelding, 0, 0, 672, 672, null);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Stencil", Font.PLAIN, 30));
            g.drawString("Score: " + pacman.getScore(), 237, 410);

        } else if (!gameover && !pacman.getGameover()) {
            if (!pacman.isOnstervelijk()) {
                if (color == 1) {
                    switch (kijkRichting) {
                        case omlaag:
                            super.afbeelding = iconName("resources/spookOmhoogGroen.png");
                            break;
                        case omhoog:
                            super.afbeelding = iconName("resources/spookOmlaagGroen.png");
                            break;
                        case rechts:
                            super.afbeelding = iconName("resources/spookLinksGroen.png");
                            break;
                        case links:
                            super.afbeelding = iconName("resources/spookRechtsGroen.png");
                            break;
                    }
                } else if (color == 2) {
                    switch (kijkRichting) {
                        case omlaag:
                            super.afbeelding = iconName("resources/spookOmhoogPaars.png");
                            break;
                        case omhoog:
                            super.afbeelding = iconName("resources/spookOmlaagPaars.png");
                            break;
                        case rechts:
                            super.afbeelding = iconName("resources/spookLinksPaars.png");
                            break;
                        case links:
                            super.afbeelding = iconName("resources/spookRechtsPaars.png");
                            break;
                    }
                } else if (color == 3) {
                    switch (kijkRichting) {
                        case omlaag:
                            super.afbeelding = iconName("resources/spookOmhoogRood.png");
                            break;
                        case omhoog:
                            super.afbeelding = iconName("resources/spookOmlaagRood.png");
                            break;
                        case rechts:
                            super.afbeelding = iconName("resources/spookLinksRood.png");
                            break;
                        case links:
                            super.afbeelding = iconName("resources/spookRechtsRood.png");
                            break;
                    }
                } else if (color == 4) {
                    switch (kijkRichting) {
                        case omlaag:
                            super.afbeelding = iconName("resources/spookOmhoogBlauw.png");
                            break;
                        case omhoog:
                            super.afbeelding = iconName("resources/spookOmlaagBlauw.png");
                            break;
                        case rechts:
                            super.afbeelding = iconName("resources/spookLinksblauw.png");
                            break;
                        case links:
                            super.afbeelding = iconName("resources/spookRechtsBlauw.png");
                            break;
                    }
                }
            } else {
                if(pacman.getCounter() <= 80){                
                super.afbeelding = iconName("resources/eetSpookBlauw.png");
                }else if(pacman.getCounter() % 2 == 0){
                    super.afbeelding = iconName("resources/eetSpookWit.png");
                }else{
                    super.afbeelding = iconName("resources/eetSpookBlauw.png");
                }
                
                
            }
            g.drawImage(afbeelding, vak.getVakX(), vak.getVakY(), grootte, grootte, null);
        }

    }

    private void detectPacman() {
        Vak vakPacman = spelobjecten[1].getVak();
        Vak eigenVak = getVak();
        if (vakPacman == eigenVak && !pacman.isOnstervelijk() && leeft) {
            if(pacman.getLevens() == 1){
            gameover = true;
            pacman.setGameover(true);
            }else{
                pacman.levenOmlaag();                
            }

        } else if (vakPacman == eigenVak && pacman.isOnstervelijk() && leeft) {
            leeft = false;
            pacman.eatSpook();

        }
    }

}
