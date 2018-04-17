package miage.view;

import miage.controller.JSONProcessor;
import miage.model.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Main {
    // Création du logger
    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    /*
     * Lancement du programme principal
     */
    public static void main(String args[]){

        // Liste des lignes de métro
        ArrayList<Ligne> lignes = new ArrayList<Ligne>();

        // Chargement des lignes de métro
        File dir = new File("src/main/resources");
        if (dir.listFiles() != null) {
            for (File fichier : dir.listFiles()) {
                Ligne l = null;
                try {
                    l = JSONProcessor.deserialize(fichier.getPath());
                    lignes.add(l);
                } catch (IOException e) {
                    LOG.warning("Erreur de désérialisation");
                }
            }
        } else {
            LOG.info("Aucune donnée trouvée");
        }

        // Sauvegarde des lignes de métro

        for (Ligne l : lignes) {
            String filename = "src/main/resources/"+l.getNomLigne()+".json";
            try {
                JSONProcessor.serialize(l,filename);
            } catch (IOException e) {
                LOG.warning("Erreur de sérialisation");
            }
        }
    }
}
