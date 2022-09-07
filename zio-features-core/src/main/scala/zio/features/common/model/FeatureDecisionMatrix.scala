package zio.features.common.model

import java.time.Instant

sealed trait FeatureDecisionMatrix[-Input] { self =>
  def ++[Input1 <: Input](that: FeatureDecisionMatrix[Input1]): FeatureDecisionMatrix[Input1] =
    FeatureDecisionMatrix.Both(self, that)

  def ||[Input1 <: Input](that: FeatureDecisionMatrix[Input1]): FeatureDecisionMatrix[Input1] =
    FeatureDecisionMatrix.Fallback(self, that)

  def when[Input1 <: Input](rule: TargetingExpr[Input1, Boolean]): FeatureDecisionMatrix[Input1] =
    FeatureDecisionMatrix.Targeted(self, rule)
}
object FeatureDecisionMatrix {
  def empty: FeatureDecisionMatrix[Any] = Empty

  def enable[Input](featureDescriptor: FeatureDescriptor[Input, _]): FeatureDecisionMatrix[Input] =
    FeatureDecisionMatrix.EnableFeature(featureDescriptor.id)

  private[model] case object Empty extends FeatureDecisionMatrix[Any]

  private[model] final case class Targeted[Input](
    featureOracle: FeatureDecisionMatrix[Input],
    targetingRule: TargetingExpr[Input, Boolean]
  ) extends FeatureDecisionMatrix[Input]

  private[model] final case class Fallback[-Input](
    left: FeatureDecisionMatrix[Input],
    right: FeatureDecisionMatrix[Input]
  ) extends FeatureDecisionMatrix[Input]

  private[model] final case class Both[-Input](left: FeatureDecisionMatrix[Input], right: FeatureDecisionMatrix[Input])
      extends FeatureDecisionMatrix[Input]

  private[model] final case class EnableFeature[-Input](featureDescriptorId: FeatureDescriptorId)
      extends FeatureDecisionMatrix[Input]
}
