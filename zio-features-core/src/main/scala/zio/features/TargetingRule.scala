package zio.features

// ParameterData => Boolean
sealed trait TargetingRule { self =>
  def &&(that: TargetingRule): TargetingRule = TargetingRule.Conjunction(self, that)

  def ||(that: TargetingRule): TargetingRule = TargetingRule.Disjunction(self, that)

  def unary_! : TargetingRule = TargetingRule.Negation(self)
}
object TargetingRule {
  case object Everyone                                                                      extends TargetingRule
  private[features] final case class Conjunction(left: TargetingRule, right: TargetingRule) extends TargetingRule
  private[features] final case class Disjunction(left: TargetingRule, right: TargetingRule) extends TargetingRule
  private[features] final case class Negation(rule: TargetingRule)                          extends TargetingRule
  private[features] final case class ParameterData[Type](projection: Parameter[Type], predicate: Predicate[Type])
      extends TargetingRule

  val everyone: TargetingRule = Everyone

  val nobody: TargetingRule = !everyone

  def param[Type](parameter: Parameter[Type], predicate: Predicate[Type]): TargetingRule =
    ParameterData(parameter, predicate)

  // TargetingRule.param(platform, TargetingRule.equals("foo"))
}
