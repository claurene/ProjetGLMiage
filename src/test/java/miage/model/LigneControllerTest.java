package miage.model;

import miage.controller.LigneController;
import miage.controller.StationController;
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
        ligneController.initialisationLignes();
        int taille = ligneController.getLignes().size();

        ArrayList<Station> stations = new ArrayList<Station>();
        Station s11 = new Station("chateau de vincennes",2,false,48.844,2.442);
        stations.add(s11);
        Station s12 = new Station("berault",2,false,48.845,2.427);
        stations.add(s12);

        ArrayList<Integer> tempsParcours = new ArrayList<Integer>();
        tempsParcours.add(1);

        String reponse = ligneController.ajouterLigne("1bis",tempsParcours,stations);
        assertAll(
                () -> assertTrue(ligneController.getLignes().size()==taille+1),
                () -> assertTrue(stations.size()>1),
                () -> assertTrue(ligneController.ligneExiste("1bis")),
                () -> assertTrue(reponse.equals("La ligne 1bis a été ajoutée avec succès."))
        );
    }

    @Test
    @DisplayName("Ajout d'une ligne déjà existante")
    void AjoutLigneExistante(){
        ligneController.initialisationLignes();
        int taille = ligneController.getLignes().size();

        ArrayList<Station> stations = new ArrayList<Station>();
        Station s11 = new Station("chateau de vincennes",2,false,48.844,2.442);
        stations.add(s11);
        Station s12 = new Station("berault",2,false,48.845,2.427);
        stations.add(s12);

        ArrayList<Integer> tempsParcours = new ArrayList<Integer>();
        tempsParcours.add(1);

        String reponse = ligneController.ajouterLigne("1",tempsParcours,stations);
        assertAll(
                () -> assertTrue(ligneController.getLignes().size()==taille),
                () -> assertTrue(ligneController.ligneExiste("1")),
                () -> assertTrue(reponse.equals("Cette ligne existe déjà."))
        );
    }

    @Test
    @DisplayName("Ajout d'une ligne sans station")
    void AjoutLigneSansStations(){
        ligneController.initialisationLignes();
        int taille = ligneController.getLignes().size();

        ArrayList<Station> stations = new ArrayList<Station>();

        ArrayList<Integer> tempsParcours = new ArrayList<Integer>();
        tempsParcours.add(1);

        String reponse = ligneController.ajouterLigne("1bis",tempsParcours,stations);
        assertAll(
                () -> assertTrue(ligneController.getLignes().size()==taille),
                () -> assertFalse(stations.size()>1),
                () -> assertTrue(reponse.equals("Il faut au moins deux stations pour pouvoir créer une ligne."))
        );
    }

    @Test
    @DisplayName("Supprimer une ligne")
    void SupprimerLigne(){
        ligneController.initialisationLignes();
        int taille = ligneController.getLignes().size();

        String reponse = ligneController.supprimerLigne("1");
        assertAll(
                () -> assertTrue(ligneController.getLignes().size()==taille-1),
                () -> assertFalse(ligneController.ligneExiste("1")),
                () -> assertTrue(reponse.equals("La ligne 1 a été supprimée."))
        );
    }

    @Test
    @DisplayName("Supprimer une ligne inexsistante")
    void SupprimerLigneInexistante(){
        ligneController.initialisationLignes();
        int taille = ligneController.getLignes().size();

        String reponse = ligneController.supprimerLigne("1000");
        assertAll(
                () -> assertTrue(ligneController.getLignes().size()==taille),
                () -> assertFalse(ligneController.ligneExiste("1000")),
                () -> assertTrue(reponse.equals("La ligne 1000 n'existe pas."))
        );
    }

    @Test
    @DisplayName("Modification du temps de parcours d'une ligne.")
    void ModifierLigneTempsParcours(){
        ligneController.initialisationLignes();
        int taille = ligneController.getLignes().size();

        Ligne l = ligneController.getLignes().get("1");
        String station1 = l.getListeStation().get(0).getNomStation();
        String station2 = l.getListeStation().get(1).getNomStation();

        String reponse = ligneController.modifierLigneTempsParcours(ligneController.getLignes().get("1").getNomLigne(),station1,station2,5);

        assertAll(
                () -> assertTrue(ligneController.getLignes().size()==taille),
                () -> assertTrue(reponse.equals("Le temps de parcours entre "+station1+" et "+station2+" a bien été modifié."))
        );
    }

    @Test
    @DisplayName("Modification du temps de parcours d'une ligne inexistante.")
    void ModifierLigneTempsParcoursInexistante(){
        ligneController.initialisationLignes();
        int taille = ligneController.getLignes().size();

        String reponse = ligneController.modifierLigneTempsParcours("1000","chateau de vincennes","berault",5);

        assertAll(
                () -> assertTrue(ligneController.getLignes().size()==taille),
                () -> assertTrue(reponse.equals("La ligne saisie n'existe pas."))
        );
    }

    @Test
    @DisplayName("Modification du temps de parcours d'une ligne avec des station non reliées.")
    void ModifierLigneTempsParcoursStationsNonReliees(){
        ligneController.initialisationLignes();
        int taille = ligneController.getLignes().size();

        Ligne l = ligneController.getLignes().get("1");
        String station1 = l.getListeStation().get(0).getNomStation();
        String station2 = l.getListeStation().get(2).getNomStation();

        String reponse = ligneController.modifierLigneTempsParcours(ligneController.getLignes().get("1").getNomLigne(),station1,station2,5);

        assertAll(
                () -> assertTrue(ligneController.getLignes().size()==taille),
                () -> assertTrue(reponse.equals("La station " + station1 + " et la station " + station2 + " ne sont pas reliées directement."))
        );
    }

    @Test
    @DisplayName("Modification du temps de parcours d'une ligne avec une station inexistante.")
    void ModifierLigneTempsParcoursStationInexistante(){
        ligneController.initialisationLignes();
        int taille = ligneController.getLignes().size();

        Ligne l = ligneController.getLignes().get("1");
        String station1 = "pas de station";
        String station2 = l.getListeStation().get(2).getNomStation();

        String reponse = ligneController.modifierLigneTempsParcours(ligneController.getLignes().get("1").getNomLigne(),station1,station2,5);

        assertAll(
                () -> assertTrue(ligneController.getLignes().size()==taille),
                () -> assertTrue(reponse.equals("La station " + station1 + " n'existe pas."))
        );
    }

    @Test
    @DisplayName("Modification du temps de parcours d'une ligne avec inversion des stations.")
    void ModifierLigneTempsParcoursInversionStation(){
        ligneController.initialisationLignes();
        int taille = ligneController.getLignes().size();

        Ligne l = ligneController.getLignes().get("1");
        String station1 = l.getListeStation().get(2).getNomStation();
        String station2 = l.getListeStation().get(1).getNomStation();

        String reponse = ligneController.modifierLigneTempsParcours(ligneController.getLignes().get("1").getNomLigne(),station1, station2,5);

        assertAll(
                () -> assertTrue(ligneController.getLignes().size()==taille),
                () -> assertTrue(reponse.equals("Le temps de parcours entre "+station2+" et "+station1+" a bien été modifié."))
        );
    }

    @Test
    @DisplayName("Modification du temps de parcours d'une ligne avec un temps <=0.")
    void ModifierLigneTempsParcoursTmpIncorrect(){
        ligneController.initialisationLignes();
        int taille = ligneController.getLignes().size();

        Ligne l = ligneController.getLignes().get("1");
        String station1 = l.getListeStation().get(0).getNomStation();
        String station2 = l.getListeStation().get(1).getNomStation();
        int tmpParc = l.getListeTempsParcours().get(0);

        String reponse = ligneController.modifierLigneTempsParcours(l.getNomLigne(),station1,station2,-2);
        int tmpParcNouv = l.getListeTempsParcours().get(0);

        assertAll(
                () -> assertTrue(ligneController.getLignes().size()==taille),
                () -> assertTrue(reponse.equals("Le temps de parcours doit être strictement supérieur à 0."))
        );
    }

    @Test
    @DisplayName("Supprimer une station dans une ligne.")
    void ModifierLigneSupprimerStation(){
        ligneController.initialisationLignes();
        int taille = ligneController.getLignes().size();

        Ligne l = ligneController.getLignes().get("1");
        String station1 = l.getListeStation().get(1).getNomStation();
        String reponse = ligneController.modifierLigneSupprimerStation(l.getNomLigne(), station1);
        assertAll(
                () -> assertTrue(ligneController.getLignes().size()==taille),
                () -> assertTrue(reponse.equals("La station "+station1+" a été supprimée de la ligne "+l.getNomLigne()+"."))
        );
    }

    @Test
    @DisplayName("Supprimer la station de départ dans une ligne.")
    void ModifierLigneSupprimerStationDepart(){
        ligneController.initialisationLignes();
        int taille = ligneController.getLignes().size();

        Ligne l = ligneController.getLignes().get("1");
        String station1 = l.getListeStation().get(0).getNomStation();
        String reponse = ligneController.modifierLigneSupprimerStation(l.getNomLigne(), station1);

        assertAll(
                () -> assertTrue(ligneController.getLignes().size()==taille),
                () -> assertTrue(reponse.equals("La station "+station1+" a été supprimée de la ligne "+l.getNomLigne()+"."))
        );
    }

    @Test
    @DisplayName("Supprimer la station terminus dans une ligne.")
    void ModifierLigneSupprimerStationTerminus(){
        ligneController.initialisationLignes();
        int taille = ligneController.getLignes().size();

        Ligne l = ligneController.getLignes().get("1");
        String station1 = l.getListeStation().get(l.getListeStation().size()-1).getNomStation();
        String reponse = ligneController.modifierLigneSupprimerStation(l.getNomLigne(), station1);

        assertAll(
                () -> assertTrue(ligneController.getLignes().size()==taille),
                () -> assertTrue(reponse.equals("La station "+station1+" a été supprimée de la ligne "+l.getNomLigne()+"."))
        );
    }

    @Test
    @DisplayName("Supprimer une station inexistante dans une ligne.")
    void ModifierLigneSupprimerStationInexistante(){
        ligneController.initialisationLignes();
        int taille = ligneController.getLignes().size();

        String reponse = ligneController.modifierLigneSupprimerStation("1", "pas station");

        assertAll(
                () -> assertTrue(ligneController.getLignes().size()==taille),
                () -> assertTrue(reponse.equals("La station pas station n'existe pas dans la ligne."))
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
        ligneController.initialisationLignes();
        stationController.initialisationStations();
        int taille = ligneController.getLignes().size();
        Ligne l = ligneController.getLignes().get("1");
        Ligne l2 = ligneController.getLignes().get("3");
        String reponse = ligneController.modifierLigneAjouterStation(1, stationController.getStations(), "1", l2.getListeStation().get(0).getNomStation(),"",l.getListeStation().get(0).getNomStation(), 0, 5);
        assertAll(
                () -> assertTrue(ligneController.getLignes().size()==taille),
                () -> assertTrue(reponse.equals("L'ajout de la station "+l2.getListeStation().get(0).getNomStation()+" a été effectué en tant que départ de la ligne 1."))
        );
    }

    @Test
    @DisplayName("Ajouter une station en position terminus dans une ligne.")
    void ModifierLigneAjouterStationTerminus(){
        ligneController.initialisationLignes();
        stationController.initialisationStations();
        int taille = ligneController.getLignes().size();
        Ligne l = ligneController.getLignes().get("1");
        Ligne l2 = ligneController.getLignes().get("3");
        String stationPrec = l.getListeStation().get(l.getListeStation().size()-1).getNomStation();
        String reponse = ligneController.modifierLigneAjouterStation(2, stationController.getStations(), "1", l2.getListeStation().get(0).getNomStation(), stationPrec,"", 5, 0);
        assertAll(
                () -> assertTrue(ligneController.getLignes().size()==taille),
                () -> assertTrue(reponse.equals("L'ajout de la station "+l2.getListeStation().get(0).getNomStation()+" a été effectué en tant que terminus de la ligne 1."))
        );
    }

    @Test
    @DisplayName("Ajouter une station entre deux stations dans une ligne.")
    void ModifierLigneAjouterStationEntreDeux(){
        ligneController.initialisationLignes();
        stationController.initialisationStations();
        int taille = ligneController.getLignes().size();
        Ligne l = ligneController.getLignes().get("1");
        Ligne l2 = ligneController.getLignes().get("3");
        String stationPrec = l.getListeStation().get(0).getNomStation();
        String stationSuiv = l.getListeStation().get(1).getNomStation();
        String reponse = ligneController.modifierLigneAjouterStation(3, stationController.getStations(), "1", l2.getListeStation().get(0).getNomStation(), stationPrec,stationSuiv, 5, 5);
        assertAll(
                () -> assertTrue(ligneController.getLignes().size()==taille),
                () -> assertTrue(reponse.equals("L'ajout de la station "+l2.getListeStation().get(0).getNomStation()+" a été effectué entre les stations " + stationPrec + " et " + stationSuiv + "."))
        );
    }

    @Test
    @DisplayName("Ajouter une station entre deux stations avec l'ordre de saisie inversé dans une ligne.")
    void ModifierLigneAjouterStationEntreDeuxInversee(){
        ligneController.initialisationLignes();
        stationController.initialisationStations();
        int taille = ligneController.getLignes().size();
        Ligne l = ligneController.getLignes().get("1");
        Ligne l2 = ligneController.getLignes().get("3");
        String stationPrec = l.getListeStation().get(1).getNomStation();
        String stationSuiv = l.getListeStation().get(0).getNomStation();
        String reponse = ligneController.modifierLigneAjouterStation(3, stationController.getStations(), "1", l2.getListeStation().get(0).getNomStation(), stationPrec,stationSuiv, 5, 5);
        assertAll(
                () -> assertTrue(ligneController.getLignes().size()==taille),
                () -> assertTrue(reponse.equals("L'ajout de la station "+l2.getListeStation().get(0).getNomStation()+" a été effectué entre les stations " + stationPrec + " et " + stationSuiv + "."))
        );
    }

    @Test
    @DisplayName("Ajouter une station entre deux stations non reliées dans une ligne.")
    void ModifierLigneAjouterStationEntreDeuxNonReliees(){
        ligneController.initialisationLignes();
        stationController.initialisationStations();
        int taille = ligneController.getLignes().size();
        Ligne l = ligneController.getLignes().get("1");
        Ligne l2 = ligneController.getLignes().get("3");
        String stationPrec = l.getListeStation().get(0).getNomStation();
        String stationSuiv = l.getListeStation().get(5).getNomStation();
        String reponse = ligneController.modifierLigneAjouterStation(3, stationController.getStations(), "1", l2.getListeStation().get(0).getNomStation(), stationPrec,stationSuiv, 5, 5);
        assertAll(
                () -> assertTrue(ligneController.getLignes().size()==taille),
                () -> assertTrue(reponse.equals("les stations " + stationPrec + " et " + stationSuiv + " ne sont pas directement reliées."))
        );
    }

    @Test
    @DisplayName("Ajouter une station entre deux stations avec tmps parcours erroné dans une ligne.")
    void ModifierLigneAjouterStationEntreDeuxTmpParcIncorrect(){
        ligneController.initialisationLignes();
        stationController.initialisationStations();
        int taille = ligneController.getLignes().size();
        Ligne l = ligneController.getLignes().get("1");
        Ligne l2 = ligneController.getLignes().get("3");
        String stationPrec = l.getListeStation().get(0).getNomStation();
        String stationSuiv = l.getListeStation().get(1).getNomStation();
        String reponse = ligneController.modifierLigneAjouterStation(3, stationController.getStations(), "1", l2.getListeStation().get(0).getNomStation(), stationPrec,stationSuiv, 5, -5);
        assertAll(
                () -> assertTrue(ligneController.getLignes().size()==taille),
                () -> assertTrue(reponse.equals("Le temps de parcours entre doit être strictement supérieur à 0."))
        );
    }

    @Test
    @DisplayName("Ajouter une station entre deux fois la meme station dans une ligne.")
    void ModifierLigneAjouterStationEntreDeuxMemeStation(){
        ligneController.initialisationLignes();
        stationController.initialisationStations();
        int taille = ligneController.getLignes().size();
        Ligne l = ligneController.getLignes().get("1");
        Ligne l2 = ligneController.getLignes().get("3");
        String stationPrec = l.getListeStation().get(0).getNomStation();
        String stationSuiv = l.getListeStation().get(0).getNomStation();
        String reponse = ligneController.modifierLigneAjouterStation(3, stationController.getStations(), "1", l2.getListeStation().get(0).getNomStation(), stationPrec,stationSuiv, 5, 5);
        assertAll(
                () -> assertTrue(ligneController.getLignes().size()==taille),
                () -> assertTrue(reponse.equals("Les stations précédente et suivante sont les mêmes."))
        );
    }

    @Test
    @DisplayName("Ajouter une station entre deux stations dont une pas dans la ligne dans une ligne.")
    void ModifierLigneAjouterStationEntreDeuxInexistante(){
        ligneController.initialisationLignes();
        stationController.initialisationStations();
        int taille = ligneController.getLignes().size();
        Ligne l = ligneController.getLignes().get("1");
        Ligne l2 = ligneController.getLignes().get("3");
        String stationPrec = l.getListeStation().get(0).getNomStation();
        String stationSuiv = l2.getListeStation().get(0).getNomStation();
        String reponse = ligneController.modifierLigneAjouterStation(3, stationController.getStations(), "1", l2.getListeStation().get(0).getNomStation(), stationPrec,stationSuiv, 5, 5);
        assertAll(
                () -> assertTrue(ligneController.getLignes().size()==taille),
                () -> assertTrue(reponse.equals("La station " + stationSuiv + " n'est pas présente dans la ligne " + l.getNomLigne() + "."))
        );
    }

    @Test
    @DisplayName("Ajouter une station inexistante dans une ligne.")
    void ModifierLigneAjouterStationInexistante(){
        ligneController.initialisationLignes();
        stationController.initialisationStations();
        int taille = ligneController.getLignes().size();
        Ligne l = ligneController.getLignes().get("1");
        String reponse = ligneController.modifierLigneAjouterStation(1, stationController.getStations(), "1", "pas station","",l.getListeStation().get(0).getNomStation(), 0, 5);
        assertAll(
                () -> assertTrue(ligneController.getLignes().size()==taille),
                () -> assertTrue(reponse.equals("La station pas station n'existe pas."))
        );
    }

    @Test
    @DisplayName("Ajouter une station qui existe déjà dans la ligne.")
    void ModifierLigneAjouterStationExistante(){
        ligneController.initialisationLignes();
        stationController.initialisationStations();
        int taille = ligneController.getLignes().size();
        Ligne l = ligneController.getLignes().get("1");
        String reponse = ligneController.modifierLigneAjouterStation(1, stationController.getStations(), "1", l.getListeStation().get(1).getNomStation(),"",l.getListeStation().get(0).getNomStation(), 0, 5);
        assertAll(
                () -> assertTrue(ligneController.getLignes().size()==taille),
                () -> assertTrue(reponse.equals("La station "+l.getListeStation().get(1).getNomStation()+" est dejà présente dans la ligne "+l.getNomLigne()+"."))
        );
    }

    @Test
    @DisplayName("Ajouter une station avec un temps de parcours incorrect une ligne.")
    void ModifierLigneAjouterStationTmpParcIncorrect(){
        ligneController.initialisationLignes();
        stationController.initialisationStations();
        int taille = ligneController.getLignes().size();
        Ligne l = ligneController.getLignes().get("1");
        Ligne l2 = ligneController.getLignes().get("3");
        String reponse = ligneController.modifierLigneAjouterStation(1, stationController.getStations(), "1", l2.getListeStation().get(0).getNomStation(),"",l.getListeStation().get(0).getNomStation(), 0, -5);
        assertAll(
                () -> assertTrue(ligneController.getLignes().size()==taille),
                () -> assertTrue(reponse.equals("Le temps de parcours entre " + l2.getListeStation().get(0).getNomStation() + " et " + l.getListeStation().get(0).getNomStation() + " doit être strictement supérieur à 0."))
        );
    }

    @Test
    @DisplayName("Ajout d'un incident sur une ligne")
    void AjoutIncidentLigne(){
        ligneController.initialisationLignes();
        String reponse = ligneController.modifierLigneIncident("1","y");
        assertAll(
                () -> assertTrue(ligneController.getLignes().get("1").isIncident()),
                () -> assertTrue(reponse.equals("La ligne "+ligneController.getLignes().get("1").getNomLigne()+" possède maintenant un incident."))
        );
    }

    @Test
    @DisplayName("Ajout d'un incident sur une ligne inexistante")
    void AjoutIncidentLigneInexistante(){
        ligneController.initialisationLignes();
        String reponse = ligneController.modifierLigneIncident("1bis","y");
        assertTrue(reponse.equals("La ligne 1bis que vous souhaitez modifier n'existe pas."));
    }

    @Test
    @DisplayName("Suppression d'un incident sur une ligne")
    void SupprimerIncidentLigne(){
        ligneController.initialisationLignes();
        String reponse = ligneController.modifierLigneIncident("1","n");
        assertAll(
                () -> assertFalse(ligneController.getLignes().get("1").isIncident()),
                () -> assertTrue(reponse.equals("La ligne "+ligneController.getLignes().get("1").getNomLigne()+" ne possède maintenant plus d'incident."))
        );
    }

}
