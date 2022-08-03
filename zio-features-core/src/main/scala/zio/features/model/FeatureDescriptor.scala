package zio.features.model

// FeatureTemplate, FeatureDescriptor
final case class FeatureDescriptor[Input, Output](
  id: FeatureDescriptorId,
  description: Vector[String],
  inputDescriptor: DataDescriptor[Input],
  outputDescriptor: DataDescriptor[Output]
) {
  self =>
  def ??(description2: String): FeatureDescriptor[Input, Output] =
    copy(description = self.description ++ Vector(description2))

  def input[Type](paramDescriptor: ParamDescriptor[Type]): FeatureDescriptor[Input with Type, Output] =
    copy(inputDescriptor = self.inputDescriptor.add(paramDescriptor))

  def output[Type](paramDescriptor: ParamDescriptor[Type]): FeatureDescriptor[Input, Output with Type] =
    copy(outputDescriptor = self.outputDescriptor.add(paramDescriptor))
}

object FeatureDescriptor {
  def apply(name: String): FeatureDescriptor[Any, Any] =
    FeatureDescriptor(FeatureDescriptorId(name), Vector.empty, DataDescriptor.empty, DataDescriptor.empty)

}
