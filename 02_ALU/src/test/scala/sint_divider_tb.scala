// ADS I Class Project
// Chair of Electronic Design Automation, RPTU in Kaiserslautern

package divider

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class SIntDividerTest extends AnyFlatSpec with ChiselScalatestTester {
  "SInt Divider" should "test all" in {
    test(new SIntDivider).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
      dut.clock.setTimeout(0)

      dut.io.rst.poke(1.B)
      dut.clock.step(1)
      dut.io.rst.poke(0.B)


      var round = 0
      for(a <- -100 to 100){ // nur 15 bit da 1 bit für sign verwendet werden muss
        for(b <- -100 to 100){
          if(b != 0) {
            val q = a / b
            val r = a % b

            dut.io.a.poke(a.S)
            dut.io.b.poke(b.S)
            dut.clock.step(18)
            dut.io.valid.expect(1.B)
            dut.io.q.expect(q.S)
            dut.io.r.expect(r.S)

            val prozent = 100 * round / 40001.0

            println(f"$prozent%.2f%%")
            round = round + 1
          }
        }
      }
    }
  }
}
