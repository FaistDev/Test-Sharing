package faistdev.at.test_sharingkaindorf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private Connection con;
    private String username="";
    private String password="";
    private static Database theInstance;
    private boolean dbRes=false;
    private boolean dbError=false;

    private Database()  {

    }

    public boolean buildDatabase() throws SQLException {

        /**
         * Start a Thread, if database username and database password are set, that establishes the Database Connection
         * Call method createTables() and save result
         * Wait while tables are created
         */

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                dbError=false;
                dbRes=false;
                try{
                    con = DriverManager.getConnection("jdbc:postgresql://10.0.2.2/test-sharing", username, password);
                    dbRes=createTables();
                }catch (Exception e){
                    dbError=true;
                    e.printStackTrace();
                }
            }
        });
        if (username != "" && password != "") {
            th.start();
            while(con==null || dbRes==false){
                //wait
                if(dbError){
                    return false;
                }
            }

            return true;
        }else{
            return false;
        }
    }

    public synchronized static Database getInstance() throws SQLException {
        if(theInstance==null){
            theInstance=new Database();
        }
        return theInstance;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public boolean createTables() throws SQLException {

        /**
         * Create tables: users
         */

        String sql = "DROP TABLE IF EXISTS users;"
                + "CREATE TABLE users"
                + "("
                + "ID SERIAL NOT NULL PRIMARY KEY,"
                + "Username character varying NOT NULL,"
                + "Password character varying NOT NULL," +
                "Mail character varying NOT NULL"
                + ")";

        Statement stat = con.createStatement();
        stat.executeUpdate(sql);
        return true;
    }

    public Connection getCon() {
        return con;
    }

    public boolean saveUser(String username, String email,String password) throws SQLException{

        /**
         * Insert a user into table users using PreparedStatement
         */

        String sql = "INSERT INTO users(" +
                "username, password, mail)" +
                "VALUES (?, ?, ?);";
        PreparedStatement preparedStatement;
        preparedStatement=con.prepareStatement(sql);
        preparedStatement.setString(1,username);
        preparedStatement.setString(2,password);
        preparedStatement.setString(3,email);
        preparedStatement.executeUpdate();

        return true;
    }
}
