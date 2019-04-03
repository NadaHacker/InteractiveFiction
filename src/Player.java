import java.util.ArrayList;

class Player {
    protected int location;
    protected ArrayList<Item> inventory = new ArrayList<>();

    Player() { this.location = 0; }

    public void changeLocation(int r) {
        if (r == -1) {
            System.out.println();
            System.out.println("There is no way here");
            return;
        }
        location = r;
    }

    public int getPlayerLocation(){ return location; }

    public void addInventory(Item a){ inventory.add(a); }

    public void removeInventory(String item) {
        for (int i = 0; i < inventorySize(); i++) {
            if (inventory.get(i).equals(item)) {
                inventory.remove(i);
            }
        }
    }

    public Item getInventory(int index){return inventory.get(index); }

    public boolean inventoryContains(String item) {
        for (int i = 0; i < inventorySize(); i++) {
            if (inventory.get(i).getItemName().toLowerCase().equals(item.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public int inventorySize() { return inventory.size(); }
}