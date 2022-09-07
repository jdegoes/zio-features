package zio.features.common.api

import zio._
import zio.stream._
import zio.features.common.model._

trait CommonApis {
  trait MetricsCollector {}

  sealed trait RepoError[EntityId]
  object RepoError {
    case class AlreadyExists[EntityId](id: EntityId) extends RepoError[EntityId]
    case class NotFound[EntityId](id: EntityId)      extends RepoError[EntityId]
  }
  trait EntityRepo[EntityId, Entity] {
    import RepoError._

    def create(entity: Entity): ZIO[Any, AlreadyExists[EntityId], Unit]

    def delete(id: EntityId): ZIO[Any, NotFound[EntityId], Unit]

    def get(id: EntityId): ZIO[Any, NotFound[EntityId], Entity]

    def list: ZStream[Any, Nothing, Entity]

    def update(entity: Entity): ZIO[Any, NotFound[EntityId], Unit]
  }

  type FeatureDescriptorRepo     = EntityRepo[FeatureDescriptorId, FeatureDescriptor[_, _]]
  type FeatureDecisionMatrixRepo = EntityRepo[FeatureDecisionMatrixId, FeatureDecisionMatrix[_]]
}
