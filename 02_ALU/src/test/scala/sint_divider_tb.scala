// ADS I Class Project
// Chair of Electronic Design Automation, RPTU in Kaiserslautern

package divider

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec
import scala.util.Random

class SIntDividerTest extends AnyFlatSpec with ChiselScalatestTester {
  "SInt Divider" should "test all" in {
    test(new SIntDivider).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
      dut.clock.setTimeout(0)

      val rand = new Random()
      var testNumber = 0
      var procent = 0.0
      println(f"$procent%.2f%%")

      dut.io.rst.poke(1.B)
      dut.clock.step(1)
      dut.io.rst.poke(0.B)

      //-------------------------------

      dut.io.a.poke(0.S)
      dut.io.b.poke(100.S)

      dut.clock.step(18)

      dut.io.valid.expect(1.B)
      dut.io.q.expect(0.S)
      dut.io.r.expect(0.S)

      testNumber += 1
      procent = testNumber / 100.0
      println(f"$procent%.2f%%")

      //---------------------------------

      dut.io.a.poke(100.S)
      dut.io.b.poke(0.S)

      dut.clock.step(18)

      dut.io.valid.expect(1.B)
      dut.io.q.expect(32767.S)
      dut.io.r.expect(100.S)

      testNumber += 1
      procent = testNumber / 100.0
      println(f"$procent%.2f%%")

      //---------------------------------

      dut.io.a.poke(0.S)
      dut.io.b.poke(0.S)

      dut.clock.step(18)

      dut.io.valid.expect(1.B)
      dut.io.q.expect(65535.S)
      dut.io.r.expect(0.S)

      testNumber += 1
      procent = testNumber / 100.0
      println(f"$procent%.2f%%")

      //---------------------------------

      dut.io.a.poke(32768.S)
      dut.io.b.poke(32768.S)

      dut.clock.step(18)

      dut.io.valid.expect(1.B)
      dut.io.q.expect(1.S)
      dut.io.r.expect(0.S)

      testNumber += 1
      procent = testNumber / 100.0
      println(f"$procent%.2f%%")

      //---------------------------------

      dut.io.a.poke(0.S)
      dut.io.b.poke(32768.S)

      dut.clock.step(18)

      dut.io.valid.expect(1.B)
      dut.io.q.expect(0.S)
      dut.io.r.expect(0.S)

      testNumber += 1
      procent = testNumber / 100.0
      println(f"$procent%.2f%%")

      //---------------------------------

      dut.io.a.poke(32768.S)
      dut.io.b.poke(0.S)

      dut.clock.step(18)

      dut.io.valid.expect(1.B)
      dut.io.q.expect(65535.S)
      dut.io.r.expect(32768.S)

      testNumber += 1
      procent = testNumber / 100.0
      println(f"$procent%.2f%%")

      //---------------------------------

      for(i <- 0 to 9993){
        val a = rand.nextInt(65536) - 32768 // -32768 to 32767
        var b = 0
        while (b == 0) {
          b = rand.nextInt(65536) - 32768 // -32768 to 32767
        }

        val q = a / b
        val r = a % b

        dut.io.a.poke(a.S)
        dut.io.b.poke(b.S)
        dut.clock.step(18)
        dut.io.valid.expect(1.B)
        dut.io.q.expect(q.S)
        dut.io.r.expect(r.S)

        testNumber += 1
        procent = testNumber / 100.0
        println(f"$procent%.2f%%")
      }
    }
  }
}
