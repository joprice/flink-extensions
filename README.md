# flink-extensions
Experimental extensions to make flink api more typesafe

## groupByField

The default group by in flink takes a string, which can be a field of a case 
class, but the value is unchecked:

```scala
case class Data(word: String, count: Int)

val counts = text
  .flatMap { _.toLowerCase.split("\\W+") }
  .map(Data(_, 1))
  .groupBy("words")
  .sum(1)
```

`groupByField` is an alternative that makes use of Shapeless to validate that
the data being grouped contains the provided field. If it doesn't, it fails at
compile-time:

```scala
  val counts = text
    .flatMap { _.toLowerCase.split("\\W+") }
    .map(Data(_, 1))
    .groupByField('words)
    .sum(1)

// [error] Could not find the field String("words") in Data
// [error]       .groupByField("words")
```

Strings or Symbols can be used as the argument to `groupByField`.

## Docs
https://ci.apache.org/projects/flink/flink-docs-master/api/scala/index.html
