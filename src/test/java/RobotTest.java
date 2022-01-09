import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class RobotTest {


    public boolean isRobotBounded(String instructions) {
        int sumUp = 0, sumRight = 0, dir = 0;
        for (char instr : instructions.toCharArray()) {

            switch (instr) {
                case 'G':
                    sumUp += dir % 2 == 0 ? 1 - dir : 0;
                    sumRight += dir % 2 == 0 ? 0 : 2 - dir;
                    break;
                case 'L':
                    dir = ((dir + 3) % 4);
                    break;
                case 'R':
                    dir = (dir + 1) % 4;
                    break;
            }
        }

        return (sumUp == 0 && sumRight == 0)
                || dir != 0;

    }


    @Test
    public void testRobot() {
        assertTrue(isRobotBounded("LR"));

        assertFalse(isRobotBounded("GLGLGGLGL"));

        assertTrue(isRobotBounded("LLG"));
        assertTrue(isRobotBounded("LLGRL"));

        assertTrue(isRobotBounded("GL"));

        assertFalse(isRobotBounded("LLLLG"));

        assertTrue(isRobotBounded("LLLL"));

        assertTrue(isRobotBounded("GLLG"));

        assertTrue(isRobotBounded("GGLLGG"));
        assertTrue(isRobotBounded("GRRG"));
        assertFalse(isRobotBounded("G"));
        assertTrue(isRobotBounded("L"));
        assertTrue(isRobotBounded("R"));
        assertFalse(isRobotBounded("GG"));
        assertTrue(isRobotBounded("LL"));
        assertTrue(isRobotBounded("RL"));
        assertTrue(isRobotBounded("RR"));

        assertTrue(isRobotBounded("GLLGG"));

    }
}
