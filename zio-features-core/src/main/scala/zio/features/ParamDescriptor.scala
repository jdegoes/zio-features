package zio.features

final case class ParamDescriptor[Type](name: String, paramType: ParamType[Type]) { self =>
  def get: TargetingExpr[Type, Type] = TargetingExpr.Extraction(self)
}

// UserData => Type
object ParamDescriptor {
  def int(name: String): ParamDescriptor[Int]       = ParamDescriptor(name, ParamType.Int)
  def string(name: String): ParamDescriptor[String] = ParamDescriptor(name, ParamType.String)
}
