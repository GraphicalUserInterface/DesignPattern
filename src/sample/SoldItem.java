package sample;

public class SoldItem {
    public SoldItem(String nameSold, int soldQuality) {
        this.nameSold = nameSold;
        this.soldQuality = soldQuality;
    }

    @Override
    public String toString() {
        return "SoldItem{" +
                "nameSold='" + nameSold + '\'' +
                ", soldQuality=" + soldQuality +
                '}';
    }

    private String nameSold;
    private int soldQuality;

    public String getNameSold() {
        return nameSold;
    }

    public void setNameSold(String nameSold) {
        this.nameSold = nameSold;
    }

    public int getSoldQuality() {
        return soldQuality;
    }

    public void setSoldQuality(int soldQuality) {
        this.soldQuality = soldQuality;
    }


}
