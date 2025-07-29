package battleship;

import static battleship.ShipType.AIRCRAFT_CARRIER;
import static battleship.ShipType.BATTLESHIP;
import static battleship.ShipType.DESTROYER;
import static battleship.ShipType.PATROL_BOAT;
import static battleship.ShipType.SUBMARINE;

import java.util.Random;

/**
 * An implementation of the Battleship game model.
 */
public class BattleshipModel implements IBattleshipModel{

  private ShipType[][] shipPlacementGrid; // where ships actually are
  private static final int GRID_SIZE = 10;

  /**
   * Constructs a BattleshipModel with an empty 10x10 grid.
   */
  public BattleshipModel() {
    shipPlacementGrid = new ShipType[GRID_SIZE][GRID_SIZE];
  }

  @Override
  public void startGame() {
    clearGrid();

    // random ship placement for all 5 ships
    ShipType[] ships = {AIRCRAFT_CARRIER, BATTLESHIP, SUBMARINE, DESTROYER, PATROL_BOAT};
    Random random = new Random();

    for(ShipType ship : ships) {
      boolean shipPlaced = false;

      while (!shipPlaced) {
        int startX = random.nextInt(GRID_SIZE);
        int startY = random.nextInt(GRID_SIZE);
        boolean horizontal = random.nextBoolean(); // horizontal (true), vertical (false)

        if(canPlaceShip(startX, startY, horizontal, ship)) {
          placeShip(startX, startY, horizontal, ship);
          shipPlaced = true;
        }
      }
    }

  }

  /**
   * Clears the ship grid, setting all cells to null.
   */
  private void clearGrid(){
    for(int i = 0; i < GRID_SIZE; i++){
      for(int j = 0; j < GRID_SIZE; j++) {
        shipPlacementGrid[i][j] = null;
      }
    }
  }

  /**
   * Checks if a ship can be placed at the specified position without overlapping other ships or going out of bounds
   * @param startX  the starting x coordinate
   * @param startY  the starting y coordinate
   * @param horizontal  true to place the ship horizontally; false for vertically
   * @param shipType the type of ship to place
   * @return true if the ship can be placed; false otherwise
   */
  private boolean canPlaceShip(int startX, int startY, boolean horizontal, ShipType shipType){
    if(horizontal) {
      if(startX + shipType.getSize() > GRID_SIZE) return false;
      for(int i = 0; i < shipType.getSize(); i++) {
        if(shipPlacementGrid[startY][startX + i] != null) return false;
      }
    } else {
      if(startY + shipType.getSize() > GRID_SIZE) return false;
      for(int i = 0; i < shipType.getSize(); i++) {
        if(shipPlacementGrid[startY + i][startX] != null) return false;
      }
    }
    return true;
  }

  /**
   * Places the ship at the given location on the ship grid
   * @param startX  the starting x coordinate
   * @param startY  the starting y coordinate
   * @param horizontal  true to place the ship horizontally; false for vertically
   * @param shipType the ship to place
   */
  private void placeShip(int startX, int startY, boolean horizontal, ShipType shipType){
    for(int i = 0; i < shipType.getSize(); i++) {
      if(horizontal){
        shipPlacementGrid[startY][startX + i] = shipType;
      } else {
        shipPlacementGrid[startY + i][startX] = shipType;
      }
    }
  }

  @Override
  public boolean makeGuess(int row, int col) {
    return false;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public boolean areAllShipsSunk() {
    return false;
  }

  @Override
  public int getGuessCount() {
    return 0;
  }

  @Override
  public int getMaxGuesses() {
    return 0;
  }

  @Override
  public CellState[][] getCellGrid() {
    return new CellState[0][];
  }

  @Override
  public ShipType[][] getShipGrid() {
    ShipType[][] shipGridCopy = new ShipType[GRID_SIZE][GRID_SIZE];
    for(int i = 0; i < GRID_SIZE; i++){
      for(int j = 0; j < GRID_SIZE; j++) {
        shipGridCopy[i][j] = shipPlacementGrid[i][j];
      }
    }

    return shipGridCopy;
  }

  public static void main(String[] args) {
    BattleshipModel game = new BattleshipModel();
    game.startGame();

    ShipType[][] shipGridCopy = game.getShipGrid();
    for (int i = 0; i < shipGridCopy.length; i++) {
      for (int j = 0; j < shipGridCopy[i].length; j++) {
        if(shipGridCopy[i][j] == null) {
          System.out.print("." + " ");
        } else {
          System.out.print(shipGridCopy[i][j] + " ");
        }
      }
      System.out.println();
    }

  }
}
