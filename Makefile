DOCKER_IMAGE = "openapitools/openapi-generator-cli:v7.2.0"
GENERATED_DIR_JAVA = "openapi"
OPENAPI_SPEC = "openapi.yaml"
DOCKER_BUILDKIT=1

.PHONY: clean-openapi
clean-openapi:
	rm -rf $(GENERATED_DIR_JAVA)

.PHONY: generate-openapi
generate-openapi:
	docker run --rm \
	  -v $$PWD:/local \
	  --user $$(id -u):$$(id -g) \
	  $(DOCKER_IMAGE) generate \
	  -i /local/$(OPENAPI_SPEC) \
	  -g spring \
	  -o /local/$(GENERATED_DIR_JAVA) \
	  --api-package=fr.bankwiz.openapi.api \
	  --additional-properties=useSpringBoot3=true \
	  --additional-properties=groupId=fr.bankwiz \
	  --additional-properties=artifactId=openapi \
	  --model-package=fr.bankwiz.openapi.model \
	  --additional-properties=interfaceOnly=true \
	  --additional-properties=dateLibrary=java8 \
	  --additional-properties=hideGenerationTimestamp=true

.PHONY: install-openapi
install-openapi:
	mvn clean install -f $(GENERATED_DIR_JAVA)/pom.xml

.PHONY: all
all: clean-openapi generate-openapi install-openapi

.PHONY: spotless-apply
spotless-apply:
	mvn spotless:apply

.PHONY: spotless-check
spotless-check:
	mvn spotless:check

.PHONY: docker-build
docker-build:
	docker build -t bankwiz_server .

.PHONY: http-coverage-domain
http-coverage-domain:
	python3 -m http.server 8001 -d domain/target/site/jacoco

.PHONY: http-coverage-infrastructure
http-coverage-infrastructure:
	python3 -m http.server 8002 -d infrastructure/target/site/jacoco

.PHONY: package
package:
	mvn clean package -Dcheckstyle.skip -DskipTests

.PHONY: test
test:
	mvn clean test -Dcheckstyle.skip

.PHONY: checkstyle
checkstyle:
	mvn checkstyle:checkstyle