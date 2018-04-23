package miage.model;

import miage.controller.StationController;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

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
}