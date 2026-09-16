package divider

import chisel3._
import chisel3.util._

class UIntDivider extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(16.W))   // Operand A (uint 16 bit)
    val b = Input(UInt(16.W))   // Operand B (uint 16 bit)

    val q = Output(UInt(16.W))  // Quotient
    val r = Output(UInt(16.W))  // Remainder

    val xcpt = Output(Bool())  // Exception
    val done = Output(Bool()) // Done
  })

  val alu = Module(new ALU)
  alu.io.operation := ALUOp.SUB

}