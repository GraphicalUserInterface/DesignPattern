package sample;

public class BoughtItem {

    public int getBoughtQuality() {
        return BoughtQuality;
    }

    public void setBoughtQuality(int BoughtQuality) {
        this.BoughtQuality = BoughtQuality;
    }

    public String nameBought;
    public int BoughtQuality;

    public String getNameBought() {
        return nameBought;
    }

    public void setNameBought(String nameBought) {
        this.nameBought = nameBought;
    }

    public BoughtItem(String nameBought, int boughtQuality) {
        this.nameBought = nameBought;
        BoughtQuality = boughtQuality;
    }

    @Override
    public String toString() {
        return "BoughtItem{" +
                "nameBought='" + nameBought + '\'' +
                ", BoughtQuality=" + BoughtQuality +
                '}';
    }
}
