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
//Using gson
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import static java.sql.Types.NULL;


public class Controller<i> implements Initializable {


    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public BoughtItem getBoughtItems() {
        return BoughtItems;
    }

    public void setBoughtItems(BoughtItem boughtItems) {
        BoughtItems = boughtItems;
    }

    private Item[] items;

    public SoldItem getSoldItems() {
        return SoldItems;
    }

    public void setSoldItems(SoldItem soldItems) {
        SoldItems = soldItems;
    }

    private SoldItem SoldItems;
    private BoughtItem BoughtItems;
    private JsonParser parser = new JsonParser();  //create JSON parser
    private JsonObject object = (JsonObject) parser.parse(new FileReader("inv.json"));
    private JsonArray array = object.get("inventory").getAsJsonArray();    //to get the array from JSON

    private int length = array.size();
    private int[] storeSellIn = new int[length];
    private int[] numbSellIn = new int[length];
    private int[] numbCd = new int[length];
    private String[] sell = new String[length];
    private String[] cd = new String[length];

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
        int j = 1;
        storeSellIn[0] = items[0].getSellIn();
        for (int i = 0; i < items.length; i++) {
            if(storeSellIn[j]== items[i].getSellIn()){
                numbSellIn[j] += 1;
            }
            else if(storeSellIn[j] != items[i].getSellIn()){
                j++;
                storeSellIn[j] = items[i].getSellIn();
                numbSellIn[j] += 1;
            }
            System.out.println("storeSellIn " + j + " = " + storeSellIn[j]);
            System.out.println("number of this sellIn " + j + " = " + numbSellIn[j]);
        }

        for (Item item : items) {
            switch (item.getCreationDate()) {
                case "Monday":
                    numbCd[1]++;
                    break;
                case "Tuesday":
                    numbCd[2]++;
                    break;
                case "Wednesday":
                    numbCd[3]++;
                    break;
                case "Thursday":
                    numbCd[4]++;
                    break;
                case "Friday":
                    numbCd[5]++;
                    break;
                case "Saturday":
                    numbCd[6]++;
                    break;
                case "Sunday":
                    numbCd[7]++;
                    break;
                default:
            }
        }
    }




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

        int j = 1;
        int i = 0;
        int[] numbSellInUpdate = new int[length];
        storeSellIn[1] = items[0].getSellIn();
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("rebuild the storeSellIn to " + storeSellIn[0]);
        while(i < items.length ) {
            if(storeSellIn[j] == items[i].getSellIn()){
                numbSellInUpdate[j] += 1;
            }
            else if(storeSellIn[j] != items[i].getSellIn()){
                j++;
                storeSellIn[j] = items[i].getSellIn();
                numbSellInUpdate[j] += 1;
            }
            //test
            System.out.println("**********************************************");
            System.out.println("storeSellIn  in  J = " + j + " is " + storeSellIn[j]);
            System.out.println("number of this sellIn  in J = " + j + " is " + numbSellIn[j]);
            System.out.println("**********************************************");
            i++;
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

        i = 1;
        XYChart.Series set1 = new XYChart.Series();
        XYChart.Series set2 = new XYChart.Series();

        while(i<j+1) {
            sell[i] = "" + storeSellIn[i];
            cd[i] = ""+items[i].getCreationDate();
            int k = 1;
            while(k < i){
                if(storeSellIn[k] == storeSellIn[i]) {
                    numbSellInUpdate[i] = numbSellInUpdate[i] + numbSellInUpdate[k];
                }
                k++;
            }
            set1.getData().add(new XYChart.Data<>(sell[i], numbSellInUpdate[i]));
            set2.getData().add(new XYChart.Data<>(cd[i], numbCd[i]));
            i++;
        }

        SellinBarChart.getData().setAll(set1);
        CreationdateBarChart.getData().setAll(set2);
        for (int n = 1;n < length;n++) {
            numbSellInUpdate[n] = NULL;
        }
        ;

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

    public void addItem(){

        return;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int i = 1;
        XYChart.Series set1 = new XYChart.Series();
        XYChart.Series set2 = new XYChart.Series();

        while(i<7) {
            sell[i] = "" + storeSellIn[i];
            cd[i] = ""+items[i].getCreationDate();
            set1.getData().add(new XYChart.Data<>(sell[i], numbSellIn[i]));
            set2.getData().add(new XYChart.Data<>(cd[i], numbCd[i]));
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
