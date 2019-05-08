organization in ThisBuild := "org.pac4j.lagom"
version in ThisBuild := "1.0.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.12.4"

// Disable Cassandra and Kafka
lagomCassandraEnabled in ThisBuild := false
lagomKafkaEnabled in ThisBuild := false

val pac4jVersion = "3.6.1"
val lagomPac4j = "org.pac4j" %% "lagom-pac4j" % "2.0.0"
val pac4jHttp = "org.pac4j" % "pac4j-http" % pac4jVersion
val pac4jJwt = "org.pac4j" % "pac4j-jwt" % pac4jVersion
val nimbusJoseJwt = "com.nimbusds" % "nimbus-jose-jwt" % "6.0"
val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.0" % Provided
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4" % Test

lazy val `lagom-pac4j-scala-demo` = (project in file("."))
  .aggregate(`lagom-pac4j-scala-demo-api`, `lagom-pac4j-scala-demo-impl`)

lazy val `lagom-pac4j-scala-demo-api` = (project in file("api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `lagom-pac4j-scala-demo-impl` = (project in file("impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      pac4jHttp,
      pac4jJwt,
      lagomPac4j,
      lagomScaladslTestKit,
      macwire,
      scalaTest,
      nimbusJoseJwt
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`lagom-pac4j-scala-demo-api`)

