package zio.features

final case class Feature[Types](id: FeatureId, description: Vector[String], ParameterData: ParameterData[Types]) {
  self =>
  def ??(description2: String): Feature[Types] = copy(description = self.description ++ Vector(description2))

  def param[Type](name: String)(implicit paramType: ParamType[Type]): Feature[Types with Type] =
    copy(ParameterData = self.ParameterData.add(name))
}

object Feature {
  def apply(name: String): Feature[Any] = Feature(FeatureId(name), Vector.empty, ParameterData.empty)
}
