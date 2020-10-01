package fi.unto55.vaults;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//TODO: YML CONFIG
public class SQLs {
  public static String HOST = Main.getPlugin(Main.class).getConfig().getString("mysql.host");
  public static String DATABASE = Main.getPlugin(Main.class).getConfig().getString("mysql.db");
  public static String USER = Main.getPlugin(Main.class).getConfig().getString("mysql.user");
  public static String PASS = Main.getPlugin(Main.class).getConfig().getString("mysql.pass");
  final static String url = "jdbc:mysql://" + HOST + ":" + Main.getPlugin(Main.class).getConfig().getString("mysql.port") + "/" + DATABASE;
  static String createTable = "CREATE TABLE IF NOT EXISTS `inventories` ( `vaultid` VARCHAR(999) NOT NULL, `base`    LONGTEXT NOT NULL, PRIMARY KEY (`vaultid`) ) engine=myisam DEFAULT charset=latin1;";
  static boolean connected = false;
  static Connection connection;
  static String nullinvbase = "rO0ABXcEAAAAG3BwcHBwcHBwcHBwcHBwcHBwcHBwcHBwcHBwcA==";
  static PreparedStatement table;

  public static void Connect() {
    System.out.println(url);
    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch(ClassNotFoundException e) {
      e.printStackTrace();
      System.err.println("jdbc driver unavailable!");
      return;
    }
    try {
      connection = DriverManager.getConnection(url, USER, PASS);@SuppressWarnings("unused")
      boolean connected = true;

    } catch(SQLException e) {
      e.printStackTrace();@SuppressWarnings("unused")
      boolean connected = false;
    }
    try {
      PreparedStatement myPreparedStatement = connection.prepareStatement(createTable);

      myPreparedStatement.executeUpdate();
    } catch(SQLException e) {
      e.printStackTrace();
    }
  }

  public static void Disconnect() {
    try {
      if (connection != null && !connection.isClosed()) {
        connection.close();
      }
    } catch(Exception e) {
      e.printStackTrace();

    }

  }
  public static void saveString(int number, String pn, String base) throws SQLException {
    Connect();
    String sql = "INSERT INTO `vaults`.`inventories` (`vaultid`, `base`) VALUES ('" + Integer.toString(number) + pn + "', '" + base + "');";
    System.out.println(sql);
    PreparedStatement myPreparedStatement = connection.prepareStatement(sql);

    myPreparedStatement.executeUpdate();
  }
  public static void updateString(int number, String pn, String base) throws SQLException {
    Connect();
    String sql = "UPDATE inventories SET base='" + base + "' WHERE vaultid='" + Integer.toString(number) + pn + "'";
    System.out.println(sql);
    PreparedStatement myPreparedStatement = connection.prepareStatement(sql);

    myPreparedStatement.executeUpdate();
  }
  public static String getString(int number, String pn) throws SQLException {
    Connect();
    String sql = "SELECT * FROM inventories WHERE vaultid='" + Integer.toString(number) + pn + "' ";
    System.out.println(sql);
    PreparedStatement psPreparedStatement = connection.prepareStatement(sql);
    ResultSet results = psPreparedStatement.executeQuery();
    if (!results.next()) {
      System.out.println("Failed to get users vault generating a new one");
      saveString(number, pn, nullinvbase);
      return nullinvbase;
    } else {
      System.out.println("Success");
      return results.getString("base");
    }

  }

}
