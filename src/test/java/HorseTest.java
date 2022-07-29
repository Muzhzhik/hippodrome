
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;
import java.util.stream.Stream;

/**
 * @author Sergey Muzhzukhin
 * ¯\_(ツ)_/¯
 */
@ExtendWith(MockitoExtension.class)
public class HorseTest {

    private final String defaultNotNullHorseName = "NotNullAndEmptyName";
    private final double randomNegativeValue;
    private final double randomPositiveValue;
    private final Horse horse;

    {
        Random random = new Random();
        randomPositiveValue = random.nextDouble();
        randomNegativeValue = randomPositiveValue * -1;
        horse = new Horse(defaultNotNullHorseName, randomPositiveValue, randomPositiveValue);
    }

    @Test
    @DisplayName("Проверить, что при передаче в конструктор первым параметром null, " +
            "будет выброшено IllegalArgumentException")
    public void nullNameTest() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Horse(null, randomPositiveValue, randomPositiveValue));
    }

    @Test
    @DisplayName("Проверить, что при передаче в конструктор первым параметром null," +
            " выброшенное исключение будет содержать сообщение \"Name cannot be null.\"")
    public void nullNameRightExceptionMessageTest() {
        String expectedErrorMessage = "Name cannot be null.";
        try {
            new Horse(null, randomPositiveValue, randomPositiveValue);
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals(expectedErrorMessage, e.getMessage());
        }
    }

    @ParameterizedTest
    @DisplayName("Проверить, что при передаче в конструктор первым параметром пустой строки или строки содержащей " +
            "только пробельные символы (пробел, табуляция и т.д.), будет выброшено IllegalArgumentException")
    @MethodSource("blankNameParamsFactory")
    public void emptyNameExceptionTest(String param) {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Horse(param, randomPositiveValue, randomPositiveValue));
    }

    @ParameterizedTest
    @DisplayName("Проверить, что при передаче в конструктор первым параметром пустой строки или строки содержащей " +
            "только пробельные символы (пробел, табуляция и т.д.), выброшенное исключение будет содержать " +
            "сообщение \"Name cannot be blank.\"")
    @MethodSource("blankNameParamsFactory")
    public void emptyNameMessageTest(String param) {
        String expectedErrorMessage = "Name cannot be blank.";
        try {
            new Horse(param, randomPositiveValue, randomPositiveValue);
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals(expectedErrorMessage, e.getMessage());
        }
    }

    static Stream<String> blankNameParamsFactory() {
        return Stream.of("", " ", " \t", "\n", "\b");
    }

    @Test
    @DisplayName("Проверить, что при передаче в конструктор вторым параметром отрицательного числа, " +
            "будет выброшено IllegalArgumentException")
    public void negativeSpeedExceptionTest() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Horse(defaultNotNullHorseName, randomNegativeValue, randomPositiveValue));
    }

    @Test
    @DisplayName("Проверить, что при передаче в конструктор вторым параметром отрицательного числа, " +
            "выброшенное исключение будет содержать сообщение \"Speed cannot be negative.\"")
    public void negativeSpeedExceptionMessageTest() {
        String expectedErrorMessage = "Speed cannot be negative.";
        try {
            new Horse(defaultNotNullHorseName, randomNegativeValue, randomPositiveValue);
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals(expectedErrorMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Проверить, что при передаче в конструктор третьим параметром отрицательного числа, " +
            "будет выброшено IllegalArgumentException")
    public void negativeDistanceExceptionTest() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Horse(defaultNotNullHorseName, randomPositiveValue, randomNegativeValue));
    }

    @Test
    @DisplayName("Проверить, что при передаче в конструктор третьим параметром отрицательного числа, выброшенное " +
            "исключение будет содержать сообщение \"Distance cannot be negative.\"")
    public void negativeDistanceExceptionMessageTest() {
        String expectedErrorMessage = "Distance cannot be negative.";
        try {
            new Horse(defaultNotNullHorseName, randomPositiveValue, randomNegativeValue);
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals(expectedErrorMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Проверить, что метод возвращает строку, которая была передана первым параметром в конструктор")
    public void getNameTest() {
        Assertions.assertEquals(defaultNotNullHorseName, horse.getName());
    }

    @Test
    @DisplayName("Проверить, что метод возвращает число, которое было передано вторым параметром в конструктор")
    public void getSpeedTest() {
        Assertions.assertEquals(randomPositiveValue, horse.getSpeed());
    }

    @Test
    @DisplayName("Проверить, что метод возвращает число, которое было передано третьим параметром в конструктор\n" +
            "Проверить, что метод возвращает ноль, если объект был создан с помощью конструктора с двумя параметрами")
    public void getDistanceTest() {
        Assertions.assertEquals(randomPositiveValue, horse.getDistance());
        Horse testHorse = new Horse(defaultNotNullHorseName, randomPositiveValue);
        Assertions.assertEquals(0, testHorse.getDistance());
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.2, 3.5})
    public void moveTest(double param) {
        try (MockedStatic<Horse> mockedStaticHorse = Mockito.mockStatic(Horse.class)) {
            horse.move();
            mockedStaticHorse.verify(() -> Horse.getRandomDouble(0.2, 0.9));
            mockedStaticHorse.when(() -> Horse.getRandomDouble(Mockito.anyDouble(), Mockito.anyDouble())).thenReturn(param);
            double expectedDistance = horse.getDistance() + horse.getSpeed() * param;
            horse.move();
            Assertions.assertEquals(expectedDistance, horse.getDistance());
        }
    }
}
