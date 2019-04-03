import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameMake{

    int numRooms;
    private String[] rLable = {"RN:", "LD:", "SD:", "IN:", "ID:", "RC:", "#"};
    protected ArrayList<Room> rooms = new ArrayList<>();
    protected String[] verbs = {"go", "take", "place", "open", "examine", "help", "use"};
    protected String[] nouns = {"north", "east", "west", "south", "inventory", "ruby", "emerald", "statue", "pickaxe"};
    boolean a = false;
    boolean b = false;
    boolean finished = false;

    GameMake() {
    }

    GameMake(String file) throws FileNotFoundException {
        ReadFile(file); // loads in all rooms, their connections, and each rooms items
        RunGame();
    }

    public void ReadFile(String file) throws FileNotFoundException {
        Scanner s = new Scanner(new File(file));
        String line = s.nextLine();
        // take care of the introduction
        if (line.indexOf("Intro") != -1) {
            line = s.nextLine();
            while (line.indexOf("Number of Rooms:") == -1) {
                if (line.equals("")) {
                } else {
                    System.out.println(line);
                }
                line = s.nextLine();
            }
        }
        numRooms = s.nextInt();
        line = s.nextLine();
        line = s.nextLine();
        line = s.nextLine();
        // -----------------------------
        if (line.indexOf("Rooms") != -1) {
            for (int a = 0; a < numRooms; a++) {
                ArrayList<String> strings = new ArrayList<>();
                int[] conn = new int[4];
                line = s.next();
                int i = 0;
                while (line.indexOf(rLable[i]) != -1) {
                    StringBuilder hold = new StringBuilder();
                    if (line.indexOf("RC:") != -1) {
                        for (int j = 0; j < conn.length; j++) {
                            conn[j] = s.nextInt();
                        }
                    }
                    line = s.next();
                    while (line.indexOf(rLable[i + 1]) == -1) {
                        if (line.equals("||")) {
                            hold.append('\n');
                            line = s.next();
                            continue;
                        }
                        hold.append(line + " ");
                        line = s.next();
                    }
                    if (hold.toString().equals("")) {
                    } else {
                        String piece = hold.toString().trim();
                        strings.add(piece);
                    }
                    if (line.equals("#")) {
                        break;
                    }
                    i++;
                }
                // new rooms
                rooms.add(new Room(strings, conn));
            }
        }
    }

    public void RunGame() {
        Player player = new Player();
        rooms.get(player.getPlayerLocation()).printRoom();
        while (finished == false) {
            ProcessCommand(ReadInput(), player);
        }
    }

    public String ReadInput() {
        String command = "";
        Scanner c = new Scanner(System.in);
        command = c.nextLine();
        return command;
    }

    public void ProcessCommand(String c, Player player) {
        String verb;
        String noun;
        boolean help = false;
        if (c.equals("help") || c.equals("Help")){
            printHelp();
            help = true;
        }
        int index = c.indexOf(" ");
        if (index != -1) {
            verb = c.substring(0, index);
            noun = c.substring(index + 1);
        }
        else{
            verb = "";
            noun = "";
        }
        boolean fail = false;
        boolean verbC = true;
        boolean nounC = false;
        for (int i = 0; i < verbs.length; i++) {
            if (verb.equals(verbs[i])) {
                verbC = true;
                break;
            }
        }
        for (int i = 0; i < nouns.length; i++) {
            if (noun.equals(nouns[i])) {
                nounC = true;
                break;
            }
        }
        if ((verbC == false || nounC == false) && help == false) {
            System.out.println("Command unknown. Try again!");
            fail = true;
        }
        if (fail == false) {
            if (verb.equals("go")) {
                Room a = rooms.get(player.getPlayerLocation());
                if (player.getPlayerLocation() == 0) {
                    a.setVisited(true);
                }
                int r = a.getDoor(noun);
                player.changeLocation(r);
                if (r != -1) {
                    rooms.get(player.getPlayerLocation()).printRoom();
                    rooms.get(player.getPlayerLocation()).setVisited(true);
                }
                if (player.getPlayerLocation() == rooms.size() - 1) {
                    finished = true;
                }
            } else if (verb.equals("take")) {
                Room b = rooms.get(player.getPlayerLocation());
                for (int i = 0; i < b.contentsSize(); i++) {
                    if (b.getContents(i).getItemName().toLowerCase().equals(noun)) {
                        player.addInventory(b.getContents(i));
                        System.out.println();
                        System.out.println("You picked up the " + noun);
                    } else {
                        System.out.println();
                        System.out.println("That item can not be picked up.");
                    }
                }
            } else if (verb.equals("place")) {
                boolean has = player.inventoryContains(noun);
                if (has == false) {
                    System.out.println();
                    System.out.println("You do not have this item.");
                }
                else if (has == true && rooms.get(player.getPlayerLocation()).contentsContains("statue")) {
                    player.removeInventory(noun);
                    System.out.println();
                    System.out.println("You have placed the " + noun +".");
                    if (noun.toLowerCase().equals("ruby")){
                        a = true;
                    }
                    if (noun.toLowerCase().equals("emerald")){
                        b = true;
                    }
                }
                else if (has == true){
                    System.out.println("This item can not be placed here.");
                }
                if (a == true && b == true) {
                    rooms.get(player.getPlayerLocation()).setDoor("east", 8);
                    System.out.println("A door opens from behind the statue!");
                }
            } else if (verb.equals("open")) {
                if (noun.equals("inventory")) {
                    System.out.println();
                    System.out.println("Your inventory contains the following: ");
                    if (player.inventorySize() == 0) {
                        System.out.println("nothing");
                    }
                    for (int i = 0; i < player.inventorySize(); i++) {
                        System.out.println(player.getInventory(i).getItemName());
                    }
                }
            } else if (verb.equals("examine")) {
                Room a = rooms.get(player.getPlayerLocation());
                for (int i = 0; i < a.contentsSize(); i++) {
                    if (a.getContents(i).getItemName().toLowerCase().equals(noun)) {
                        System.out.println();
                        System.out.println(a.getContents(i).getItemDes());
                    }
                }
            } else if (verb.equals("use")){
                if (noun.equals("pickaxe")) {
                    Room a = rooms.get(player.getPlayerLocation());
                    if (a.contentsContains("door")) {
                        System.out.println();
                        System.out.println("You smash though the rubble!");
                        rooms.get(player.getPlayerLocation()).setDoor("west", 7);
                    }
                    else{
                        System.out.println();
                        System.out.println("You can not use that here");
                    }
                }
                else{
                    System.out.println();
                    System.out.println("You can not use that");
                }
            }
            else {
                System.out.println("That is not a command. Try 'help' for assistance");
            }
        }
    }

    public void printHelp(){
        System.out.println();
        System.out.println("-----Help Menu-----");
        System.out.println("All commands are two words besides the help command");
        System.out.println("Available Commands:");
        System.out.println("- help");
        System.out.println("- go      (followed by a direction: north, south, east, west)");
        System.out.println("- take    (followed by an object in the game)");
        System.out.println("- place   (followed by an object in the game)");
        System.out.println("- open    (followed by inventory)");
        System.out.println("- examine (followed by an object in the game)");
        System.out.println("- use     (followed by an object in the game)");
    }
}
