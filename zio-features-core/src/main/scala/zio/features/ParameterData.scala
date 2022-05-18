package zio.features

final case class ParameterData[+Types] private (private val map: Map[String, ParamType[_]]) {
  def add[Type](name: String)(implicit paramType: ParamType[Type]): ParameterData[Types with Type] =
    copy(map = map.updated(name, paramType))
}
object ParameterData {
  val empty: ParameterData[Any] = new ParameterData(Map())

  val example: ParameterData[Int with String] = empty.add[Int]("foo").add[String]("bar")
}
