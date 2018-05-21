package miage.controller;

import miage.model.Ligne;
import miage.model.Station;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class LigneController {
    // Création du logger
    private static final Logger LOG = Logger.getLogger(LigneController.class.getName());

    // Liste des lignes de métro
    private static HashMap<String,Ligne> lignes = new HashMap<String,Ligne>();

    public static HashMap<String, Ligne> getLignes() {
        return lignes;
    }

    public static void setLignes(HashMap<String, Ligne> nvlLignes) {
        lignes = nvlLignes;
    }

    /**
     * Méthode qui permet de recuperer les donnees du fichier lignes.txt
     */
    public static void initialisationLignes(){
        FileInputStream in;
        ObjectInputStream ois = null;

        try {
            in = new FileInputStream("src/main/resources/lignes.txt");
            try {
                ois = new ObjectInputStream(in);
                while(true) {
                    try {
                        Ligne l = (Ligne) ois.readObject();
                        LigneController.lignes.put(l.getNomLigne(),l);
                    } catch (EOFException e){
                        break;
                    }
                }

            } catch (StreamCorruptedException e) {
                // Fichier corrompu
                LOG.warning("Fichier lignes.txt corrompu");
            }  catch (Exception e ) {
                e.printStackTrace();
            } finally {
                try {
                    ois.close();
                } catch (IOException e) {
                }
            }
        } catch (FileNotFoundException e) {
            // Fichier ligne non trouvé
            LOG.warning("Aucun fichier lignes.txt trouvé");
        }
    }

    /**
     * Methode qui permet de sauvegarder les nouvelles donnees dans le fichier lignes.txt
     */
    public static void sauvegardeLignes(){
        FileOutputStream out=null;
        ObjectOutputStream oos=null;

        try {
            out = new FileOutputStream("src/main/resources/lignes.txt");
        } catch (FileNotFoundException e) {
            // Fichier non trouvé : création du fichier
            LOG.info("Création d'un fichier lignes.txt");
        }

        try {
            oos = new ObjectOutputStream(out);
            for (HashMap.Entry<String,Ligne> entry : LigneController.lignes.entrySet()) {
                oos.writeObject(entry.getValue());
            }
            oos.flush();
        } catch (IOException e) {
        } finally {
            try {
                oos.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * Méthode qui permet de lister toutes les lignes existantes
     * @return string la liste des lignes
     */
    public static String listeLigne(){
        String reponse = "";
        if(LigneController.lignes.size()>0){
            for(Map.Entry<String, Ligne> ligne : LigneController.lignes.entrySet()){
                reponse += ligne.getKey()+"\n";
            }
        } else {
            reponse = "noLigne";
        }
        return reponse;
    }

    /**
     * Méthode qui permet d'afficher les stations d'une ligne donnée ainsi que le temps de parcours entre chaque station
     * @param nomLigne ligne dont on souhaite les stations
     * @return string la liste des stations de la ligne
     */
    public static String listeStationsParLigne(String nomLigne){
        String reponse = "";
        ArrayList<Station> listeStations = getLignes().get(nomLigne).getListeStation();
        ArrayList<Integer> tmpParcours = getLignes().get(nomLigne).getListeTempsParcours();
        System.out.println(listeStations.size());
        for(int i = 0; i<listeStations.size(); i++){
            reponse += "Station : "+listeStations.get(i).getNomStation()+"\n";
            if(i != listeStations.size()-1) {
                reponse += "Temps de parcours : " + tmpParcours.get(i) + " min\n";
            }
        }
        return reponse;
    }

    /**
     * Methode qui affiche les informations d'une ligne
     * @param nomLigne la ligne dont l'utilisateur veut les informations
     * @return string les informations de la ligne
     */
    public static String afficherLigne(String nomLigne){
        String reponse = "";
        if(LigneController.lignes.containsKey(nomLigne)){
            reponse += LigneController.lignes.get(nomLigne);
        }else{
            reponse = "La ligne "+nomLigne+" n'existe pas.";
        }
        return reponse;
    }

    /**
     * Méthode qui permet de supprimer une ligne
     * @param nomLigne la ligne à supprimer
     * @return string la ligne supprimée
     */
    public static String supprimerLigne(String nomLigne){
        String reponse;
        if(LigneController.lignes.containsKey(nomLigne)){
            //Suppression de la boisson
            LigneController.lignes.remove(nomLigne);
            reponse = "La ligne "+nomLigne+" a été supprimée.";
        }else{
            reponse = "La ligne "+nomLigne+" n'existe pas.";
        }
        return reponse;
    }

    /**
     * Méthode qui permet de déterminer si une ligne existe déjà
     * @param nomLigne la ligne à identifier
     * @return true si la ligne existe
     */
    public static Boolean ligneExiste(String nomLigne){
        Boolean reponse = false;
        if(LigneController.lignes.containsKey(nomLigne)){
            reponse = true;
        }
        return reponse;
    }

    /**
     * Méthode qui permet d'ajouter une ligne en fonction de plusieurs critères
     * @param nomLigne le nom de la ligne a ajouter
     * @param tempsParcours liste des temps de parcours entre les stations
     * @param listeStation liste des stations de la ligne
     * @return string la ligne a été ajoutée
     */
    public static String ajouterLigne(String nomLigne, ArrayList<Integer> tempsParcours, ArrayList<Station> listeStation){
        String reponse;
        if(!ligneExiste(nomLigne)){
            if(listeStation.size() > 1){
                Ligne l = new Ligne(nomLigne, tempsParcours, listeStation);
                LigneController.lignes.put(nomLigne, l);
                reponse = "La ligne "+ nomLigne +" a été ajoutée avec succès.";
            }else{
                reponse = "Il faut au moins deux stations pour pouvoir créer une ligne.";
            }
        }else{
            reponse = "Cette ligne existe déjà.";
        }
        return reponse;
    }

    /**
     * Méthode qui permet de modifier le temps de parcours d'une ligne
     * @param nomLigne la ligne à modifier
     * @param station1 la premiere station entre laquelle on souhaite changer le temps de parcours
     * @param station2 la deuxieme station entre laquelle on souhaite changer le temps de parcours
     * @param tmpParc le nouveau temps de parcours entre les deux stations
     * @return String la ligne a été modifiée
     */
    public static String modifierLigneTempsParcours(String nomLigne, String station1, String station2, int tmpParc){
        String reponse;
        int posStation1, posStation2;

        //On vérifie que la ligne existe
        if(LigneController.lignes.containsKey(nomLigne)){
            Ligne ligne = LigneController.lignes.get(nomLigne);

            //On vérifie que le temps de parcours saisie est strictement supérieur à 0
            if(tmpParc > 0) {

                //On vérifie que les deux stations sont bien différentes
                if (!station1.equals(station2)) {

                    //On vérifie que les deux stations sont bien présentes dans la ligne
                    if (ligne.trouverStation(station1)) {
                        if (ligne.trouverStation(station2)) {

                            //On récupère la position des deux stations dans la liste
                            posStation1 = ligne.trouverPosListeStation(station1);
                            posStation2 = ligne.trouverPosListeStation(station2);

                            int posTmpParcours = posStation1 - posStation2;

                            //on vérifie que les deux stations sont l'une à la suite de l'autre
                            if (posTmpParcours == 1 || posTmpParcours == -1) {
                                if (posTmpParcours == 1) {
                                    //On modifie le temps de parcours entre les deux
                                    ligne.setTempsParcours(tmpParc,station2,ligne.getListeTempsParcours());
                                    reponse = "Le temps de parcours entre " + station2 + " et "+station1+" a bien été modifié.";
                                }else{
                                    //On modifie le temps de parcours entre les deux
                                    ligne.setTempsParcours(tmpParc,station1,ligne.getListeTempsParcours());
                                    reponse = "Le temps de parcours entre " + station1 + " et "+station2+" a bien été modifié.";
                                }
                            } else {
                                reponse = "La station " + station1 + " et la station " + station2 + " ne sont pas reliées directement.";
                            }

                        } else {
                            reponse = "La station " + station2 + " n'existe pas.";
                        }
                    } else {
                        reponse = "La station " + station1 + " n'existe pas.";
                    }
                } else {
                    reponse = "Les deux stations doivent être différentes";
                }
            }else{
                reponse = "Le temps de parcours doit être strictement supérieur à 0.";
            }
        }else{
            reponse = "La ligne saisie n'existe pas.";
        }
        return reponse;
    }

    /**
     * Méthode qui permet de supprimer une station d'une ligne
     * @param nomLigne la ligne à modifier
     * @param nomStation la station à supprimer
     * @return String la ligne a été supprimée
     */
    public static String modifierLigneSupprimerStation(String nomLigne, String nomStation){
        String reponse;
        int pos;
        //On vérifie que la ligne existe
        if(LigneController.lignes.containsKey(nomLigne)) {
            Ligne ligne = LigneController.lignes.get(nomLigne);

            //On vérifier que la ligne détient la station
            if (ligne.trouverStation(nomStation)) {

                //On vérifier qu'il restera au moins deux stations à la ligne en cas de suppression
                if(ligne.getListeStation().size() > 2) {

                    //On récupère la position de la station dans la liste
                    pos = ligne.trouverPosListeStation(nomStation);

                    if (pos == 0) {
                        ligne.getListeTempsParcours().remove(pos);
                        ligne.getListeStation().remove(pos);
                    }else if(pos == ligne.getListeStation().size() - 1){
                        ligne.getListeTempsParcours().remove(pos-1);
                        ligne.getListeStation().remove(pos);
                    } else {
                        String stationPrec = ligne.getListeStation().get(pos-1).getNomStation();
                        int tmpParcPrec = ligne.getListeTempsParcours().get(pos-1);
                        int tmpParcSuiv = ligne.getListeTempsParcours().get(pos);
                        int somTmpParc = tmpParcPrec + tmpParcSuiv;
                        ligne.setTempsParcours(somTmpParc, stationPrec,ligne.getListeTempsParcours());
                        ligne.getListeTempsParcours().remove(pos);
                        ligne.getListeStation().remove(pos);
                    }
                    reponse = "La station "+nomStation+" a été supprimée de la ligne "+nomLigne+".";
                }else{
                    reponse = "La station "+nomStation+" ne peut pas être supprimer puisque la ligne ne comportera plus qu'une seule station.";
                }
            }else{
                reponse = "La station "+nomStation+" n'existe pas dans la ligne.";
            }
        }else{
            reponse = "La ligne saisie n'existe pas.";
        }

        return reponse;
    }

    /**
     * Méthode qui permet d'ajouter une station en départ, entre 2 ou en Terminus d'une ligne
     * @param choix départ, entre deux ou terminus
     * @param listeStations liste de toutes les stations
     * @param nomLigne nom de la ligne à modifier
     * @param nouvStation nouvelle station à ajouter
     * @param precStation station après laquelle ajouter la nouvelle station
     * @param suivStation station avant laquelle ajouter la nouvelle station
     * @param tmpParcPrec temps de parcours entre la précédente et la nouvelle station
     * @param tmpParcSuiv temps de parcours entre la nouvelle et la station suivante
     * @return string la ligne a été modifiée
     */
    public static String modifierLigneAjouterStation(int choix, HashMap<String, Station> listeStations, String nomLigne, String nouvStation, String precStation, String suivStation, int tmpParcPrec, int tmpParcSuiv){
        String reponse = "";
        //On vérifie que la ligne existe
        if(LigneController.lignes.containsKey(nomLigne)){
            Ligne ligne = LigneController.lignes.get(nomLigne);
            if(!ligne.trouverStation(nouvStation)) {
                switch (choix) {
                    case 1:
                        //Station Départ
                        if(listeStations.containsKey(nouvStation)) {
                            if (ligne.trouverStation(suivStation)) {
                                if (tmpParcSuiv > 0) {
                                    ligne.getListeStation().add(0, listeStations.get(nouvStation));
                                    ligne.getListeTempsParcours().add(0, tmpParcSuiv);
                                    reponse = "L'ajout de la station "+nouvStation+" a été effectué en tant que départ de la ligne "+nomLigne+".";
                                } else {
                                    reponse = "Le temps de parcours entre " + nouvStation + " et " + suivStation + " doit être strictement supérieur à 0.";
                                }
                            } else {
                                reponse = "La station " + suivStation + " n'est pas présente dans la ligne " + nomLigne + ".";
                            }
                        }else{
                            reponse = "La station " + nouvStation + " n'existe pas.";
                        }
                        break;
                    case 2:
                        //Station Terminus
                        if(listeStations.containsKey(nouvStation)) {
                            if (ligne.trouverStation(precStation)) {
                                if (tmpParcPrec > 0) {
                                    ligne.getListeStation().add(listeStations.get(nouvStation));
                                    ligne.getListeTempsParcours().add(tmpParcPrec);
                                    reponse = "L'ajout de la station "+nouvStation+" a été effectué en tant que terminus de la ligne "+nomLigne+".";
                                } else {
                                    reponse = "Le temps de parcours entre " + precStation + " et " + nouvStation + " doit être strictement supérieur à 0.";
                                }
                            } else {
                                reponse = "La station " + precStation + " n'est pas présente dans la ligne " + nomLigne + ".";
                            }
                        }else{
                            reponse = "La station " + nouvStation + " n'existe pas.";
                        }
                        break;
                    case 3:
                        //Entre deux stations
                        if(listeStations.containsKey(nouvStation)) {
                            if(!precStation.equals(suivStation)){
                                if (ligne.trouverStation(precStation)) {
                                    if (ligne.trouverStation(suivStation)) {
                                        if (tmpParcPrec > 0 && tmpParcSuiv > 0) {
                                            int posPrec = ligne.trouverPosListeStation(precStation);
                                            int posSuiv = ligne.trouverPosListeStation(suivStation);
                                            int somPos = posSuiv - posPrec;
                                            if(somPos ==1 || somPos ==-1 ){
                                                if(somPos == -1) {
                                                    int postmp = posPrec;
                                                    posPrec = posSuiv;
                                                    posSuiv = postmp;
                                                }
                                                ligne.getListeStation().add(posSuiv, listeStations.get(nouvStation));
                                                ligne.getListeTempsParcours().set(posPrec, tmpParcPrec);
                                                ligne.getListeTempsParcours().add(posSuiv, tmpParcSuiv);
                                                reponse = "L'ajout de la station " + nouvStation + " a été effectué entre les stations " + precStation + " et " + suivStation + ".";
                                            }else{
                                                reponse = "les stations " + precStation + " et " + suivStation + " ne sont pas directement reliées.";
                                            }
                                        } else {
                                            reponse = "Le temps de parcours entre doit être strictement supérieur à 0.";
                                        }
                                    } else {
                                        reponse = "La station " + suivStation + " n'est pas présente dans la ligne " + nomLigne + ".";
                                    }
                                } else {
                                    reponse = "La station " + precStation + " n'est pas présente dans la ligne " + nomLigne + ".";
                                }
                            } else {
                                reponse = "Les stations précédente et suivante sont les mêmes.";
                            }
                        }else{
                            reponse = "La station " + nouvStation + " n'existe pas.";
                        }
                        break;
                }
            }else{
                reponse = "La station "+nouvStation+" est dejà présente dans la ligne "+nomLigne+"." ;
            }

        }else{
            reponse = "La ligne saisie n'existe pas.";
        }
        return reponse;
    }

    /**
     * Méthode qui permet de modifier l'incident d'une ligne
     * @param nomLigne la ligne qu'on souhaite modifier l'incident
     * @param incident yes/no il y a un incident
     * @return string l'indent a été modifié
     */
    public static String modifierLigneIncident(String nomLigne, String incident){
        String reponse;
        if (ligneExiste(nomLigne)){
            Ligne l = LigneController.lignes.get(nomLigne);
            if (incident.equals("y")) {
                l.setIncident(true);
                reponse = "La ligne "+nomLigne+" possède maintenant un incident.";
            } else {
                l.setIncident(false);
                reponse = "La ligne "+nomLigne+" ne possède maintenant plus d'incident.";
            }
        } else {
            reponse = "La ligne "+nomLigne+" que vous souhaitez modifier n'existe pas.";
        }
        return reponse;
    }
    /**
     * Fonction qui permet de renvoyer sous forme de liste les lignes dont fait partie la station renseignée
     * en paramètre
     * @param nomStation le nom de la station
     * @return une Liste comprenant les lignes dont fait partie la station
     */

    public static List<String> lignesDeLaStation(String nomStation){
        List<String> lignesDeLaStation = new ArrayList<>();
        for(Map.Entry<String, Ligne> entry : LigneController.lignes.entrySet()) {
            ArrayList<Station> stations = entry.getValue().getListeStation();
            for (Station station : stations){
                if (station.getNomStation().equals(nomStation)){
                    lignesDeLaStation.add(entry.getValue().getNomLigne());
                }
            }
        }
        return lignesDeLaStation;
    }

    /**
     * Méthode qui permet de supprimer une station d'une ligne
     * @param nom nom de la station à supprimer
     * @return boolean si la station a ete supprimee
     */
    public static void supprimerStationLigne(String nom) {
        for (HashMap.Entry<String, Ligne> entry : LigneController.lignes.entrySet()) {
            if(entry.getValue().trouverStation(nom) && entry.getValue().getListeStation().size() > 2) {
                    ArrayList<Station> listeStation = entry.getValue().getListeStation();
                    ArrayList<Integer> listeTempsParcours = entry.getValue().getListeTempsParcours();
                    int index = entry.getValue().trouverPosListeStation(nom);
                    // On supprime la valeur du temps de parcours ou on la modifie selon le cas
                    if (index == 0) {
                        listeTempsParcours.remove(0);
                    } else if (index == listeStation.size() - 1) {
                        listeTempsParcours.remove(listeTempsParcours.size() - 1);
                    } else {
                        listeTempsParcours.set(index, listeTempsParcours.get(index) + listeTempsParcours.get(index - 1));
                        listeTempsParcours.remove(index - 1);
                    }
                    listeStation.remove(index);
                    LigneController.lignes.get(entry.getKey()).setListeTempsParcours(listeTempsParcours);
                    LigneController.lignes.get(entry.getKey()).setListeStation(listeStation);
            }
        }
    }
}
