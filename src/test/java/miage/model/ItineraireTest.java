package miage.model;

import miage.controller.LigneController;
import miage.controller.StationController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//TODO: assertions

@DisplayName("Itinéraire")
public class ItineraireTest {
    private final LigneController ligneController = new LigneController();
    private final StationController stationController = new StationController();

    @Test
    @DisplayName("Itinéraire rapide avec le jeu de données")
    void itineraireRapide(){
        ligneController.initialisationLignes();
        stationController.initialisationStations();

        Graphe g = new Graphe(ligneController.getLignes());
        Itineraire i = new Itineraire(g,"temple","bastille");

        i.constItineraireRapide();
    }
}
