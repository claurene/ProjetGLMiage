package miage.controller;

import miage.model.Ligne;

import java.util.HashMap;

public class LigneController {
    Ligne ligne;

    /**
     * Méthode qui permet de lister toutes les lignes existantes
     * @param listeLignes arraylist contenant chacune de nos lignes
     * @return booleen pour savoir s'il existe des lignes
     */
    public boolean listeLigne(HashMap<String, Ligne> listeLignes){
        boolean listeExiste = false;
        System.out.println("Les lignes disponibles sont : ");
        System.out.println(listeLignes.get("paris"));
        if(listeLignes.size()>0){
            ligne.afficherListeLigne(listeLignes);
            listeExiste = true;
        } else {
            System.out.println("Il n'y a aucune ligne de métro disponible.");
        }
        return listeExiste;
    }

    public String afficherLigne(HashMap<String, Ligne> listeLignes, String nomLigne){
        if(listeLignes.containsKey(nomLigne)){
            System.out.println(listeLignes.get(nomLigne));
        }
        return "";
    }
}
