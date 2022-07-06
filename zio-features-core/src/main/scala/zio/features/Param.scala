package zio.features

// FIXME
final case class Param[Type](paramType: ParamType[Type], paramData: Type)
