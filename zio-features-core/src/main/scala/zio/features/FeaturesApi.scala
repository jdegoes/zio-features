package zio.features

trait FeaturesApi[Types] {
  def findFeatures(data: Data[Types]): FeatureSet
}
