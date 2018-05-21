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
        LigneController.initialisationLignes();
        StationController.initialisationStations();

        HashMap<String,Station> listeStations = StationController.getStations();

        GrapheStation g = new GrapheStation(LigneController.getLignes(),listeStations);
        ItineraireStation i = new ItineraireStation(g,listeStations.get("temple"),listeStations.get("bastille"));


        assertAll(
                () -> assertEquals(i.getTotalTempsParcours(),34)
        );
    }
}
