package miage.model;

import miage.controller.ItineraireController;
import miage.controller.LigneController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArcTest {


    @Test
    @DisplayName("Creation d'un Arc")
    void creationArc(){
        Arc a = new Arc("gare du nord","gare de l'est",2,"Ligne A","gare de l'est","gare du nord");
        assertAll(
                () -> assertTrue(a.getOrigine()=="gare du nord"),
                () -> assertTrue(a.getDestination()=="gare de l'est"),
                () -> assertTrue(a.getTempsParcours()==2),
                () -> assertTrue(a.getDirectionAvant()=="gare de l'est"),
                () -> assertTrue(a.getDirectionInverse()=="gare du nord"),
                () -> assertTrue(a.getNomLigne()=="Ligne A")
        );
    }
}
