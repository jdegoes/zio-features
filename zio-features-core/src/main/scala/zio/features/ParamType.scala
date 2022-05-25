package zio.features

import scala.annotation.implicitNotFound

@implicitNotFound("The type ${A} is not supported as a parameter type and cannot be used for this method.")
sealed trait ParamType[A]
object ParamType {
  implicit case object Int     extends ParamType[scala.Int]
  implicit case object String  extends ParamType[java.lang.String]
  implicit case object Double  extends ParamType[scala.Double]
  implicit case object Boolean extends ParamType[scala.Boolean]
}
