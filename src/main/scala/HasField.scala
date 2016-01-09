package com.joprice.flink

import shapeless._
import shapeless.ops.record._
import shapeless.tag._
import scala.annotation.implicitNotFound

//This only exists to customize the default shapeless error, which loses the out type and shows tags and symbol:
// No field shapeless.tag.@@[Symbol,String("words")] in record this.Out
@implicitNotFound("Could not find the field ${U} in ${T}")
trait HasField[T, U]

object HasField{
  implicit def mkHasField[T <: Product, L <: HList, F](
    implicit lgen: LabelledGeneric.Aux[T, L], s: Selector[L, F]
  ): HasField[T, F] = {
    // casting null to avoid pointless allocation
    null.asInstanceOf[HasField[T, F]]
  }
}
