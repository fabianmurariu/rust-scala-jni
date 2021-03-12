package sparse4s

import java.nio.Buffer
import cz.adamh.utils.NativeUtils

class GrB {

  NativeUtils.loadLibraryFromJar("/Linux/x86_64/libsparse_mat.so")

  @native def init: Long
  @native def Finalize: Long
  @native def createMatrix(tpe: Long, row: Long, cols: Long): Long
  @native def freeMatrix(mat:Long): Long
}

object GrB {
  val grb = new GrB
}
