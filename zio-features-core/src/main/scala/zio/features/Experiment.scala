package zio.features

final case class Experiment[-Input](
  id: ExperimentId,
  startTime: java.time.Instant,
  duration: java.time.Duration,
  subset: TargetingRule[Input],
  groups: List[(TargetingRule[Input], FeatureSet)],
  defaultGroup: FeatureSet
) {
  def defaultGroup(g: FeatureSet): Experiment[Input] = copy(defaultGroup = g)

  def duration(d: java.time.Duration): Experiment[Input] =
    copy(duration = d)

  def group[Input1 <: Input](rule: TargetingRule[Input1], g: FeatureSet): Experiment[Input1] = groups((rule, g))

  def groups[Input1 <: Input](
    g: (TargetingRule[Input1], FeatureSet),
    gs: (TargetingRule[Input1], FeatureSet)*
  ): Experiment[Input1] =
    copy(groups = g :: gs.toList ::: groups)

  def startTime(time: java.time.Instant): Experiment[Input] =
    copy(startTime = time)

  def subset[Input1 <: Input](subset2: TargetingRule[Input1]): Experiment[Input1] =
    copy(subset = subset && subset2)
}
object Experiment {
  def apply(id: ExperimentId, defaultGroup: FeatureSet): Experiment[Any] =
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
