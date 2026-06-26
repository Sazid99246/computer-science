# coding: iso-8859-1
class Rock
  def fight other
    other.fightWithRock self
  end

  def fightWithRock other
    "Tie"
  end

  def fightWithPaper other
    "Paper wins"
  end

  def fightWithScissors other
    "Rock wins"
  end
end

class Paper
  def fight other
    other.fightWithPaper self
  end

  def fightWithRock other
    "Paper wins"
  end

  def fightWithPaper other
    "Tie"
  end

  def fightWithScissors other
    "Scissors wins"
  end
end

class Scissors
  def fight other
    other.fightWithScissors self
  end

  def fightWithRock other
    "Rock wins"
  end

  def fightWithPaper other
    "Scissors wins"
  end

  def fightWithScissors other
    "Tie"
  end
end


class NegCounter
  def visitInt(int, arg)
    0
  end
  def visitNeg(neg, arg)
    1 + neg.e.accept(self)
  end
  def visitAdd(add, arg)
    add.e1.accept(self) + add.e2.accept(self)
  end
end

class Stringer
  def visitInt(int, arg)
    int.i.to_s
  end
  def visitNeg(neg, arg)
    "­(" + neg.e.accept(self) + ")"
  end
  def visitAdd(add, arg)
    "(" + add.e1.accept(self) + " + " + add.e2.accept(self) + ")"
  end
end

class Evaluator
  def visitInt(int, arg)
    int
  end
  def visitNeg(neg, arg)
    Int.new(­ neg.e.accept(self).i)
  end
  def visitAdd(add, arg)
    Int.new(add.e1.accept(self).i + add.e2.accept(self).i)
  end
end
