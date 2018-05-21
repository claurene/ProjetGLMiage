package miage.controller;

import miage.controller.LigneController;
import miage.controller.StationController;
import miage.model.Ligne;
import miage.model.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LigneController")
public class LigneControllerTest {
    private final LigneController ligneController = new LigneController();
    private final StationController stationController= new StationController();

    @Test
    @DisplayName("Ajout d'une ligne")
    void AjoutLigne(){
        LigneController.initialisationLignes();
        int taille = LigneController.getLignes().size();

        ArrayList<Station> stations = new ArrayList<Station>();
        Station s11 = new Station("chateau de vincennes",2,false,48.844,2.442);
        stations.add(s11);
        Station s12 = new Station("berault",2,false,48.845,2.427);
        stations.add(s12);

        ArrayList<Integer> tempsParcours = new ArrayList<Integer>();
        tempsParcours.add(1);

        String reponse = LigneController.ajouterLigne("1bis",tempsParcours,stations);
        assertAll(
                () -> assertEquals(LigneController.getLignes().size(), taille + 1),
                () -> assertTrue(stations.size()>1),
                () -> assertTrue(LigneController.ligneExiste("1bis")),
                () -> assertEquals("La ligne 1bis a été ajoutée avec succès.", reponse)
        );
    }

    @Test
    @DisplayName("Ajout d'une ligne déjà existante")
    void AjoutLigneExistante(){
        LigneController.initialisationLignes();
        int taille = LigneController.getLignes().size();

        ArrayList<Station> stations = new ArrayList<Station>();
        Station s11 = new Station("chateau de vincennes",2,false,48.844,2.442);
        stations.add(s11);
        Station s12 = new Station("berault",2,false,48.845,2.427);
        stations.add(s12);

        ArrayList<Integer> tempsParcours = new ArrayList<Integer>();
        tempsParcours.add(1);

        String reponse = LigneController.ajouterLigne("1",tempsParcours,stations);
        assertAll(
                () -> assertEquals(LigneController.getLignes().size(), taille),
                () -> assertTrue(LigneController.ligneExiste("1")),
                () -> assertEquals("Cette ligne existe déjà.", reponse)
        );
    }

    @Test
    @DisplayName("Ajout d'une ligne sans station")
    void AjoutLigneSansStations(){
        LigneController.initialisationLignes();
        int taille = LigneController.getLignes().size();

        ArrayList<Station> stations = new ArrayList<>();

        ArrayList<Integer> tempsParcours = new ArrayList<Integer>();
        tempsParcours.add(1);

        String reponse = LigneController.ajouterLigne("2bis",tempsParcours,stations);
        System.out.println(reponse);
        assertAll(
                () -> assertEquals(LigneController.getLignes().size(), taille),
                () -> assertFalse(stations.size()>1),
                () -> assertEquals("Il faut au moins deux stations pour pouvoir créer une ligne.", reponse)
        );
    }

    @Test
    @DisplayName("Supprimer une ligne")
    void SupprimerLigne(){
        LigneController.initialisationLignes();
        int taille = LigneController.getLignes().size();

        String reponse = LigneController.supprimerLigne("1");
        assertAll(
                () -> assertEquals(LigneController.getLignes().size(), taille - 1),
                () -> assertFalse(LigneController.ligneExiste("1")),
                () -> assertEquals("La ligne 1 a été supprimée.", reponse)
        );
    }

    @Test
    @DisplayName("Supprimer une ligne inexsistante")
    void SupprimerLigneInexistante(){
        LigneController.initialisationLignes();
        int taille = LigneController.getLignes().size();

        String reponse = LigneController.supprimerLigne("1000");
        assertAll(
                () -> assertEquals(LigneController.getLignes().size(), taille),
                () -> assertFalse(LigneController.ligneExiste("1000")),
                () -> assertEquals("La ligne 1000 n'existe pas.", reponse)
        );
    }

    @Test
    @DisplayName("Modification du temps de parcours d'une ligne.")
    void ModifierLigneTempsParcours(){
        LigneController.initialisationLignes();
        int taille = LigneController.getLignes().size();

        Ligne l = LigneController.getLignes().get("1");
        String station1 = l.getListeStation().get(0).getNomStation();
        String station2 = l.getListeStation().get(1).getNomStation();

        String reponse = LigneController.modifierLigneTempsParcours(LigneController.getLignes().get("1").getNomLigne(),station1,station2,5);

        assertAll(
                () -> assertEquals(LigneController.getLignes().size(), taille),
                () -> assertEquals(reponse, "Le temps de parcours entre " + station1 + " et " + station2 + " a bien été modifié.")
        );
    }

    @Test
    @DisplayName("Modification du temps de parcours d'une ligne inexistante.")
    void ModifierLigneTempsParcoursInexistante(){
        LigneController.initialisationLignes();
        int taille = LigneController.getLignes().size();

        String reponse = LigneController.modifierLigneTempsParcours("1000","chateau de vincennes","berault",5);

        assertAll(
                () -> assertEquals(LigneController.getLignes().size(), taille),
                () -> assertEquals("La ligne saisie n'existe pas.", reponse)
        );
    }

    @Test
    @DisplayName("Modification du temps de parcours d'une ligne avec des station non reliées.")
    void ModifierLigneTempsParcoursStationsNonReliees(){
        LigneController.initialisationLignes();
        int taille = LigneController.getLignes().size();

        Ligne l = LigneController.getLignes().get("1");
        String station1 = l.getListeStation().get(0).getNomStation();
        String station2 = l.getListeStation().get(2).getNomStation();

        String reponse = LigneController.modifierLigneTempsParcours(LigneController.getLignes().get("1").getNomLigne(),station1,station2,5);

        assertAll(
                () -> assertEquals(LigneController.getLignes().size(), taille),
                () -> assertEquals(reponse, "La station " + station1 + " et la station " + station2 + " ne sont pas reliées directement.")
        );
    }

    @Test
    @DisplayName("Modification du temps de parcours d'une ligne avec une station inexistante.")
    void ModifierLigneTempsParcoursStationInexistante(){
        LigneController.initialisationLignes();
        int taille = LigneController.getLignes().size();

        Ligne l = LigneController.getLignes().get("1");
        String station1 = "pas de station";
        String station2 = l.getListeStation().get(2).getNomStation();

        String reponse = LigneController.modifierLigneTempsParcours(LigneController.getLignes().get("1").getNomLigne(),station1,station2,5);

        assertAll(
                () -> assertEquals(LigneController.getLignes().size(), taille),
                () -> assertEquals(reponse, "La station " + station1 + " n'existe pas.")
        );
    }

    @Test
    @DisplayName("Modification du temps de parcours d'une ligne avec inversion des stations.")
    void ModifierLigneTempsParcoursInversionStation(){
        LigneController.initialisationLignes();
        int taille = LigneController.getLignes().size();

        Ligne l = LigneController.getLignes().get("1");
        String station1 = l.getListeStation().get(2).getNomStation();
        String station2 = l.getListeStation().get(1).getNomStation();

        String reponse = LigneController.modifierLigneTempsParcours(LigneController.getLignes().get("1").getNomLigne(),station1, station2,5);

        assertAll(
                () -> assertEquals(LigneController.getLignes().size(), taille),
                () -> assertEquals(reponse, "Le temps de parcours entre " + station2 + " et " + station1 + " a bien été modifié.")
        );
    }

    @Test
    @DisplayName("Modification du temps de parcours d'une ligne avec un temps <=0.")
    void ModifierLigneTempsParcoursTmpIncorrect(){
        LigneController.initialisationLignes();
        int taille = LigneController.getLignes().size();

        Ligne l = LigneController.getLignes().get("1");
        String station1 = l.getListeStation().get(0).getNomStation();
        String station2 = l.getListeStation().get(1).getNomStation();
        int tmpParc = l.getListeTempsParcours().get(0);

        String reponse = LigneController.modifierLigneTempsParcours(l.getNomLigne(),station1,station2,-2);
        int tmpParcNouv = l.getListeTempsParcours().get(0);

        assertAll(
                () -> assertEquals(LigneController.getLignes().size(), taille),
                () -> assertEquals("Le temps de parcours doit être strictement supérieur à 0.", reponse)
        );
    }

    @Test
    @DisplayName("Supprimer une station dans une ligne.")
    void ModifierLigneSupprimerStation(){
        LigneController.initialisationLignes();
        int taille = LigneController.getLignes().size();

        Ligne l = LigneController.getLignes().get("1");
        String station1 = l.getListeStation().get(1).getNomStation();
        String reponse = LigneController.modifierLigneSupprimerStation(l.getNomLigne(), station1);
        assertAll(
                () -> assertEquals(LigneController.getLignes().size(), taille),
                () -> assertEquals(reponse, "La station " + station1 + " a été supprimée de la ligne " + l.getNomLigne() + ".")
        );
    }

    @Test
    @DisplayName("Supprimer la station de départ dans une ligne.")
    void ModifierLigneSupprimerStationDepart(){
        LigneController.initialisationLignes();
        int taille = LigneController.getLignes().size();

        Ligne l = LigneController.getLignes().get("1");
        String station1 = l.getListeStation().get(0).getNomStation();
        String reponse = LigneController.modifierLigneSupprimerStation(l.getNomLigne(), station1);

        assertAll(
                () -> assertEquals(LigneController.getLignes().size(), taille),
                () -> assertEquals(reponse, "La station " + station1 + " a été supprimée de la ligne " + l.getNomLigne() + ".")
        );
    }

    @Test
    @DisplayName("Supprimer la station terminus dans une ligne.")
    void ModifierLigneSupprimerStationTerminus(){
        LigneController.initialisationLignes();
        int taille = LigneController.getLignes().size();

        Ligne l = LigneController.getLignes().get("1");
        String station1 = l.getListeStation().get(l.getListeStation().size()-1).getNomStation();
        String reponse = LigneController.modifierLigneSupprimerStation(l.getNomLigne(), station1);

        assertAll(
                () -> assertEquals(LigneController.getLignes().size(), taille),
                () -> assertEquals(reponse, "La station " + station1 + " a été supprimée de la ligne " + l.getNomLigne() + ".")
        );
    }

    @Test
    @DisplayName("Supprimer une station inexistante dans une ligne.")
    void ModifierLigneSupprimerStationInexistante(){
        LigneController.initialisationLignes();
        int taille = LigneController.getLignes().size();

        String reponse = LigneController.modifierLigneSupprimerStation("1", "pas station");

        assertAll(
                () -> assertEquals(LigneController.getLignes().size(), taille),
                () -> assertEquals("La station pas station n'existe pas dans la ligne.", reponse)
        );
    }

    /*
    A revoir car aucune ligne n'a que deux stations dans jeu de données
    @Test
    @DisplayName("Supprimer une station alors que la ligne n'a plus que 2 stations.")
    void ModifierLigneSupprimerStationListeInsuffisante(){
        ligneController.initialisationLignes();
        int taille = ligneController.getLignes().size();


        Ligne l = ligneController.getLignes().get("1");
        String station1 = l.getListeStation().get(1).getNomStation();
        String reponse = ligneController.modifierLigneSupprimerStation(l.getNomLigne(), station1);

        assertAll(
                () -> assertTrue(ligneController.getLignes().size()==taille),
                () -> assertTrue(reponse.equals("La station "+station1+" ne peut pas être supprimer puisque la ligne ne comportera plus qu'une seule station."))
        );
    }*/

    @Test
    @DisplayName("Ajouter une station en position départ dans une ligne.")
    void ModifierLigneAjouterStationDepart(){
        LigneController.initialisationLignes();
        StationController.initialisationStations();
        int taille = LigneController.getLignes().size();
        Ligne l = LigneController.getLignes().get("1");
        Ligne l2 = LigneController.getLignes().get("3");
        String reponse = LigneController.modifierLigneAjouterStation(1, StationController.getStations(), "1", l2.getListeStation().get(0).getNomStation(),"",l.getListeStation().get(0).getNomStation(), 0, 5);
        assertAll(
                () -> assertEquals(LigneController.getLignes().size(), taille),
                () -> assertEquals(reponse, "L'ajout de la station " + l2.getListeStation().get(0).getNomStation() + " a été effectué en tant que départ de la ligne 1.")
        );
    }

    @Test
    @DisplayName("Ajouter une station en position terminus dans une ligne.")
    void ModifierLigneAjouterStationTerminus(){
        LigneController.initialisationLignes();
        StationController.initialisationStations();
        int taille = LigneController.getLignes().size();
        Ligne l = LigneController.getLignes().get("1");
        Ligne l2 = LigneController.getLignes().get("3");
        String stationPrec = l.getListeStation().get(l.getListeStation().size()-1).getNomStation();
        String reponse = LigneController.modifierLigneAjouterStation(2, StationController.getStations(), "1", l2.getListeStation().get(0).getNomStation(), stationPrec,"", 5, 0);
        assertAll(
                () -> assertEquals(LigneController.getLignes().size(), taille),
                () -> assertEquals(reponse, "L'ajout de la station " + l2.getListeStation().get(0).getNomStation() + " a été effectué en tant que terminus de la ligne 1.")
        );
    }

    @Test
    @DisplayName("Ajouter une station entre deux stations dans une ligne.")
    void ModifierLigneAjouterStationEntreDeux(){
        LigneController.initialisationLignes();
        StationController.initialisationStations();
        int taille = LigneController.getLignes().size();
        Ligne l = LigneController.getLignes().get("1");
        Ligne l2 = LigneController.getLignes().get("3");
        String stationPrec = l.getListeStation().get(0).getNomStation();
        String stationSuiv = l.getListeStation().get(1).getNomStation();
        String reponse = LigneController.modifierLigneAjouterStation(3, StationController.getStations(), "1", l2.getListeStation().get(0).getNomStation(), stationPrec,stationSuiv, 5, 5);
        assertAll(
                () -> assertEquals(LigneController.getLignes().size(), taille),
                () -> assertEquals(reponse, "L'ajout de la station " + l2.getListeStation().get(0).getNomStation() + " a été effectué entre les stations " + stationPrec + " et " + stationSuiv + ".")
        );
    }

    @Test
    @DisplayName("Ajouter une station entre deux stations avec l'ordre de saisie inversé dans une ligne.")
    void ModifierLigneAjouterStationEntreDeuxInversee(){
        LigneController.initialisationLignes();
        StationController.initialisationStations();
        int taille = LigneController.getLignes().size();
        Ligne l = LigneController.getLignes().get("1");
        Ligne l2 = LigneController.getLignes().get("3");
        String stationPrec = l.getListeStation().get(1).getNomStation();
        String stationSuiv = l.getListeStation().get(0).getNomStation();
        String reponse = LigneController.modifierLigneAjouterStation(3, StationController.getStations(), "1", l2.getListeStation().get(0).getNomStation(), stationPrec,stationSuiv, 5, 5);
        assertAll(
                () -> assertEquals(LigneController.getLignes().size(), taille),
                () -> assertEquals(reponse, "L'ajout de la station " + l2.getListeStation().get(0).getNomStation() + " a été effectué entre les stations " + stationPrec + " et " + stationSuiv + ".")
        );
    }

    @Test
    @DisplayName("Ajouter une station entre deux stations non reliées dans une ligne.")
    void ModifierLigneAjouterStationEntreDeuxNonReliees(){
        LigneController.initialisationLignes();
        StationController.initialisationStations();
        int taille = LigneController.getLignes().size();
        Ligne l = LigneController.getLignes().get("1");
        Ligne l2 = LigneController.getLignes().get("3");
        String stationPrec = l.getListeStation().get(0).getNomStation();
        String stationSuiv = l.getListeStation().get(5).getNomStation();
        String reponse = LigneController.modifierLigneAjouterStation(3, StationController.getStations(), "1", l2.getListeStation().get(0).getNomStation(), stationPrec,stationSuiv, 5, 5);
        assertAll(
                () -> assertEquals(LigneController.getLignes().size(), taille),
                () -> assertEquals(reponse, "les stations " + stationPrec + " et " + stationSuiv + " ne sont pas directement reliées.")
        );
    }

    @Test
    @DisplayName("Ajouter une station entre deux stations avec tmps parcours erroné dans une ligne.")
    void ModifierLigneAjouterStationEntreDeuxTmpParcIncorrect(){
        LigneController.initialisationLignes();
        StationController.initialisationStations();
        int taille = LigneController.getLignes().size();
        Ligne l = LigneController.getLignes().get("1");
        Ligne l2 = LigneController.getLignes().get("3");
        String stationPrec = l.getListeStation().get(0).getNomStation();
        String stationSuiv = l.getListeStation().get(1).getNomStation();
        String reponse = LigneController.modifierLigneAjouterStation(3, StationController.getStations(), "1", l2.getListeStation().get(0).getNomStation(), stationPrec,stationSuiv, 5, -5);
        assertAll(
                () -> assertEquals(LigneController.getLignes().size(), taille),
                () -> assertEquals("Le temps de parcours entre doit être strictement supérieur à 0.", reponse)
        );
    }

    @Test
    @DisplayName("Ajouter une station entre deux fois la meme station dans une ligne.")
    void ModifierLigneAjouterStationEntreDeuxMemeStation(){
        LigneController.initialisationLignes();
        StationController.initialisationStations();
        int taille = LigneController.getLignes().size();
        Ligne l = LigneController.getLignes().get("1");
        Ligne l2 = LigneController.getLignes().get("3");
        String stationPrec = l.getListeStation().get(0).getNomStation();
        String stationSuiv = l.getListeStation().get(0).getNomStation();
        String reponse = LigneController.modifierLigneAjouterStation(3, StationController.getStations(), "1", l2.getListeStation().get(0).getNomStation(), stationPrec,stationSuiv, 5, 5);
        assertAll(
                () -> assertEquals(LigneController.getLignes().size(), taille),
                () -> assertEquals("Les stations précédente et suivante sont les mêmes.", reponse)
        );
    }

    @Test
    @DisplayName("Ajouter une station entre deux stations dont une pas dans la ligne dans une ligne.")
    void ModifierLigneAjouterStationEntreDeuxInexistante(){
        LigneController.initialisationLignes();
        StationController.initialisationStations();
        int taille = LigneController.getLignes().size();
        Ligne l = LigneController.getLignes().get("1");
        Ligne l2 = LigneController.getLignes().get("3");
        String stationPrec = l.getListeStation().get(0).getNomStation();
        String stationSuiv = l2.getListeStation().get(0).getNomStation();
        String reponse = LigneController.modifierLigneAjouterStation(3, StationController.getStations(), "1", l2.getListeStation().get(0).getNomStation(), stationPrec,stationSuiv, 5, 5);
        assertAll(
                () -> assertEquals(LigneController.getLignes().size(), taille),
                () -> assertEquals(reponse, "La station " + stationSuiv + " n'est pas présente dans la ligne " + l.getNomLigne() + ".")
        );
    }

    @Test
    @DisplayName("Ajouter une station inexistante dans une ligne.")
    void ModifierLigneAjouterStationInexistante(){
        LigneController.initialisationLignes();
        StationController.initialisationStations();
        int taille = LigneController.getLignes().size();
        Ligne l = LigneController.getLignes().get("1");
        String reponse = LigneController.modifierLigneAjouterStation(1, StationController.getStations(), "1", "pas station","",l.getListeStation().get(0).getNomStation(), 0, 5);
        assertAll(
                () -> assertEquals(LigneController.getLignes().size(), taille),
                () -> assertEquals("La station pas station n'existe pas.", reponse)
        );
    }

    @Test
    @DisplayName("Ajouter une station qui existe déjà dans la ligne.")
    void ModifierLigneAjouterStationExistante(){
        LigneController.initialisationLignes();
        StationController.initialisationStations();
        int taille = LigneController.getLignes().size();
        Ligne l = LigneController.getLignes().get("1");
        String reponse = LigneController.modifierLigneAjouterStation(1, StationController.getStations(), "1", l.getListeStation().get(1).getNomStation(),"",l.getListeStation().get(0).getNomStation(), 0, 5);
        assertAll(
                () -> assertEquals(LigneController.getLignes().size(), taille),
                () -> assertEquals(reponse, "La station " + l.getListeStation().get(1).getNomStation() + " est dejà présente dans la ligne " + l.getNomLigne() + ".")
        );
    }

    @Test
    @DisplayName("Ajouter une station avec un temps de parcours incorrect une ligne.")
    void ModifierLigneAjouterStationTmpParcIncorrect(){
        LigneController.initialisationLignes();
        StationController.initialisationStations();
        int taille = LigneController.getLignes().size();
        Ligne l = LigneController.getLignes().get("1");
        Ligne l2 = LigneController.getLignes().get("3");
        String reponse = LigneController.modifierLigneAjouterStation(1, StationController.getStations(), "1", l2.getListeStation().get(0).getNomStation(),"",l.getListeStation().get(0).getNomStation(), 0, -5);
        assertAll(
                () -> assertEquals(LigneController.getLignes().size(), taille),
                () -> assertEquals(reponse, "Le temps de parcours entre " + l2.getListeStation().get(0).getNomStation() + " et " + l.getListeStation().get(0).getNomStation() + " doit être strictement supérieur à 0.")
        );
    }

    @Test
    @DisplayName("Ajout d'un incident sur une ligne")
    void AjoutIncidentLigne(){
        LigneController.initialisationLignes();
        String reponse = LigneController.modifierLigneIncident("1","y");
        assertAll(
                () -> assertTrue(LigneController.getLignes().get("1").isIncident()),
                () -> assertEquals(reponse, "La ligne " + LigneController.getLignes().get("1").getNomLigne() + " possède maintenant un incident.")
        );
    }

    @Test
    @DisplayName("Ajout d'un incident sur une ligne inexistante")
    void AjoutIncidentLigneInexistante(){
        LigneController.initialisationLignes();
        String reponse = LigneController.modifierLigneIncident("1bis","y");
        assertEquals("La ligne 1bis que vous souhaitez modifier n'existe pas.", reponse);
    }

    @Test
    @DisplayName("Suppression d'un incident sur une ligne")
    void SupprimerIncidentLigne(){
        LigneController.initialisationLignes();
        String reponse = LigneController.modifierLigneIncident("1","n");
        assertAll(
                () -> assertFalse(LigneController.getLignes().get("1").isIncident()),
                () -> assertEquals(reponse, "La ligne " + LigneController.getLignes().get("1").getNomLigne() + " ne possède maintenant plus d'incident.")
        );
    }

}
