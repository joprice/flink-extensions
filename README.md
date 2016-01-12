# flink-extensions
Experimental extensions to make flink api more typesafe

## field functions

There are functions provided that mirror the existing api, with the additional 'Field' suffix. So far, 
Only `groupByField`, `sumField` are implemented.

The default group by in flink takes a string, which can be a field of a case 
class, but the value is unchecked:

```scala
case class Data(word: String, count: Int)

val counts = text
  .flatMap { _.toLowerCase.split("\\W+") }
  .map(Data(_, 1))
  .groupBy("words")
  .sum("counts")
```

`groupByField` is an alternative that makes use of Shapeless to validate that
the data being grouped contains the provided field. If it doesn't, it fails at
compile-time:

```scala
  val counts = text
    .flatMap { _.toLowerCase.split("\\W+") }
    .map(Data(_, 1))
    .groupByField('word)
    .sumField('counts)

// [error] Could not find the fields shapeless.::[Symbol with shapeless.tag.Tagged[String("counts")],shapeless.HNil] in Data2
// [error]       .sumField('counts)')
```

* Strings or Symbols can be used as the arguments.

* `groupByField` takes one or many fields.

## Docs
https://ci.apache.org/projects/flink/flink-docs-master/api/scala/index.html
