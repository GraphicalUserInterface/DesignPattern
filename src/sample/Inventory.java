package sample;

public class Inventory {

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

    public Item[] items;


    public Inventory() {
        super();
        items = new Item[]{
                new Item(Dexterity_Vest, 10, 20),
                new Item(Aged_Brie, 2, 0),
                new Item(Elixir_of_the_Mongoose, 5, 7),
                new Item(Sulfuras_Hand_of_Ragnaros, 0, 80),
                new Item(Backstage_passes_to_a_TAFKAL80ETC_concert, 15, 20),
                new Item(Conjured_Mana_Cake, 3, 6)
        };

    }

    public void printInventory() {
        System.out.println("***************");
        for (Item item : items) {
            System.out.println(item);
        }
        System.out.println("***************");
        System.out.println("\n");
    }
    //new update Quality
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
    }
}