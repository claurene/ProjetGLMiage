package miage.model;

import miage.controller.LigneController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

//TODO: assertions

@DisplayName("Graphe")
public class GrapheTest {
    private final LigneController ligneController = new LigneController();

    @Test
    @DisplayName("Création d'un graphe simple")
    void creationGrapheSimple(){
        ArrayList<Integer> tempsParcours = new ArrayList<>();
        tempsParcours.add(10);
        ArrayList<Station> listeStation = new ArrayList<Station>();
        listeStation.add(new Station("Gare du nord",2,false,48.79,2.12));
        listeStation.add(new Station("Gare de l'est",2,false,48.79,2.12));
        Ligne l = new Ligne("Metro 5",tempsParcours,listeStation);

        HashMap<String,Ligne> listeLignes = new HashMap<String,Ligne>();
        listeLignes.put(l.getNomLigne(),l);

        Graphe g = new Graphe(listeLignes);
        Itineraire i = new Itineraire(g,"Gare du nord","Gare de l'est");

        System.out.println(i.constItineraireRapide());
    }

    @Test
    @DisplayName("Création du graphe du jeu de données")
    void creationGrapheDonnees(){
        ligneController.initialisationLignes();

        Graphe g = new Graphe(ligneController.getLignes());

        System.out.println(g.toString());
    }
}
