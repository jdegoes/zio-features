package zio.features.model

import java.time.Instant

final case class FeatureOracleWithId[Input](id: String, oracle: FeatureOracle[Input])

sealed trait FeatureOracle[-Input] { self =>
  def &&[Input1 <: Input](that: FeatureOracle[Input1]): FeatureOracle[Input1] = FeatureOracle.Both(self, that)

  def ||[Input1 <: Input](that: FeatureOracle[Input1]): FeatureOracle[Input1] = FeatureOracle.Fallback(self, that)

  def target[Input1 <: Input](rule: TargetingRule[Input1]): FeatureOracle[Input1] = FeatureOracle.Targeted(self, rule)
}
object FeatureOracle {
  def apply[Input](featureDescriptorId: FeatureDescriptorId): FeatureOracle[Input] =
    FeatureOracle.FeatureDescriptor(featureDescriptorId)

  final case class Targeted[Input](FeatureOracle: FeatureOracle[Input], targetingRule: TargetingRule[Input])
      extends FeatureOracle[Input]

  final case class Fallback[-Input](left: FeatureOracle[Input], right: FeatureOracle[Input])
      extends FeatureOracle[Input]

  final case class Both[-Input](left: FeatureOracle[Input], right: FeatureOracle[Input]) extends FeatureOracle[Input]

  final case class FeatureDescriptor[-Input](featureDescriptorId: FeatureDescriptorId) extends FeatureOracle[Input]
}
