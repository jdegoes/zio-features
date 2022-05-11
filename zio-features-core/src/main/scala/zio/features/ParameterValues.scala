package zio.features

final case class ParameterValues[Types] private (private val map: Map[ParamType[_], Any])
