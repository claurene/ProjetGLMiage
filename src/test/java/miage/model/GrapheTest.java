package miage.model;

import miage.controller.LigneController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        Graphe g = new Graphe(listeLignes,listeStations);

        assertAll(
                () -> assertTrue(g.getNomLigne("gare du nord","gare de l'est")=="Metro 5"),
                () -> assertTrue(g.getNomLigne("gare de l'est","gare du nord")=="Metro 5"),
                () -> assertTrue(g.getPoids("gare du nord","gare de l'est")==13),
                () -> assertTrue(g.getPoids("gare de l'est","gare du nord")==12),
                () -> assertTrue(g.getPoids("gare de l'est","gare du sud")==12),
                () -> assertTrue(g.getPoids("gare du nord","gare du sud")==-1),
                () -> assertTrue(g.getNomLigne("gare du nord","gare de l'est")=="Metro 5"),
                () -> assertTrue(g.getNombreSommets()==3),
                () -> assertTrue(g.getNombreArcs()==2)
        );
    }

    @Test
    @DisplayName("Création d'un graphe vide")
    void creationGrapheVide(){
        HashMap<String,Ligne> listeLignes = new HashMap<String,Ligne>();
        HashMap<String,Station> listeStations = new HashMap<>();
        Graphe g = new Graphe(listeLignes,listeStations);
        assertAll(
                () -> assertTrue(g.getNombreSommets()==0),
                () -> assertTrue(g.getNombreArcs()==0)
        );
    }

}
