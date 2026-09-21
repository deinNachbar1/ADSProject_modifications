package divider

import chisel3._
import chisel3.util._
import Assignment02.{ALU, ALUOp}

class UIntDivider extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(16.W))   // Operand A (uint 16 bit)
    val b = Input(UInt(16.W))   // Operand B (uint 16 bit

    val rst = Input(Bool())  // Reset

    val q = Output(UInt(16.W))  // Quotient
    val r = Output(UInt(16.W))  // Remainder

    val xcpt = Output(Bool())  // Exception
    val valid = Output(Bool())  // Valid
  })

  val controller = Module(new Controller)

  controller.io.a := io.a
  controller.io.b := io.b
  controller.io.rst := io.rst

  io.q := controller.io.q
  io.r := controller.io.r
  io.xcpt := controller.io.xcpt
  io.valid := controller.io.valid
}

class Controller extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(16.W))
    val b = Input(UInt(16.W))

    val rst = Input(Bool())

    val xcpt = Output(Bool())
    val valid = Output(Bool())

    val q = Output(UInt(16.W))
    val r = Output(UInt(16.W))
  })

  val alu = Module(new ALU)
  val divisorRegister = Module(new DivisorRegister)
  val quotientRegister = Module(new QuotientRegister)
  val remainderRegister = Module(new RemainderRegister)

  val running = RegInit(0.U(Bool()))
  val iteration = RegInit(0.U(5.W))

  alu.io.operandA := remainderRegister.io.dataOut
  alu.io.operandB := divisorRegister.io.dataOut
  alu.io.operation := ALUOp.SUB
  remainderRegister.io.dataIn := alu.io.aluResult

  divisorRegister.io.dataLoad := io.b

  io.q := quotientRegister.io.dataOut
  io.r := remainderRegister.io.dataOut(15, 0)

  io.valid := 0.U

  when(running) {
    remainderRegister.io.dataWrite := remainderRegister.io.dataOut + divisorRegister.io.dataOut

    when(remainderRegister.io.dataOut < 0.U) { // Remainder < 0
      remainderRegister.io.write := 1.U        // Restore
      quotientRegister.io.dataIn := 0.U
    }.otherwise {                              // Remainder >= 0
      remainderRegister.io.write := 0.U
      quotientRegister.io.dataIn := 1.U
    }
    when(iteration === 17.U) {
      io.valid := 1.U
      running := 0.U
    }
    iteration := iteration + 1.U

  }.otherwise {
    remainderRegister.io.dataWrite := io.a
    quotientRegister.io.dataIn := 0.U
    iteration := 0.U
  }

  when(io.rst) {
    running := 0.U
  }.otherwise {
    when(!running) {
      remainderRegister.io.write := 1.U
      divisorRegister.io.load := 1.U
      running := 1.U
    }
  }

}

class DivisorRegister extends Module {
  val io = IO(new Bundle {
    val load = Input(Bool())
    val dataLoad = Input(UInt(16.W))
    val dataOut = Output(UInt(32.W))
  })

  val reg = RegInit(0.U(32.W))

  when (io.load) {
    reg := Cat(io.dataLoad, "0000000000000000".U)
  }.otherwise {
    reg := Cat(0.U, reg(31, 1))
  }

  io.dataOut := reg
}

class QuotientRegister extends Module {
  val io = IO(new Bundle {
    val dataIn = Input(Bool())
    val dataOut = Output(UInt(16.W))
  })

  val reg = RegInit(0.U(16.W))

  reg := Cat(reg(30, 0), io.dataIn)

  io.dataOut := reg
}

class RemainderRegister extends Module {
  val io = IO(new Bundle {
    val write = Input(Bool())
    val dataWrite = Input(UInt(32.W))
    val dataIn = Input(UInt(32.W))
    val dataOut = Output(UInt(32.W))
  })

  val reg = RegInit(0.U(32.W))

  when (io.write) {
    reg := io.dataWrite
  }.otherwise {
    reg := io.dataIn
  }

  io.dataOut := reg
}