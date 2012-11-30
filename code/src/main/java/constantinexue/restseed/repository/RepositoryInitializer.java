package constantinexue.restseed.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.persist.PersistService;

import constantinexue.restseed.entity.UserEntity;
import constantinexue.restseed.util.IDUtils;
import constantinexue.restseed.util.PropertiesNames;

@Singleton
public class RepositoryInitializer {
    
    @Named(PropertiesNames.CONNECTION_URL)
    @Inject
    private String connectionUrl;
    
    @Inject
    private UserRepository userRepository;
    
    @Inject
    public RepositoryInitializer(PersistService service) {
        service.start();
    }
    
    @Inject
    public void initialize() throws Exception {
        // 初始化数据库，建表
        Connection connection = DriverManager.getConnection(connectionUrl);
        executeSqlScript(connection, new File("./conf/create.sql"));
        connection.close();
        
        // 创建一些测试用户
        for (int i = 0; i < 10; i++) {
            UserEntity user = new UserEntity();
            user.setId(IDUtils.generate())
                .setName("User" + i);
            userRepository.persist(user);
        }
    }
    
    /**
     * According to http://stackoverflow.com/questions/1497569/how-to-execute-sql-script-file-using-jdbc
     * 
     * @param conn
     * @param inputFile
     */
    private void executeSqlScript(Connection conn, File inputFile) {
        // Delimiter
        String delimiter = ";";
        
        // Create scanner
        Scanner scanner;
        try {
            scanner = new Scanner(inputFile).useDelimiter(delimiter);
        }
        catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return;
        }
        
        // Loop through the SQL file statements
        Statement currentStatement = null;
        while (scanner.hasNext()) {
            
            // Get statement
            String rawStatement = scanner.next() + delimiter;
            try {
                // Execute statement
                currentStatement = conn.createStatement();
                currentStatement.execute(rawStatement);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                // Release resources
                if (currentStatement != null) {
                    try {
                        currentStatement.close();
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                currentStatement = null;
            }
        }
    }
}
