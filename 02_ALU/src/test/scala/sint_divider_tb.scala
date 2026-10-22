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
      println(f"SInt Test: $procent%.2f%%")

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
      println(f"SInt Test: $procent%.2f%% (0 / 100 = 0, Remainder: 0)")

      //---------------------------------

      dut.io.a.poke(100.S)
      dut.io.b.poke(0.S)

      dut.clock.step(18)

      dut.io.valid.expect(1.B)
      dut.io.q.expect(32767.S)
      dut.io.r.expect(100.S)

      testNumber += 1
      procent = testNumber / 100.0
      println(f"SInt Test: $procent%.2f%% (100 / 0 = 32767, Remainder: 100)")

      //---------------------------------

      dut.io.a.poke(0.S)
      dut.io.b.poke(0.S)

      dut.clock.step(18)

      dut.io.valid.expect(1.B)
      dut.io.q.expect(32767.S)
      dut.io.r.expect(0.S)

      testNumber += 1
      procent = testNumber / 100.0
      println(f"SInt Test: $procent%.2f%% (0 / 0 = 32767, Remainder: 0)")

      //---------------------------------

      dut.io.a.poke(32767.S)
      dut.io.b.poke(32767.S)

      dut.clock.step(18)

      dut.io.valid.expect(1.B)
      dut.io.q.expect(1.S)
      dut.io.r.expect(0.S)

      testNumber += 1
      procent = testNumber / 100.0
      println(f"SInt Test: $procent%.2f%% (32767 / 32767 = 1, Remainder: 0)")

      //---------------------------------

      dut.io.a.poke(0.S)
      dut.io.b.poke(32767.S)

      dut.clock.step(18)

      dut.io.valid.expect(1.B)
      dut.io.q.expect(0.S)
      dut.io.r.expect(0.S)

      testNumber += 1
      procent = testNumber / 100.0
      println(f"SInt Test: $procent%.2f%% (0 / 32767 = 0, Remainder: 0)")

      //---------------------------------

      dut.io.a.poke(32767.S)
      dut.io.b.poke(0.S)

      dut.clock.step(18)

      dut.io.valid.expect(1.B)
      dut.io.q.expect(32767.S)
      dut.io.r.expect(32767.S)

      testNumber += 1
      procent = testNumber / 100.0
      println(f"SInt Test: $procent%.2f%% (32767 / 0 = 32767, Remainder: 32767)")

      //---------------------------------

      dut.io.a.poke(-32768.S)
      dut.io.b.poke(1.S)

      dut.clock.step(18)

      dut.io.valid.expect(1.B)
      dut.io.q.expect(-32768.S)
      dut.io.r.expect(0.S)

      testNumber += 1
      procent = testNumber / 100.0
      println(f"SInt Test: $procent%.2f%% (-32768 / 1 = -32768, Remainder: 0)")

      //---------------------------------

      dut.io.a.poke(-32768.S)
      dut.io.b.poke(-1.S)

      dut.clock.step(18)

      dut.io.valid.expect(1.B)
      dut.io.q.expect(-32768.S)
      dut.io.r.expect(0.S)

      testNumber += 1
      procent = testNumber / 100.0
      println(f"SInt Test: $procent%.2f%% (-32768 / -1 = -32768, Remainder: 0)")

      //---------------------------------

      dut.io.a.poke(32767.S)
      dut.io.b.poke(-1.S)

      dut.clock.step(18)

      dut.io.valid.expect(1.B)
      dut.io.q.expect(-32767.S)
      dut.io.r.expect(0.S)

      testNumber += 1
      procent = testNumber / 100.0
      println(f"SInt Test: $procent%.2f%% (32767 / -1 = -32767, Remainder: 0)")

      //---------------------------------

      dut.io.a.poke(-7.S)
      dut.io.b.poke(3.S)

      dut.clock.step(18)

      dut.io.valid.expect(1.B)
      dut.io.q.expect(-2.S)
      dut.io.r.expect(-1.S)

      testNumber += 1
      procent = testNumber / 100.0
      println(f"SInt Test: $procent%.2f%% (-7 / 3 = -2, Remainder: -1)")

      //---------------------------------

      dut.io.a.poke(7.S)
      dut.io.b.poke(-3.S)

      dut.clock.step(18)

      dut.io.valid.expect(1.B)
      dut.io.q.expect(-2.S)
      dut.io.r.expect(1.S)

      testNumber += 1
      procent = testNumber / 100.0
      println(f"SInt Test: $procent%.2f%% (7 / -3 = -2, Remainder: 1)")

      //---------------------------------

      dut.io.a.poke(-7.S)
      dut.io.b.poke(-3.S)

      dut.clock.step(18)

      dut.io.valid.expect(1.B)
      dut.io.q.expect(2.S)
      dut.io.r.expect(-1.S)

      testNumber += 1
      procent = testNumber / 100.0
      println(f"SInt Test: $procent%.2f%% (-7 / -3 = 2, Remainder: -1)")

      //---------------------------------

      dut.io.a.poke(-32768.S)
      dut.io.b.poke(2.S)

      dut.clock.step(18)

      dut.io.valid.expect(1.B)
      dut.io.q.expect(-16384.S)
      dut.io.r.expect(0.S)

      testNumber += 1
      procent = testNumber / 100.0
      println(f"SInt Test: $procent%.2f%% (-32768 / 2 = -16384, Remainder: 0)")

      //---------------------------------

      dut.io.a.poke(32767.S)
      dut.io.b.poke(2.S)

      dut.clock.step(18)

      dut.io.valid.expect(1.B)
      dut.io.q.expect(16383.S)
      dut.io.r.expect(1.S)

      testNumber += 1
      procent = testNumber / 100.0
      println(f"SInt Test: $procent%.2f%% (32767 / 2 = 16383, Remainder: 1)")

      // rst test

      dut.io.a.poke(23.S)
      dut.io.b.poke(23.S)

      dut.clock.step(5)
      dut.io.valid.expect(0.B)

      dut.io.rst.poke(1.B)
      dut.clock.step(1)
      dut.io.rst.poke(0.B)

      //---------------------------------

      for(i <- 0 to 9985){
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
        println(f"SInt Test: $procent%.2f%% ($a / $b = $q, Remainder: $r)")
      }
    }
  }
}
