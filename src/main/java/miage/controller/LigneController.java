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
            reponse = nomLigne+" a été supprimé \n";
        }else{
            reponse = "La ligne "+nomLigne+" n'existe pas.";
        }
        return reponse;
    }

    public Boolean ligneExiste(String nomLigne){
        Boolean reponse = false;
        if(this.lignes.containsKey(nomLigne)){
            reponse = true;
        }
        return reponse;
    }

    public String ajouterLigne(String nomLigne, int idLigne, ArrayList<Integer> tempsParcours, ArrayList<Station> listeStation){
        Ligne l = new Ligne(idLigne, nomLigne, tempsParcours, listeStation);
        this.lignes.put(nomLigne, l);
        return "La ligne a bien été ajoutée";
    }

    public String modifierLigne(String nomLigne, int idLigne, ArrayList<Integer> tempsParcours, ArrayList<Station> listeStation){
        String reponse = "";
        Ligne l = new Ligne(idLigne, nomLigne, tempsParcours, listeStation);
        if (ligneExiste(nomLigne)){
            this.lignes.put(nomLigne,l);
            reponse = "La ligne a bien été modifiée";
        } else {
            reponse = "La ligne que vous souhaitez modifier n'existe pas";
        }
        return reponse;
    }

    public String modifierLigneIncident(String nomLigne, boolean inc){
        String reponse = "";
        if (ligneExiste(nomLigne)){
            Ligne l = this.lignes.get(nomLigne);
            l.setIncident(inc);
            if (inc) {
                reponse = "La ligne possède maintenant un incident";
            } else {
                reponse = "La ligne ne possède maintenant plus d'incident";
            }
        } else {
            reponse = "La ligne que vous souhaitez modifier n'existe pas";
        }
        return reponse;
    }

}
