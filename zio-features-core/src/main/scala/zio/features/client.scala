package zio.features

import zio.features.api.client._
import zio.features.api.common._

object client extends ClientApis with CommonApis {
  type FeatureDecisionMatrix[-Input] = zio.features.model.FeatureDecisionMatrix[Input]
  val FeatureDecisionMatrix = zio.features.model.FeatureDecisionMatrix

  type FeatureDecisionMatrixWithId[-Input] = zio.features.model.FeatureDecisionMatrixWithId[Input]
  val FeatureDecisionMatrixWithId = zio.features.model.FeatureDecisionMatrixWithId

  type FeatureDecisionMatrixId = zio.features.model.FeatureDecisionMatrixId
  val FeatureDecisionMatrixId = zio.features.model.FeatureDecisionMatrixId

  type Params[Types] = zio.features.model.Params[Types]
  val Params = zio.features.model.Params

  type TargetingExpr[-Input, +Output] = zio.features.model.TargetingExpr[Input, Output]
  val TargetingExpr = zio.features.model.TargetingExpr

  type ParamType[Type] = zio.features.model.ParamType[Type]
  val ParamType = zio.features.model.ParamType

  type ParamDescriptor[Type] = zio.features.model.ParamDescriptor[Type]
  val ParamDescriptor = zio.features.model.ParamDescriptor

  type FeatureDescriptorId = zio.features.model.FeatureDescriptorId
  val FeatureDescriptorId = zio.features.model.FeatureDescriptorId

  type FeatureDescriptor[Input, Output] = zio.features.model.FeatureDescriptor[Input, Output]
  val FeatureDescriptor = zio.features.model.FeatureDescriptor
}
