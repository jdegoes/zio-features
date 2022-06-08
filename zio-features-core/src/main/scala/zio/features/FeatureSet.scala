package zio.features

sealed abstract case class FeatureSet private (features: Set[Feature[_]])
object FeatureSet {
  val empty: FeatureSet = new FeatureSet(Set.empty) {}
}
