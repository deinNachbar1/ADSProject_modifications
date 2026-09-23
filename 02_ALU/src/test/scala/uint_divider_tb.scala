// ADS I Class Project
// Chair of Electronic Design Automation, RPTU in Kaiserslautern

package divider

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class UIntDividerTest extends AnyFlatSpec with ChiselScalatestTester {
  "UInt Divider" should "test all" in {
    test(new UIntDivider).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
      dut.clock.setTimeout(0)

      dut.io.rst.poke(1.B)
      dut.clock.step(1)
      dut.io.rst.poke(0.B)

      dut.io.a.poke(0.U)
      dut.io.b.poke(100.U)

      dut.clock.step(18)

      dut.io.valid.expect(1.B)
      dut.io.q.expect(0.U)
      dut.io.r.expect(0.U)

      /*
      var round = 0
      for(a <- 0 to 32767){ // nur 15 bit da 1 bit für sign verwendet werden muss
        for(b <- 1 to 32767){

          val q = a / b
          val r = a % b

          dut.io.a.poke(a.U)
          dut.io.b.poke(b.U)
          dut.clock.step(18)
          dut.io.valid.expect(1.B)
          dut.io.q.expect(q.U)
          dut.io.r.expect(r.U)

          val prozent = 100 * round / 1073643522.0

          println(f"$prozent%.4f%%")
          round = round + 1
        }
      }
      */
    }
  }
}
