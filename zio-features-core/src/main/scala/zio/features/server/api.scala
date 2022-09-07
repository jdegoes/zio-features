package zio.features.server

import zio._
import zio.features.common.api._
import zio.features.client.services._

object api extends ServerApis with CommonApis {
  type FeatureDecisionMatrix[-Input] = zio.features.common.model.FeatureDecisionMatrix[Input]
  val FeatureDecisionMatrix = zio.features.common.model.FeatureDecisionMatrix

  type FeatureDecisionMatrixWithId[-Input] = zio.features.common.model.FeatureDecisionMatrixWithId[Input]
  val FeatureDecisionMatrixWithId = zio.features.common.model.FeatureDecisionMatrixWithId

  type FeatureDecisionMatrixId = zio.features.common.model.FeatureDecisionMatrixId
  val FeatureDecisionMatrixId = zio.features.common.model.FeatureDecisionMatrixId

  type Params[Types] = zio.features.common.model.Params[Types]
  val Params = zio.features.common.model.Params

  type TargetingExpr[-Input, +Output] = zio.features.common.model.TargetingExpr[Input, Output]
  val TargetingExpr = zio.features.common.model.TargetingExpr

  type ParamType[Type] = zio.features.common.model.ParamType[Type]
  val ParamType = zio.features.common.model.ParamType

  type ParamDescriptor[Type] = zio.features.common.model.ParamDescriptor[Type]
  val ParamDescriptor = zio.features.common.model.ParamDescriptor

  type FeatureDescriptorId = zio.features.common.model.FeatureDescriptorId
  val FeatureDescriptorId = zio.features.common.model.FeatureDescriptorId

  type FeatureDescriptor[Input, Output] = zio.features.common.model.FeatureDescriptor[Input, Output]
  val FeatureDescriptor = zio.features.common.model.FeatureDescriptor
}
