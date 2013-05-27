package constantinexue.restseed.util;

import java.util.Random;
import java.util.UUID;

public abstract class IDUtils {
    
    public static final String generate() {
        return generateLongId();
    }
    
    public static final String generateLongId(){
        return UUID.randomUUID().toString().replace("-", "");
        
    }
    
    public static final String generateShortId(){
        Random random = new Random(System.currentTimeMillis());
        int id = random.nextInt(900000) + 100000;
        
        return Integer.toString(id);
    }
}
