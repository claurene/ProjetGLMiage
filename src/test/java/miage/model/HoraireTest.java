package miage.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Horaire")
public class HoraireTest {

    @Test
    @DisplayName("Changement de statut")
    void changementStatut(){
        LocalDateTime heureTest = LocalDateTime.now();
        ArrayList<Integer> tempsParcours = new ArrayList<>();
        tempsParcours.add(10);
        Station s = new Station("Gare de l'est",2,false,48.79,2.12);
        ArrayList<Station> listeStation = new ArrayList<Station>();
        listeStation.add(new Station("Gare du nord",2,false,48.79,2.12));
        listeStation.add(new Station("Gare de l'est",2,false,48.79,2.12));
        Ligne l = new Ligne("Metro 5",tempsParcours,listeStation);
        Horaire h = new Horaire(s,l,"En service",heureTest);

        h.setStatut("Annulé");

        assertEquals(h.getStatut(),"Annulé","Le statut devrait être Annulé");
    }

    @Test
    @DisplayName("Incident sur la station")
    void incidentStation(){
        LocalDateTime heureTest = LocalDateTime.now();
        ArrayList<Integer> tempsParcours = new ArrayList<>();
        tempsParcours.add(10);
        Station s = new Station("Gare de l'est",2,false,48.79,2.12);
        s.setIncident(true);
        ArrayList<Station> listeStation = new ArrayList<Station>();
        listeStation.add(new Station("Gare du nord",2,false,48.79,2.12));
        listeStation.add(new Station("Gare de l'est",2,false,48.79,2.12));
        Ligne l = new Ligne("Metro 5",tempsParcours,listeStation);

        Horaire h = new Horaire(s,l,heureTest);

        assertEquals(h.getStatut(),"Incident sur la station","Le statut devrait indiquer un incident");
    }

    @Test
    @DisplayName("Incident sur la ligne")
    void incidentLigne(){
        LocalDateTime heureTest = LocalDateTime.now();
        ArrayList<Integer> tempsParcours = new ArrayList<>();
        tempsParcours.add(10);
        Station s = new Station("Gare de l'est",2,false,48.79,2.12);
        ArrayList<Station> listeStation = new ArrayList<Station>();
        listeStation.add(new Station("Gare du nord",2,false,48.79,2.12));
        listeStation.add(new Station("Gare de l'est",2,false,48.79,2.12));
        Ligne l = new Ligne("Metro 5",tempsParcours,listeStation);
        l.setIncident(true);

        Horaire h = new Horaire(s,l,heureTest);

        assertEquals(h.getStatut(),"Incident sur la ligne","Le statut devrait indiquer un incident");
    }

    @Test
    @DisplayName("Horaire pour une station de départ")
    void horaireDepart(){
        LocalDateTime heureTest = LocalDateTime.of(LocalDate.now(), LocalTime.of(6,0));
        ArrayList<Integer> tempsParcours = new ArrayList<>();
        tempsParcours.add(10);
        Station s = new Station("Gare du nord",2,false,48.79,2.12);
        ArrayList<Station> listeStation = new ArrayList<Station>();
        listeStation.add(new Station("Gare du nord",2,false,48.79,2.12));
        listeStation.add(new Station("Gare de l'est",2,false,48.79,2.12));
        Ligne l = new Ligne("Metro 5",tempsParcours,listeStation);

        Horaire h = new Horaire(s,l,"En service",heureTest);
        System.out.println(h.toString());

        assertEquals(h.getHoraire(),LocalDateTime.of(LocalDate.now(),LocalTime.of(6,6)));

    }

    @Test
    @DisplayName("Horaire pour une station quelconque")
    void horaireStation(){
        LocalDateTime heureTest = LocalDateTime.of(LocalDate.now(), LocalTime.of(6,0));
        ArrayList<Integer> tempsParcours = new ArrayList<>();
        tempsParcours.add(11);
        Station s = new Station("Gare de l'est",2,false,48.79,2.12);
        ArrayList<Station> listeStation = new ArrayList<Station>();
        listeStation.add(new Station("Gare du nord",2,false,48.79,2.12));
        listeStation.add(new Station("Gare de l'est",2,false,48.79,2.12));
        Ligne l = new Ligne("Metro 5",tempsParcours,listeStation);

        Horaire h = new Horaire(s,l,"En service",heureTest);

        /* Temps entre les deux stations = 13min, rames toutes les 6 min à partir de 00h00 au départ :
         * Donc rames à 00h13, 00h18, etc. => 06h01
         */
        assertEquals(h.getHoraire(),LocalDateTime.of(LocalDate.now(),LocalTime.of(6,1)));

    }

    @Test
    @DisplayName("Horaire pour une station de départ après 24h")
    void horaire24HDepart(){
        LocalDateTime heureTest = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,55));
        ArrayList<Integer> tempsParcours = new ArrayList<>();
        tempsParcours.add(10);
        Station s = new Station("Gare du nord",2,false,48.79,2.12);
        ArrayList<Station> listeStation = new ArrayList<Station>();
        listeStation.add(new Station("Gare du nord",2,false,48.79,2.12));
        listeStation.add(new Station("Gare de l'est",2,false,48.79,2.12));
        Ligne l = new Ligne("Metro 5",tempsParcours,listeStation);

        Horaire h = new Horaire(s,l,"En service",heureTest);

        assertEquals(h.getHoraire(),LocalDateTime.of(heureTest.toLocalDate().plusDays(1),LocalTime.MIDNIGHT));

    }

    @Test
    @DisplayName("Horaire pour une station quelconque après 24h")
    void horaire24H(){
        LocalDateTime heureTest = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,55));
        ArrayList<Integer> tempsParcours = new ArrayList<>();
        tempsParcours.add(11);
        Station s = new Station("Gare de l'est",2,false,48.79,2.12);
        ArrayList<Station> listeStation = new ArrayList<Station>();
        listeStation.add(new Station("Gare du nord",2,false,48.79,2.12));
        listeStation.add(new Station("Gare de l'est",2,false,48.79,2.12));
        Ligne l = new Ligne("Metro 5",tempsParcours,listeStation);

        Horaire h = new Horaire(s,l,"En service",heureTest);

        // De même, rames de 00h13 à 00h01 d'après le temps de parcours
        assertEquals(h.getHoraire(),LocalDateTime.of(heureTest.toLocalDate().plusDays(1),LocalTime.MIDNIGHT.plusMinutes(1)));

    }

    @Test
    @DisplayName("Horaire pour la ligne passant dans l'autre sens")
    void horaireStationDirection(){
        LocalDateTime heureTest = LocalDateTime.of(LocalDate.now(), LocalTime.of(6,0));
        ArrayList<Station> listeStation = new ArrayList<Station>();
        ArrayList<Integer> tempsParcours = new ArrayList<>();
        tempsParcours.add(10);
        tempsParcours.add(5);
        tempsParcours.add(7);
        Station s = new Station("Gare du nord",2,false,48.79,2.12);
        listeStation.add(new Station("Gare du nord",2,false,48.79,2.12));
        listeStation.add(new Station("Gare de l'est",2,false,49.82,2.2));
        listeStation.add(new Station("Jacques-Bonsergent",2,false,50.19,2.4));
        listeStation.add(new Station("Republique",2,false,51.19,2.6));
        Ligne l = new Ligne("Metro 5",tempsParcours,listeStation);

        Horaire h1 = new Horaire(s,l,"En service",heureTest);
        Horaire h2 = new Horaire(s,l.getDirectionInverse(),"En service",heureTest);

        assertAll(
                () -> assertEquals(h1.getHoraire(),LocalDateTime.of(LocalDate.now(),LocalTime.of(6,6))),
                () -> assertEquals(h2.getHoraire(),LocalDateTime.of(LocalDate.now(),LocalTime.of(6,4)))
        );
    }

    @Test
    @DisplayName("Table des horaires d'un arret")
    void tableHoraires(){
        LocalDateTime heureTest = LocalDateTime.of(LocalDate.now(), LocalTime.of(6,0));
        ArrayList<Station> listeStation = new ArrayList<Station>();
        ArrayList<Integer> tempsParcours = new ArrayList<>();
        tempsParcours.add(10);
        tempsParcours.add(5);
        tempsParcours.add(7);
        Station s = new Station("Gare du nord",2,false,48.79,2.12);
        listeStation.add(new Station("Gare du nord",2,false,48.79,2.12));
        listeStation.add(new Station("Gare de l'est",2,false,49.82,2.2));
        listeStation.add(new Station("Jacques-Bonsergent",2,false,50.19,2.4));
        listeStation.add(new Station("Republique",2,false,51.19,2.6));
        Ligne l = new Ligne("Metro 5",tempsParcours,listeStation);

        Horaire h1 = new Horaire(s,l,"En service",heureTest);

        assertAll(
                () -> assertEquals(h1.getTableHoraire().get(0),LocalDateTime.of(LocalDate.now(),LocalTime.MIDNIGHT)),
                () -> assertEquals(h1.getTableHoraire().get(h1.getTableHoraire().size()-1),LocalDateTime.of(LocalDate.now(),LocalTime.of(23,54)))
        );
    }
}
