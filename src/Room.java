import java.util.ArrayList;

class Room {
    static final int NORTH = 0;
    static final int EAST = 1;
    static final int WEST = 2;
    static final int SOUTH = 3;

    protected String name;
    protected String longD;
    protected String shortD;
    protected ArrayList<Item> contents = new ArrayList<>();
    protected int[] doors = new int[4];
    protected boolean visited;

    Room() {
    }

    Room(ArrayList<String> s, int[] c) {
        this.name = s.get(0);
        this.longD = s.get(1);
        this.shortD = s.get(2);
        //------
        contents.add(new Item(s.get(3), s.get(4)));
        //------
        this.doors[NORTH] = c[0];
        this.doors[EAST] = c[1];
        this.doors[WEST] = c[2];
        this.doors[SOUTH] = c[3];
    }

    public int getDoor(String s) {
        int door;
        if (s.equals("north")) {
            return doors[0];
        }
        if (s.equals("east")) {
            return doors[1];
        }
        if (s.equals("west")) {
            return doors[2];
        }
        if (s.equals("south")) {
            return doors[3];
        }
        return -1;
    }

    public void printRoom() {
        System.out.println();

        System.out.println(this.name);
        if (this.visited == true) {
            System.out.println(this.shortD);
        } else {
            System.out.println(this.longD);
        }
    }

    public void setDoors(int north, int east, int west, int south) {
        doors[NORTH] = north;
        doors[EAST] = east;
        doors[WEST] = west;
        doors[SOUTH] = south;
    }

    public void setDoor(String dir, int room){
        if (dir.equals("west")) {
            doors[WEST] = room;
        }
        if (dir.equals("east")) {
            doors[EAST] = room;
        }
    }

    public int contentsSize(){ return contents.size(); }

    public Item getContents(int index) { return contents.get(index); }

    public boolean contentsContains(String noun) {
        boolean has = false;
        for (int i = 0; i < contents.size(); i++){
            if (contents.get(i).getItemName().equals(noun)){
                has = true;
            }
        }
        return has;
    }

    public int getDoor(int direction) { return doors[direction]; }

    public String getRoomName() { return name; }

    public String getLongDescription() { return longD; }

    public String getShortDescription() { return shortD; }

    public boolean getVisted() { return visited; }

    public void setVisited(boolean newVisited) { visited = newVisited; }
}