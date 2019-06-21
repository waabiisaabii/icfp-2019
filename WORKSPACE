load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

http_archive(
    name = "io_bazel_rules_kotlin",
    urls = ["https://github.com/cgruber/rules_kotlin/archive/cde63912cbb6fa88262392e695f59500fb3b1309.zip"],
    sha256 = "654eba643e1cd6a62c689519abfe1b0d553d84dec7c296f0b12a73c5c9bdd115",
    type = "zip",
    strip_prefix = "rules_kotlin-cde63912cbb6fa88262392e695f59500fb3b1309"
)

http_archive(
    name = "io_bazel_rules_rust",
    sha256 = "29d9fc1cdbd737c51db5983d1ac8e64cdc684c4683bafbcc624d3d81de92a32f",
    strip_prefix = "rules_rust-8417c8954efbd0cefc8dd84517b2afff5e907d5a",
    urls = ["https://github.com/bazelbuild/rules_rust/archive/8417c8954efbd0cefc8dd84517b2afff5e907d5a.tar.gz"],
)

http_archive(
    name = "bazel_skylib",
    sha256 = "2ea8a5ed2b448baf4a6855d3ce049c4c452a6470b1efd1504fdb7c1c134d220a",
    strip_prefix = "bazel-skylib-0.8.0",
    url = "https://github.com/bazelbuild/bazel-skylib/archive/0.8.0.tar.gz",
)

load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kotlin_repositories", "kt_register_toolchains")
kotlin_repositories()
kt_register_toolchains()

load("@io_bazel_rules_rust//rust:repositories.bzl", "rust_repositories")
rust_repositories()

load("@io_bazel_rules_rust//:workspace.bzl", "bazel_version")
bazel_version(name = "bazel_version")

