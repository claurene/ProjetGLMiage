package miage.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

//TODO: assertions

@DisplayName("Itinéraire Controller")
public class ItineraireControllerTest {
    private final ItineraireController itineraireController = new ItineraireController();
    private final LigneController ligneController = new LigneController();

    @Test
    @DisplayName("Affichage d'un itinéraire rapide")
    void afficherItineraireRapide(){
        ligneController.initialisationLignes();

        String response = itineraireController.itinerairePlusRapide(ligneController.getLignes(),"temple","bastille");

        System.out.println(response);
    }

    @Test
    @DisplayName("Itinéraire rapide de stations inexistantes")
    void itineraireStationInexistante(){
        ligneController.initialisationLignes();

        String response1 = itineraireController.itinerairePlusRapide(ligneController.getLignes(),"templee","bastille");
        String response2 = itineraireController.itinerairePlusRapide(ligneController.getLignes(),"temple","bastillee");

        assertAll(
                () -> assertEquals(response1,"Aucun chemin possible avec les paramètres renseignés"),
                () -> assertEquals(response2,"Aucun chemin possible avec les paramètres renseignés")
        );
    }

    @Test
    @DisplayName("Affichage itinéraire avec changements définis")
    void itineraireChangements(){
        ligneController.initialisationLignes();

        ArrayList<String> listeStations = new ArrayList<String>();
        listeStations.add("temple");
        listeStations.add("avron");
        listeStations.add("bastille");

        String response = itineraireController.itineraireAvecChangements(ligneController.getLignes(),listeStations);

        System.out.println(response);
    }

}
