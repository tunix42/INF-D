
package project.d.pacman;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.Timer;

/**
 *
 * @author Ruben
 */
public class Speelbord {
    
    private final Vak[][] vakken = new Vak[21][21];
    private final Spelobject[][] muren = new Spelobject[21][21];
    private final Bolletje[][] bolletjes = new Bolletje[21][21];
    private final SuperBolletje[][] superbolletjes = new SuperBolletje[21][21];
    private final Spelobject[] spelobjecten = new Spelobject[8];
    private final int vakGrootte;
    private int huidigAantalBolletjes = 0;
    private int totaalAantalBolletjes = 0;
    private boolean kersGepakt = false;
    private int randX;
    private int randY;
    private int[][] murenIndeling;
    private int huidigLevel = 0;
    private boolean pauze = false;

    public Speelbord(int vakGrootte) {
        this.vakGrootte = vakGrootte;
        spelobjecten[1] = new Pacman(vakGrootte, vakken[1][1], spelobjecten, this);
        spelobjecten[6] = new Kers(vakGrootte, vakken[randX][randY]);
        
        volgendeLevel();
        
    }
    
    public void setRandom(){
        Random random = new Random();
        int randXtest = random.nextInt((21 - 1) + 1);
        int randYtest = random.nextInt((21 - 1) + 1);
        if(!vakken[randXtest][randYtest].isMuur()){
            randX = randXtest;
            randY = randYtest;
        }else{
            setRandom();
        }
    }
    
    public final void telBolletjes(){
        for (int x = 0; x < murenIndeling.length; x++) {
            for (int y = 0; y < murenIndeling[0].length; y++) {
                if (vakken[x][y].heeftBol()|| vakken[x][y].heeftSuperBol()) {
                    huidigAantalBolletjes++;
                }
            }
        }
        totaalAantalBolletjes = huidigAantalBolletjes;
    }
    
    public void render(Graphics g) {
        
        for (Spelobject o : spelobjecten) {
            if (o != null && !(o instanceof Pacman)) {
                o.render(g);
            }
        }
        

        for (int x = 0; x < murenIndeling.length; x++) {
            for (int y = 0; y < murenIndeling[0].length; y++) {
                if (vakken[x][y].isMuur()) {
                    muren[x][y].render(g);
                }
            }
        }
        
        for (int x = 0; x < murenIndeling.length; x++) {
            for (int y = 0; y < murenIndeling[0].length; y++) {
                if (vakken[x][y].heeftBol()) {
                    bolletjes[x][y].render(g);
                }
            }
        }
        for (int x = 0; x < murenIndeling.length; x++) {
            for (int y = 0; y < murenIndeling[0].length; y++) {
                if (vakken[x][y].heeftSuperBol()) {
                    superbolletjes[x][y].render(g);
                }
            }
        }
        spelobjecten[6].render(g);
        spelobjecten[1].render(g);
        spelobjecten[2].render(g);
        spelobjecten[3].render(g);
        spelobjecten[4].render(g);
        spelobjecten[5].render(g);
        
        
        
        if(spelobjecten[1].getVak().heeftBol()){            
            spelobjecten[1].getVak().setBolletje(false);
            huidigAantalBolletjes--;
        }else if(spelobjecten[1].getVak().heeftSuperBol()){
            spelobjecten[1].getVak().setSuperBol(false);     
            huidigAantalBolletjes--;
        }if(spelobjecten[1].getVak().heeftKers()){
            spelobjecten[1].getVak().setKers(false);
        }
        

        g.setColor(Color.WHITE);
        g.setFont(new Font("Stencil", Font.PLAIN, 24));
        g.drawString("Level: " + Integer.toString(huidigLevel), 545, 25);
        
        bollenOpgepakt();
        
        
    }

    private void setVakBuren() {
        for (int x = 0; x < vakken.length; x++) {
            for (int y = 0; y < vakken[0].length; y++) {
                if (y != 0) {
                    vakken[x][y].setBuur(Directie.omhoog, vakken[x][y - 1]);
                }
                if (y < vakken.length - 1) {

                    vakken[x][y].setBuur(Directie.omlaag, vakken[x][y + 1]);
                }

                if (x != 0) {
                    vakken[x][y].setBuur(Directie.links, vakken[x - 1][y]);
                }
                if (x < vakken.length - 1) {
                    vakken[x][y].setBuur(Directie.rechts, vakken[x + 1][y]);
                }
            }
        }
    }

   
    private void maakLevel() {
        for (int x = 0; x < vakken.length; x++) {
            for (int y = 0; y < vakken[0].length; y++) {

                //van dubble int array naar objecten
                //1 = muur,  0 = leeg vak
                switch (murenIndeling[x][y]) {
                    case 1:
                        vakken[x][y] = new Vak(x * vakGrootte, y * vakGrootte, vakGrootte, vakGrootte, true, false, false, false);
                        muren[x][y] = new Muur(vakGrootte, vakken[x][y]);
                        break;                                       
                    case 2:
                        vakken[x][y] = new Vak(x * vakGrootte, y * vakGrootte, vakGrootte, vakGrootte, false, false, true, false);
                        superbolletjes[x][y] = new SuperBolletje(vakGrootte, vakken[x][y]);
                        break;
                    default:
                        vakken[x][y] = new Vak(x * vakGrootte, y * vakGrootte, vakGrootte, vakGrootte, false, true, false, false);
                        bolletjes[x][y] = new Bolletje(vakGrootte, vakken[x][y]);
                }
            }
        }
        setRandom();
        if(huidigAantalBolletjes <= (totaalAantalBolletjes/2)){
            spelobjecten[6].setVak(vakken[randX][randY]);
        }
        
        spelobjecten[1].setVak(vakken[10][3]);
        spelobjecten[2].setVak(vakken[9][9]);
        spelobjecten[3].setVak(vakken[10][9]);
        spelobjecten[4].setVak(vakken[11][9]); 
        spelobjecten[5].setVak(vakken[10][10]);
        spelobjecten[1].setBeginVak(vakken[10][3]);
        spelobjecten[2].setBeginVak(vakken[9][9]);
        spelobjecten[3].setBeginVak(vakken[10][9]);
        spelobjecten[4].setBeginVak(vakken[11][9]);
        spelobjecten[5].setBeginVak(vakken[10][10]);
        
        
        
        setVakBuren();
    }

    private void setLevel1() {
        //0 = leeg vak, 1 = muur, 2 = superBolletje (225 * 0.05 = 11.25)
        //eerste reeks is eerste verticale rij in level
        int[][] verdeling = {
        { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, },
        { 1, 2, 0, 0, 0, 0, 0, 1, 0, 0, 2, 0, 0, 1, 0, 0, 0, 0, 0, 2, 1, },
        { 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, },
        { 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, },
        { 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, },
        { 1, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0, 0, 1, 0, 0, 2, 1, 0, 0, 0, 1, },
        { 1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, },
        { 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, },
        { 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, },
        { 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, },
        { 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 2, 1, 1, 1, 0, 1, 0, 1, 1, 1, },
        { 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, },
        { 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, },
        { 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, },
        { 1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, },
        { 1, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0, 0, 1, 0, 0, 2, 1, 0, 0, 0, 1, },
        { 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, },
        { 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, },
        { 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, },
        { 1, 2, 0, 0, 0, 0, 0, 1, 0, 0, 2, 0, 0, 1, 0, 0, 0, 0, 0, 2, 1, },
        { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, },};
        //sout(verdeling);
        murenIndeling = verdeling;
        
        
    }

    private void setLevel2() {
        
        int[][] verdeling = {
        { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, },
        { 1, 2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 2, 1, },
        { 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, },
        { 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, },
        { 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, },
        { 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, },
        { 1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, },
        { 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, },
        { 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, },
        { 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, },
        { 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 2, 1, 1, 1, 0, 1, 0, 1, 1, 1, },
        { 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, },
        { 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, },
        { 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, },
        { 1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, },
        { 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, },
        { 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, },
        { 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, },
        { 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, },
        { 1, 2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 2, 1, },
        { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, },};

        
        murenIndeling = verdeling;
    }

    private void setLevel3() {
        
        int[][] verdeling = {
        { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, },
        { 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 2, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, },
        { 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, },
        { 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, },
        { 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, },
        { 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, },
        { 1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, },
        { 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, },
        { 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, },
        { 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, },
        { 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, },
        { 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, },
        { 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, },
        { 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, },
        { 1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, },
        { 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, },
        { 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, },
        { 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, },
        { 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, },
        { 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 2, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, },
        { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, },};
        
        
        murenIndeling = verdeling;
    }

    public Spelobject getSpelobject(int i) {
        return spelobjecten[i];
    }

    

    private void volgendeLevel() {
            
        
        
        
        if (huidigLevel == 0) {
            setLevel1();
            spelobjecten[2] = new SlimSpook(vakGrootte, vakken[3][3], spelobjecten, 1, (Pacman)getSpelobject(1), 1);
            spelobjecten[3] = new SlimSpook(vakGrootte, vakken[2][3], spelobjecten, 2, (Pacman)getSpelobject(1), 1);
            spelobjecten[4] = new DronkenSpook(vakGrootte, vakken[3][2], spelobjecten, 3, (Pacman)getSpelobject(1), 1);
            spelobjecten[5] = new DronkenSpook(vakGrootte, vakken[1][3], spelobjecten, 4, (Pacman)getSpelobject(1), 1);
            
        }
        if (huidigLevel == 1) {
            setLevel2();
            spelobjecten[2] = new SlimSpook(vakGrootte, vakken[3][3], spelobjecten, 1, (Pacman)getSpelobject(1), 2);
            spelobjecten[3] = new SlimSpook(vakGrootte, vakken[2][3], spelobjecten, 2, (Pacman)getSpelobject(1), 2);
            spelobjecten[4] = new DronkenSpook(vakGrootte, vakken[3][2], spelobjecten, 3, (Pacman)getSpelobject(1), 2);
            spelobjecten[5] = new DronkenSpook(vakGrootte, vakken[1][3], spelobjecten, 4, (Pacman)getSpelobject(1), 2);
            
        } else if (huidigLevel >= 2) {
            setLevel3();
            spelobjecten[2] = new SlimSpook(vakGrootte, vakken[3][3], spelobjecten, 1, (Pacman)getSpelobject(1), 3);
            spelobjecten[3] = new SlimSpook(vakGrootte, vakken[2][3], spelobjecten, 2, (Pacman)getSpelobject(1), 3);
            spelobjecten[4] = new DronkenSpook(vakGrootte, vakken[3][2], spelobjecten, 3, (Pacman)getSpelobject(1), 3);
            spelobjecten[5] = new DronkenSpook(vakGrootte, vakken[1][3], spelobjecten, 4, (Pacman)getSpelobject(1), 3);
            
        }
       
        maakLevel();
        
        
        huidigLevel++;
        telBolletjes();
        kersGepakt = false;
        
        
    }
    
    public void pause(boolean set){
        pauze = set;
    }
    
    public boolean isPauze(){
        return pauze;
    }
    
    public int getHuidigLevel(){
        return huidigLevel;
    }
    
    public void maakKers(){
        if(huidigAantalBolletjes <= (totaalAantalBolletjes/2) && !kersGepakt){
            spelobjecten[6].vak.setKers(true);
            kersGepakt = true;
        }
    }
    
    public void bollenOpgepakt(){
        if (huidigAantalBolletjes == 0) {
            volgendeLevel();
        }
    }
    
    public int getVakGrootte(){
        return vakGrootte;
    }
    
    
}
