package sample;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

public class Second extends Application {
    private Item[] items;
    JsonParser parser = new JsonParser();  //create JSON
    //创建J
    JsonObject object = (JsonObject) parser.parse(new FileReader("inv.json"));
    JsonArray array = object.get("inventory").getAsJsonArray();    //to get the array from JSON


    public Second() throws FileNotFoundException {
        int length = array.size();
        items = new Item[length];
        for (int i = 0; i < items.length; i++) {
            items[i] = new Item("default", 0, 0,"default");
        }
        for (int i = 0; i < array.size(); i++) {
            String N;
            int S;
            int Q;
            JsonObject subObject = array.get(i).getAsJsonObject();
            N = subObject.get("name").getAsString();
            S = subObject.get("sellIn").getAsInt();
            Q = subObject.get("quality").getAsInt();
            System.out.println(S + " " + Q + " " + N);
            items[i].setName(N);
            items[i].setQuality(Q);
            items[i].setSellIn(S);
        }
        int numb1 = 0;
        int numb2 = 0;
        int numb3 = 0;
        int numb4 = 0;
        int numb5 = 0;
        int numb6 = 0;
        for (Item item : items) {
            switch (item.getName()) {
                case "Sulfuras, Hand of Ragnaros":
                    numb1++;
                    break;
                case "AgedBrie":
                    numb2++;
                    break;
                case "Backstage passes to a TAFKAL80ETC concert":
                    numb3++;
                    break;
                case "Conjured Mana Cake":
                    numb4++;
                    break;
                case "Elixir of the Mongoose":
                    numb5++;
                    break;
                case "+5 Dexterity Vest":
                    numb6++;
                    break;
            }
        }
        System.out.println(numb1 + " " + numb2 + " " + numb3 + " " + numb4 + " "+ numb5 + " "+ numb6 + " ");
    }
    Stage stage=new Stage();

    @Override
    public void start(Stage stage) {

        int length = array.size();
        items = new Item[length];
        for (int i = 0; i < items.length; i++) {
            items[i] = new Item("default", 0, 0,"default");
        }
        for (int i = 0; i < array.size(); i++) {
            String N;
            int S;
            int Q;
            JsonObject subObject = array.get(i).getAsJsonObject();
            N = subObject.get("name").getAsString();
            S = subObject.get("sellIn").getAsInt();
            Q = subObject.get("quality").getAsInt();
            System.out.println(S + " " + Q + " " + N);
            items[i].setName(N);
            items[i].setQuality(Q);items[i].setSellIn(S);
        }
        int numb1 = 0;
        int numb2 = 0;
        int numb3 = 0;
        int numb4 = 0;
        int numb5 = 0;
        int numb6 = 0;
        for (Item item : items) {
            switch (item.getName()) {
                case "Sulfuras, Hand of Ragnaros":
                    numb1++;
                    break;
                case "AgedBrie":
                    numb2++;
                    break;
                case "Backstage passes to a TAFKAL80ETC concert":
                    numb3++;
                    break;
                case "Conjured Mana Cake":
                    numb4++;
                    break;
                case "Elixir of the Mongoose":
                    numb5++;
                    break;
                case "+5 Dexterity Vest":
                    numb6++;
                    break;
            }
        }

        Scene scene = new Scene(new Group());
        stage.setTitle("Imported items");
        stage.setWidth(500);
        stage.setHeight(500);
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Sulfuras, Hand of Ragnaros", numb1),
                        new PieChart.Data("AgedBrie", numb2),
                        new PieChart.Data("Backstage passes to a TAFKAL80ETC concert", numb3),
                        new PieChart.Data("Conjured Mana Cake", numb4),
                        new PieChart.Data("Elixir of the Mongoose", numb5),
                        new PieChart.Data("+5 Dexterity Vest", numb6));
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Imported items");

        ((Group) scene.getRoot()).getChildren().add(chart);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void  showWindow() throws Exception {
        start(stage);
    }

}
