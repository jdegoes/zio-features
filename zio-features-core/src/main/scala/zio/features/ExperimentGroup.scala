package zio.features

sealed abstract case class ExperimentGroup private (features: Set[Feature[_]])
object ExperimentGroup {
  val empty: ExperimentGroup = new ExperimentGroup(Set.empty) {}
}
