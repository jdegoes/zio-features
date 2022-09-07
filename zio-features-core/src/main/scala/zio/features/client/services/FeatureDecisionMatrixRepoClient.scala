package zio.features.client.services

import zio._
import zio.stream._
import zio.features.client.api._

final case class FeatureDecisionMatrixRepoClient(config: ZioFeaturesServerConfig) extends FeatureDecisionMatrixRepo {
  import RepoError._

  def initialize: UIO[Any] = ZIO.unit

  def create(fd: FeatureDecisionMatrix[_]): ZIO[Any, AlreadyExists[FeatureDecisionMatrixId], Unit] = ???

  def delete(id: FeatureDecisionMatrixId): ZIO[Any, NotFound[FeatureDecisionMatrixId], Unit] = ???

  def destroy: UIO[Any] = ZIO.unit

  def get(id: FeatureDecisionMatrixId): ZIO[Any, NotFound[FeatureDecisionMatrixId], FeatureDecisionMatrix[_]] = ???

  def list: ZStream[Any, Nothing, FeatureDecisionMatrix[_]] = ???

  def update(fd: FeatureDecisionMatrix[_]): ZIO[Any, NotFound[FeatureDecisionMatrixId], Unit] = ???
}
object FeatureDecisionMatrixRepoClient {
  val layer: ZLayer[ZioFeaturesServerConfig, Nothing, FeatureDecisionMatrixRepoClient] =
    ZLayer.scoped {
      for {
        config <- ZIO.service[ZioFeaturesServerConfig]
        impl    = FeatureDecisionMatrixRepoClient(config)
        _      <- ZIO.acquireRelease(impl.initialize)(_ => impl.destroy)
      } yield impl
    }
}
