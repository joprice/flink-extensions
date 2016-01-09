package com.joprice.flink

import org.apache.flink.api.scala._

object FlinkExtensions {

  implicit class RichGroupedDataSet[T](dataSet: DataSet[T]) {
    def groupByField(field: String)(
      implicit hf: HasField[T, field.type]
    ) = dataSet.groupBy(field)
  }

}
