
import common._

package object scalashop {

  /** The value of every pixel is represented as a 32 bit integer. */
  type RGBA = Int

  /** Returns the red component. */
  def red(c: RGBA): Int = (0xff000000 & c) >>> 24

  /** Returns the green component. */
  def green(c: RGBA): Int = (0x00ff0000 & c) >>> 16

  /** Returns the blue component. */
  def blue(c: RGBA): Int = (0x0000ff00 & c) >>> 8

  /** Returns the alpha component. */
  def alpha(c: RGBA): Int = (0x000000ff & c) >>> 0

  /** Used to create an RGBA value from separate components. */
  def rgba(r: Int, g: Int, b: Int, a: Int): RGBA = {
    (r << 24) | (g << 16) | (b << 8) | (a << 0)
  }

  /** Restricts the integer into the specified range. */
  def clamp(v: Int, min: Int, max: Int): Int = {
    if (v < min) min
    else if (v > max) max
    else v
  }

  /** Image is a two-dimensional matrix of pixel values. */
  class Img(val width: Int, val height: Int, private val data: Array[RGBA]) {
    def this(w: Int, h: Int) = this(w, h, new Array(w * h))
    def apply(x: Int, y: Int): RGBA = data(y * width + x)
    def update(x: Int, y: Int, c: RGBA): Unit = data(y * width + x) = c
  }

  implicit def tupleAddition(t: (Int,Int,Int,Int)) = new TupleAdder(t)

  class TupleAdder(val t: (Int,Int,Int,Int)) {
    def +(t2: (Int,Int,Int,Int)) = (t._1 + t2._1,t._2 + t2._2,t._3 + t2._3,t._4 + t2._4)
    def /(t2: (Int,Int,Int,Int)) = (t._1 / t2._1,t._2 / t2._2,t._3 / t2._3,t._4 / t2._4)
  }

  /** Computes the blurred RGBA value of a single pixel of the input image. */
  def boxBlurKernel(src: Img, x: Int, y: Int, radius: Int): RGBA = {
    val pixels = (for {
      i <- x-radius to x+radius
      j <- y-radius to y+radius
      pixel = src(clamp(i,0,src.height-1),clamp(j,0,src.width-1))
    } yield pixel).toList

    val p = pixels.map(x => (red(x),green(x),blue(x),alpha(x)))
    val p2 = p.reduceLeft((x,y) => x + y) / (pixels.size,pixels.size,pixels.size,pixels.size)

    rgba(p2._1,p2._2,p2._3,p2._4)
  }

}
