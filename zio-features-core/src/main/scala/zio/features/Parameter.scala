package zio.features

final case class Parameter[Type](name: String, paramType: ParamType[Type]) { self =>
  def get: TargetingExpr[Type] = TargetingExpr.Extraction(self)
}

// UserData => Type
object Parameter {
  def int(name: String): Parameter[Int]       = Parameter(name, ParamType.Int)
  def string(name: String): Parameter[String] = Parameter(name, ParamType.String)
}
