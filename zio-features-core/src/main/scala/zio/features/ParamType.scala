package zio.features

sealed trait ParamType[A]
object ParamType {
  implicit case object Int    extends ParamType[scala.Int]
  implicit case object String extends ParamType[java.lang.String]
}
