package project.d.pacman;

import static java.lang.Math.abs;
import java.util.ArrayList;

/**
 *
 * @author Jelmer
 */
public class AStar {

    private ArrayList<Vak> openList = new ArrayList<>();
    private ArrayList<Vak> closedList = new ArrayList<>();
    private ArrayList<Vak> pad = new ArrayList<>();

    public Directie zoekPad(Vak spook, Vak pacman) {

        //vakken en boolean
        Vak currentDoorzoeken = spook;
        boolean gevonden = false;

        //positie pacman vaststellen
        int pacmanX = pacman.getVakX() / 32;//speelbord.getVakGrootte();
        int pacmanY = pacman.getVakY() / 32;//speelbord.getVakGrootte();

        System.out.println("pad zoeken...");
        while (!gevonden) {
            //Lists aanpassen, buren van currentDoorzoeken setten
            closedList.add(currentDoorzoeken);
            for (Directie d : Directie.values()) {
                Vak buur = currentDoorzoeken.getBuur(d);
                boolean alInList = false;
                for (int i = 0; i < openList.size(); i++) { //checken of buur al in openList zit
                    Vak openVak = openList.get(i);
                    if (buur == openVak) {
                        alInList = true;
                    }
                }
                for (int i = 0; i < closedList.size(); i++) { //checken of buur al in closedList zit
                    Vak closedVak = closedList.get(i);
                    if (buur == closedVak) {
                        alInList = true;
                    }
                }
                if (!alInList && !buur.isMuur()) {
                    openList.add(buur);
                    buur.setParent(currentDoorzoeken);
                }
            }

            //buren van currentDoorzoeken checken
            for (Directie d : Directie.values()) {
                Vak buur = currentDoorzoeken.getBuur(d);
                if (!buur.isMuur()) { //als buur geen muur is:
                    //h waarden berekenen
                    int buurX = buur.getVakX() / 32;//speelbord.getVakGrootte();
                    int buurY = buur.getVakY() / 32;//speelbord.getVakGrootte();
                    int verschilX = abs(buurX - pacmanX);
                    int verschilY = abs(buurY - pacmanY);
                    int h = verschilX + verschilY;

                    //h en g waarde setten
                    buur.setH(h);
                    if (buur.getParent() != null) {
                        buur.setG(1 + buur.getParent().getG());
                    } else {
                        buur.setG(1);
                    }
                }
            }

            //openList checken op kleinste F waarden en die setten naar currentDoorzoeken
            int kleinsteF = 9999;
            Vak kleinsteFVak = null;
            for (int i = 0; i < openList.size(); i++) {
                if (openList.get(i).getF() < kleinsteF) {
                    kleinsteF = openList.get(i).getF();
                    kleinsteFVak = openList.get(i);
                }
            }
            currentDoorzoeken = kleinsteFVak;
            openList.remove(kleinsteFVak);

            //base case
            if (currentDoorzoeken == pacman) {
                gevonden = true;
            }
        }
        System.out.println("pad gevonden!");

        //pad gevonden, pad ArrayList vullen (.get(0) = pacman, .get(size-1) = spook)
        boolean klaar = false;
        Vak huidigVak = pacman;
        while (!klaar) {
            pad.add(huidigVak);
            if (huidigVak == spook) {
                klaar = true;
            }
            huidigVak = huidigVak.getParent();
        }

        //Directie bepalen
        Vak beweegVak = pad.get(pad.size() - 2);
        Directie beweegDirectie = null;
        for (Directie d : Directie.values()) {
            if (beweegVak == spook.getBuur(d)) {
                beweegDirectie = d;
            }
        }

        //alles resetten
        for(int i=0; i<openList.size(); i++){
            openList.get(i).reset();
        }     
        for(int i=0; i<closedList.size(); i++){
            closedList.get(i).reset();
        } 
        
        openList.removeAll(openList);
        closedList.removeAll(closedList);
        pad.removeAll(pad);
        
        //return
        return beweegDirectie;

    }
}
