package zio.features.model

// FIXME
final case class Param[Type](paramType: ParamType[Type], paramData: Type)
