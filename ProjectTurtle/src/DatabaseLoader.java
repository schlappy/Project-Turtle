import java.sql.*;

/**
 * Created by Niels on 16.08.2015.
 */
public class DatabaseLoader {

    Connection c;

    public DatabaseLoader(){

        c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:projectTurtle.db");
            Statement s = c.createStatement();
            s.execute("CREATE  TABLE IF NOT EXISTS \"main\".\"haushaltsbuch\" (\"id\" INTEGER PRIMARY KEY  NOT NULL  UNIQUE , \"bezeichnung\" VARCHAR NOT NULL , \"kategorie\" VARCHAR NOT NULL , \"datum\" DATETIME NOT NULL , \"einnahme\" DOUBLE NOT NULL , \"ausgabe\" DOUBLE NOT NULL )");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");



    }

    public ResultSet getAllEntries(){

        try {
            Statement s = c.createStatement();
            return s.executeQuery("SELECT * FROM Haushaltsbuch");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void addEntry(HhbPosten posten){

        String sql = "INSERT INTO haushaltsbuch (id, bezeichnung,kategorie,datum,einnahme,ausgabe) VALUES ("
                +posten.getId()+","
                +"'"+posten.getBezeichnung()+"'"+","
                +"'"+posten.getKategorie()+"'"+","
                +"'"+posten.getDatum()+"'"+","
                +posten.getEinnahme()+","
                +posten.getAusgabe()+")";

        try {
            Statement s =c.createStatement();
            s.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(sql);


    }

}
