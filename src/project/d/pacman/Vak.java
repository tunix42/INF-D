package project.d.pacman;

import java.awt.Rectangle;

/**
 *
 * @author Ruben
 */
public class Vak extends Rectangle{
    
    private boolean muur;
    private boolean heeftBolletje;
    private boolean heeftSuperBolletje;
    private boolean heeftKers;
    
    private Vak boven;
    private Vak onder;
    private Vak links;
    private Vak rechts;
    
    //voor kortste pad
    private int h; //h = verschil x + verschil y = afstand
    private int g; //g = movement costs vanaf de start(spook dus)
    private int f = g+h;
    public Vak parent;
    private boolean gebruikt = false;

    public Vak(int x, int y, int width, int height, boolean muur, boolean heeftBolletje, boolean heeftSuperBolletje, boolean heeftKers) {
        super(x, y, width, height);
        
        this.muur = muur;
        this.heeftBolletje = heeftBolletje;
        this.heeftSuperBolletje = heeftSuperBolletje;
        this.heeftKers = heeftKers;
        
    }
    
    

    public boolean isMuur() {
        return muur;
    }

    public void setMuur(boolean muur) {
        this.muur = muur;
    }
    
    public boolean heeftBol(){
        return heeftBolletje;
    }
    public void setBolletje(boolean heeftBolletje){
        this.heeftBolletje = heeftBolletje;
    }

    public Vak getBuur(Directie directie) {
        Vak vak = null;
        switch (directie) {
            case omhoog:
                vak = boven;
                break;
            case omlaag:
                vak = onder;
                break;
            case links:
                vak = links;
                break;
            case rechts:
                vak = rechts;
                break;

        }
        return vak;
    }

    public void setBuur(Directie directie, Vak vak) {
        switch (directie) {
            case omhoog:
                boven = vak;
                break;
            case omlaag:
                onder = vak;
                break;
            case links:
                links = vak;
                break;
            case rechts:
                rechts = vak;
                break;
        }
    }

    public int getVakX() {
        return x;
    }

    public int getVakY() {
        return y;
    }   

    public boolean heeftSuperBol() {
        return heeftSuperBolletje;        
    }
    public void setSuperBol(boolean set){
        heeftSuperBolletje = set;
    }
    public boolean heeftKers(){
        return heeftKers;
    }
    public void setKers(boolean set){
        heeftKers = set;
    }
    
    public int getH(){
        return h;
    }
    public void setH(int h){
        this.h = h;
    }
    public int getG(){
        return g;
    }
    public void setG(int g){
        this.g = g;
    }
    public int getF(){
        return f;
    }
    public void setF(int f){
        this.f = f;
    }
    public Vak getParent(){
        return parent;
    }
    public void setParent(Vak p){
        parent = p;
    }
    public boolean getGebruikt(){
        return gebruikt;
    }
    public void setGebruikt(boolean set){
        gebruikt = set;
        parent = null;
    }
    public void reset(){
        h = -1;
        f = -1;
        g = -1;
        parent = null;
        
    }
}
