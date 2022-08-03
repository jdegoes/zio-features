package zio.features.model

final case class Experiment[-Input](
  id: ExperimentId,
  subset: TargetingRule[Input],
  groups: List[(TargetingRule[Input], FeatureDescriptorSet[Input])],
  defaultGroup: FeatureDescriptorSet[Input]
) {
  def defaultGroup[Input1 <: Input](g: FeatureDescriptorSet[Input1]): Experiment[Input1] =
    copy(defaultGroup = g)

  def group[Input1 <: Input](rule: TargetingRule[Input1], g: FeatureDescriptorSet[Input1]): Experiment[Input1] = groups(
    (rule, g)
  )

  def groups[Input1 <: Input](
    g: (TargetingRule[Input1], FeatureDescriptorSet[Input1]),
    gs: (TargetingRule[Input1], FeatureDescriptorSet[Input1])*
  ): Experiment[Input1] =
    copy(groups = g :: gs.toList ::: groups)

  def subset[Input1 <: Input](subset2: TargetingRule[Input1]): Experiment[Input1] =
    copy(subset = subset && subset2)
}
object Experiment {
  def apply[A](id: ExperimentId, defaultGroup: FeatureDescriptorSet[A]): Experiment[A] =
    Experiment(
      id,
      TargetingRule.everyone,
      Nil,
      defaultGroup
    )
}
