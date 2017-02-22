package week1

import java.util.concurrent.Callable

object PNorm {

  def power(base: Int,exp: Double): Int = math.exp(exp * math.log(math.abs(base))).toInt

  def sumSegment(a: Array[Int], p: Double, s: Int, t: Int): Int
    = (a slice(s,t)).map(math.exp(_).floor.toInt).sum
  
  def pNormTwoPart(a: Array[Int], p: Double): Int = {
    def runThread(from: Int, to: Int) = {
      var value = 0
      new Thread() {
        override def run() = {
          value = sumSegment(a, p, from,to)
        }

        def getValue() = value
      }
    }

    val m = a.length / 2
    val (t1, t2) = (runThread(0,m), runThread(m, a.length))
    t1.start()
    t2.start()
    t1.join()
    t2.join()

    power(t1.getValue() + t2.getValue(), 1/p)
  }
}
