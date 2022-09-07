package zio.features.server.services

import zio._
import zio.stream._
import zio.features.server.api._

final case class FeatureDescriptorRepoCache(
  cache: Cache[FeatureDescriptorId, FeatureDescriptor[_, _]],
  featureRepo: FeatureDescriptorRepo
) extends FeatureDescriptorRepo {
  import RepoError._

  def create(fd: FeatureDescriptor[_, _]): ZIO[Any, AlreadyExists[FeatureDescriptorId], Unit] =
    featureRepo.create(fd)

  def delete(id: FeatureDescriptorId): ZIO[Any, NotFound[FeatureDescriptorId], Unit] =
    featureRepo.delete(id)

  def get(id: FeatureDescriptorId): ZIO[Any, NotFound[FeatureDescriptorId], FeatureDescriptor[_, _]] =
    featureRepo.get(id)

  def list: ZStream[Any, Nothing, FeatureDescriptor[_, _]] =
    featureRepo.list

  def update(fd: FeatureDescriptor[_, _]): ZIO[Any, NotFound[FeatureDescriptorId], Unit] =
    featureRepo.update(fd)
}
object FeatureDescriptorRepoCache {
  val layer: ZLayer[
    FeatureDescriptorRepo & Cache[FeatureDescriptorId, FeatureDescriptor[_, _]],
    Nothing,
    FeatureDescriptorRepo
  ] =
    ZLayer {
      for {
        featureRepo <- ZIO.service[FeatureDescriptorRepo]
        cache       <- ZIO.service[Cache[FeatureDescriptorId, FeatureDescriptor[_, _]]]
      } yield FeatureDescriptorRepoCache(cache, featureRepo)
    }
}
