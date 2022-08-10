package zio.features.api.common

import zio._
import zio.stream._
import zio.features.model._

trait CommonApis {

  trait FeatureDescriptorRepo {
    import FeatureDescriptorRepo.Error._

    def create(fd: FeatureDescriptor[_, _]): ZIO[Any, AlreadyExists, Unit]

    def delete(id: FeatureDescriptorId): ZIO[Any, NotFound, Unit]

    def get(id: FeatureDescriptorId): ZIO[Any, Throwable, Option[FeatureDescriptor[_, _]]]

    def list: ZStream[Any, Nothing, FeatureDescriptor[_, _]]

    def update(fd: FeatureDescriptor[_, _]): ZIO[Any, NotFound, Unit]
  }
  object FeatureDescriptorRepo {
    sealed trait Error
    object Error {
      case class AlreadyExists(id: FeatureDescriptorId) extends Error
      case class NotFound(id: FeatureDescriptorId)      extends Error
    }
  }

  trait FeatureDecisionMatrixRepo {
    import FeatureDecisionMatrixRepo.Error._

    def create(fd: FeatureDecisionMatrix[_]): ZIO[Any, AlreadyExists, Unit]

    def delete(id: FeatureDecisionMatrixId): ZIO[Any, NotFound, Unit]

    def get(id: FeatureDecisionMatrixId): ZIO[Any, Throwable, Option[FeatureDecisionMatrix[_]]]

    def list: ZStream[Any, Nothing, FeatureDecisionMatrix[_]]

    def update(fd: FeatureDecisionMatrix[_]): ZIO[Any, NotFound, Unit]
  }
  object FeatureDecisionMatrixRepo {
    sealed trait Error
    object Error {
      case class AlreadyExists(id: FeatureDecisionMatrixId) extends Error
      case class NotFound(id: FeatureDecisionMatrixId)      extends Error
    }
  }

}
