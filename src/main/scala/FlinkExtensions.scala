package com.joprice.flink

import org.apache.flink.api.scala._
import shapeless.ops.hlist.{ToTraversable, Mapper}
import shapeless._
import tag._
import scala.language.implicitConversions

object FlinkExtensions {

  object string extends Poly1 {
    implicit def caseString[S <: String] = at[S](identity)
    implicit def caseSymbol[T <: Symbol] = at[T](_.name)
  }

  implicit class RichGroupedDataSet[T <: Product](dataSet: GroupedDataSet[T]) {
    def sumField(field: String)(implicit h: HasFields[T, (field.type) :: HNil]) =
      dataSet.sum(field)

    // using Witness here to get around singleton type limitations for symbols
    def sumField(field: Witness.Lt[Symbol])(implicit h: HasFields[T, (field.T) :: HNil]) =
      dataSet.sum(field.value.name)
  }

  implicit class RichDataSet[T <: Product, TL <: HList](dataSet: DataSet[T]) {
    val groupByField = new FieldsFunction[T, GroupedDataSet[T]] ({ fieldNames =>
      dataSet.groupBy(fieldNames.head, fieldNames.tail: _*)
    })
  }

  class FieldsFunction[T, U](f: Seq[String] => U) extends SingletonProductArgs {
    def applyProduct[L <: HList, N <: HList](fields: L)(
      implicit
      // make sure the method cannot be called with empty arguments
      ev: L =:!= HNil,
      // validate T contains all provided fields
      h: HasFields[T, L],
      // convert symbols and strings to strings
      mapper: Mapper.Aux[string.type, L, N],
      // turn the HList into a list of strings
      tt: ToTraversable.Aux[N, List, String]
    ): U =
      f(tt(mapper(fields)))
  }

}
