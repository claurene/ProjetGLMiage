package miage.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Ligne")
public class LigneTest {
    @Test
    @DisplayName("Initialisation d'une ligne sans incident")
    void CreationLigneSansIncident(){
        ArrayList<Station> listeStation = new ArrayList<Station>();
        listeStation.add(new Station("Gare du nord",2,false,48.79,2.12));
        listeStation.add(new Station("Gare de l'est",2,false,48.79,2.12));
        Ligne l = new Ligne(71,"Metro 5",50,listeStation);
        assertFalse(l.isIncident());
    }

    @Test
    @DisplayName("Initialisation d'une ligne avec incident")
    void CreationLigneAvecIncident(){
        ArrayList<Station> listeStation = new ArrayList<Station>();
        listeStation.add(new Station("Gare du nord",2,true,48.79,2.12));
        listeStation.add(new Station("Gare de l'est",2,false,48.79,2.12));
        Ligne l = new Ligne(71,"Metro 5",50,listeStation);
        assertTrue( l.isIncident());
    }

    @Test
    @DisplayName("Changement du temps de parcours d'une ligne en 60 minutes")
    void LigneChangementTempsParcours(){
        ArrayList<Station> listeStation = new ArrayList<Station>();
        listeStation.add(new Station("Gare du nord",2,false,48.79,2.12));
        listeStation.add(new Station("Gare de l'est",2,false,48.79,2.12));
        Ligne l = new Ligne(71,"Metro 5",50,listeStation);
        l.setTempsParcours(60);
        assertEquals(l.getTempsParcours(),60, "Le temps devrait être 60.");
    }

    @Test
    @DisplayName("Recherche station existante")
    void LigneTrouverStationExistante(){
        ArrayList<Station> listeStation = new ArrayList<Station>();
        listeStation.add(new Station("Gare du nord",2,false,48.79,2.12));
        listeStation.add(new Station("Gare de l'est",2,false,48.79,2.12));
        Ligne l = new Ligne(71,"Metro 5",50,listeStation);
        assertTrue(l.trouverStation("Gare de l'est"));
    }

    @Test
    @DisplayName("Recherche station de départ de la ligne")
    void trouverStationDepart(){
        ArrayList<Station> listeStation = new ArrayList<Station>();
        listeStation.add(new Station("Gare du nord",2,false,48.79,2.12));
        listeStation.add(new Station("Gare de l'est",2,false,49.82,2.2));
        listeStation.add(new Station("Jacques-Bonsergent",2,false,50.19,2.4));
        listeStation.add(new Station("Republique",2,false,51.19,2.6));
        Ligne l = new Ligne(71,"Metro 5",50,listeStation);
        assertEquals(l.getDepart(),listeStation.get(0), "La station finale doit etre Gare du nord.");
    }

    @Test
    @DisplayName("Recherche station finale (terminus) de la ligne")
    void trouverStationTerminus(){
        ArrayList<Station> listeStation = new ArrayList<Station>();
        listeStation.add(new Station("Gare du nord",2,false,48.79,2.12));
        listeStation.add(new Station("Gare de l'est",2,false,49.82,2.2));
        listeStation.add(new Station("Jacques-Bonsergent",2,false,50.19,2.4));
        listeStation.add(new Station("Republique",2,false,51.19,2.6));
        Ligne l = new Ligne(71,"Metro 5",50,listeStation);
        assertEquals(l.getTerminus(),listeStation.get(listeStation.size()-1), "La station finale doit etre Republique.");
    }

}
