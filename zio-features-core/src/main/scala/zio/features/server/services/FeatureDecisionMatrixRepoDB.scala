package zio.features.server.services

import zio._
import zio.stream._
import zio.features.server.api._

final case class FeatureDecisionMatrixRepoDB() extends FeatureDecisionMatrixRepo {
  import RepoError._

  def initialize: UIO[Any] = ZIO.unit

  def create(fd: FeatureDecisionMatrix[_]): ZIO[Any, AlreadyExists[FeatureDecisionMatrixId], Unit] = ???

  def delete(id: FeatureDecisionMatrixId): ZIO[Any, NotFound[FeatureDecisionMatrixId], Unit] = ???

  def destroy: UIO[Any] = ZIO.unit

  def get(id: FeatureDecisionMatrixId): ZIO[Any, NotFound[FeatureDecisionMatrixId], FeatureDecisionMatrix[_]] = ???

  def list: ZStream[Any, Nothing, FeatureDecisionMatrix[_]] = ???

  def update(fd: FeatureDecisionMatrix[_]): ZIO[Any, NotFound[FeatureDecisionMatrixId], Unit] = ???
}
object FeatureDecisionMatrixRepoDB {
  val layer: ZLayer[Any, Nothing, FeatureDecisionMatrixRepoDB] =
    ZLayer.scoped {
      for {
        impl <- ZIO.succeed(FeatureDecisionMatrixRepoDB())
        _    <- ZIO.acquireRelease(impl.initialize)(_ => impl.destroy)
      } yield impl
    }
}
