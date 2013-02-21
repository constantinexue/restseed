package constantinexue.restseed.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public abstract class SQLHelper {
    
    public static void executeScript(String connectionUrl, String scriptFilePath) throws SQLException {
        // 初始化数据库，建表
        Connection connection = DriverManager.getConnection(connectionUrl);
        executeSqlScript(connection, new File(scriptFilePath));
        connection.close();
    }
    
    /**
     * According to http://stackoverflow.com/questions/1497569/how-to-execute-sql-script-file-using-jdbc
     * 
     * @param conn
     * @param inputFile
     */
    private static void executeSqlScript(Connection conn, File inputFile) {
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
