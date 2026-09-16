package divider

import chisel3._
import chisel3.util._

class IntDivider extends Module {
  val io = IO(new Bundle {
    val a = Input(Int(16.W))   // Operand A (int 16 bit)
    val b = Input(Int(16.W))   // Operand B (int 16 bit)

    val q = Output(Int(16.W))  // Quotient
    val r = Output(Int(16.W))  // Remainder

    val xcpt = Output(Bool())  // Exception
    val valid = Output(Bool()) // Valid
  })

  val uIntDivider = Module(new UIntDivider)
}