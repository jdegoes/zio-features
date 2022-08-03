package zio.features.api.common

import zio._
import zio.stream._
import zio.features.model._

trait CommonApis {

  trait ExperimentRepo {
    import ExperimentRepo.Error._

    def create(experiment: Experiment[_]): ZIO[Any, ExperimentAlreadyExists, Unit]

    def delete(id: ExperimentId): ZIO[Any, ExperimentNotFound, Unit]

    def get(id: ExperimentId): ZIO[Any, Throwable, Option[Experiment[_]]]

    def list: ZStream[Any, Nothing, Experiment[_]]

    def update(experiment: Experiment[_]): ZIO[Any, ExperimentNotFound, Unit]
  }
  object ExperimentRepo {
    sealed trait Error
    object Error {
      case class ExperimentAlreadyExists(experimentId: ExperimentId) extends Error
      case class ExperimentNotFound(experimentId: ExperimentId)      extends Error
    }
  }

  trait FeatureRepo {
    import FeatureRepo.Error._

    def create[In](fd: Feature[In]): ZIO[Any, FeatureAlreadyExists, Unit]

    def delete(id: FeatureId): ZIO[Any, FeatureNotFound, Unit]

    def get(id: FeatureId): ZIO[Any, Throwable, Option[Feature[_]]]

    def list: ZStream[Any, Nothing, Feature[_]]

    def update[In, Out](fd: Feature[In]): ZIO[Any, FeatureNotFound, Unit]
  }
  object FeatureRepo {
    sealed trait Error
    object Error {
      case class FeatureAlreadyExists(featureId: FeatureId) extends Error
      case class FeatureNotFound(featureId: FeatureId)      extends Error
    }
  }

}
