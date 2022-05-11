package zio.features

final case class Experiment(
  id: ExperimentId,
  startTime: java.time.Instant,
  duration: java.time.Duration,
  subset: TargetingRule,
  groups: List[(TargetingRule, ExperimentGroup)],
  defaultGroup: ExperimentGroup
) {
  def defaultGroup(g: ExperimentGroup): Experiment = copy(defaultGroup = g)

  def duration(d: java.time.Duration): Experiment =
    copy(duration = d)

  def group(rule: TargetingRule, g: ExperimentGroup): Experiment = groups((rule, g))

  def groups(g: (TargetingRule, ExperimentGroup), gs: (TargetingRule, ExperimentGroup)*): Experiment =
    copy(groups = g :: gs.toList ::: groups)

  def startTime(time: java.time.Instant): Experiment =
    copy(startTime = time)

  def subset(subset2: TargetingRule): Experiment =
    copy(subset = subset && subset2)
}
object Experiment {
  def apply(id: ExperimentId, defaultGroup: ExperimentGroup): Experiment =
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
