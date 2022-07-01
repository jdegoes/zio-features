package zio.features

final case class Experiment[-Input](
  id: ExperimentId,
  startTime: java.time.Instant,
  duration: java.time.Duration,
  subset: TargetingRule[Input],
  groups: List[(TargetingRule[Input], FeatureDescriptorSet[Input])],
  defaultGroup: FeatureDescriptorSet[Input]
) {
  def defaultGroup[Input1 <: Input](g: FeatureDescriptorSet[Input1]): Experiment[Input1] =
    copy(defaultGroup = g)

  def duration(d: java.time.Duration): Experiment[Input] =
    copy(duration = d)

  def group[Input1 <: Input](rule: TargetingRule[Input1], g: FeatureDescriptorSet[Input1]): Experiment[Input1] = groups(
    (rule, g)
  )

  def groups[Input1 <: Input](
    g: (TargetingRule[Input1], FeatureDescriptorSet[Input1]),
    gs: (TargetingRule[Input1], FeatureDescriptorSet[Input1])*
  ): Experiment[Input1] =
    copy(groups = g :: gs.toList ::: groups)

  def startTime(time: java.time.Instant): Experiment[Input] =
    copy(startTime = time)

  def subset[Input1 <: Input](subset2: TargetingRule[Input1]): Experiment[Input1] =
    copy(subset = subset && subset2)
}
object Experiment {
  def apply2[A](id: ExperimentId, defaultGroup: FeatureDescriptorSet[A]): Experiment[A] =
    Experiment(
      id,
      java.time.Instant.MIN,
      java.time.Duration.ofNanos(Long.MaxValue),
      TargetingRule.everyone,
      Nil,
      defaultGroup
    )
}

/*

Experiment(myId, defaultGroup)
  .startTime(here)
  .duration(1.week)
  .subset(TargetingRule.age(18, 30))
  .group(TargetingRule.age(18, 30), group1)
  .group(TargetingRule.age(30, 40), group2)
  .group(TargetingRule.age(40, 50), group3)

 */
