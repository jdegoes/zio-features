package zio.features

final case class Experiment(
  id: ExperimentId, 
  startTime: java.time.Instant,
  duration: java.time.Duration,
  groups: NonEmptySet[ExperimentGroup],
  controlGroup: NonEmptySet[ExperimentGroup],
  selectionCriteria: SelectionCriteria)
