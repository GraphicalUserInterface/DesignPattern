package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;
//Using gson
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sun.rmi.runtime.NewThreadAction;

import static java.sql.Types.NULL;


public class Controller<i, cd> implements Initializable {


    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }




    public Item[] items;


    public SoldItem[] getSoldItems() {
        return SoldItems;
    }

    public void setSoldItems(SoldItem[] soldItems) {
        SoldItems = soldItems;
    }

    public BoughtItem[] getBoughtItems() {
        return BoughtItems;
    }

    public void setBoughtItems(BoughtItem[] boughtItems) {
        BoughtItems = boughtItems;
    }

    private SoldItem[] SoldItems;
    private BoughtItem[] BoughtItems;
    private JsonParser parser = new JsonParser();  //create JSON parser
    private JsonObject object = (JsonObject) parser.parse(new FileReader("inv.json"));
    private JsonArray array = object.get("inventory").getAsJsonArray();    //to get the array from JSON

    private int length = array.size();
    private int times = 0;
    private int[] storeSellIn = new int[length];
    private int[] numbSellIn = new int[length];
    private int[] numbCd = new int[length];
    private int[] numbSellOnDay = new int[20];
    private int[] numbBuyOnDay = new int[20];
    private String[] sell = new String[length];
    private String[] cd = new String[length];
    private int totalProfits = 0;
    private int lastBuy = 0;
    private int totalBuy = 0;
    private int lastSell = 0;
    private int totalSell = 0;



    @FXML
    ListView<String> listView;

    @FXML
    ListView<Integer> ListViewSellIn;

    @FXML
    ListView<Integer> ListViewQuality;

    @FXML
    ListView<String> ListViewSoldItem;

    @FXML
    ListView<String> ListViewBoughtItem;

    @FXML
    ListView<Integer> ListViewTotalProfits;

    @FXML
    Button updateButton;

    @FXML
    private ChoiceBox<String> ChoiceBoxBuy;

    ObservableList<String> ChoiceBoxBuyList = FXCollections.observableArrayList();

    //loadData to build the ChoiceBox
    @FXML
    private void loadData(){
        ChoiceBoxBuyList.removeAll(ChoiceBoxBuyList);
        String[] buyMyItem = new String[7];
        buyMyItem[1] = "Sulfuras, Hand of Ragnaros";
        buyMyItem[2] = "AgedBrie";
        buyMyItem[3] = "Backstage passes to a TAFKAL80ETC concert";
        buyMyItem[4] = "Conjured Mana Cake";
        buyMyItem[5] = "Elixir of the Mongoose";
        buyMyItem[6] = "+5 Dexterity Vest";
        ChoiceBoxBuyList.addAll(buyMyItem);
        ChoiceBoxBuy.getItems().setAll(ChoiceBoxBuyList);
    }

    @FXML
    private void clickOnUpdate(){
        updateButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                times = times + event.getClickCount();
                System.out.println("times = " + times);
                int j = 0;
                for (SoldItem soldItem : SoldItems) {
                    if (soldItem.getNameSold() != "default") {
                        j = j + 1;
                    }
                }
                totalSell = j;
                j = j - lastSell;
                lastSell = totalSell;
                numbSellOnDay[times] = j;
                int k = 0;
                for (BoughtItem boughtItem : BoughtItems){
                    if(boughtItem.getNameBought() != "default") {
                       k = k + 1;
                    }
                }
                totalBuy = k;
                k = k - lastBuy;
                lastBuy = totalBuy;
                numbBuyOnDay[times] = k;


                XYChart.Series set1 = new XYChart.Series();
                XYChart.Series set2 = new XYChart.Series();

                 set1.setName("Buy");
                 set2.setName("Sell");
                    for(int t = 0; t < times; t++) {
                        String thisDay = "day " + t;
                        System.out.println(thisDay);
                        set1.getData().add(new XYChart.Data<>(thisDay, numbBuyOnDay[t+1]));
                        set2.getData().add(new XYChart.Data<>(thisDay, numbSellOnDay[t+1]));

                    }
//                 String thisDay = "day " + times;
//                                    set1.getData().add(new XYChart.Data<>(thisDay,j));
//                                    set2.getData().add(new XYChart.Data<>(thisDay,k));
                SellAndBuyBarChart.getData().setAll(set1,set2);

            }
        });
    }
    //To build the function when clicking on the Items we choose.
    @FXML
    private void clickOnChoiceBox(){
        ChoiceBoxBuy.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY) {
                    String myBought = ChoiceBoxBuy.getItems().get(ChoiceBoxBuy.getSelectionModel().getSelectedIndex());
                    System.out.println("Buy this Item "+ myBought);
                    buyItem();
                } else {
                    System.out.println("Nothing happened");
                }
            }
        });
    }

    @FXML
    private void setSellNameFromListView(){
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.SECONDARY){
                    sellItem();
                    System.out.println("Sell This Item");
                }
                else {
                    System.out.println("Choose This Item");
                }
            }
        });
//        String SI = listView.getItems().get(listView.getSelectionModel().getSelectedIndex());
        System.out.println("Mouse enter the listView");
    }

    @FXML
    private BarChart<?, ?> SellinBarChart;

    @FXML
    private BarChart<?, ?> CreationdateBarChart;

    @FXML
    private BarChart<?, ?> SellAndBuyBarChart;

    public Controller() throws FileNotFoundException {
        int length = array.size();
        items = new Item[20];
        for (int i = 0; i < 20; i++) {
            items[i] = new Item("default", 0, 0,"default");
        }
        //Build sellItem
        SoldItems = new SoldItem[10];
        for (int i = 0; i < 10; i++) {
            SoldItems[i] = new SoldItem("default", 0);
        }
        //Build BoughtItem
        BoughtItems = new BoughtItem[10];
        for (int i = 0; i < 10; i++) {
            BoughtItems[i] = new BoughtItem("default", 0);
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
        showFigure();
    }

    public void showFigure(){
        int j = 1;
        int i = 0;
        int[] numbSellInUpdate = new int[length];
        storeSellIn[1] = items[0].getSellIn();
//        System.out.println("-----------------------------------------------------------------------------");
//        System.out.println("rebuild the storeSellIn to " + storeSellIn[0]);
        while(i < items.length ) {
            if(storeSellIn[j] == items[i].getSellIn() && items[i].getName() != "default"){
                numbSellInUpdate[j] += 1;
            }
            else if(storeSellIn[j] != items[i].getSellIn() && items[i].getName() != "default"){
                j++;
                storeSellIn[j] = items[i].getSellIn();
                numbSellInUpdate[j] += 1;
            }
//            System.out.println("**********************************************");
//            System.out.println("storeSellIn  in  J = " + j + " is " + storeSellIn[j]);
//            System.out.println("number of this sellIn  in J = " + j + " is " + numbSellIn[j]);
//            System.out.println("**********************************************");
            i++;
        }

        //refresh the listView of SellIn and show the updated value
        listView.getItems().setAll();
        ListViewSellIn.getItems().setAll();
        ListViewQuality.getItems().setAll();
        ListViewSoldItem.getItems().setAll();
        ListViewBoughtItem.getItems().setAll();
        ListViewTotalProfits.getItems().setAll(totalProfits);
        for (Item item : items) {
            if(item.getName() != "default") {
                listView.getItems().add(item.getName());
                ListViewSellIn.getItems().add(item.getSellIn());
                ListViewQuality.getItems().add(item.getQuality());
            }
        }

        //refresh the name of SoldItems
        for (SoldItem soldItem : SoldItems){
            if(soldItem.getNameSold() != "default"){
                ListViewSoldItem.getItems().add(soldItem.getNameSold());
            }
        }
        //refresh the name of BoughtItems
        for (BoughtItem boughtItem : BoughtItems){
            if(boughtItem.getNameBought() != "default") {
                ListViewBoughtItem.getItems().add(boughtItem.getNameBought());
            }
        }

        i = 1;
        XYChart.Series set1 = new XYChart.Series();
        XYChart.Series set2 = new XYChart.Series();

        while(i<=j+1) {
            sell[i] = "" + storeSellIn[i];
            int k = 1;
            while(k < i){
                if(storeSellIn[k] == storeSellIn[i]) {
                    numbSellInUpdate[i] = numbSellInUpdate[i] + numbSellInUpdate[k];
                }
                k++;
            }
            set1.getData().add(new XYChart.Data<>(sell[i], numbSellInUpdate[i]));
            i++;
        }
        int m = 1;
        while(m<8){
            set2.getData().add(new XYChart.Data<>(cd[m], numbCd[m]));
            m++;
        }

        SellinBarChart.getData().setAll(set1);
        CreationdateBarChart.getData().setAll(set2);
        for (int n = 1;n < length;n++) {
            numbSellInUpdate[n] = NULL;
        }

    }

    public void loadFile() {
        //To refresh All the ListView
        listView.getItems().setAll();
        ListViewSellIn.getItems().setAll();
        ListViewQuality.getItems().setAll();
        ListViewSoldItem.getItems().setAll();
        ListViewBoughtItem.getItems().setAll();

        //TO show the existing items name, sellIn, quality
        for (Item item : items) {
            if(item.getName() != "default") {
                listView.getItems().add(item.getName());
                ListViewSellIn.getItems().add(item.getSellIn());
                ListViewQuality.getItems().add(item.getQuality());
            }
        }

        //To show the name of SoldItems
        for (SoldItem soldItem : SoldItems){
            if(soldItem.getNameSold() != "default"){
            ListViewSoldItem.getItems().add(soldItem.getNameSold());
            }
        }

        //To show the name of BoughtItems
        for (BoughtItem boughtItem : BoughtItems){
            if(boughtItem.getNameBought() != "default")
            ListViewBoughtItem.getItems().add(boughtItem.getNameBought());
        }

    }

    public void sellItem(){
        String SI = listView.getItems().get(listView.getSelectionModel().getSelectedIndex());
        System.out.println(" SI = " + SI);
        for(Item item: items){
            if(item.getName() == SI){
                item.setName("default");
                item.setCreationDate("default");
                totalProfits = totalProfits + item.getQuality();
                break;
            }
        }
        for (int i = 0; i < 10; i++) {
            if(SoldItems[i].getNameSold() == "default"){
                SoldItems[i].setNameSold(SI);
                System.out.println(SoldItems[i]);
                loadFile();
                showFigure();
                break;
            }
        }
    }

    public void buyItem(){
        for (int i = 0; i < 10; i++) {
            if(BoughtItems[i].getNameBought() == "default"){
                String myBought = ChoiceBoxBuy.getItems().get(ChoiceBoxBuy.getSelectionModel().getSelectedIndex());
                BoughtItems[i].setNameBought(myBought);
                break;
            }
        }
        for (int i = 0; i < items.length;i++) {
            if(items[i].getName() == "default"){
                String addToItems = ChoiceBoxBuy.getItems().get(ChoiceBoxBuy.getSelectionModel().getSelectedIndex());
                if(addToItems.equals("Sulfuras, Hand of Ragnaros")){
                    items[i].setName(addToItems);
                    items[i].setQuality(80);
                    items[i].setSellIn(0);
                    totalProfits = totalProfits - items[i].getQuality();
                }
                else if(addToItems.equals("AgedBrie")){
                    items[i].setName(addToItems);
                    items[i].setQuality(0);
                    items[i].setSellIn(2);
                    totalProfits = totalProfits - items[i].getQuality();
                }
                else if(addToItems.equals("Backstage passes to a TAFKAL80ETC concert")){
                    items[i].setName(addToItems);
                    items[i].setQuality(20);
                    items[i].setSellIn(15);
                    totalProfits = totalProfits - items[i].getQuality();
                }
                else if(addToItems.equals("Conjured Mana Cake")){
                    items[i].setName(addToItems);
                    items[i].setQuality(6);
                    items[i].setSellIn(3);
                    totalProfits = totalProfits - items[i].getQuality();
                }
                else if(addToItems.equals("Elixir of the Mongoose")){
                    items[i].setName(addToItems);
                    items[i].setQuality(7);
                    items[i].setSellIn(5);
                    totalProfits = totalProfits - items[i].getQuality();
                }
                else if(addToItems.equals("+5 Dexterity Vest")){
                    items[i].setName(addToItems);
                    items[i].setQuality(20);
                    items[i].setSellIn(10);
                    totalProfits = totalProfits - items[i].getQuality();
                }
                break;
            }
        }
        showFigure();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int i = 1;
        int j = 1;
        XYChart.Series set1 = new XYChart.Series();
        XYChart.Series set2 = new XYChart.Series();
        cd[1] = "Monday";
        cd[2] = "Tuesday";
        cd[3] = "Wednesday";
        cd[4] = "Thursday";
        cd[5] = "Friday";
        cd[6] = "Saturday";
        cd[7] = "Sunday";

        while(i<7) {
            sell[i] = "" + storeSellIn[i];
            set1.getData().add(new XYChart.Data<>(sell[i], numbSellIn[i]));
            i++;
        }
        set2.getData().add(new XYChart.Data<>(cd[i], numbCd[i]));

        while(j<8) {
            set2.getData().add(new XYChart.Data<>(cd[j], numbCd[j]));
            j++;
        }
        SellinBarChart.getData().setAll(set1);
        CreationdateBarChart.getData().setAll(set2);
        loadData();
    }


    public void changeWindow() throws Exception {
        Second second=new Second();
        second.showWindow();
    }

}
