organization in ThisBuild := "com.example"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.12.8"

lazy val `hello-world` = (project in file("."))
  .aggregate(`hello-world-api`, `hello-world-impl`, `hello-world-stream-api`, `hello-world-stream-impl`)

lazy val `hello-world-api` = (project in file("hello-world-api"))
  .settings(common)
  .settings(
    libraryDependencies ++= Seq(
      lagomJavadslApi,
      lombok
    )
  )

lazy val `hello-world-impl` = (project in file("hello-world-impl"))
  .enablePlugins(LagomJava)
  .settings(common)
  .settings(
    libraryDependencies ++= Seq(
      lagomJavadslPersistenceCassandra,
      lagomJavadslKafkaBroker,
      lagomLogback,
      lagomJavadslTestKit,
      lombok
    )
  )
  .settings(lagomForkedTestSettings)
  .dependsOn(`hello-world-api`)

lazy val `hello-world-stream-api` = (project in file("hello-world-stream-api"))
  .settings(common)
  .settings(
    libraryDependencies ++= Seq(
      lagomJavadslApi
    )
  )

lazy val `hello-world-stream-impl` = (project in file("hello-world-stream-impl"))
  .enablePlugins(LagomJava)
  .settings(common)
  .settings(
    libraryDependencies ++= Seq(
      lagomJavadslPersistenceCassandra,
      lagomJavadslKafkaClient,
      lagomLogback,
      lagomJavadslTestKit
    )
  )
  .dependsOn(`hello-world-stream-api`, `hello-world-api`)

val lombok = "org.projectlombok" % "lombok" % "1.18.8"

def common = Seq(
  javacOptions in Compile += "-parameters"
)
