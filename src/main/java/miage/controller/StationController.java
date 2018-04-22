package miage.controller;

import miage.model.Station;

import java.io.*;
import java.util.HashMap;
import java.util.logging.Logger;

public class StationController {
    // Création du logger
    private static final Logger LOG = Logger.getLogger(StationController.class.getName());

    //Liste des stations de metro
    HashMap<String,Station> stations = new HashMap<String,Station>();

    /**
     * Méthode qui permet de recuperer les donnees du fichier stations.txt
     */
    public void initialisationStations(){
        FileInputStream in = null;
        ObjectInputStream ois = null;

        try {
            in = new FileInputStream("src/main/resources/stations.txt");
            try {
                ois = new ObjectInputStream(in);
                while(true) {
                    try {
                        Station s = (Station) ois.readObject();
                        stations.put(s.getNomStation(),s);
                    } catch (EOFException e){
                        break;
                    }
                }

            } catch (StreamCorruptedException e) {
                // Fichier corrompu
                LOG.warning("Fichier stations.txt corrompu");
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
            LOG.warning("Aucun fichier stations.txt trouvé");
        }
    }

    /**
     * Methode qui permet de sauvegarder les nouvelles donnees dans le fichier stations.txt
     */
    public void sauvegardeStations(){
        FileOutputStream out=null;
        ObjectOutputStream oos=null;

        try {
            out = new FileOutputStream("src/main/resources/stations.txt");
        } catch (FileNotFoundException e) {
            // Fichier non trouvé : création du fichier
            LOG.info("Création d'un fichier stations.txt");
        }

        try {
            oos = new ObjectOutputStream(out);
            for (HashMap.Entry<String,Station> entry : stations.entrySet()) {
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
}
