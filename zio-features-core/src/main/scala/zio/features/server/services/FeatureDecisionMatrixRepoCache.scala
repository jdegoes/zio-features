package zio.features.server.services

import zio._
import zio.stream._
import zio.features.server.api._

final case class FeatureDecisionMatrixRepoCache(
  cache: Cache[FeatureDecisionMatrixId, FeatureDecisionMatrix[_]],
  matrixRepo: FeatureDecisionMatrixRepo
) extends FeatureDecisionMatrixRepo {
  import RepoError._

  def create(fd: FeatureDecisionMatrix[_]): ZIO[Any, AlreadyExists[FeatureDecisionMatrixId], Unit] =
    matrixRepo.create(fd)

  def delete(id: FeatureDecisionMatrixId): ZIO[Any, NotFound[FeatureDecisionMatrixId], Unit] =
    matrixRepo.delete(id)

  def get(id: FeatureDecisionMatrixId): ZIO[Any, NotFound[FeatureDecisionMatrixId], FeatureDecisionMatrix[_]] =
    matrixRepo.get(id)

  def list: ZStream[Any, Nothing, FeatureDecisionMatrix[_]] =
    matrixRepo.list

  def update(fd: FeatureDecisionMatrix[_]): ZIO[Any, NotFound[FeatureDecisionMatrixId], Unit] =
    matrixRepo.update(fd)
}
object FeatureDecisionMatrixRepoCache {
  val layer: ZLayer[
    FeatureDecisionMatrixRepo & Cache[FeatureDecisionMatrixId, FeatureDecisionMatrix[_]],
    Nothing,
    FeatureDecisionMatrixRepo
  ] =
    ZLayer {
      for {
        matrixRepo <- ZIO.service[FeatureDecisionMatrixRepo]
        cache      <- ZIO.service[Cache[FeatureDecisionMatrixId, FeatureDecisionMatrix[_]]]
      } yield FeatureDecisionMatrixRepoCache(cache, matrixRepo)
    }
}
