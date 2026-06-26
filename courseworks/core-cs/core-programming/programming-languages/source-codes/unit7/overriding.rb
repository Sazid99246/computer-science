class Point
  attr_accessor :x, :y

  def initialize(x,y)
    @x = x
    @y = y
  end
  def distFromOrigin
    Math.sqrt(@x * @x  + @y * @y) # why a module method? Less OOP :-(
  end
  def distFromOrigin2
    Math.sqrt(x * x + y * y) # uses getter methods
  end

end

class ColorPoint < Point
  attr_accessor :color

  def initialize(x,y,c="clear") # or could skip this and color starts unset
    super(x,y) # keyword super calls same method in superclass
    @color = c
  end
end

class ThreeDPoint < Point
  attr_accessor :z

  def initialize(x,y,z)
    super(x,y)
    @z = z
  end
  def distFromOrigin
    d = super
    Math.sqrt(d * d + @z * @z)
  end
  def distFromOrigin2
    d = super
    Math.sqrt(d * d + z * z)
  end
end

class PolarPoint < Point
  # Interesting: by not calling super constructor, no x and y instance vars
  # In Java/C#/Smalltalk would just have unused x and y fields
  def initialize(r,theta)
    @r = r
    @theta = theta
  end
  def x
    @r * Math.cos(@theta)
  end
  def y
    @r * Math.sin(@theta)
  end
  def x= a
    b = y # avoids multiple calls to y method
    @theta = Math.atan2(b,a)
    @r = Math.sqrt(a*a + b*b)
    self
  end
  def y= b
    a = x # avoid multiple calls to y method
    @theta = Math.atan2(b,a)
    @r = Math.sqrt(a*a + b*b)
    self
  end
  def distFromOrigin # must override since inherited method does wrong thing
    @r
  end
  # inherited distFromOrigin2 already works!!
end


