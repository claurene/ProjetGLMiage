package miage.controller;

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
        System.out.println(response);
    }

    @Test
    @DisplayName("Affichage itinéraire avec changements définis")
    void itineraireChangementsSimple(){
        ligneController.initialisationLignes();
        stationController.initialisationStations();

        ArrayList<Station> listeStations = new ArrayList<Station>();
        listeStations.add(new Station("château de vincennes",2,false,48.79,2.12));
        listeStations.add(new Station("bastille",2,false,48.79,2.12));
        listeStations.add(new Station("château de vincennes",2,false,48.79,2.12));

        String response = itineraireController.itineraireAvecChangements(ligneController.getLignes(),stationController.getStations(),listeStations);
        assertAll(
                () -> assertTrue(response.contains("Descendre à bastille")),
                () -> assertTrue(response.contains("Descendre à château de vincennes")),
                () -> assertFalse(response.contains("Aucun chemin possible avec les paramètres renseignés"))
        );

        System.out.println(response);
    }

}
