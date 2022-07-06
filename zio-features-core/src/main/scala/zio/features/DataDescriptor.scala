package zio.features

import zio._

/**
 * A data descriptor describes the structure of record-like data.
 */
sealed abstract case class DataDescriptor[+Types] private (private val chunk: Chunk[ParamDescriptor[_]]) {
  def add[Type](paramDescriptor: ParamDescriptor[Type]): DataDescriptor[Types with Type] =
    new DataDescriptor(chunk = chunk :+ paramDescriptor) {}

}
object DataDescriptor {
  val empty: DataDescriptor[Any] = new DataDescriptor(Chunk.empty) {}
}
