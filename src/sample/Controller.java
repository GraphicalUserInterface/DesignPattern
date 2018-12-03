package sample;


import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class Controller<i> implements Initializable {


    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    private Item[] items;
    JsonParser parser = new JsonParser();  //create JSON
    //创建J
    private JsonObject object = (JsonObject) parser.parse(new FileReader("inv.json"));
    JsonArray array = object.get("inventory").getAsJsonArray();    //to get the array from JSON

    private int[] numb = new int[10];
    private String[] sell = new String[10];
    private String[] cd = new String[10];


    public Controller() throws FileNotFoundException {
        int length = array.size();
        items = new Item[length];
        for (int i = 0; i < items.length; i++) {
            items[i] = new Item("default", 0, 0,"default");
        }
        for (int i = 0; i < array.size(); i++) {
            String N;
            int S;
            int Q;
            String C;
            JsonObject subObject = array.get(i).getAsJsonObject();
            N = subObject.get("name").getAsString();
            S = subObject.get("sellIn").getAsInt();
            Q = subObject.get("quality").getAsInt();
            C = subObject.get("creationDate").getAsString();
            System.out.println(S + " " + Q + " " + N + " " + C);
            items[i].setName(N);
            items[i].setQuality(Q);
            items[i].setSellIn(S);
            items[i].setCreationDate(C);
        }

        for (Item item : items) {
            switch (item.getName()) {
                case "Sulfuras, Hand of Ragnaros":
                    numb[1]++;
                    break;
                case "AgedBrie":
                    numb[2]++;
                    break;
                case "Backstage passes to a TAFKAL80ETC concert":
                    numb[3]++;
                    break;
                case "Conjured Mana Cake":
                    numb[4]++;
                    break;
                case "Elixir of the Mongoose":
                    numb[5]++;
                    break;
                case "+5 Dexterity Vest":
                    numb[6]++;
                    break;
            }
        }
    }

    @FXML
    ListView<String> listView;

    @FXML
    ListView<Integer> ListViewSellIn;

    @FXML
    ListView<Integer> ListViewQuality;

    @FXML
    private BarChart<?, ?> SellinBarChart;

    @FXML
    private BarChart<?, ?> CreationdateBarChart;



    public void updateQuality() {
        //use interface ItemStrategy to Encapsulate the different strategy
        ItemStrategy itemStrategy;

        for (Item item : items) {
            switch (item.getName()) {
                case "Aged Brie":
                    itemStrategy = new AgedBrie();
                    break;
                case "Backstage passes to a TAFKAL80ETC concert":
                    itemStrategy = new BackstagePasses();
                    break;
                case "Sulfuras, Hand of Ragnaros":
                    itemStrategy = new Sulfuras();
                    break;
                case "Conjured Mana Cake":
                    itemStrategy = new Conjured();
                    break;
                default:
                    itemStrategy = new NormalItem();
            }
            itemStrategy.update(item);
        }

        //refresh the listView of SellIn and show the updated value
        ListViewSellIn.getItems().setAll();
        for (Item item : items) {
            ListViewSellIn.getItems().add(item.getSellIn());
        }
        //refresh the listView of Quality and show the updated value
        ListViewQuality.getItems().setAll();
        for (Item item : items) {
            ListViewQuality.getItems().add(item.getQuality());
        }
        int i = 1;

        XYChart.Series set1 = new XYChart.Series();
        XYChart.Series set2 = new XYChart.Series();

        while(i<6) {
            sell[i] = "" + items[i].getSellIn();
            cd[i] = ""+items[i].getCreationDate();
            set1.getData().add(new XYChart.Data<>(sell[i], numb[i]));
            set2.getData().add(new XYChart.Data<>(cd[i], numb[i]));
            i++;
        }

        SellinBarChart.getData().setAll(set1);
        CreationdateBarChart.getData().setAll(set2);
    }

    public void loadFile() {
        listView.getItems().setAll();
        ListViewSellIn.getItems().setAll();
        ListViewQuality.getItems().setAll();
        for (Item item : items) {
            listView.getItems().add(item.getName());
            ListViewSellIn.getItems().add(item.getSellIn());
            ListViewQuality.getItems().add(item.getQuality());
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int i = 1;
        XYChart.Series set1 = new XYChart.Series();
        XYChart.Series set2 = new XYChart.Series();

        while(i<6) {
            sell[i] = "" + items[i].getSellIn();
            cd[i] = ""+items[i].getCreationDate();
            set1.getData().add(new XYChart.Data<>(sell[i], numb[i]));
            set2.getData().add(new XYChart.Data<>(cd[i], numb[i]));
            i++;
        }

        SellinBarChart.getData().setAll(set1);
        CreationdateBarChart.getData().setAll(set2);


    }


    public void changeWindow() throws Exception {
        Second second=new Second();
        second.showWindow();


    }

}
