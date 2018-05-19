package miage.model;

import miage.controller.LigneController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

//TODO: assertions

@DisplayName("Graphe")
public class GrapheTest {
    private final LigneController ligneController = new LigneController();

    @Test
    @DisplayName("Création d'un graphe simple")
    void creationGrapheSimple(){
        ArrayList<Integer> tempsParcours = new ArrayList<>();
        tempsParcours.add(10);
        tempsParcours.add(10);
        ArrayList<Station> listeStation = new ArrayList<Station>();
        listeStation.add(new Station("gare du nord",2,false,48.79,2.12));
        listeStation.add(new Station("gare de l'est",3,false,48.79,2.12));
        listeStation.add(new Station("gare du sud",2,false,48.79,2.12));
        Ligne l = new Ligne("Metro 5",tempsParcours,listeStation);

        HashMap<String,Ligne> listeLignes = new HashMap<String,Ligne>();
        HashMap<String,Station> listeStations = new HashMap<String,Station>();
        for (Station s : listeStation){
            listeStations.put(s.getNomStation(),s);
        }
        listeLignes.put(l.getNomLigne(),l);

        GrapheStation g = new GrapheStation(listeLignes,listeStations);

        assertAll(
                () -> assertSame("Metro 5", g.getNomLigne("gare du nord", "gare de l'est")),
                () -> assertSame("Metro 5", g.getNomLigne("gare de l'est", "gare du nord")),
                () -> assertEquals(13, g.getPoids("gare du nord", "gare de l'est")),
                () -> assertEquals(12, g.getPoids("gare de l'est", "gare du nord")),
                () -> assertEquals(12, g.getPoids("gare de l'est", "gare du sud")),
                () -> assertEquals(g.getPoids("gare du nord", "gare du sud"), -1),
                () -> assertSame("Metro 5", g.getNomLigne("gare du nord", "gare de l'est")),
                () -> assertEquals(3, g.getNombreSommets()),
                () -> assertEquals(2, g.getNombreArcs())
        );
    }

    @Test
    @DisplayName("Création d'un graphe vide")
    void creationGrapheVide(){
        HashMap<String,Ligne> listeLignes = new HashMap<String,Ligne>();
        HashMap<String,Station> listeStations = new HashMap<>();
        GrapheStation g = new GrapheStation(listeLignes,listeStations);
        assertAll(
                () -> assertEquals(0, g.getNombreSommets()),
                () -> assertEquals(0, g.getNombreArcs())
        );
    }

    @Test
    @DisplayName("Création d'un graphe simple de lignes")
    void creationGrapheLignes(){
        LigneController.initialisationLignes();

        GrapheLigne grapheLigne = new GrapheLigne(LigneController.getLignes());

        //TODO: assertions
    }

}
