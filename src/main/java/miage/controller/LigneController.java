package miage.controller;

import miage.model.Ligne;
import miage.model.Station;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class LigneController {
    // Création du logger
    private static final Logger LOG = Logger.getLogger(LigneController.class.getName());

    // Liste des lignes de métro
    HashMap<String,Ligne> lignes = new HashMap<String,Ligne>();

    public HashMap<String, Ligne> getLignes() {
        return lignes;
    }

    public void setLignes(HashMap<String, Ligne> lignes) {
        this.lignes = lignes;
    }

    /**
     * Méthode qui permet de recuperer les donnees du fichier lignes.txt
     */
    public void initialisationLignes(){
        FileInputStream in = null;
        ObjectInputStream ois = null;

        try {
            in = new FileInputStream("src/main/resources/lignes.txt");
            try {
                ois = new ObjectInputStream(in);
                while(true) {
                    try {
                        Ligne l = (Ligne) ois.readObject();
                        this.lignes.put(l.getNomLigne(),l);
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
    public void sauvegardeLignes(){
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
            for (HashMap.Entry<String,Ligne> entry : this.lignes.entrySet()) {
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
    public String listeLigne(){
        String reponse = "";
        if(this.lignes.size()>0){
            for(Map.Entry<String, Ligne> ligne : this.lignes.entrySet()){
                reponse += ligne.getKey()+"\n";
            }
        } else {
            reponse = "noLigne";
        }
        return reponse;
    }

    /**
     * Methode qui affiche les informations d'une ligne
     * @param nomLigne la ligne dont l'utilisateur veut les informations
     * @return string les informations de la ligne
     */
    public String afficherLigne(String nomLigne){
        String reponse = "";
        if(this.lignes.containsKey(nomLigne)){
            reponse += this.lignes.get(nomLigne);
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
    public String supprimerLigne(String nomLigne){
        String reponse = "";
        if(this.lignes.containsKey(nomLigne)){
            //Suppression de la boisson
            this.lignes.remove(nomLigne);
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
    public Boolean ligneExiste(String nomLigne){
        Boolean reponse = false;
        if(this.lignes.containsKey(nomLigne)){
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
    public String ajouterLigne(String nomLigne, ArrayList<Integer> tempsParcours, ArrayList<Station> listeStation){
        String reponse;
        if(!ligneExiste(nomLigne)){
            if(listeStation.size() > 1){
                Ligne l = new Ligne(nomLigne, tempsParcours, listeStation);
                this.lignes.put(nomLigne, l);
                reponse = "La ligne "+ nomLigne +" a été ajoutée avec succès.";
            }else{
                reponse = "Il faut au moins deux stations pour pouvoir créer une ligne.";
            }
        }else{
            reponse = "Cette ligne existe déjà.";
        }
        return reponse;
    }

    public String modifierLigne(String nomLigne, ArrayList<Integer> tempsParcours, ArrayList<Station> listeStation){
        String reponse = "";
        Ligne l = new Ligne(nomLigne, tempsParcours, listeStation);
        if (ligneExiste(nomLigne)){
            this.lignes.put(nomLigne,l);
            reponse = "La ligne a bien été modifiée";
        } else {
            reponse = "La ligne que vous souhaitez modifier n'existe pas";
        }
        return reponse;
    }

    /**
     * Méthode qui permet de modifier l'incident d'une ligne
     * @param nomLigne la ligne qu'on souhaite modifier l'incident
     * @param incident yes/no il y a un incident
     * @return string l'indent a été modifié
     */
    public String modifierLigneIncident(String nomLigne, String incident){
        String reponse = "";
        if (ligneExiste(nomLigne)){
            Ligne l = this.lignes.get(nomLigne);
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

    public boolean supprimerStationLigne(String nom) {
        boolean suppressionpossible = true;
        boolean suppressioneffectue = false;
        // On commence par vérifier que la station existe et si la suppression est possible
        // C'est a dire que la station n'est pas l'une des deux seules stations d'une ligne
        for (HashMap.Entry<String, Ligne> entry : this.lignes.entrySet()) {
            if (entry.getValue().trouverStation(nom) && entry.getValue().getListeStation().size() <= 2) {
                suppressionpossible = false;
            }
        }
        // Si la suppression est possible alors
        if (suppressionpossible) {
            // Pour chaque ligne où elle existe
            for (HashMap.Entry<String, Ligne> entry : this.lignes.entrySet()) {
                if (entry.getValue().trouverStation(nom)) {
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
                    this.lignes.get(entry.getKey()).setListeTempsParcours(listeTempsParcours);
                    this.lignes.get(entry.getKey()).setListeStation(listeStation);
                    suppressioneffectue = true;
                }
            }
        }
        return suppressioneffectue;
    }
}
