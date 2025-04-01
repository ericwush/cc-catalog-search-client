workspace(name = "cc_catalog_search_client")

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

# Java rules
RULES_JVM_EXTERNAL_TAG = "4.5"
RULES_JVM_EXTERNAL_SHA = "b17d7388feb9bfa7f2fa09031b32707df529f26c91ab9e5d909eb1676badd9a6"

http_archive(
    name = "rules_jvm_external",
    sha256 = RULES_JVM_EXTERNAL_SHA,
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

load("@rules_jvm_external//:repositories.bzl", "rules_jvm_external_deps")

rules_jvm_external_deps()

load("@rules_jvm_external//:setup.bzl", "rules_jvm_external_setup")

rules_jvm_external_setup()

# Proto and gRPC rules
http_archive(
    name = "rules_proto",
    sha256 = "dc3fb206a2cb3441b485eb1e423165b231235a1ea9b031b4433cf7bc1fa460dd",
    strip_prefix = "rules_proto-5.3.0-21.7",
    urls = [
        "https://github.com/bazelbuild/rules_proto/archive/refs/tags/5.3.0-21.7.tar.gz",
    ],
)

load("@rules_proto//proto:repositories.bzl", "rules_proto_dependencies", "rules_proto_toolchains")

rules_proto_dependencies()
rules_proto_toolchains()

# gRPC Java
http_archive(
    name = "io_grpc_grpc_java",
    sha256 = "73f1f612b5e03501f2ebcb8195d2f53c0ac6f301b94d503ee77f9c58e38bb114",
    strip_prefix = "grpc-java-1.61.0",
    urls = ["https://github.com/grpc/grpc-java/archive/v1.61.0.tar.gz"],
)

load("@io_grpc_grpc_java//:repositories.bzl", "IO_GRPC_GRPC_JAVA_ARTIFACTS", "IO_GRPC_GRPC_JAVA_OVERRIDE_TARGETS", "grpc_java_repositories")

# Maven dependencies
load("@rules_jvm_external//:defs.bzl", "maven_install")

maven_install(
    artifacts = [
        "com.google.protobuf:protobuf-java:3.25.1",
        "io.grpc:grpc-netty:1.61.0",
        "io.grpc:grpc-protobuf:1.61.0",
        "io.grpc:grpc-stub:1.61.0",
        "org.slf4j:slf4j-api:2.0.12",
    ] + IO_GRPC_GRPC_JAVA_ARTIFACTS,
    generate_compat_repositories = True,
    override_targets = IO_GRPC_GRPC_JAVA_OVERRIDE_TARGETS,
    repositories = [
        "https://repo.maven.apache.org/maven2/",
    ],
)

grpc_java_repositories()