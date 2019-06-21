load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_jvm_binary", "kt_jvm_test")

kt_jvm_binary(
    name = "misfits",
    main_class = 'icfp2019.AppKt',
    srcs = glob(["src/main/kotlin/**/*.kt"]),
    deps = [
        "@maven//:com_google_guava_guava",
        # "//path/to/dependency",
    ],
)

kt_jvm_test(
    name = "misfits_test",
    srcs = glob(["src/test/kotlin/**/*.kt"]),
    main_class = "org.junit.platform.console.ConsoleLauncher",
    args = [ "--select-package", "icfp2019"],
    deps = [
        ":misfits",
        "@maven//:org_junit_platform_junit_platform_console",
        "@maven//:org_junit_jupiter_junit_jupiter_api",
        "@maven//:org_junit_jupiter_junit_jupiter_engine",
        # "//path/to/dependency",
    ],
)
