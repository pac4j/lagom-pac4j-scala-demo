organization in ThisBuild := "org.pac4j.lagom"
version in ThisBuild := "1.0.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.12.18"

// Disable Cassandra and Kafka
lagomCassandraEnabled in ThisBuild := false
lagomKafkaEnabled in ThisBuild := false

val pac4jVersion = "6.3.2"
val lagomPac4j = "org.pac4j" %% "lagom-pac4j" % "2.2.1"
val pac4jHttp = "org.pac4j" % "pac4j-http" % pac4jVersion
val pac4jJwt = "org.pac4j" % "pac4j-jwt" % pac4jVersion
val nimbusJoseJwt = "com.nimbusds" % "nimbus-jose-jwt" % "10.8"
val macwire = "com.softwaremill.macwire" %% "macros" % "2.6.7" % Provided
val scalaTest = "org.scalatest" %% "scalatest" % "3.2.19" % Test

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

