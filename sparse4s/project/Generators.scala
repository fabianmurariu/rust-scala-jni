import sbt._
import scala.meta._
import scala.util.Try

object Generators {

  case class Mapping(typeName: String, tn: Type.Name)

  val typeNames: List[Mapping] = List(
    Mapping("boolean", t"Boolean"),
    Mapping("byte", t"Byte"),
    Mapping("short", t"Short"),
    Mapping("int", t"Int"),
    Mapping("long", t"Long"),
    Mapping("float", t"Float"),
    Mapping("double", t"Double")
  )

  def generateMeta(path: File): Seq[File] = {


    val grbTypes:List[Decl.Def] = typeNames.map{
      case Mapping(name, _) =>
        val funName = Term.Name(s"grb${name.charAt(0).toUpper}${name.tail}")
        q"@native def $funName:Long"
    }

    val code = q"""
                class GrBType {

                    ..$grbTypes
                }"""

    val file = path / "sparse4s" / "GrBType.scala"

    Try {IO.delete(file)}
    IO.append(file, "package sparse4s\n")
    IO.append(file, code.syntax.toString())

    Seq(file)
  }

}
