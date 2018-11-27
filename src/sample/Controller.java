package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class Controller implements Initializable {


    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public final String Dexterity_Vest = "+5 Dexterity Vest";
    public final String Aged_Brie = "Aged Brie";
    public final String Elixir_of_the_Mongoose = "Elixir of the Mongoose";
    public final String Sulfuras_Hand_of_Ragnaros = "Sulfuras, Hand of Ragnaros";
    public final String Backstage_passes_to_a_TAFKAL80ETC_concert = "Backstage passes to a TAFKAL80ETC concert";
    public final String Conjured_Mana_Cake = "Conjured Mana Cake";

    private Item[] items;
    JsonParser parser = new JsonParser();  //创建JSON解析器
    //创建J
    JsonObject object = (JsonObject) parser.parse(new FileReader("inv.json"));
    JsonArray array = object.get("inventory").getAsJsonArray();    //得到为json的数组


    public Controller() throws FileNotFoundException {
        int length = array.size();
        items = new Item[length];
        for (int i = 0; i < items.length; i++) {
            items[i] = new Item("default", 0, 0);
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



    }

    @FXML
    ListView<String> listView;

    @FXML
    ListView<Integer> ListViewSellIn;

    @FXML
    ListView<Integer> ListViewQuality;


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
    }

    public void loadFile() {
        listView.getItems().setAll();
        ListViewSellIn.getItems().setAll();
        ListViewQuality.getItems().setAll();
        for (Item item : items){
            listView.getItems().add(item.getName());
            ListViewSellIn.getItems().add(item.getSellIn());
            ListViewQuality.getItems().add(item.getQuality());
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
