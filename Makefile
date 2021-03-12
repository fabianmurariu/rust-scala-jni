##
# Sparse Matrix Scala Bindings
#
# @file
# @version 0.1

sparse4s-compile:
	cd sparse4s && sbt clean package

all: javah copy-lib
	cd sparse4s && sbt test package

javah: sparse4s-compile
	export SCALA_VERSION=$$(grep "scalaVersion" sparse4s/build.sbt | awk '{print $$5}' | sed 's/\"//g') && \
	export SPARSE4S_LIB=$$(find . -name "sparse4s*.jar") && \
	javah -cp "$$HOME/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/$$SCALA_VERSION/scala-library-$$SCALA_VERSION.jar:$$SPARSE4S_LIB" -d sparse_mat "sparse4s.GrBType" && \
	javah -cp "$$HOME/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/$$SCALA_VERSION/scala-library-$$SCALA_VERSION.jar:$$SPARSE4S_LIB" -d sparse_mat "sparse4s.GrB"

rust-release:
	cd sparse_mat && cargo build --release

copy-lib: rust-release
	cd sparse_mat && \
	export LIB_FILE=$$(find ./target/release -name "libsparse*.so" | grep -v deps) && \
	mkdir -p ../sparse4s/src/main/resources/`uname`/`arch`/ && \
	cp $$LIB_FILE ../sparse4s/src/main/resources/`uname`/`arch`/

# end
