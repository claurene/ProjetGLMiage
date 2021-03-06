package miage.controller;

import miage.controller.HoraireController;
import miage.model.Ligne;
import miage.model.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class HoraireControllerTest {
    private final HoraireController horaireController = new HoraireController();

    @Test
    @DisplayName("Afficher le prochain passage d'une rame")
    void afficherProchainPassage(){
        LocalDateTime heureTest = LocalDateTime.of(LocalDate.of(2018,05,21), LocalTime.of(6,0));
        ArrayList<Station> listeStation = new ArrayList<Station>();
        ArrayList<Integer> tempsParcours = new ArrayList<>();
        tempsParcours.add(10);
        tempsParcours.add(5);
        tempsParcours.add(7);
        Station s = new Station("Gare du nord",2,false,48.79,2.12);
        listeStation.add(new Station("Gare du nord",2,false,48.79,2.12));
        listeStation.add(new Station("Gare de l'est",2,false,49.82,2.2));
        listeStation.add(new Station("Jacques-Bonsergent",2,false,50.19,2.4));
        listeStation.add(new Station("Republique",2,false,51.19,2.6));
        Ligne l = new Ligne("Metro 5",tempsParcours,listeStation);

        String reponse = horaireController.afficherProchainPassage(s,l,heureTest);
        assertEquals(reponse,"Le prochain passage d'une rame à l'arrêt : Gare du nord de la ligne Metro 5 direction : Republique se fera le 2018-05-21 à 06:06");
    }

    @Test
    @DisplayName("Afficher les horaires d'une rame")
    void afficherHoraires(){
        LocalDateTime heureTest = LocalDateTime.of(LocalDate.of(2018,05,21), LocalTime.of(6,0));
        ArrayList<Station> listeStation = new ArrayList<Station>();
        ArrayList<Integer> tempsParcours = new ArrayList<>();
        tempsParcours.add(10);
        tempsParcours.add(5);
        tempsParcours.add(7);
        Station s = new Station("Gare du nord",2,false,48.79,2.12);
        listeStation.add(new Station("Gare du nord",2,false,48.79,2.12));
        listeStation.add(new Station("Gare de l'est",2,false,49.82,2.2));
        listeStation.add(new Station("Jacques-Bonsergent",2,false,50.19,2.4));
        listeStation.add(new Station("Republique",2,false,51.19,2.6));
        Ligne l = new Ligne("Metro 5",tempsParcours,listeStation);

        String reponse = horaireController.afficherTableHoraire(s,l,heureTest,6);
        assertAll(
                () -> assertTrue(reponse.contains("Horaires pour la ligne Metro 5 direction Republique à l'arrêt Gare du nord :")),
                () -> assertTrue(reponse.contains("- 2018-05-21 06:06")),
                () -> assertTrue(reponse.contains("- 2018-05-21 06:36")),
                () -> assertFalse(reponse.contains("La ligne Metro 5 ne passe pas à l'arret Gare du nord"))
        );
    }

    @Test
    @DisplayName("Rame ne passant pas par l'arret")
    void horaireRameErronee(){
        LocalDateTime heureTest = LocalDateTime.of(LocalDate.now(), LocalTime.of(6,0));
        ArrayList<Station> listeStation = new ArrayList<Station>();
        ArrayList<Integer> tempsParcours = new ArrayList<>();
        tempsParcours.add(10);
        tempsParcours.add(5);
        tempsParcours.add(7);
        Station s = new Station("Gare du nord",2,false,48.79,2.12);
        listeStation.add(new Station("Gare de l'est",2,false,49.82,2.2));
        listeStation.add(new Station("Jacques-Bonsergent",2,false,50.19,2.4));
        listeStation.add(new Station("Republique",2,false,51.19,2.6));
        Ligne l = new Ligne("Metro 5",tempsParcours,listeStation);


        assertAll(
                () -> assertEquals(horaireController.afficherProchainPassage(s,l,heureTest),"La ligne Metro 5 ne passe pas à l'arret Gare du nord")
        );

    }
}
