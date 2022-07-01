package zio.features

trait FeatureDescriptorSet[-Input] {
  def featureDescriptors[Input1 <: Input]: Set[FeatureDescriptor[_ >: Input1, _]]
}
object FeatureDescriptorSet {
  val empty: FeatureDescriptorSet[Any] =
    new FeatureDescriptorSet[Any] {
      def featureDescriptors[Input1 <: Any]: Set[FeatureDescriptor[_ >: Input1, _]] = Set()
    }
}
