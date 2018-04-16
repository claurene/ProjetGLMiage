package miage.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Position")
public class PositionTest {

    @Test
    @DisplayName("Initialisation d'un position aléatoire")
    void PosititionAleatoireNotNull(){
        // Initialisation d'une position aléatoire
        Position p = new Position();
        System.out.println(p.toString());
        assertTrue( p != null);
    }

    @Test
    @DisplayName("Initialisation d'un position aléatoire qui doit etre entre la longitude et la latitude définie")
    void PositionAleatoireIntervalle(){
        Position p = new Position();
        System.out.println(p.toString());
        assertAll(
                () -> assertTrue(p.getLat() <= p.getLATITUDE_MAX()),
                () -> assertTrue(p.getLat() >= p.getLATITUDE_MIN()),
                () -> assertTrue(p.getLon() <= p.getLONGITUDE_MAX()),
                () -> assertTrue(p.getLon() >= p.getLONGITUDE_MIN()),
                () -> assertTrue(p.VerifierLatitude(p.getLat())),
                () -> assertTrue(p.VerifierLongitude(p.getLon())),
                () -> assertTrue(p.VerifierPosition(p.getLat(),p.getLon()))

        );
    }

    @Test
    @DisplayName("Initialisation d'un position par l'utilisateur")
    void PosititionChoisieNotNull(){
        // Initialisation d'une position aléatoire
        Position p = new Position(48.8, 2.12);
        System.out.println(p.toString());
        assertTrue( p != null);
    }

    @Test
    @DisplayName("Initialisation d'un position par l'utilisateur de façon correcte")
    void PositionChoisieIntervalle(){
        Position p = new Position(48.8, 2.12);
        System.out.println(p.toString());
        assertAll(
                () -> assertTrue(p.getLat() <= p.getLATITUDE_MAX()),
                () -> assertTrue(p.getLat() >= p.getLATITUDE_MIN()),
                () -> assertTrue(p.getLon() <= p.getLONGITUDE_MAX()),
                () -> assertTrue(p.getLon() >= p.getLONGITUDE_MIN()),
                () -> assertTrue(p.VerifierLatitude(p.getLat())),
                () -> assertTrue(p.VerifierLongitude(p.getLon())),
                () -> assertTrue(p.VerifierPosition(p.getLat(),p.getLon()))
        );
    }

    @Test
    @DisplayName("Initialisation d'un position par l'utilisateur de façon erronnée")
    void PositionChoisiePasIntervalle(){
        Position p = new Position(60, 2.12);
        System.out.println(p.toString());
        assertAll(
                () -> assertEquals(p.getLat(),0,"Latitude = 0" ),
                () -> assertEquals(p.getLon(), 0, "Longitude = 0")
        );
    }

    @Test
    @DisplayName("Initialisation d'un position par l'utilisateur de façon erronnée et negative")
    void PositionChoisieNegative(){
        Position p = new Position(-48.8, -2.12);
        System.out.println(p.toString());
        assertAll(
                () -> assertEquals(p.getLat(),0,"Latitude = 0" ),
                () -> assertEquals(p.getLon(), 0, "Longitude = 0")
        );
    }

    @Test
    @DisplayName("Initialisation d'un position par l'utilisateur puis modification latitude")
    void PositionChoisieModifierLatitude(){
        Position p = new Position(48.8, 2.12);
        p.setLat(60);
        System.out.println(p.toString());
        assertAll(
                () -> assertTrue(p.getLat() <= p.getLATITUDE_MAX()),
                () -> assertTrue(p.getLat() >= p.getLATITUDE_MIN()),
                () -> assertTrue(p.VerifierLatitude(p.getLat())),
                () -> assertTrue(p.VerifierPosition(p.getLat(),p.getLon()))
        );
    }

    @Test
    @DisplayName("Initialisation d'un position par l'utilisateur puis modification longitude")
    void PositionChoisieModifierLongitude(){
        Position p = new Position(48.8, 2.12);
        p.setLon(8.24);
        System.out.println(p.toString());
        assertAll(
                () -> assertTrue(p.getLon() <= p.getLONGITUDE_MAX()),
                () -> assertTrue(p.getLon() >= p.getLONGITUDE_MIN()),
                () -> assertTrue(p.VerifierLongitude(p.getLon())),
                () -> assertTrue(p.VerifierPosition(p.getLat(),p.getLon()))
        );
    }

    @Test
    @DisplayName("Initialisation d'un position par l'utilisateur puis modification latitude negative")
    void PositionChoisieModifierLatitudeNegative(){
        Position p = new Position(48.8, 2.12);
        p.setLat(-48.8);
        System.out.println(p.toString());
        assertAll(
                () -> assertTrue(p.getLat() <= p.getLATITUDE_MAX()),
                () -> assertTrue(p.getLat() >= p.getLATITUDE_MIN()),
                () -> assertTrue(p.VerifierLatitude(p.getLat())),
                () -> assertTrue(p.VerifierPosition(p.getLat(),p.getLon()))
        );
    }

    @Test
    @DisplayName("Initialisation d'un position par l'utilisateur puis modification longitude negative")
    void PositionChoisieModifierLongitudeNegative(){
        Position p = new Position(48.8, 2.12);
        p.setLon(-2.12);
        System.out.println(p.toString());
        assertAll(
                () -> assertTrue(p.getLon() <= p.getLONGITUDE_MAX()),
                () -> assertTrue(p.getLon() >= p.getLONGITUDE_MIN()),
                () -> assertTrue(p.VerifierLongitude(p.getLon())),
                () -> assertTrue(p.VerifierPosition(p.getLat(),p.getLon()))
        );
    }








}