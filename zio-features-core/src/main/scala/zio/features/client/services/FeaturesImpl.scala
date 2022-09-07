package zio.features.client.services

import zio._
import zio.features.client.api._

final case class FeaturesImpl(matrixRepo: FeatureDecisionMatrixRepo, featureRepo: FeatureDescriptorRepo)
    extends Features {
  def catalog[Input](input: Params[Input]): FeatureCatalog[Input] = ???
}
object FeaturesImpl {
  val layer: ZLayer[FeatureDecisionMatrixRepo & FeatureDescriptorRepo, Nothing, Features] =
    ZLayer {
      for {
        matrixRepo  <- ZIO.service[FeatureDecisionMatrixRepo]
        featureRepo <- ZIO.service[FeatureDescriptorRepo]
      } yield FeaturesImpl(matrixRepo, featureRepo)
    }
}
