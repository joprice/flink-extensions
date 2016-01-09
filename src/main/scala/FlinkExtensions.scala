package com.joprice.flink

import org.apache.flink.api.scala._
import shapeless.Witness
import shapeless.tag.@@

object FlinkExtensions {

  implicit class RichGroupedDataSet[T](dataSet: DataSet[T]) {
    def groupByField(field: Witness.Lt[Symbol])(
      implicit hf: HasField[T, field.T]
    ): GroupedDataSet[T] = {
      dataSet.groupBy(field.value.name)
    }

    def groupByField(field: String)(
      implicit hf: HasField[T, Symbol @@ field.type]
    ): GroupedDataSet[T] = dataSet.groupBy(field)
  }

}
