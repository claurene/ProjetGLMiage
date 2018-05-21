package miage.controller;

import miage.model.Ligne;
import miage.model.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


@DisplayName("Itinéraire Controller")
public class ItineraireControllerTest {
    private final ItineraireController itineraireController = new ItineraireController();

    @Test
    @DisplayName("Affichage d'un itinéraire rapide")
    void afficherItineraireRapide(){
        LigneController.initialisationLignes();
        StationController.initialisationStations();

        HashMap<String,Station> listeStations = StationController.getStations();

        String response = itineraireController.itinerairePlusRapide(LigneController.getLignes(),listeStations,listeStations.get("temple"),listeStations.get("bastille"));

        assertAll(
                () -> assertTrue(response.contains("Descendre à bastille")),
                () -> assertFalse(response.contains("Aucun chemin possible avec les paramètres renseignés"))
        );
    }

    @Test
    @DisplayName("Affichage itinéraire avec changements définis")
    void itineraireChangementsSimple(){
        LigneController.initialisationLignes();
        StationController.initialisationStations();

        ArrayList<Station> listeStations = new ArrayList<Station>();
        listeStations.add(new Station("gambetta",2,false,48.79,2.12));
        listeStations.add(new Station("père-lachaise",2,false,48.79,2.12));
        listeStations.add(new Station("menilmontant",2,false,48.79,2.12));

        String response = itineraireController.itineraireAvecChangements(LigneController.getLignes(), StationController.getStations(),listeStations);
        assertAll(
                () -> assertTrue(response.contains("Descendre à père-lachaise")),
                () -> assertTrue(response.contains("Descendre à menilmontant")),
                () -> assertFalse(response.contains("Aucun chemin possible avec les paramètres renseignés"))
        );
    }

    @Test
    @DisplayName("Itinéraire avec le moins de changements de ligne")
    void itineraireMoinsChangements(){
        LigneController.initialisationLignes();
        StationController.initialisationStations();

        String response = itineraireController.itineraireMoinsChangements(LigneController.getLignes(),"nation","temple");

        assertAll(
                () -> assertTrue(response.contains("Prendre la ligne 2 direction \"porte dauphine\"")),
                () -> assertTrue(response.contains("À père-lachaise prendre la ligne 3 direction \"pont de levallois-becon\"")),
                () -> assertFalse(response.contains("Aucun chemin possible avec les paramètres renseignés"))
        );
    }

    @Test
    @DisplayName("Itinéraire avec le moins de changements sur la même ligne")
    void itineraireMoinsChangementsMemeLigne(){
        LigneController.initialisationLignes();
        StationController.initialisationStations();

        String response = itineraireController.itineraireMoinsChangements(LigneController.getLignes(),"nation","bastille");

        assertEquals(response,"Prendre la ligne 1 de nation jusqu'à bastille");
    }


    @Test
    @DisplayName("Aucun itinéraire avec le moins de changements de ligne")
    void aucunItineraireMoinsChangements(){
        LigneController.initialisationLignes();
        StationController.initialisationStations();

        String response = itineraireController.itineraireMoinsChangements(LigneController.getLignes(),"nation","templee");

        assertEquals(response,"Aucun chemin possible avec les paramètres renseignés");
    }

}
