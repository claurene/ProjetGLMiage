package miage.model;

import miage.controller.LigneController;
import miage.controller.StationController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

@DisplayName("Itinéraire")
public class ItineraireTest {
    private final LigneController ligneController = new LigneController();
    private final StationController stationController = new StationController();

    @Test
    @DisplayName("Itinéraire rapide avec le jeu de données")
    void itineraireRapide(){
        ligneController.initialisationLignes();
        stationController.initialisationStations();

        HashMap<String,Station> listeStations = stationController.getStations();

        Graphe g = new Graphe(ligneController.getLignes(),listeStations);
        Itineraire i = new Itineraire(g,listeStations.get("temple"),listeStations.get("bastille"));

        i.constItineraireRapide();

        assertAll(
                () -> assertEquals(i.getTotalTempsParcours(),34)
        );
    }
}
