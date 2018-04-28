package miage.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Horaire")
public class HoraireTest {

    @Test
    @DisplayName("Changement de direction")
    void changementDirection(){
        LocalDateTime heureTest = LocalDateTime.now();
        ArrayList<Integer> tempsParcours = new ArrayList<>();
        tempsParcours.add(10);
        Station s = new Station("Gare de l'est",2,false,48.79,2.12);
        ArrayList<Station> listeStation = new ArrayList<Station>();
        listeStation.add(new Station("Gare du nord",2,false,48.79,2.12));
        listeStation.add(new Station("Gare de l'est",2,false,48.79,2.12));
        Ligne l = new Ligne("Metro 5",tempsParcours,listeStation);
        Horaire h = new Horaire(s,l,"Nord-Est","En service",heureTest);

        h.setDirection("Est-Nord");

        System.out.println(h.toString());
        assertEquals(h.getDirection(),"Est-Nord","La direction devrait être Est-Nord");
    }


    @Test
    @DisplayName("Changement de statut")
    void changementStatut(){
        LocalDateTime heureTest = LocalDateTime.now();
        ArrayList<Integer> tempsParcours = new ArrayList<>();
        tempsParcours.add(10);
        Station s = new Station("Gare de l'est",2,false,48.79,2.12);
        ArrayList<Station> listeStation = new ArrayList<Station>();
        listeStation.add(new Station("Gare du nord",2,false,48.79,2.12));
        listeStation.add(new Station("Gare de l'est",2,false,48.79,2.12));
        Ligne l = new Ligne("Metro 5",tempsParcours,listeStation);
        Horaire h = new Horaire(s,l,"Nord-Est","En service",heureTest);

        h.setStatut("Annulé");

        assertEquals(h.getStatut(),"Annulé","Le statut devrait être Annulé");
    }
}
