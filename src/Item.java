public class Item {
    protected String itemN;
    protected String itemD;

    Item(){ }
    Item(String n, String d) {
        this.itemN = n;
        this.itemD = d;
    }
    public String getItemName() { return itemN; }

    public String getItemDes() { return itemD; }
}