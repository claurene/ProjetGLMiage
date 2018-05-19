package miage.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Station")
public class StationTest {


    @Test
    @DisplayName("Initialisation d'une station sans incident")
    void CreationStationSansIncident(){
        Station s = new Station("Gare de l'est",2,false,48.79,2.12);
        System.out.println(s.toString());
        assertFalse( s.isIncident() );
    }

    @Test
    @DisplayName("Initialisation d'une station avec incident")
    void CreationStationAvecIncident(){
        Station s = new Station("Gare de l'est",2,true,48.79,2.12);
        System.out.println(s.toString());
        assertTrue( s.isIncident());
    }

    @Test
    @DisplayName("Initialisation d'une station avec un mauvais temps d'arret (Il devient alors le temps Minimum)")
    void CreationStationTempsTropElevee(){
        Station s = new Station("Gare de l'est",200,false,48.79,2.12);
        System.out.println(s.toString());
        assertAll(
                () -> assertEquals(s.getTempsArret(),s.getTEMPS_MINIMUM_ARRET(),"Le temps minimum doit etre "+s.getTEMPS_MINIMUM_ARRET()),
                () -> assertTrue(Station.verifierTempsArret(s.getTempsArret()))
        );
    }

    @Test
    @DisplayName("Initialisation d'une station avec un temps d'arret negatif (Il devient alors le temps Minimum)")
    void CreationStationTempsNegatif(){
        Station s = new Station("Gare de l'est",-2,false,48.79,2.12);
        System.out.println(s.toString());
        assertAll(
                () -> assertEquals(s.getTempsArret(),s.getTEMPS_MINIMUM_ARRET(),"Le temps minimum doit etre "+s.getTEMPS_MINIMUM_ARRET()),
                () -> assertTrue(Station.verifierTempsArret(s.getTempsArret()))
        );
    }


    @Test
    @DisplayName("Changement d'un incident en true (Incident sur la station)")
    void StationChangementIncidentTrue(){
        Station s = new Station("Gare de l'est",200,false,48.79,2.12);
        s.setIncident(true);
        System.out.println(s.toString());
        assertTrue(s.isIncident());
    }

    @Test
    @DisplayName("Changement d'un incident en false (plus d'Incident sur la station)")
    void StationChangementIncidentFalse(){
        Station s = new Station("Gare de l'est",200,true,48.79,2.12);
        s.setIncident(false);
        System.out.println(s.toString());
        assertFalse(s.isIncident());
    }

    @Test
    @DisplayName("Changement du temps d'arret d'une station en 5 minutes")
    void StationChangementTemps(){
        Station s = new Station("Gare de l'est",2,false,48.79,2.12);
        s.setTempsArret(5);
        System.out.println(s.toString());
        assertEquals(s.getTempsArret(),5,"Le temps devrait être 5");
    }
}
