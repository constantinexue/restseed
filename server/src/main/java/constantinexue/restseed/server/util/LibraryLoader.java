package constantinexue.restseed.server.util;

import java.io.IOException;
import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LibraryLoader {
    
    private static final Logger logger = LoggerFactory.getLogger(LibraryLoader.class);
    
    private static String libFolder;
    
    private static OSTypes osType;
    
    static {
        libFolder = System.getProperty("user.dir") + "/natives/";
        String osName = System.getProperty("os.name");
        String osArch = System.getProperty("os.arch");
        if (osName.startsWith("Windows")) {
            osType = osArch.equals("amd64") ? OSTypes.WIN64 : OSTypes.WIN32;
        }
        else if (osName.startsWith("Linux")) {
            osType = osArch.equals("amd64") ? OSTypes.LINUX64 : OSTypes.LINUX32;
        }
        else {
            throw new RuntimeException("OS Type NOT supported");
        }
    }
    
    public static void loadAll() throws Exception {
        addDir(libFolder);
        switch (osType) {
            case WIN64:
                load("sigar/sigar-amd64-winnt.dll");
                break;
            case LINUX64:
                load("sigar/libsigar-amd64-linux.so");
                break;
            default:
                break;
        }
    }
    
    public static void addDir(String s) throws IOException {
        try {
            Field field = ClassLoader.class.getDeclaredField("usr_paths");
            field.setAccessible(true);
            String[] paths = (String[])field.get(null);
            for (int i = 0; i < paths.length; i++) {
                if (s.equals(paths[i])) {
                    return;
                }
            }
            String[] tmp = new String[paths.length + 1];
            System.arraycopy(paths, 0, tmp, 0, paths.length);
            tmp[paths.length] = s;
            field.set(null, tmp);
        }
        catch (IllegalAccessException e) {
            throw new IOException("Failed to get permissions to set library path");
        }
        catch (NoSuchFieldException e) {
            throw new IOException("Failed to get field handle to set library path");
        }
    }
    
    public static void load(String... libraryNames) {
        for (String libraryName : libraryNames) {
            String libPath = libFolder + libraryName;
            System.load(libPath);
            
            logger.debug("Loaded native library: {}", libPath);
        }
    }
    
    enum OSTypes {
        WIN32,
        WIN64,
        LINUX32,
        LINUX64
    }
}
