import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Sergey Muzhzukhin
 * ¯\_(ツ)_/¯
 */
public class HippodromeTest {

    List<Horse> horseList;

    @BeforeEach
    public void beforeEachInit() {
        horseList = new ArrayList<>();
    }

    @Test
    @DisplayName("Проверить, что при передаче в конструктор null, будет выброшено IllegalArgumentException")
    public void constructorNullExceptionTest() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(null));
    }

    @Test
    @DisplayName("Проверить, что при передаче в конструктор null, выброшенное исключение будет содержать " +
            "сообщение \"Horses cannot be null.\"")
    public void constructorNullExceptionMessageTest() {
        String expectedErrorMessage = "Horses cannot be null.";
        try {
            new Hippodrome(null);
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals(expectedErrorMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Проверить, что при передаче в конструктор пустого списка, будет выброшено IllegalArgumentException")
    public void constructorEmptyListExceptionTest() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(horseList));
    }

    @Test
    @DisplayName("Проверить, что при передаче в конструктор пустого списка, выброшенное исключение будет " +
            "содержать сообщение \"Horses cannot be empty.\"")
    public void constructorEmptyListExceptionMessageTest() {
        String expectedErrorMessage = "Horses cannot be empty.";
        try {
            new Hippodrome(horseList);
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals(expectedErrorMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Проверить, что метод возвращает список, который содержит те же объекты и в " +
            "той же последовательности, что и список который был передан в конструктор. При создании " +
            "объекта Hippodrome передай в конструктор список из 30 разных лошадей")
    public void getHorsesTest() {
        for (int i = 0; i < 30; i++) {
            horseList.add(new Horse("Horse" + i, Mockito.anyDouble(), Mockito.anyDouble()));
        }
        Hippodrome hippodrome = new Hippodrome(horseList);
        Assertions.assertArrayEquals(horseList.toArray(), hippodrome.getHorses().toArray());
    }

    @Test
    @DisplayName("Проверить, что метод вызывает метод move у всех лошадей. При создании объекта Hippodrome " +
            "передай в конструктор список из 50 моков лошадей и воспользуйся методом verify")
    public void moveTest() {
        final int horseCount = 50;
        for (int i = 0; i < horseCount; i++) {
            horseList.add(Mockito.mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(horseList);
        hippodrome.move();
        for(Horse horse : horseList) {
            Mockito.verify(horse).move();
        }
    }

    @Test
    @DisplayName("Проверить, что метод возвращает лошадь с самым большим значением distance")
    public void getWinnerTest() {
        for (int i = 0; i < 30; i++) {
            horseList.add(new Horse("Horse" + i, Mockito.anyDouble(), 0.1 + i));
        }
        Hippodrome hippodrome = new Hippodrome(horseList);
        Horse expectedHorse = horseList.stream().max(Comparator.comparingDouble(Horse::getDistance)).orElse(null);
        Assertions.assertEquals(expectedHorse, hippodrome.getWinner());
    }
}
