# zio-features

| CI | Release | Snapshot | Discord |
| --- | --- | --- | --- |
| ![CI][Badge-CI] | [![Release Artifacts][Badge-SonatypeReleases]][Link-SonatypeReleases] | [![Snapshot Artifacts][Badge-SonatypeSnapshots]][Link-SonatypeSnapshots] | [![Badge-Discord]][Link-Discord] |

# Summary

A starter seed for ZIO 2.0 web applications, together with workshop material for the course, _Building Web Applications with ZIO 2.0_.

You can find the workshop in the `zio-features-workshop` directory, and the starter project in the `zio-features-core` directory.

# Testing

Start an `sbt` shell and switch to the `workshop` project using  
```
project workshop
```
Run all tests using
```
test
```
or all tests in a particular test spec using e.g.
```
testOnly webapp.workshop.SchemaSpec
```
or all test suites with a particular name using
```
testOnly webapp.workshop.SchemaSpec -- -t "record capabilities"
```

# Documentation

[zio-features Microsite](https://zio.github.io/zio-features/)

# Contributing

[Documentation for contributors](https://zio.github.io/zio-features/docs/about/about_contributing)

## Code of Conduct

See the [Code of Conduct](https://zio.github.io/zio-features/docs/about/about_coc)

## Support

Come chat with us on [![Badge-Discord]][Link-Discord].

# License

[License](LICENSE)

[Badge-SonatypeReleases]: https://img.shields.io/nexus/r/https/oss.sonatype.org/dev.zio/zio-features_2.13.8.svg "Sonatype Releases"
[Badge-SonatypeSnapshots]: https://img.shields.io/nexus/s/https/oss.sonatype.org/dev.zio/zio-features_2.13.8.svg "Sonatype Snapshots"
[Badge-Discord]: https://img.shields.io/discord/2ccFBr4?logo=discord "chat on discord"
[Link-SonatypeReleases]: https://oss.sonatype.org/content/repositories/releases/dev/zio/zio-features_2.13.8/ "Sonatype Releases"
[Link-SonatypeSnapshots]: https://oss.sonatype.org/content/repositories/snapshots/dev/zio/zio-features_2.13.8/ "Sonatype Snapshots"
[Link-Discord]: https://discord.com/invite/2ccFBr4 "Discord"
[Badge-CI]: https://github.com/zio/zio-features/workflows/CI/badge.svg


