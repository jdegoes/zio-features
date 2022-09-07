package zio.features.common.model

import zio._

/**
 * A data descriptor describes the structure of record-like data.
 */
sealed abstract case class ParamsDescriptor[+Types] private (private val chunk: Chunk[ParamDescriptor[_]]) {
  def add[Type](paramDescriptor: ParamDescriptor[Type]): ParamsDescriptor[Types with Type] =
    new ParamsDescriptor(chunk = chunk :+ paramDescriptor) {}

}
object ParamsDescriptor {
  val empty: ParamsDescriptor[Any] = new ParamsDescriptor(Chunk.empty) {}
}
