val ZioVersion           = "2.0.2"
val ZioJsonVersion       = "0.3.0-RC11"
val ZioHttpVersion       = "2.0.0-RC11"
val ZioConfigVersion     = "3.0.2"
val ZioSchemaVersion     = "0.2.1"
val ZioLoggingVersion    = "2.1.0"
val ZioMetricsConnectors = "2.0.0"

val ScalikeVersion = "4.0.0"
val H2Version      = "2.1.210"
val LogbackVersion = "1.2.3"

ThisBuild / organization := "dev.zio"
ThisBuild / version      := "0.1.0"
ThisBuild / scalaVersion := "2.13.8"
ThisBuild / homepage     := Some(url("https://zio.github.io/zio-features"))
ThisBuild / description  := "A starter seed for ZIO 2.0 web applications."
ThisBuild / licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))
ThisBuild / developers := List(
  Developer(
    "jdeoges",
    "John De Goes",
    "@jdegoes",
    url("https://github.com/jdegoes")
  )
)

addCommandAlias("fmt", "all root/scalafmtSbt root/scalafmtAll")
addCommandAlias("check", "all root/scalafmtSbtCheck root/scalafmtCheckAll")

scalacOptions in ThisBuild ++= Seq("-deprecation", "-feature")

lazy val root = project
  .in(file("."))
  .settings(
    publish / skip := true,
    console        := (core / Compile / console).value
  )
  .aggregate(core, docs)

lazy val commonDeps = libraryDependencies ++= Seq(
  "dev.zio"         %% "zio"                    % ZioVersion,
  "dev.zio"         %% "zio-json"               % ZioJsonVersion,
  "io.d11"          %% "zhttp"                  % ZioHttpVersion,
  "dev.zio"         %% "zio-config"             % ZioConfigVersion,
  "dev.zio"         %% "zio-schema"             % ZioSchemaVersion,
  "dev.zio"         %% "zio-schema-derivation"  % ZioSchemaVersion,
  "dev.zio"         %% "zio-schema-protobuf"    % ZioSchemaVersion,
  "dev.zio"         %% "zio-logging"            % ZioLoggingVersion,
  "dev.zio"         %% "zio-logging-slf4j"      % ZioLoggingVersion,
  "dev.zio"         %% "zio-metrics-connectors" % ZioMetricsConnectors,
  "org.scalikejdbc" %% "scalikejdbc"            % ScalikeVersion,
  "com.h2database"   % "h2"                     % H2Version,
  "ch.qos.logback"   % "logback-classic"        % LogbackVersion,
  // "io.d11"          %% "zhttp-test"             % ZioHttpVersion % Test,
  "dev.zio"         %% "zio-test"               % ZioVersion     % Test,
  "dev.zio"         %% "zio-test-sbt"           % ZioVersion     % Test
)

lazy val core = (project in file("zio-features-core"))
  .settings(
    name := "zio-features",
    commonDeps,
    testFrameworks := Seq(new TestFramework("zio.test.sbt.ZTestFramework"))
  )

lazy val docs = project
  .in(file("zio-features-docs"))
  .settings(
    publish / skip := true,
    moduleName     := "zio-features-docs",
    scalacOptions -= "-Yno-imports",
    scalacOptions -= "-Xfatal-warnings",
    ScalaUnidoc / unidoc / unidocProjectFilter := inProjects(core),
    ScalaUnidoc / unidoc / target              := (LocalRootProject / baseDirectory).value / "website" / "static" / "api",
    cleanFiles += (ScalaUnidoc / unidoc / target).value,
    docusaurusCreateSite     := docusaurusCreateSite.dependsOn(Compile / unidoc).value,
    docusaurusPublishGhpages := docusaurusPublishGhpages.dependsOn(Compile / unidoc).value
  )
  .dependsOn(core)
  .enablePlugins(MdocPlugin, DocusaurusPlugin, ScalaUnidocPlugin)
