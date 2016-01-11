package com.joprice.flink

import shapeless._
import shapeless.ops.record._
import scala.annotation.implicitNotFound
import shapeless.tag._

@implicitNotFound("Could not find the fields ${L} in ${T}")
trait HasFields[T, L <: HList]

object HasFields{

  implicit def hnilToHasFieldMapper[T]: HasFields[T, HNil] = null

  implicit def stringToHasFieldMapper[T, Head, L <: HList, Tail <: HList](
    implicit
    lgen: LabelledGeneric.Aux[T, L],
    s: Selector[L, Symbol @@ Head],
    tail: HasFields[T, Tail]
  ): HasFields[T, Head :: Tail] = null

  implicit def symbolToHasFieldMapper[T, Head, L <: HList, Tail <: HList](
    implicit
    lgen: LabelledGeneric.Aux[T, L],
    s: Selector[L, Head],
    tail: HasFields[T, Tail]
  ): HasFields[T, Head :: Tail] = null

}
