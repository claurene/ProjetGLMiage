package miage.model;

import miage.controller.StationController;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;


@DisplayName("StationController")
public class StationControllerTest {
    @Test
    @DisplayName("2 Plus proches stations différentes")
    void DeuxPlusProchesNotEquals(){
        StationController stationController = new StationController();
        stationController.initialisationStations();
        Position utilisateur = new Position();
        List<Station> plusProches = StationController.deuxplusProches(utilisateur,stationController.getStations());
        assertNotEquals(plusProches.get(0),plusProches.get(1));
    }

    @Test
    @DisplayName("2 Plus proches stations pas d'incidents")
    void DeuxPlusProchesPasIncident(){
        StationController stationController = new StationController();
        stationController.initialisationStations();
        Position utilisateur = new Position();
        List<Station> plusProches = StationController.deuxplusProches(utilisateur,stationController.getStations());
        assertAll(
                () -> assertFalse(plusProches.get(0).isIncident()),
                () -> assertFalse(plusProches.get(1).isIncident())
        );
    }

    @Test
    @DisplayName("Ajout d'une station dans la HashMap")
    void AjoutStationHashMapJuste(){
        StationController stationController = new StationController();
        stationController.initialisationStations();
        int taille = stationController.getStations().size();
        String reponse = stationController.ajouterStation("Gare de l'est",2,false,48.79,2.12);
        assertAll(
                () -> assertTrue(stationController.getStations().size()==taille+1),
                () -> assertTrue(reponse.equals("La station a bien été ajoutée"))
        );
    }

    @Test
    @DisplayName("Ajout d'une station fausse dans la HashMap")
    void AjoutStationHashMapFausse(){
        StationController stationController = new StationController();
        stationController.initialisationStations();
        int taille = stationController.getStations().size();
        String reponse = stationController.ajouterStation("Gare de l'est",2,false,60.79,2.12);
        assertAll(
                () -> assertTrue(stationController.getStations().size()==taille),
                () -> assertTrue(reponse.equals("La station n'a pas été ajoutée pour cause d'erreur de position"))
        );
    }

    @Test
    @DisplayName("Ajout d'une station remplacée dans la HashMap")
    void AjoutStationHashMapRemplacement(){
        StationController stationController = new StationController();
        stationController.initialisationStations();
        int taille = stationController.getStations().size();
        String reponse = stationController.ajouterStation("gare du nord",2,false,48.79,2.12);
        assertAll(
                () -> assertTrue(stationController.getStations().size()==taille),
                () -> assertTrue(reponse.equals("La station a été remplacée"))
        );
    }

    @Test
    @DisplayName("Suppression d'une station de la HashMap")
    void SuppressionStationHashMap(){
        StationController stationController = new StationController();
        stationController.initialisationStations();
        int taille = stationController.getStations().size();
        String reponse = stationController.supprimerStation("gare du nord");
        assertAll(
                () -> assertTrue(stationController.getStations().size()==taille-1),
                () -> assertTrue(reponse.equals("La station a bien été supprimée"))
        );
    }

    @Test
    @DisplayName("Suppression d'une station inexistante de la HashMap")
    void SuppressionStationInexistanteHashMap(){
        StationController stationController = new StationController();
        stationController.initialisationStations();
        int taille = stationController.getStations().size();
        String reponse = stationController.supprimerStation("gare fantooooooome");
        assertAll(
                () -> assertTrue(stationController.getStations().size()==taille),
                () -> assertTrue(reponse.equals("La station n'a pas pu être supprimée car elle n'existe pas"))
        );
    }

}