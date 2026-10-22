// ADS I Class Project
// Chair of Electronic Design Automation, RPTU in Kaiserslautern

package divider

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec
import scala.util.Random

class UIntDividerTest extends AnyFlatSpec with ChiselScalatestTester {
  "UInt Divider" should "test all" in {
    test(new UIntDivider).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
      dut.clock.setTimeout(0)

      val rand = new Random()
      var testNumber = 0
      var procent = 0.0
      println(f"UInt Test: $procent%.2f%%")

      dut.io.rst.poke(1.B)
      dut.clock.step(1)
      dut.io.rst.poke(0.B)

      //-------------------------------

      dut.io.a.poke(0.U)
      dut.io.b.poke(100.U)

      dut.clock.step(18)

      dut.io.valid.expect(1.B)
      dut.io.q.expect(0.U)
      dut.io.r.expect(0.U)

      testNumber += 1
      procent = testNumber / 100.0
      println(f"UInt Test: $procent%.2f%% (0 / 100 = 0, Remainder: 0)")

      //---------------------------------

      dut.io.a.poke(100.U)
      dut.io.b.poke(0.U)

      dut.clock.step(18)

      dut.io.valid.expect(1.B)
      dut.io.q.expect(65535.U)
      dut.io.r.expect(100.U)

      testNumber += 1
      procent = testNumber / 100.0
      println(f"UInt Test: $procent%.2f%% (100 / 0 = 65535, Remainder: 100)")

      //---------------------------------

      dut.io.a.poke(0.U)
      dut.io.b.poke(0.U)

      dut.clock.step(18)

      dut.io.valid.expect(1.B)
      dut.io.q.expect(65535.U)
      dut.io.r.expect(0.U)

      testNumber += 1
      procent = testNumber / 100.0
      println(f"UInt Test: $procent%.2f%% (0 / 0 = 65535, Remainder: 0)")

      //---------------------------------

      dut.io.a.poke(32768.U)
      dut.io.b.poke(32768.U)

      dut.clock.step(18)

      dut.io.valid.expect(1.B)
      dut.io.q.expect(1.U)
      dut.io.r.expect(0.U)

      testNumber += 1
      procent = testNumber / 100.0
      println(f"UInt Test: $procent%.2f%% (32768 / 32768 = 1, Remainder: 0)")

      //---------------------------------

      dut.io.a.poke(0.U)
      dut.io.b.poke(32768.U)

      dut.clock.step(18)

      dut.io.valid.expect(1.B)
      dut.io.q.expect(0.U)
      dut.io.r.expect(0.U)

      testNumber += 1
      procent = testNumber / 100.0
      println(f"UInt Test: $procent%.2f%% (0 / 32768 = 0, Remainder: 0)")

      //---------------------------------

      dut.io.a.poke(32768.U)
      dut.io.b.poke(0.U)

      dut.clock.step(18)

      dut.io.valid.expect(1.B)
      dut.io.q.expect(65535.U)
      dut.io.r.expect(32768.U)

      testNumber += 1
      procent = testNumber / 100.0
      println(f"UInt Test: $procent%.2f%% (32768 / 0 = 65535, Remainder: 32768)")

      // rst test

      dut.io.a.poke(23.U)
      dut.io.b.poke(23.U)

      dut.clock.step(5)
      dut.io.valid.expect(0.B)

      dut.io.rst.poke(1.B)
      dut.clock.step(1)
      dut.io.rst.poke(0.B)

      for(i <- 0 to 9993){
        val a = rand.nextInt(32769) // 0 to 32768
        val b = rand.nextInt(32769) // 0 to 32768

        val q = if (b != 0) a / b else 65535
        val r = if (b != 0) a % b else a

        dut.io.a.poke(a.U)
        dut.io.b.poke(b.U)
        dut.clock.step(18)
        dut.io.valid.expect(1.B)
        dut.io.q.expect(q.U)
        dut.io.r.expect(r.U)

        testNumber += 1
        procent = testNumber / 100.0
        println(f"UInt Test: $procent%.2f%% ($a / $b = $q, Remainder: $r)")
      }
    }
  }
}
