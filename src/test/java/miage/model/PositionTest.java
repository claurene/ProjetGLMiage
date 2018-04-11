package miage.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;

@DisplayName("Position")
public class PositionTest {
    private Position position;

    @Test
    @DisplayName("Initialisation")
    void initPosition(){
        // Initialisation d'une position aléatoire
        assertTrue(position != null);
    }
}
