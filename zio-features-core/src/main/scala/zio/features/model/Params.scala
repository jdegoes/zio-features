package zio.features.model

import zio._

sealed abstract case class Params[Types] private (private val data: Map[ParamDescriptor[_], Any]) {
  def get[Key <: Singleton with String, Value: ParamType](pd: ParamDescriptor[(Key, Value)])(implicit
    subset: (Key, Value) <:< Types
  ): Value =
    data(pd).asInstanceOf[Value]

  def add[Key <: Singleton with String, Value: ParamType](
    pd: ParamDescriptor[(Key, Value)],
    value: Value
  ): Params[Types & (Key, Value)] =
    new Params[Types & (Key, Value)](data + (pd -> value)) {}

  private def add[Key <: Singleton with String, Value: ParamType](
    name: Key,
    value: Value
  ): Params[Types & (Key, Value)] =
    new Params[Types & (Key, Value)](data + (ParamDescriptor[Key, Value](name) -> value)) {}
}
object Params {
  val empty: Params[Any] = new Params[Any](Map.empty) {}

  def apply[Key <: Singleton with String, Value: ParamType](key: Key, value: Value): Params[(Key, Value)] =
    empty.add(key, value)

  def apply[Key1 <: Singleton with String, Value1: ParamType, Key2 <: Singleton with String, Value2: ParamType](
    tuple1: (Key1, Value1),
    tuple2: (Key2, Value2)
  ): Params[(Key1, Value1) & (Key2, Value2)] =
    empty.add[Key1, Value1](tuple1._1, tuple1._2).add[Key2, Value2](tuple2._1, tuple2._2)

  def apply[
    Key1 <: Singleton with String,
    Value1: ParamType,
    Key2 <: Singleton with String,
    Value2: ParamType,
    Key3 <: Singleton with String,
    Value3: ParamType
  ](
    tuple1: (Key1, Value1),
    tuple2: (Key2, Value2),
    tuple3: (Key3, Value3)
  ): Params[(Key1, Value1) & (Key2, Value2) & (Key3, Value3)] =
    empty
      .add[Key1, Value1](tuple1._1, tuple1._2)
      .add[Key2, Value2](tuple2._1, tuple2._2)
      .add[Key3, Value3](tuple3._1, tuple3._2)

  def apply[
    Key1 <: Singleton with String,
    Value1: ParamType,
    Key2 <: Singleton with String,
    Value2: ParamType,
    Key3 <: Singleton with String,
    Value3: ParamType,
    Key4 <: Singleton with String,
    Value4: ParamType
  ](
    tuple1: (Key1, Value1),
    tuple2: (Key2, Value2),
    tuple3: (Key3, Value3),
    tuple4: (Key4, Value4)
  ): Params[(Key1, Value1) & (Key2, Value2) & (Key3, Value3) & (Key4, Value4)] =
    empty
      .add[Key1, Value1](tuple1._1, tuple1._2)
      .add[Key2, Value2](tuple2._1, tuple2._2)
      .add[Key3, Value3](tuple3._1, tuple3._2)
      .add[Key4, Value4](tuple4._1, tuple4._2)
}
