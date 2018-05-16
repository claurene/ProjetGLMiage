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

        GrapheStation g = new GrapheStation(ligneController.getLignes(),listeStations);
        ItineraireStation i = new ItineraireStation(g,listeStations.get("temple"),listeStations.get("bastille"));

        System.out.println(i.constItineraireRapide());

        assertAll(
                () -> assertEquals(i.getTotalTempsParcours(),34)
        );
    }
}
