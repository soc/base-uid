lazy val root = (project in file("."))
  .settings(
    name                 := "base-uid",
    organization         := "me.soc",
    managedScalaInstance := false,
    crossPaths           := false,
    version              := "0",
    homepage             := Some(url("https://github.com/soc/base-uid")),
    licenses             := Seq("Mozilla Public License 2.0" -> url("https://opensource.org/licenses/MPL-2.0")),
    fork                 := true,
    // The javaHome setting can be removed if building against the latest installed version of Java is acceptable.
    // Running the tests requires removing the setting.
    // It can also be changed to point to a different Java version.
    // javaHome             := Some(file("/home/soc/apps/zulu6.19.0.1-jdk6.0.103-linux_x64/")),
    libraryDependencies  += "junit"          % "junit"           % "4.13.2" % Test,
    libraryDependencies  += "com.github.sbt" % "junit-interface" % "0.13.2" % Test,
    Test / testOptions   := Seq(Tests.Argument(TestFrameworks.JUnit, "-a")),
    /*
    publishTo            := {
      val nexus = "https://oss.sonatype.org/"
      if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
      else Some("releases" at nexus + "service/local/staging/deploy/maven2")
    },
    */
    Compile / packageBin / packageOptions += {
      import java.util.jar.{Attributes, Manifest}
      val manifest = new Manifest
      manifest.getMainAttributes.put(new Attributes.Name("Automatic-Module-Name"), "me.soc.base-uid")
      Package.JarManifest(manifest)
    },
    pomIncludeRepository := { _ => false },
    pomExtra             :=
      <scm>
        <url>git@github.com:soc/cuid.git</url>
        <connection>scm:git:git@github.com:soc/base-uid.git</connection>
      </scm>
      <developers>
        <developer>
          <id>soc</id>
          <name>Simon Ochsenreither</name>
          <url>https://github.com/soc</url>
          <roles>
            <role>Project Lead</role>
          </roles>
        </developer>
      </developers>
  )
