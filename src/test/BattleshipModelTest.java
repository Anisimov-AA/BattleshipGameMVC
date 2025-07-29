package test;

import static org.junit.jupiter.api.Assertions.*;

import battleship.BattleshipModel;
import battleship.ShipType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BattleshipModelTest {

  private BattleshipModel model;

  @BeforeEach
  void setUp() {
    model = new BattleshipModel();
    model.startGame();
  }

  // Ship placement (reliability testing)
  @Test
  void testShipPlacementReliability() {
    for(int i = 0; i < 100; i++) {
      model = new BattleshipModel();
      model.startGame();
      testAllShipsArePlaced(model.getShipGrid());
    }
  }

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


}