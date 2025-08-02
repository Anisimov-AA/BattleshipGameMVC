package test;

import static org.junit.jupiter.api.Assertions.*;

import battleship.BattleshipModel;
import battleship.ShipType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the BattleshipModel class.
 * Verifies ship placement and player interaction behavior.
 */
class BattleshipModelTest {

  private BattleshipModel model;

  @BeforeEach
  void setUp() {
    model = new BattleshipModel();
    model.startGame();
  }

  // 1. Ship placement system testing

  /**
   * Ensure that all ships are reliably placed every time the game starts.
   * Repeats the placement check 100 times to validate randomness and completeness.
   */
  @Test
  void testShipPlacementReliability() {
    for(int i = 0; i < 100; i++) {
      model = new BattleshipModel();
      model.startGame();
      testAllShipsArePlaced(model.getShipGrid());
    }
  }

  /**
   * Verifies that each ship type has the correct number of cells occupied in the grid.
   * @param grid the ship grid to validate
   */
  void testAllShipsArePlaced(ShipType[][] grid) {
    // count cells for each ship type
    int aircraftCarrierCells = 0;
    int battleshipCells = 0;
    int submarineCells = 0;
    int destroyerCells = 0;
    int patrolBoatCells = 0;

    // loops to count each ship type
    for(int i = 0; i < grid.length; i++) {
      for(int j = 0; j < grid.length; j++) {
        if(grid[i][j] == ShipType.AIRCRAFT_CARRIER) aircraftCarrierCells++;
        if(grid[i][j] == ShipType.BATTLESHIP) battleshipCells++;
        if(grid[i][j] == ShipType.SUBMARINE) submarineCells++;
        if(grid[i][j] == ShipType.DESTROYER) destroyerCells++;
        if(grid[i][j] == ShipType.PATROL_BOAT) patrolBoatCells++;
      }
    }

    // assertions to verify counts
    assertEquals(ShipType.AIRCRAFT_CARRIER.getSize(), aircraftCarrierCells);
    assertEquals(ShipType.BATTLESHIP.getSize(), battleshipCells);
    assertEquals(ShipType.SUBMARINE.getSize(), submarineCells);
    assertEquals(ShipType.DESTROYER.getSize(), destroyerCells);
    assertEquals(ShipType.PATROL_BOAT.getSize(), patrolBoatCells);
  }

  // 2. Player interaction system testing

  /**
   * Test edge-case validations for the makeGuess method:
   * - Out-of-bounds coordinates
   * - Duplicate guesses
   * - Game over scenario
   */
  @Test
  void testMakeGuessValidations() {
    model.startGame();

    // Your task: Test the exception cases
    // out of bounds coordinates
    assertThrows(IllegalArgumentException.class, () -> model.makeGuess(-1, 0));
    assertThrows(IllegalArgumentException.class, () -> model.makeGuess(10, 0));
    assertThrows(IllegalArgumentException.class, () -> model.makeGuess(0, -1));
    assertThrows(IllegalArgumentException.class, () -> model.makeGuess(0, 10));

    // already guessed position
    model.makeGuess(0,0);
    assertThrows(IllegalArgumentException.class, () -> model.makeGuess(0,0));

    // game over state
    // not implemented yet
  }

  /**
   * Test that a guess on a ship location registers as a hit
   */
  @Test
  void testMakeGuessHit() {
    model.startGame();

    ShipType[][] shipGrid = model.getShipGrid();
    for (int i = 0; i < shipGrid.length; i++) {
      for (int j = 0; j < shipGrid[i].length; j++) {
        if(shipGrid[i][j] != null) {
          assertTrue(model.makeGuess(j, i)); // found ship, test hit
          return; // exit test after first hit
        }
      }
    }
    fail("No ships found");
  }

  /**
   * Test that a guess on an empty location registers as a miss.
   */
  @Test
  void testMakeGuessMiss() {
    model.startGame();

    ShipType[][] shipGrid = model.getShipGrid();
    for (int i = 0; i < shipGrid.length; i++) {
      for (int j = 0; j < shipGrid[i].length; j++) {
        if(shipGrid[i][j] == null) {
          assertFalse(model.makeGuess(j, i)); // found ship, test hit
          return; // exit test after first hit
        }
      }
    }
    fail("No empty space found");
  }

}