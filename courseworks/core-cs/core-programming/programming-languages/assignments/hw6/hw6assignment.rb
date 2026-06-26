# University of Washington, Programming Languages, Homework 6, hw6assignment.rb

# This is the only file you turn in, so do not modify the other files as
# part of your solution.

class MyPiece < Piece
  # Class constant containing exactly the ten "normal" pieces (7 classic + 3 new ones) [cite: 50]
  All_My_Pieces = Piece::All_Pieces + [
    # 1. 5-block long line piece
    [[[0, 0], [-1, 0], [1, 0], [2, 0], [-2, 0]],
     [[0, 0], [0, -1], [0, 1], [0, 2], [0, -2]]],
    # 2. 5-block non-line corner/L piece [cite: 57]
    Piece.rotations([[0, 0], [1, 0], [0, 1], [0, 2], [1, 2]]),
    # 3. 3-block corner piece
    Piece.rotations([[0, 0], [1, 0], [0, 1]])
  ]

  # Class method to choose the next piece randomly from the 10 enhanced pieces [cite: 29]
  def self.next_piece (board)
    MyPiece.new(All_My_Pieces.sample, board)
  end

  # Class method to generate the 1x1 cheat piece [cite: 34]
  def self.cheat_piece (board)
    MyPiece.new([[[0, 0]]], board)
  end
end

class MyBoard < Board
  def initialize (game)
    super(game)
    @cheat_requested = false
  end

  # Sets @current_block to the next piece, accounting for the cheat status [cite: 49]
  def next_piece
    if @cheat_requested
      @current_block = MyPiece.cheat_piece(self)
      @cheat_requested = false
    else
      @current_block = MyPiece.next_piece(self)
    end
    @current_pos = nil
  end

  # Overridden to handle block structures of any size dynamically (1, 3, 4, or 5 blocks)
  def store_current
    locations = @current_block.current_rotation
    displacement = @current_block.position
    (0..(locations.size - 1)).each{|index|
      current = locations[index];
      @grid[current[1]+displacement[1]][current[0]+displacement[0]] =
      @current_pos[index]
    }
    remove_filled
    @delay = [@delay - 2, 80].max
  end

  # Rotates the current piece 180 degrees (moving 2 indices over in the rotation array) [cite: 27]
  def rotate_180
    if !game_over? and @game.is_running?
      @current_block.move(0, 0, 2)
    end
    draw
  end

  # Triggers the cheat mechanism if conditions are met [cite: 33, 34]
  def trigger_cheat
    if !@cheat_requested && @score >= 100
      @score -= 100
      @cheat_requested = true
      @game.update_score
    end
  end
end

class MyTetris < Tetris
  # Overridden to wire up our custom enhanced Board class
  def set_board
    @canvas = TetrisCanvas.new
    @board = MyBoard.new(self)
    @canvas.place(@board.block_size * @board.num_rows + 3,
                  @board.block_size * @board.num_columns + 6, 24, 80)
    @board.draw
  end

  # Overridden to append our new key bindings while preserving standard ones [cite: 44]
  def key_bindings
    super
    # 'u' key rotates the active piece 180 degrees [cite: 27]
    @root.bind('u', proc {@board.rotate_180})

    # 'c' key activates the cheat block mechanism [cite: 33]
    @root.bind('c', proc {@board.trigger_cheat})
  end
end
