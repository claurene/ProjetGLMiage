package miage.controller;

import miage.model.Ligne;

import java.util.HashMap;
import java.util.Map;

public class LigneController {

    /**
     * Méthode qui permet de lister toutes les lignes existantes
     * @param listeLignes HashMap contenant chacune de nos lignes
     * @return booleen pour savoir s'il existe des lignes
     */
    public void listeLigne(HashMap<String, Ligne> listeLignes){
        if(listeLignes.size()>0){
            for(Map.Entry<String, Ligne> ligne : listeLignes.entrySet()){
                System.out.println(ligne.getKey());
            }
        } else {
            System.out.println("Il n'y a aucune ligne de métro disponible.");
        }
    }

    public void afficherLigne(HashMap<String, Ligne> listeLignes, String nomLigne){
        if(listeLignes.containsKey(nomLigne)){
            System.out.println(listeLignes.get(nomLigne));
        }else{
            System.out.println("La ligne "+nomLigne+" n'existe pas.");
        }
    }
}
