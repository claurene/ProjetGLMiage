package miage.controller;

import miage.controller.LigneController;
import miage.controller.StationController;

import miage.model.Position;
import miage.model.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;


@DisplayName("StationController")
public class StationControllerTest {
    private final StationController stationController = new StationController();
    private final LigneController ligneController = new LigneController();
    @Test
    @DisplayName("2 Plus proches stations différentes")
    void DeuxPlusProchesNotEquals(){
        StationController.initialisationStations();
        Position utilisateur = new Position();
        List<Station> plusProches = StationController.deuxplusProches(utilisateur);
        assertNotEquals(plusProches.get(0),plusProches.get(1));
    }

    @Test
    @DisplayName("2 Plus proches stations pas d'incidents")
    void DeuxPlusProchesPasIncident(){
        StationController.initialisationStations();
        Position utilisateur = new Position();
        List<Station> plusProches = StationController.deuxplusProches(utilisateur);
        assertAll(
                () -> assertFalse(plusProches.get(0).isIncident()),
                () -> assertFalse(plusProches.get(1).isIncident())
        );
    }

    @Test
    @DisplayName("Ajout d'une station dans la HashMap")
    void AjoutStationHashMapJuste(){
        StationController.initialisationStations();
        int taille = StationController.getStations().size();
        String reponse = StationController.ajouterStation("Gare de l'est",2,false,48.79,2.12);
        assertAll(
                () -> assertEquals(StationController.getStations().size(), taille + 1),
                () -> assertEquals("La station a bien été ajoutée", reponse)
        );
    }

    @Test
    @DisplayName("Ajout d'une station déjà existante dans la HashMap")
    void AjoutStationHashMapExistante(){
        StationController.initialisationStations();
        int taille = StationController.getStations().size();
        String reponse = StationController.ajouterStation("gare du nord",2,false,48.79,2.12);
        assertAll(
                () -> assertEquals(StationController.getStations().size(), taille),
                () -> assertEquals("La station existe déjà", reponse)
        );
    }

    @Test
    @DisplayName("Ajout d'une station fausse dans la HashMap")
    void AjoutStationHashMapFausse(){
        StationController.initialisationStations();
        int taille = StationController.getStations().size();
        String reponse = StationController.ajouterStation("Gare de l'est",2,false,60.79,2.12);
        assertAll(
                () -> assertEquals(StationController.getStations().size(), taille),
                () -> assertEquals("La station n'a pas été ajoutée pour cause d'erreur de position", reponse)
        );
    }

    @Test
    @DisplayName("Modification d'une station dans la HashMap")
    void ModifierStationHashMap(){
        StationController.initialisationStations();
        int taille = StationController.getStations().size();
        String reponse = StationController.modifierStation("gare du nord",2,false,48.79,2.12);
        assertAll(
                () -> assertEquals(StationController.getStations().size(), taille),
                () -> assertEquals("La station a été remplacée", reponse)
        );
    }

    @Test
    @DisplayName("Modification d'une station dans la HashMap pour la rendre fausse")
    void ModifierStationHashMapFausse(){
        StationController.initialisationStations();
        int taille = StationController.getStations().size();
        String reponse = StationController.modifierStation("gare du nord",2,false,78.79,2.12);
        assertAll(
                () -> assertEquals(StationController.getStations().size(), taille),
                () -> assertEquals("La station n'a pas été modifiée pour cause d'erreur de position", reponse)
        );
    }

    @Test
    @DisplayName("Modification d'une station dans la HashMap qui n'existe pas")
    void ModifierStationHashMapInexistante(){
        StationController.initialisationStations();
        int taille = StationController.getStations().size();
        String reponse = StationController.modifierStation("gare inexistante",2,false,48.79,2.12);
        assertAll(
                () -> assertEquals(StationController.getStations().size(), taille),
                () -> assertEquals("La station que vous souhaitez modifier n'existe pas", reponse)
        );
    }

    @Test
    @DisplayName("Suppression d'une station de la HashMap")
    void SuppressionStationHashMap(){
        StationController.initialisationStations();
        LigneController.initialisationLignes();
        int taille = StationController.getStations().size();
        int tailleligne = LigneController.getLignes().get("4").getListeStation().size();
        int tailleparcours = LigneController.getLignes().get("4").getListeTempsParcours().size();
        String reponse = StationController.supprimerStation("gare du nord");
        assertAll(
                () -> assertEquals(StationController.getStations().size(), taille - 1),
                () -> assertEquals(LigneController.getLignes().get("4").getListeStation().size(), tailleligne - 1),
                () -> assertEquals(LigneController.getLignes().get("4").getListeTempsParcours().size(), tailleparcours - 1),
                () -> assertEquals("La station a bien été supprimée", reponse)
        );
    }

    @Test
    @DisplayName("Suppression d'une station inexistante de la HashMap")
    void SuppressionStationInexistanteHashMap(){
        StationController.initialisationStations();
        LigneController.initialisationLignes();
        int taille = StationController.getStations().size();
        int tailleligne = LigneController.getLignes().get("4").getListeStation().size();
        int tailleparcours = LigneController.getLignes().get("4").getListeTempsParcours().size();
        String reponse = StationController.supprimerStation("gare fantooooooome");
        assertAll(
                () -> assertEquals(StationController.getStations().size(), taille),
                () -> assertEquals(LigneController.getLignes().get("4").getListeStation().size(), tailleligne),
                () -> assertEquals(LigneController.getLignes().get("4").getListeTempsParcours().size(), tailleparcours),
                () -> assertEquals("La station n'a pas pu être supprimée car elle n'existe pas", reponse)
        );
    }


    @Test
    @DisplayName("Modification de l'incident d'une station en True")
    void ModifierStationIncidentHashMapTrue(){
        StationController.initialisationStations();
        String reponse = StationController.modifierStationIncident("gare du nord",true);
        assertAll(
                () -> assertTrue(StationController.getStations().get("gare du nord").isIncident()),
                () -> assertEquals("La station possède maintenant un incident", reponse)
        );
    }

    @Test
    @DisplayName("Modification de l'incident d'une station en False")
    void ModifierStationIncidentHashMapFalse(){
        StationController.initialisationStations();
        String reponse = StationController.modifierStationIncident("gare du nord",false);
        assertAll(
                () -> assertFalse(StationController.getStations().get("gare du nord").isIncident()),
                () -> assertEquals("La station ne possède maintenant plus d'incident", reponse)
        );
    }

    @Test
    @DisplayName("Modification de l'incident d'une station en False")
    void ModifierStationIncidentHashMapInexistante(){
        StationController.initialisationStations();
        String reponse = StationController.modifierStationIncident("gare inexistante",false);
        assertEquals("La station que vous souhaitez modifiée n'existe pas", reponse);
    }

}