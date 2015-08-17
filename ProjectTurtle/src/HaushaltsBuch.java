import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by Niels on 14.08.15.
 */
public class HaushaltsBuch {

    ObservableList<HhbPosten> posten;
    ObservableList<String> kategorien;

    DatabaseLoader db;

    public HaushaltsBuch() {

        posten = FXCollections.observableArrayList();
        kategorien = FXCollections.observableArrayList();

        db= new DatabaseLoader();

    }

    public void neuerEintrag(HhbPosten posten){

        this.posten.add(posten);
        db.addEntry(posten);

    }

    public void aktualisieren(){

        ResultSet rs =db.getAllEntries();
        ObservableList<HhbPosten> backup = this.getPosten();
        this.getPosten().clear();

        if (rs != null) {

            try {
                while (rs.next()) {

                    IntegerProperty id = new SimpleIntegerProperty(rs.getInt("id"));
                    StringProperty bez = new SimpleStringProperty(rs.getString("bezeichnung"));
                    StringProperty kat = new SimpleStringProperty(rs.getString("kategorie"));
                    LocalDate date = rs.getDate("datum").toLocalDate();
                    DoubleProperty einnahme = new SimpleDoubleProperty(rs.getDouble("einnahme"));
                    DoubleProperty ausgabe = new SimpleDoubleProperty(rs.getDouble("ausgabe"));

                    this.getPosten().add(new HhbPosten(id, bez, kat, date, einnahme, ausgabe));

                }


            } catch (SQLException e) {
                e.printStackTrace();
                this.getPosten().addAll(backup);
            }

        }

    }

    public ObservableList<String> getKategorien() {
        return kategorien;
    }

    public void setKategorien(ObservableList<String> kategorien) {
        this.kategorien = kategorien;
    }

    public ObservableList<HhbPosten> getPosten() {
        return posten;
    }

    public void setPosten(ObservableList<HhbPosten> posten) {
        this.posten = posten;
    }

    public SimpleIntegerProperty getAvailibleId(){

        ArrayList<Integer> ids= new ArrayList<Integer>();


        for(int i =0;i<posten.size();i++){

            ids.add((posten.get(i).getId()));

        }

        int i;

        for (i=0;i<ids.size();i++){

            if (ids.contains(i)){


            } else {

                return new SimpleIntegerProperty(i);

            }

        }
        return new SimpleIntegerProperty(i);

    }

    public void addKategorie(String kat){

        kategorien.add(kat);

    }
}
