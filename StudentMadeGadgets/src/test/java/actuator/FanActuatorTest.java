package actuator;

import greenhouse.Greenhouse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the FanActuator
 */
class FanActuatorTest {

  @Test void checkIfNewFanActuatorHasZeroSpeed() {
    String id = "67";
    Greenhouse greenhouse = new Greenhouse(5,"TestGreenhouse");
    FanActuator fanActuator = new FanActuator(id, greenhouse);

    assertEquals(0, fanActuator.getSpeed());
  }
}