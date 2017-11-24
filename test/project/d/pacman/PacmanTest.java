/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.d.pacman;

import java.awt.Graphics;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ruben
 */
public class PacmanTest {

    Vak midden;
    Vak omhoog;
    Vak omlaag;
    Vak rechts;
    Vak links;
    int vakgrootte = 32;

    public PacmanTest() {
        midden = new Vak(2 * vakgrootte, 2 * vakgrootte, vakgrootte, vakgrootte, false, false, false, false);
        omhoog = new Vak(2 * vakgrootte, 1 * vakgrootte, vakgrootte, vakgrootte, true, false, false, false);
        omlaag = new Vak(2 * vakgrootte, 3 * vakgrootte, vakgrootte, vakgrootte, false, false, false, false);
        rechts = new Vak(3 * vakgrootte, 2 * vakgrootte, vakgrootte, vakgrootte, false, false, false, false);
        links = new Vak(1 * vakgrootte, 2 * vakgrootte, vakgrootte, vakgrootte, true, false, false, false);
        midden.setBuur(Directie.omhoog, omhoog);
        midden.setBuur(Directie.omlaag, omlaag);
        midden.setBuur(Directie.rechts, rechts);
        midden.setBuur(Directie.links, links);
        omhoog.setBuur(Directie.omlaag, midden);
        omlaag.setBuur(Directie.omhoog, midden);
        rechts.setBuur(Directie.links, midden);
        links.setBuur(Directie.rechts, midden);

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of beweeg method, of class Pacman.
     */
    @Test
    public void testBeweeg() {
        System.out.println("test rechts");
        Directie directie = Directie.rechts;
        Pacman instance = new Pacman(vakgrootte, midden, null, null);
        instance.beweeg(directie);
        if (instance.getVak() != rechts) {
            fail("Pacman is niet bewogen terwijl er geen muur staat rechts.");
        }

    }

    @Test
    public void testBeweeg2() {
        System.out.println("test omhoog");
        Directie directie = Directie.omhoog;
        Pacman instance = new Pacman(vakgrootte, midden, null, null);
        instance.beweeg(directie);
        if (instance.getVak() != midden) {
            fail("Pacman is bewogen terwijl er een muur stond boven.");
        }

    }

    @Test
    public void testBeweeg3() {
        System.out.println("test links");
        Directie directie = Directie.links;
        Pacman instance = new Pacman(vakgrootte, midden, null, null);
        instance.beweeg(directie);
        if (instance.getVak() != midden) {
            fail("Pacman is bewogen terwijl er een muur stond links.");
        }

    }

    @Test
    public void testBeweeg4() {
        System.out.println("test omlaag");
        Directie directie = Directie.omlaag;
        Pacman instance = new Pacman(vakgrootte, midden, null, null);
        instance.beweeg(directie);
        if (instance.getVak() != omlaag) {
            fail("Pacman is niet bewogen terwijl er geen muur stond onder.");
        }

    }

    @Test
    public void testBeweeg5() {
        System.out.println("Fysiek testgeval 1(testmaat 1)");
        Pacman instance = new Pacman(vakgrootte, midden, null, null);

        //speler drukt op pauze:
        instance.switchPauze();

        //speler drukt op pijltje naar onder:        
        instance.beweeg(Directie.omlaag);
        if (instance.getVak() != midden) {
            fail("Pacman is bewogen terwijl het spel op pauze stond.");
        }

        //speler drukt weer op pauze:
        instance.switchPauze();

        //speler drukt op pijltje naar onder:
        instance.beweeg(Directie.omlaag);
        if (instance.getVak() != omlaag) {
            fail("Pacman is niet bewogen terwijl er geen muur stond onder en het spel niet op pauze stond.");
        }

    }

    @Test
    public void testBeweeg6() {
        System.out.println("Fysiek testgeval 2(testmaat 1)");
        Pacman instance = new Pacman(vakgrootte, midden, null, null);

        //speler drukt op pijltje naar boven:        
        instance.beweeg(Directie.omhoog);
        if (instance.getVak() != midden) {
            fail("Pacman is bewogen terwijl er een muur stond boven.");
        }

        //speler drukt op pijltje naar onder:
        instance.beweeg(Directie.omlaag);
        if (instance.getVak() != omlaag) {
            fail("Pacman is niet bewogen terwijl er geen muur stond onder.");
        }

    }

    @Test
    public void testBeweeg7() {
        System.out.println("Fysiek testgeval 1(testmaat 2)");
        Pacman instance = new Pacman(vakgrootte, midden, null, null);

        //speler drukt op pauze:
        instance.switchPauze();

        //speler drukt op pijltje naar onder:        
        instance.beweeg(Directie.omlaag);
        if (instance.getVak() != midden) {
            fail("Pacman is bewogen terwijl het spel op pauze stond.");
        }

        //speler drukt weer op pauze:
        instance.switchPauze();

        //speler drukt op pijltje naar boven:
        instance.beweeg(Directie.omhoog);
        if (instance.getVak() != midden) {
            fail("Pacman is bewogen terwijl er een muur stond boven.");
        }

        //speler drukt op pijltje naar rechts:
        instance.beweeg(Directie.rechts);
        if (instance.getVak() != rechts) {
            fail("Pacman is niet bewogen terwijl er geen muur stond rechts en het spel niet op pauze stond.");
        }

    }

    @Test
    public void testBeweeg8() {
        System.out.println("Fysiek testgeval 2(testmaat 2)");
        Pacman instance = new Pacman(vakgrootte, midden, null, null);

        instance.beweeg(Directie.rechts);
        if (instance.getVak() != rechts) {
            fail("Pacman is niet bewogen terwijl er geen muur staat rechts.");
        }

    }

    @Test
    public void testBeweeg9() {
        System.out.println("Fysiek testgeval 3(testmaat 2)");
        Pacman instance = new Pacman(vakgrootte, midden, null, null);

        //speler drukt op pauze:
        instance.switchPauze();

        //speler drukt op pijltje naar onder:        
        instance.beweeg(Directie.rechts);
        if (instance.getVak() != midden) {
            fail("Pacman is bewogen terwijl het spel op pauze stond.");
        }

        //speler drukt op pijltje naar onder:        
        instance.beweeg(Directie.rechts);
        if (instance.getVak() != midden) {
            fail("Pacman is bewogen terwijl het spel op pauze stond.");
        }

        //speler drukt weer op pauze:
        instance.switchPauze();

        //speler drukt op pijltje naar boven:
        instance.beweeg(Directie.omhoog);
        if (instance.getVak() != midden) {
            fail("Pacman is bewogen terwijl er een muur stond boven.");
        }

        //speler drukt weer op pauze:
        instance.switchPauze();

        //speler drukt op pijltje naar rechts:
        instance.beweeg(Directie.rechts);
        if (instance.getVak() != midden) {
            fail("Pacman is bewogen terwijl het spel op pauze stond.");
        }

        //speler drukt weer op pauze:
        instance.switchPauze();

        //speler drukt op pijltje naar rechts:
        instance.beweeg(Directie.rechts);
        if (instance.getVak() != rechts) {
            fail("Pacman is niet bewogen terwijl er geen muur stond rechts en het spel niet op pauze stond.");
        }

    }

}
