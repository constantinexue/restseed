package constantinexue.guicelike.util;

import java.util.UUID;

public class IDUtils {
    
    public static final String generate() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
