import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

/**
 * @author Sergey Muzhzukhin
 * ¯\_(ツ)_/¯
 */
public class MainTest {

    @Test
    @Timeout(value = 22, unit = TimeUnit.SECONDS)
    @Disabled
    public void mainWorkTimeTest() throws Exception {
        Main.main(null);
    }
}
