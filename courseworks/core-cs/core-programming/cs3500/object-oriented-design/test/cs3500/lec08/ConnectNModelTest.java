package cs3500.lec08;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ConnectNModelTest {
  ConnectNModel connect4 = ConnectNModelImpl.builder().build();

  ConnectNModelImpl connect3 = ConnectNModelImpl.builder().width(2).height(3).goal(3).players(2).build();

  @Test
  public void testDefaults() {
    assertEquals(7, ConnectNModel.DEFAULT_WIDTH);
    assertEquals(6, ConnectNModel.DEFAULT_HEIGHT);
    assertEquals(4, ConnectNModel.DEFAULT_GOAL);
  }

  @Test
  public void testGetWidth_4() {
    assertEquals(ConnectNModel.DEFAULT_WIDTH, connect4.getWidth());
  }

  @Test
  public void testGetWidth_2() {
    assertEquals(2, connect3.getWidth());
  }

  @Test
  public void testGetHeight_4() {
    assertEquals(ConnectNModel.DEFAULT_HEIGHT, connect4.getHeight());
  }

  @Test
  public void testGetHeight_3() {
    assertEquals(3, connect3.getHeight());
  }

  @Test
  public void testGetGoal_4() {
    assertEquals(ConnectNModel.DEFAULT_GOAL, connect4.getGoal());
  }

  @Test
  public void testGetGoal_3() {
    assertEquals(3, connect3.getGoal());
  }

  @Test
  public void testGetPlayers_4() {
    assertEquals(2, connect4.getPlayers());
  }

  @Test
  public void testGetPlayers_3() {
    assertEquals(2, connect3.getPlayers());
  }

  @Test
  public void testIsGameOver_no4() {
    assertFalse(connect4.isGameOver());
  }

  @Test
  public void testIsGameOver_no3() {
    assertFalse(connect3.isGameOver());
  }

  @Test
  public void testGetNextPlayer_first4() {
    assertEquals(0, connect4.getNextPlayer());
  }

  @Test
  public void testGetNextPlayer_first3() {
    assertEquals(0, connect3.getNextPlayer());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetWinner_exn4() {
    connect4.getWinner();
  }

  @Test(expected = IllegalStateException.class)
  public void testGetWinner_exn3() {
    connect3.getWinner();
  }

  @Test
  public void testGetPlayerAt_empty4() {
    assertNull(connect4.getPlayerAt(0, 0));
    assertNull(connect4.getPlayerAt(connect4.getWidth() - 1, connect4.getHeight() - 1));
  }

  @Test
  public void testGetPlayerAt_empty3() {
    assertNull(connect3.getPlayerAt(0, 0));
    assertNull(connect3.getPlayerAt(connect3.getWidth() - 1, connect3.getHeight() - 1));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testGetPlayerAt_oobCol4() {
    assertNull(connect4.getPlayerAt(connect4.getWidth(), connect4.getHeight() - 1));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testGetPlayerAt_oobCol3() {
    assertNull(connect3.getPlayerAt(connect3.getWidth(), connect3.getHeight() - 1));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testGetPlayerAt_oobRow4() {
    assertNull(connect4.getPlayerAt(connect4.getWidth() - 1, connect4.getHeight()));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testGetPlayerAt_oobRow3() {
    assertNull(connect3.getPlayerAt(connect3.getWidth() - 1, connect3.getHeight()));
  }

  @Test
  public void testMove_1_4() {
    assertEquals(0, connect4.move(0, 2));
    assertEquals((Integer) 0, connect4.getPlayerAt(2, 0));
    assertNull(connect4.getPlayerAt(2, 1));
  }

  @Test(expected = IllegalStateException.class)
  public void testMove_wrongPlayer1_4() {
    connect4.move(1, 2);
  }

  @Test(expected = IllegalStateException.class)
  public void testMove_wrongPlayer2_4() {
    connect4.move(0, 2);
    connect4.move(0, 2);
  }

  @Test
  public void testMove_winHorizontal_4() {
    assertEquals(0, connect4.move(0, 2));
    assertEquals(1, connect4.move(1, 2));
    assertEquals(0, connect4.move(0, 1));
    assertEquals(1, connect4.move(1, 1));
    assertEquals(0, connect4.move(0, 4));
    assertEquals(1, connect4.move(1, 4));
    assertFalse(connect4.isGameOver());
    assertEquals(0, connect4.move(0, 3));
    assertTrue(connect4.isGameOver());
    assertEquals(0, connect4.getWinner());
  }

  @Test
  public void testMove_winVertical_4() {
    assertEquals(0, connect4.move(0, 2));
    assertEquals(0, connect4.move(1, 3));
    assertEquals(1, connect4.move(0, 2));
    assertEquals(1, connect4.move(1, 3));
    assertEquals(2, connect4.move(0, 2));
    assertEquals(2, connect4.move(1, 3));
    assertEquals(0, connect4.move(0, 0));
    assertFalse(connect4.isGameOver());
    assertEquals(3, connect4.move(1, 3));
    assertTrue(connect4.isGameOver());
    assertEquals(1, connect4.getWinner());
  }

  @Test
  public void testMove_winDiagonal_4() {
    assertEquals(0, connect4.move(0, 0));
    assertEquals(0, connect4.move(1, 1));
    assertEquals(0, connect4.move(0, 2));
    assertEquals(1, connect4.move(1, 2));
    assertEquals(2, connect4.move(0, 2));
    assertEquals(0, connect4.move(1, 3));
    assertEquals(1, connect4.move(0, 3));
    assertEquals(2, connect4.move(1, 3));
    assertEquals(3, connect4.move(0, 3));
    assertEquals(4, connect4.move(1, 3));
    assertFalse(connect4.isGameOver());
    assertEquals(1, connect4.move(0, 1));
    assertTrue(connect4.isGameOver());
    assertEquals(0, connect4.getWinner());
  }

  @Test
  public void testMove_notTooTall_4() {
    assertEquals(0, connect4.move(connect4.getNextPlayer(), 0));
    assertEquals(1, connect4.move(connect4.getNextPlayer(), 0));
    assertEquals(2, connect4.move(connect4.getNextPlayer(), 0));
    assertEquals(3, connect4.move(connect4.getNextPlayer(), 0));
    assertEquals(4, connect4.move(connect4.getNextPlayer(), 0));
    assertEquals(5, connect4.move(connect4.getNextPlayer(), 0));
  }

  @Test(expected = IllegalStateException.class)
  public void testMove_tooTall_4() {
    testMove_notTooTall_4();
    connect4.move(connect4.getNextPlayer(), 0);
  }

  private void fourMoves_3() {
    connect3.move(0, 0);
    connect3.move(1, 1);
    connect3.move(0, 0);
    connect3.move(1, 1);
  }

  @Test
  public void testMove_stalemate_3() {
    fourMoves_3();
    connect3.move(0, 1);
    assertFalse(connect3.isGameOver());
    connect3.move(1, 0);
    assertTrue(connect3.isGameOver());
    assertEquals(ConnectNModelImpl.Status.Stalemate, connect3.getStatus());
  }

  @Test(expected = IllegalStateException.class)
  public void testMove_gameOver_3() {
    testMove_stalemate_3();
    connect3.move(0, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void testMove_tooTall_3() {
    fourMoves_3();
    connect3.move(0, 0);
    connect3.move(1, 0);
  }

  @Test
  public void testToString_3() {
    fourMoves_3();
    assertEquals("| | |\n" + "|0|1|\n" + "|0|1|\n" + "+-+-+\n" + " 0 1\n", connect3.toString());
  }
}