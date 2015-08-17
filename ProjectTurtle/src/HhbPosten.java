import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

/**
 * Created by Niels on 14.08.15.
 */
public class HhbPosten {

    final IntegerProperty id;
    final StringProperty bezeichnung,kategorie;
    final LocalDate datum;
    final DoubleProperty einnahme,ausgabe;

    public HhbPosten(IntegerProperty id, StringProperty bezeichnung, StringProperty kategorie, LocalDate datum, DoubleProperty einnahme, DoubleProperty ausgabe) {
        this.id = id;
        this.bezeichnung = bezeichnung;
        this.kategorie = kategorie;
        this.datum = datum;
        this.einnahme = einnahme;
        this.ausgabe = ausgabe;
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getBezeichnung() {
        return bezeichnung.get();
    }

    public StringProperty bezeichnungProperty() {
        return bezeichnung;
    }

    public String getKategorie() {
        return kategorie.get();
    }

    public StringProperty kategorieProperty() {
        return kategorie;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public double getEinnahme() {
        return einnahme.get();
    }

    public DoubleProperty einnahmeProperty() {
        return einnahme;
    }

    public double getAusgabe() {
        return ausgabe.get();
    }

    public DoubleProperty ausgabeProperty() {
        return ausgabe;
    }
}
