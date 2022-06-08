package zio.features

final case class Feature[Types](featureDescriptor: FeatureDescriptor[Types], data: Data[Types])
