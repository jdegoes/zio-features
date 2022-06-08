package zio.features

final case class Param[Type](paramType: ParamType[Type], paramData: Type)
