package unittest;

import com.constx.Configuration;
import com.constx.Console;
import com.constx.Environment;
import com.constx.Environment.Profile;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class UnitTest {

    public static String VERSION = "0.1.0";

    @BeforeClass
    public static void setup() {
        Environment.config(VERSION, Profile.DEVELOPMENT);
    }

    @Test
    public void environment() throws Exception {
        Environment.current().isDevelopment();
        Assert.assertTrue(Environment.current().isDevelopment());
        Assert.assertFalse(Environment.current().isProduction());
        Assert.assertTrue(Environment.current().processId() > 0);
        Assert.assertEquals(VERSION, Environment.current().version());
    }

    @Test
    public void configuration() throws Exception {
        Configuration config = Configuration.load("app.properties");
        Assert.assertEquals("xk", config.getString("name"));
        Assert.assertEquals(31, (int)config.getInteger("age"));
        Assert.assertNull(config.getProperties());
        Assert.assertNull(config.getString("name2"));
        Assert.assertNull(config.getInteger("age2"));
    }

    @Test
    public void console() throws Exception {
        Console.out("%s", 123);
        Console.out(new StringBuilder());
        Console.err("%s", 123);
        Console.err(new StringBuilder());
    }
}
