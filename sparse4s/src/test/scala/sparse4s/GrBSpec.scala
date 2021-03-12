package sparse4s

import org.scalatest.funsuite.AnyFunSuite
import sparse4s.GrBType
import org.scalatest.matchers.should.Matchers

class GrBSpec extends AnyFunSuite with Matchers{
  test("I can create a Matrix") {
    GrB.grb.init shouldBe 0L

    val bool = new GrBType

    val mat = GrB.grb.createMatrix(bool.grbBoolean, 10, 10)
    GrB.grb.freeMatrix(mat) shouldBe 0L
    GrB.grb.Finalize shouldBe 0L
  }
}
