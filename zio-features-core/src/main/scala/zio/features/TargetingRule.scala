package zio.features

// ParameterData => Boolean
final case class TargetingRule(predicate: TargetingExpr[Boolean]) { self =>
  def &&(that: TargetingRule): TargetingRule = TargetingRule(self.predicate && that.predicate)

  def ||(that: TargetingRule): TargetingRule = TargetingRule(self.predicate || that.predicate)

  def unary_! : TargetingRule = TargetingRule(!self.predicate)
}
object TargetingRule {
  val everyone: TargetingRule = TargetingRule(true)

  val nobody: TargetingRule = !everyone

  def param[Type](parameter: Parameter[Type])(f: TargetingExpr[Type] => TargetingExpr[Boolean]): TargetingRule =
    TargetingRule(f(TargetingExpr.extract(parameter)))
}
