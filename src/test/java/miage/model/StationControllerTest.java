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
    private final StationController stationController = new StationController();

    @Test
    @DisplayName("2 Plus proches stations différentes")
    void DeuxPlusProchesNotEquals(){
        stationController.initialisationStations();
        Position utilisateur = new Position();
        List<Station> plusProches = StationController.deuxplusProches(utilisateur,stationController.getStations());
        assertNotEquals(plusProches.get(0),plusProches.get(1));
    }

    @Test
    @DisplayName("2 Plus proches stations pas d'incidents")
    void DeuxPlusProchesPasIncident(){
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
        stationController.initialisationStations();
        int taille = stationController.getStations().size();
        String reponse = stationController.ajouterStation("Gare de l'est",2,false,48.79,2.12);
        assertAll(
                () -> assertTrue(stationController.getStations().size()==taille+1),
                () -> assertTrue(reponse.equals("La station a bien été ajoutée"))
        );
    }

    @Test
    @DisplayName("Ajout d'une station déjà existante dans la HashMap")
    void AjoutStationHashMapExistante(){
        stationController.initialisationStations();
        int taille = stationController.getStations().size();
        String reponse = stationController.ajouterStation("gare du nord",2,false,48.79,2.12);
        assertAll(
                () -> assertTrue(stationController.getStations().size()==taille),
                () -> assertTrue(reponse.equals("La station existe déjà"))
        );
    }

    @Test
    @DisplayName("Ajout d'une station fausse dans la HashMap")
    void AjoutStationHashMapFausse(){
        stationController.initialisationStations();
        int taille = stationController.getStations().size();
        String reponse = stationController.ajouterStation("Gare de l'est",2,false,60.79,2.12);
        assertAll(
                () -> assertTrue(stationController.getStations().size()==taille),
                () -> assertTrue(reponse.equals("La station n'a pas été ajoutée pour cause d'erreur de position"))
        );
    }

    @Test
    @DisplayName("Modification d'une station dans la HashMap")
    void ModifierStationHashMap(){
        stationController.initialisationStations();
        int taille = stationController.getStations().size();
        String reponse = stationController.modifierStation("gare du nord",2,false,48.79,2.12);
        assertAll(
                () -> assertTrue(stationController.getStations().size()==taille),
                () -> assertTrue(reponse.equals("La station a été remplacée"))
        );
    }

    @Test
    @DisplayName("Modification d'une station dans la HashMap pour la rendre fausse")
    void ModifierStationHashMapFausse(){
        stationController.initialisationStations();
        int taille = stationController.getStations().size();
        String reponse = stationController.modifierStation("gare du nord",2,false,78.79,2.12);
        assertAll(
                () -> assertTrue(stationController.getStations().size()==taille),
                () -> assertTrue(reponse.equals("La station n'a pas été modifiée pour cause d'erreur de position"))
        );
    }

    @Test
    @DisplayName("Modification d'une station dans la HashMap qui n'existe pas")
    void ModifierStationHashMapInexistante(){
        stationController.initialisationStations();
        int taille = stationController.getStations().size();
        String reponse = stationController.modifierStation("gare inexistante",2,false,48.79,2.12);
        assertAll(
                () -> assertTrue(stationController.getStations().size()==taille),
                () -> assertTrue(reponse.equals("La station que vous souhaitez modifier n'existe pas"))
        );
    }

    @Test
    @DisplayName("Suppression d'une station de la HashMap")
    void SuppressionStationHashMap(){
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
        stationController.initialisationStations();
        int taille = stationController.getStations().size();
        String reponse = stationController.supprimerStation("gare fantooooooome");
        assertAll(
                () -> assertTrue(stationController.getStations().size()==taille),
                () -> assertTrue(reponse.equals("La station n'a pas pu être supprimée car elle n'existe pas"))
        );
    }

    @Test
    @DisplayName("Modification de l'incident d'une station en True")
    void ModifierStationIncidentHashMapTrue(){
        stationController.initialisationStations();
        String reponse = stationController.modifierStationIncident("gare du nord",true);
        assertAll(
                () -> assertTrue(stationController.getStations().get("gare du nord").isIncident()==true),
                () -> assertTrue(reponse.equals("La station possède maintenant un incident"))
        );
    }

    @Test
    @DisplayName("Modification de l'incident d'une station en False")
    void ModifierStationIncidentHashMapFalse(){
        stationController.initialisationStations();
        String reponse = stationController.modifierStationIncident("gare du nord",false);
        assertAll(
                () -> assertTrue(stationController.getStations().get("gare du nord").isIncident()==false),
                () -> assertTrue(reponse.equals("La station ne possède maintenant plus d'incident"))
        );
    }

    @Test
    @DisplayName("Modification de l'incident d'une station en False")
    void ModifierStationIncidentHashMapInexistante(){
        stationController.initialisationStations();
        String reponse = stationController.modifierStationIncident("gare inexistante",false);
        assertTrue(reponse.equals("La station que vous souhaitez modifiée n'existe pas"));
    }

}