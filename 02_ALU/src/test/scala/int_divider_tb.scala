// ADS I Class Project
// Pipelined RISC-V Core with Hazard Detection and Resolution
//
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

      dut.io.a.poke(29.U)
      dut.io.b.poke(5.U)

      dut.clock.step(1)

      dut.io.rst.poke(0.B)

      dut.clock.step(18)

      dut.io.valid.expect(1.B)
      dut.io.q.expect(5.U)
      dut.io.r.expect(4.U)

      dut.io.a.poke(20.U)
      dut.io.b.poke(0.U)

      dut.clock.step(18)

      dut.io.valid.expect(1.B)
      dut.io.q.expect("b1111111111111111".U)
      dut.io.r.expect(20.U)

      dut.clock.step(18)
    }
  }
}
