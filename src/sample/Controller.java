package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.ListView;
import sun.font.TrueTypeFont;


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
    public final String Conjured_Mana_Cake= "Conjured Mana Cake";

    private Item[] items;


    public Controller() {
        items = new Item[]{
                new Item(Dexterity_Vest, 10, 20),
                new Item(Aged_Brie, 2, 0),
                new Item(Elixir_of_the_Mongoose, 5, 7),
                new Item(Sulfuras_Hand_of_Ragnaros, 0, 80),
                new Item(Backstage_passes_to_a_TAFKAL80ETC_concert, 15, 20),
                new Item(Conjured_Mana_Cake, 3, 6)
        };

    }
    @FXML
    ListView<String> listView;

    @FXML
    ListView<Integer> ListViewSellIn;

    @FXML
    ListView<Integer> ListViewQuality;


    public  void updateQuality() {
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
        //refresh the listView and show the updated SellIn
        ListViewSellIn.getItems().setAll();
        for (Item item : items){
            ListViewSellIn.getItems().add(item.getSellIn());
        }
        //refresh the listView and show the updated Quality
        ListViewQuality.getItems().setAll();
        for (Item item : items){
            ListViewQuality.getItems().add(item.getQuality());
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Item item : items) {
            listView.getItems().add(item.getName());
            ListViewSellIn.getItems().add(item.getSellIn());
            ListViewQuality.getItems().add(item.getQuality());
        }
    }
}
