package zio.features

final case class Data[Types] private (private val data: Map[ParamType[_], Any])
