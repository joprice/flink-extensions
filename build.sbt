
val common = Seq(
  scalaVersion := "2.11.7",
  libraryDependencies ++= Seq(
    "org.apache.flink" %% "flink-scala" % "0.10.1", 
    "org.apache.flink" %% "flink-clients" % "0.10.1",
    "com.chuusai" %% "shapeless" % "2.2.5"
),
  fork in run := true,
  cancelable in Global := true,
  scalacOptions ++= Seq(
    "-feature",
    "-deprecation"
  )
)

lazy val root = project
  .in(file("."))
  .settings(common:_*)

