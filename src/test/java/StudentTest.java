import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    private static final long USER_ID = 1L;
    private static final String NIC_NAME = "Вася";
    private static Student student;
    private static String stringName = "Петя";
    private static int goodQuestion = 2;


    @BeforeAll
    public static void SetUp() {
        student = new Student(USER_ID, NIC_NAME);
        student.setFirstName(stringName);
        student.setGoodQuestion(goodQuestion);
    }

    @Test
    void getFirstNameTest() {
        String expectedName = student.getFirstName();
        assertEquals(expectedName, NIC_NAME);
    }

    @Test
    void getIdTest() {
        Long expectedId = student.getId();
        assertEquals(expectedId, USER_ID);
    }

    @Test
    void setFirstName() {
        String expectedNameSet = student.getFirstName();
        assertEquals(expectedNameSet, stringName);
    }

    @Test
    void getGoodQuestionTest() {
        int expectedGoodQuestion = student.getGoodQuestion();
        assertEquals(expectedGoodQuestion, goodQuestion);
    }
}
