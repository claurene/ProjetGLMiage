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

//TODO: assertions

@DisplayName("Itinéraire Controller")
public class ItineraireControllerTest {
    private final ItineraireController itineraireController = new ItineraireController();
    private final LigneController ligneController = new LigneController();
    private final StationController stationController = new StationController();

    @Test
    @DisplayName("Affichage d'un itinéraire rapide")
    void afficherItineraireRapide(){
        ligneController.initialisationLignes();
        stationController.initialisationStations();

        HashMap<String,Station> listeStations = stationController.getStations();

        String response = itineraireController.itinerairePlusRapide(ligneController.getLignes(),listeStations,listeStations.get("temple"),listeStations.get("bastille"));

        assertAll(
                () -> assertTrue(response.contains("Descendre à bastille")),
                () -> assertFalse(response.contains("Aucun chemin possible avec les paramètres renseignés"))
        );
    }

    @Test
    @DisplayName("Affichage itinéraire avec changements définis")
    void itineraireChangementsSimple(){
        ligneController.initialisationLignes();
        stationController.initialisationStations();

        ArrayList<Station> listeStations = new ArrayList<Station>();
        listeStations.add(new Station("gambetta",2,false,48.79,2.12));
        listeStations.add(new Station("père-lachaise",2,false,48.79,2.12));
        listeStations.add(new Station("menilmontant",2,false,48.79,2.12));

        String response = itineraireController.itineraireAvecChangements(ligneController.getLignes(),stationController.getStations(),listeStations);
        assertAll(
                () -> assertTrue(response.contains("Descendre à père-lachaise")),
                () -> assertTrue(response.contains("Descendre à menilmontant")),
                () -> assertFalse(response.contains("Aucun chemin possible avec les paramètres renseignés"))
        );
    }

    @Test
    @DisplayName("Itinéraire avec le moins de changements de ligne")
    void itineraireMoinsChangements(){
        ligneController.initialisationLignes();
        stationController.initialisationStations();

        String response = itineraireController.itineraireMoinsChangements(ligneController.getLignes(),"nation","temple");

        assertAll(
                () -> assertTrue(response.contains("Prendre la ligne 2 direction \"porte dauphine\"")),
                () -> assertTrue(response.contains("À père-lachaise prendre la ligne 3 direction \"pont de levallois-becon\"")),
                () -> assertFalse(response.contains("Aucun chemin possible avec les paramètres renseignés"))
        );
    }

    @Test
    @DisplayName("Itinéraire avec le moins de changements sur la même ligne")
    void itineraireMoinsChangementsMemeLigne(){
        ligneController.initialisationLignes();
        stationController.initialisationStations();

        String response = itineraireController.itineraireMoinsChangements(ligneController.getLignes(),"nation","bastille");

        assertEquals(response,"Prendre la ligne 1 de nation jusqu'à bastille");
    }


    @Test
    @DisplayName("Aucun itinéraire avec le moins de changements de ligne")
    void aucunItineraireMoinsChangements(){
        ligneController.initialisationLignes();
        stationController.initialisationStations();

        String response = itineraireController.itineraireMoinsChangements(ligneController.getLignes(),"nation","templee");

        assertEquals(response,"Aucun chemin possible avec les paramètres renseignés");
    }

    //TODO: ajouter des tests sur l'itinéraire avec moins de changements


}
