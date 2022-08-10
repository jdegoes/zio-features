package zio.features.model

final case class FeatureDecisionMatrixWithId[-Input](
  id: FeatureDecisionMatrixId,
  decisionMatrix: FeatureDecisionMatrix[Input]
)
