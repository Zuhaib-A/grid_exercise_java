package org.example;

import java.util.ArrayList;
import java.util.Scanner;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Sprint 2 Grid Exercise
 *
 */

class Grid {

    private int gridSize;
    private ArrayList<ArrayList<GridObject>> mapGrid;
    private int treasurePosI;
    private int treasurePosJ;

    public Grid(int gridSize, Player player) {
        this.gridSize = gridSize;
        this.mapGrid = createGrid(gridSize, player);
    }

    public int getGridSize() {
        return  gridSize;
    }

    public ArrayList<ArrayList<GridObject>> createGrid(int gridSize, Player player) {

        Random randomGen = new Random();
        int treasurePosI = randomGen.nextInt(gridSize);
        int treasurePosJ = randomGen.nextInt(gridSize);

        int initialPlayerPosI;
        int initialPlayerPosJ;

        do {
            initialPlayerPosI = randomGen.nextInt(gridSize);
            initialPlayerPosJ = randomGen.nextInt(gridSize);
        } while (initialPlayerPosI == treasurePosI && initialPlayerPosJ == treasurePosJ);

        ArrayList<ArrayList<GridObject>> mapGrid = new ArrayList<>();
        for (int i = 0; i < gridSize; i++) {

            ArrayList<GridObject> row = new ArrayList<>();

            for (int j = 0; j < gridSize; j++) {
                if (i == initialPlayerPosI) {
                    if (j == initialPlayerPosJ) {
                        row.add(player);
                        player.setPositionI(initialPlayerPosI);
                        player.setPositionJ(initialPlayerPosJ);
                    } else {
                        row.add(null);
                    }
                } else {
                    if (i == treasurePosI && j == treasurePosJ) {
                        GridTreasure randomTreasure = new GridTreasure("treasure");
                        row.add(randomTreasure);
                        System.out.println("Treasure Row: " + treasurePosI + ", Column: " + treasurePosJ + ")");
                    } else {
                        int randMonsterLocation;

                        do {
                            randMonsterLocation = randomGen.nextInt(gridSize);
                        } while (i == treasurePosI && randMonsterLocation == treasurePosJ);

                        if (j == randMonsterLocation) {
                            GridMonster randomMonster = new GridMonster("monster");
                            row.add(randomMonster);
                        } else {
                            row.add(null);
                        }
                    }
                }
            }

            mapGrid.add(row);
        }

        return mapGrid;
    }

    public ArrayList<ArrayList<GridObject>> getMapGrid() {
        return mapGrid;
    }

    public String getGridAsString() {
        StringBuilder result = new StringBuilder();
        for (ArrayList<GridObject> row : mapGrid) {
            for (GridObject gridObject : row) {
                if (gridObject != null) {
                    switch (gridObject.getObjectType()) {
                        case "monster":
                            result.append(((GridMonster) gridObject).getMonsterNickname());
                            result.append("   ");
                            break;
                        case "treasure":
                            result.append(((GridTreasure) gridObject).getTreasureNickname());
                            result.append("          ");
                            break;
                        case "player":
                            result.append(((Player) gridObject).getPlayerNickname());
                            result.append("          ");
                            break;
                    }
                } else {
                    result.append("0");
                    result.append("         ");
                }

            }
            result.append("\n");
        }
        result.append("\n-> Monster Types:");
        result.append("\nFM = Fire Monster, RM = Rock Monster, WM = Water Monster");
        result.append("\nStr = Strong, Avg = Average, Wk = Weak");
        result.append("\n-> !P! = Player, !T! = Treasure");

        return result.toString();
    }

    public int getTreasurePosI() {
        return treasurePosI;
    }

    public int getTreasurePosJ() {
        return treasurePosJ;
    }

}

class GridObject {

    private String objectType;
    private int positionI;
    private int positionJ;

    public GridObject(String objectType) {
        this.objectType = objectType;
    }

    public int getPositionI() {
        return positionI;
    }

    public void setPositionI(int positionI) {
        this.positionI = positionI;
    }

    public int getPositionJ() {
        return positionJ;
    }

    public void setPositionJ(int positionJ) {
        this.positionJ = positionJ;
    }

    @Override
    public String toString() {
        return "objectType= " + objectType;
    }

    public String getObjectType(){
        return objectType;
    }
}

class GridMonster extends GridObject {

    private String monsterName;
    private String monsterStrength;
    private String monsterGreeting;
    private String monsterNickname;

    List<String> monsterNamesList = Arrays.asList("Fire Monster", "Water Monster", "Rock Monster");
    List<String> monsterNickname1List = Arrays.asList("FM-", "WM-", "RM-");
    List<String> monsterStrengthList = Arrays.asList("Strong", "Average", "Weak");
    List<String> monsterNickname2List = Arrays.asList("Str", "Avg", "Wk");
    List<String> monsterGreetingList = Arrays.asList("Hi.", "Hello.", "I'm a monster.");

    public GridMonster(String objectType) {
        super(objectType);
        Random rand = new Random();

        int monstersNicknamePos1 = rand.nextInt(monsterNamesList.size());
        this.monsterName = monsterNamesList.get(monstersNicknamePos1);

        int monstersNicknamePos2 = rand.nextInt(monsterStrengthList.size());
        this.monsterStrength = monsterStrengthList.get(monstersNicknamePos2);
        this.monsterGreeting = monsterGreetingList.get(rand.nextInt(monsterGreetingList.size()));

        this.monsterNickname = monsterNickname1List.get(monstersNicknamePos1) + monsterNickname2List.get(monstersNicknamePos2);

    }

    @Override
    public String toString() {
        return super.toString() +
                "\nmonster name= " + monsterName +
                "\nmonster strength= " + monsterStrength +
                "\nmonster greeting= " + monsterGreeting +
                "\nmonster nickname= " + monsterNickname;
    }

    public String getMonsterNickname(){
        return monsterNickname;
    }
}

class GridTreasure extends GridObject {
    private String treasureNickname;

    public GridTreasure(String objectType) {
        super(objectType);
        this.treasureNickname = "!T!";
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public String getTreasureNickname() {
        return treasureNickname;
    }
}

class Player extends GridObject {

    private String playerName;
    private String playerNickname;
    private int prevPositionI;
    private int prevPositionJ;

    public Player(String playerName) {
        super("player");
        this.playerName = playerName;
        this.playerNickname = "!P!";
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPlayerNickname() {
        return playerNickname;
    }


    public void movePlayer(String movement, Grid grid) {
        int currentPositionI = getPositionI();
        int currentPositionJ = getPositionJ();

        prevPositionI = currentPositionI;
        prevPositionJ = currentPositionJ;

        int newI = currentPositionI;
        int newJ = currentPositionJ;

        if (movement.equalsIgnoreCase("up")) {
            newI--;
        } else if (movement.equalsIgnoreCase("down")) {
            newI++;
        } else if (movement.equalsIgnoreCase("left")) {
            newJ--;
        } else if (movement.equalsIgnoreCase("right")) {
            newJ++;
        }

        if (isValidPosition(newI, newJ, grid)) {
            GridObject currentPlayer = grid.getMapGrid().get(currentPositionI).get(currentPositionJ);
            grid.getMapGrid().get(newI).set(newJ, currentPlayer);

            grid.getMapGrid().get(prevPositionI).set(prevPositionJ, null);

            setPositionI(newI);
            setPositionJ(newJ);
        }
    }

    private boolean isValidPosition(int i, int j, Grid grid) {
        return i >= 0 && i < grid.getGridSize() && j >= 0 && j < grid.getGridSize();
    }
}

class GameState {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Please enter your name:");
        String enteredPlayerName = input.nextLine();
        Player playerMain = new Player(enteredPlayerName);
        System.out.printf("Hello %s!\n", playerMain.getPlayerName());

        System.out.println("Enter a grid size n (this will create a symmetrical grid of size n):");
        int userGridSize = input.nextInt();
        Grid grid = new Grid(userGridSize, playerMain);

        System.out.println();

        System.out.println(grid.getGridAsString());




        int moves = 0;
        while (true) {
            System.out.println("Would you like to move up, down, right, or left? (or type 'exit' to quit)");
            String nextMovement = input.next();

            if (nextMovement.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                break;
            }

            playerMain.movePlayer(nextMovement, grid);
            moves++;

            if (playerMain.getPositionI() == grid.getTreasurePosI() && playerMain.getPositionJ() == grid.getTreasurePosJ()) {
                System.out.println("Congratulations, you found the treasure in " + moves + " moves!");
                break;
            }

            System.out.println(grid.getGridAsString());
        }
    }
}
