import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import net.aksingh.owmjapis.HourlyForecast;
import net.aksingh.owmjapis.OpenWeatherMap;
import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class Controller {

    private static String apiKey ="524901&APPID=86ae04679fdbf8a297228b8f8cedea5c";

    public LineChart maxTemp;
    public LineChart minTemp;
    public LineChart hum;
    public LineChart wind;
    public LineChart pressure;
    public LineChart actTemp;

    public TextField stadtFeld;
    public TextField landFeld;
    public Button suchen;

    public Button addButton;
    public TextField bezFeld;
    public ChoiceBox katSel;
    public DatePicker dateSel;
    public ChoiceBox artSel;
    public TextField betragFeld;

    public TextField katFeld;
    public Button katButton;

    public TableView table;
    public TableColumn bezCol;
    public TableColumn katCol;
    public TableColumn dateCol;
    public TableColumn einCol;
    public TableColumn ausCol;

    OpenWeatherMap owm ;
    HaushaltsBuch hb;

    public Controller(){

        /*
        owm = new OpenWeatherMap(apiKey);
        hb = new HaushaltsBuch();

        bezCol = new TableColumn();
        katCol = new TableColumn();
        dateCol = new TableColumn();
        einCol = new TableColumn();
        ausCol = new TableColumn();

        bezCol.setCellValueFactory(new PropertyValueFactory<HhbPosten,String>("bezeichnung"));
        katCol.setCellValueFactory(new PropertyValueFactory<HhbPosten,String>("kategorie"));
        dateCol.setCellValueFactory(new PropertyValueFactory<HhbPosten,String>("datum"));
        einCol.setCellValueFactory(new PropertyValueFactory<HhbPosten,Double>("einnahme"));
        ausCol.setCellValueFactory(new PropertyValueFactory<HhbPosten,Double>("ausgabe"));

        table.getColumns().clear();
        table.getColumns().addAll(bezCol,katCol,dateCol,einCol,ausCol);

        ObservableList<HhbPosten> testPosten = FXCollections.observableArrayList();
        testPosten.add(new HhbPosten(new SimpleIntegerProperty(1),new SimpleStringProperty("Test"),new SimpleStringProperty("Test"),null,new SimpleDoubleProperty(100),new SimpleDoubleProperty(100)));
        table.setItems(testPosten);
        table.setVisible(true);*/


    }

    public void initialize(){

        owm = new OpenWeatherMap(apiKey);
        hb = new HaushaltsBuch();

        katSel.getItems().clear();
        artSel.getItems().clear();

        hb.addKategorie("Auto");

        katSel.setItems(hb.getKategorien());
        artSel.getItems().addAll("Ausgabe", "Einnahme");



        bezCol.setCellValueFactory(new PropertyValueFactory<HhbPosten, String>("bezeichnung"));
        katCol.setCellValueFactory(new PropertyValueFactory<HhbPosten,String>("kategorie"));
        dateCol.setCellValueFactory(new PropertyValueFactory<HhbPosten,LocalDate>("datum"));
        einCol.setCellValueFactory(new PropertyValueFactory<HhbPosten,Double>("einnahme"));
        ausCol.setCellValueFactory(new PropertyValueFactory<HhbPosten,Double>("ausgabe"));

        table.setItems(hb.getPosten());

        hb.aktualisieren();


    }

    public void handleButton(){
        //mal gucken ob das klappt :D

        HourlyForecast cw=null;

        try {
            cw = owm.hourlyForecastByCityName(stadtFeld.getText() + "," + landFeld.getText());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }



        String foundCity = cw.getCityInstance().getCityName()+ " " +cw.getCityInstance().getCountryCode();

        XYChart.Series maxData = new XYChart.Series();
        XYChart.Series minData = new XYChart.Series();
        XYChart.Series humData = new XYChart.Series();
        XYChart.Series windData = new XYChart.Series();
        XYChart.Series pressureData = new XYChart.Series();
        XYChart.Series actTempData = new XYChart.Series();

        maxData.setName(foundCity);
        minData.setName(foundCity);
        humData.setName(foundCity);
        windData.setName(foundCity);
        pressureData.setName(foundCity);
        actTempData.setName(foundCity);





        for(int i=0;i<cw.getForecastCount();i++){

            maxData.getData().add(new XYChart.Data(cw.getForecastInstance(i).getDateTimeText(), this.fahrenheitToCelsius(cw.getForecastInstance(i).getMainInstance().getMaxTemperature())));

        }

        for(int i=0;i<cw.getForecastCount();i++){

            minData.getData().add(new XYChart.Data(cw.getForecastInstance(i).getDateTimeText(), this.fahrenheitToCelsius(cw.getForecastInstance(i).getMainInstance().getMinTemperature())));

        }

        for(int i=0;i<cw.getForecastCount();i++){

            humData.getData().add(new XYChart.Data(cw.getForecastInstance(i).getDateTimeText(), cw.getForecastInstance(i).getMainInstance().getHumidity()));

        }

        for(int i=0;i<cw.getForecastCount();i++){

            windData.getData().add(new XYChart.Data(cw.getForecastInstance(i).getDateTimeText(), cw.getForecastInstance(i).getWindInstance().getWindSpeed()));

        }

        for(int i=0;i<cw.getForecastCount();i++){

            pressureData.getData().add(new XYChart.Data(cw.getForecastInstance(i).getDateTimeText(), cw.getForecastInstance(i).getMainInstance().getPressure()));

        }

        for(int i=0;i<cw.getForecastCount();i++){

            actTempData.getData().add(new XYChart.Data(cw.getForecastInstance(i).getDateTimeText(), this.fahrenheitToCelsius(cw.getForecastInstance(i).getMainInstance().getTemperature())));

        }




        maxTemp.getData().addAll(maxData);
        minTemp.getData().addAll(minData);
        hum.getData().addAll(humData);
        wind.getData().addAll(windData);
        pressure.getData().addAll(pressureData);
        actTemp.getData().addAll(actTempData);


    }

    public void clear(){

        maxTemp.getData().clear();
        minTemp.getData().clear();
        hum.getData().clear();
        wind.getData().clear();
        pressure.getData().clear();
        actTemp.getData().clear();


    }

    public double fahrenheitToCelsius(float temperatur){

        return (float) ((temperatur-32)/1.8);

    }

    public void addButton(){

        SimpleIntegerProperty id = hb.getAvailibleId();
        SimpleStringProperty bezeichnung = new SimpleStringProperty(bezFeld.getText());
        SimpleStringProperty kategorie =new SimpleStringProperty(katSel.getValue().toString());
        LocalDate datum = dateSel.getValue();
        SimpleDoubleProperty einnahme;
        SimpleDoubleProperty ausgabe;

        if(artSel.getValue().equals("Einnahme")){

            einnahme = new SimpleDoubleProperty(Double.valueOf(betragFeld.getText()));
            ausgabe=new SimpleDoubleProperty(0.0);

        } else {

            ausgabe = new SimpleDoubleProperty(Double.valueOf(betragFeld.getText()));
            einnahme=new SimpleDoubleProperty(0.0);

        }

        hb.neuerEintrag(new HhbPosten(id,bezeichnung,kategorie,datum,einnahme,ausgabe));

        bezFeld.clear();
        katSel.setValue(null);
        dateSel.setValue(null);
        artSel.setValue(null);
        betragFeld.clear();

    }

    public void handleKatButton(){

        hb.addKategorie(katFeld.getText());
        katFeld.clear();

    }


}