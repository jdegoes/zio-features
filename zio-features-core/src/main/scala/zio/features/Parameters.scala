package zio.features

final case class Parameters[+Types] private (private val map: Map[String, ParamType[_]]) {
  def add[Type](name: String)(implicit paramType: ParamType[Type]): Parameters[Types with Type] = copy(map = map.updated(name, paramType))
}
object Parameters {
  val empty: Parameters[Any] = new Parameters(Map())
  
  val example : Parameters[Int with String] = empty.add[Int]("foo").add[String]("bar")
}