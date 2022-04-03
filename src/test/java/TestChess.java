import cz.cvut.fel.pjv.aspone.utils.Writer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Mukan Atazhanov
 * @project atazhmuk
 * @created 03/04/2022 - 23:18
 */

public class TestChess {

    @ParameterizedTest(name = "Test if {0} is converted in {1}")
    @CsvSource({"0,a", "1,b", "2, c", "3,d", "4,e","5,f", "6,g","7,h"})
    public void utilityTest_xToLetterTest(int x, String result){
        //arrange
        Writer helper = new Writer();
        int xCoordinate = x;
        String expectedResult = result;

        //act
        String realResult = helper.xToLetter(x);

        //arrange
        assertEquals(expectedResult,realResult);
    }

    @ParameterizedTest(name = "Test if {0} is converted in {1}")
    @CsvSource({"0,8", "1,7", "2, 6", "3,5", "4,4","5,3", "6,2","7,1"})
    public void utilityTest_yInvertTest(int y, int result){
        //arrange
        Writer helper = new Writer();
        int yCoordinate = y;
        int expectedResult = result;

        //act
        int realResult = helper.yInvert(y);

        //arrange
        assertEquals(expectedResult,realResult);
    }
}
