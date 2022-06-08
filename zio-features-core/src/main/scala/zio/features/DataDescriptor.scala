package zio.features

/**
 * A data descriptor describes the structure of record-like data.
 */
final case class DataDescriptor[+Types] private (private val map: Map[String, ParamType[_]]) {
  def add[Type](paramDescriptor: ParamDescriptor[Type]): DataDescriptor[Types with Type] =
    copy(map = map + (paramDescriptor.name -> paramDescriptor.paramType))

}
object DataDescriptor {
  val empty: DataDescriptor[Any] = new DataDescriptor(Map())
}
