package zio.features.client.services

import zio._
import zio.stream._
import zio.features.client.api._

final case class FeatureDescriptorRepoClient(config: ZioFeaturesServerConfig) extends FeatureDescriptorRepo {
  import RepoError._

  def initialize: UIO[Any] = ZIO.unit

  def create(fd: FeatureDescriptor[_, _]): ZIO[Any, AlreadyExists[FeatureDescriptorId], Unit] = ???

  def delete(id: FeatureDescriptorId): ZIO[Any, NotFound[FeatureDescriptorId], Unit] = ???

  def get(id: FeatureDescriptorId): ZIO[Any, NotFound[FeatureDescriptorId], FeatureDescriptor[_, _]] = ???

  def list: ZStream[Any, Nothing, FeatureDescriptor[_, _]] = ???

  def update(fd: FeatureDescriptor[_, _]): ZIO[Any, NotFound[FeatureDescriptorId], Unit] = ???

  def destroy: UIO[Any] = ZIO.unit
}
object FeatureDescriptorRepoClient {
  val layer: ZLayer[ZioFeaturesServerConfig, Nothing, FeatureDescriptorRepo] =
    ZLayer.scoped {
      for {
        config <- ZIO.service[ZioFeaturesServerConfig]
        impl    = FeatureDescriptorRepoClient(config)
        _      <- ZIO.acquireRelease(impl.initialize)(_ => impl.destroy)
      } yield impl
    }
}
