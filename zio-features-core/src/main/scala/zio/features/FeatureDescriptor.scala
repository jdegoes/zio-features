package zio.features

// FeatureTemplate, FeatureDescriptor
final case class FeatureDescriptor[Types](
  id: FeatureId,
  description: Vector[String],
  dataDescriptor: DataDescriptor[Types]
) {
  self =>
  def ??(description2: String): FeatureDescriptor[Types] = copy(description = self.description ++ Vector(description2))

  def param[Type](paramDescriptor: ParamDescriptor[Type]): FeatureDescriptor[Types with Type] =
    copy(dataDescriptor = self.dataDescriptor.add(paramDescriptor))
}

object FeatureDescriptor {
  def apply(name: String): FeatureDescriptor[Any] =
    FeatureDescriptor(FeatureId(name), Vector.empty, DataDescriptor.empty)

}
