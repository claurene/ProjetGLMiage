package miage.model;

import miage.controller.LigneController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//TODO: assertions

@DisplayName("Itinéraire")
public class ItineraireTest {
    private final LigneController ligneController = new LigneController();

    @Test
    @DisplayName("Itinéraire rapide avec le jeu de données")
    void itineraireRapide(){
        ligneController.initialisationLignes();

        Graphe g = new Graphe(ligneController.getLignes());
        Itineraire i = new Itineraire(g,"temple","bastille");

        System.out.println(i.constItineraireRapide().toString());
    }
}
