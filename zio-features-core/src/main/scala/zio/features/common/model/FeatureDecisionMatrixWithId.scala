package zio.features.common.model

final case class FeatureDecisionMatrixWithId[-Input](
  id: FeatureDecisionMatrixId,
  decisionMatrix: FeatureDecisionMatrix[Input]
)
