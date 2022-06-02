import java.sql.*;

public class Bridge {
    private String url, usr, pass;
    public Connection koneksi;

    Bridge(){
        this.url = "jdbc:mysql://localhost:3306/perpustakaan";
        this.usr = "root";
        this.pass = "123";
    }

    public void koneksiMysql() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        koneksi = (Connection) DriverManager.getConnection(this.url, this.usr, this.pass);
    }

    public Statement openConnection() throws SQLException{
        return koneksi.createStatement();
    }
}
