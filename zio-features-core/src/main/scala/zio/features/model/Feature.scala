package zio.features.model

final case class Feature[Input](featureId: FeatureId, oracle: FeatureOracle[Input])
