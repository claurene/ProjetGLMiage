package miage.model;

/**
 * Classe représentant un arc, cad. un bout de ligne liant deux stations adjacentes
 */
public class Arc {
    private String origine;
    private String destination;
    private int tempsParcours;
    private String nomLigne;
    private String directionAvant;
    private String directionInverse;

    // Constructeur
    public Arc(String origine, String destination, int tempsParcours, String nomLigne, String directionAvant, String directionInverse) {
        this.origine = origine;
        this.destination = destination;
        this.tempsParcours = tempsParcours;
        this.nomLigne = nomLigne;
        this.directionAvant = directionAvant;
        this.directionInverse = directionInverse;
    }

    // Getters & Setters
    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getTempsParcours() {
        return tempsParcours;
    }

    public void setTempsParcours(int tempsParcours) {
        this.tempsParcours = tempsParcours;
    }

    public String getNomLigne() {
        return nomLigne;
    }

    public void setNomLigne(String nomLigne) {
        this.nomLigne = nomLigne;
    }

    public String getDirectionAvant() {
        return directionAvant;
    }

    public void setDirectionAvant(String directionAvant) {
        this.directionAvant = directionAvant;
    }

    public String getDirectionInverse() {
        return directionInverse;
    }

    public void setDirectionInverse(String directionInverse) {
        this.directionInverse = directionInverse;
    }

    @Override
    public String toString() {
        return "Arc{" +
                "origine='" + origine + '\'' +
                ", destination='" + destination + '\'' +
                ", tempsParcours=" + tempsParcours +
                ", nomLigne='" + nomLigne + '\'' +
                ", directionAvant='" + directionAvant + '\'' +
                ", directionInverse='" + directionInverse + '\'' +
                '}';
    }
}
