package zio.features

sealed trait ParamDescriptor[KeyValue] { self =>
  type Key <: Singleton with String
  type Value

  def name: Key

  def paramType: ParamType[Value]

  def get: TargetingExpr[KeyValue, Value] = TargetingExpr.Extraction[KeyValue, Value](self)
}
object ParamDescriptor {
  def apply[N <: Singleton with String, T](name0: N)(implicit paramType0: ParamType[T]): ParamDescriptor[(N, T)] =
    new ParamDescriptor[(N, T)] {
      type Key   = N
      type Value = T
      def name: N                 = name0
      def paramType: ParamType[T] = paramType0
    }

  def int[N <: Singleton with String](name0: N): ParamDescriptor[(N, Int)] = ParamDescriptor[N, Int](name0)

  def string[N <: Singleton with String](name0: N): ParamDescriptor[(N, String)] = ParamDescriptor[N, String](name0)
}
