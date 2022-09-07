package zio.features.server.services

import zio._
import zio.stream._
import zio.features.client.api._

final case class FeatureDescriptorRepoDB() extends FeatureDescriptorRepo {
  import RepoError._

  def initialize: UIO[Any] = ZIO.unit

  def create(fd: FeatureDescriptor[_, _]): ZIO[Any, AlreadyExists[FeatureDescriptorId], Unit] = ???

  def delete(id: FeatureDescriptorId): ZIO[Any, NotFound[FeatureDescriptorId], Unit] = ???

  def get(id: FeatureDescriptorId): ZIO[Any, NotFound[FeatureDescriptorId], FeatureDescriptor[_, _]] = ???

  def list: ZStream[Any, Nothing, FeatureDescriptor[_, _]] = ???

  def update(fd: FeatureDescriptor[_, _]): ZIO[Any, NotFound[FeatureDescriptorId], Unit] = ???

  def destroy: UIO[Any] = ZIO.unit
}
object FeatureDescriptorRepoDB {
  val layer: ZLayer[Any, Nothing, FeatureDescriptorRepo] =
    ZLayer.scoped {
      for {
        impl <- ZIO.succeed(FeatureDescriptorRepoDB())
        _    <- ZIO.acquireRelease(impl.initialize)(_ => impl.destroy)
      } yield impl
    }
}
