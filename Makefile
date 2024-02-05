DOCKER_IMAGE = "openapitools/openapi-generator-cli:v7.2.0"
GENERATED_DIR_JAVA = "openapi"
OPENAPI_SPEC = "openapi.yaml"
CURRENT_GIT_BRANCH := $(shell git rev-parse --abbrev-ref HEAD)
DOCKER_BUILDKIT=1

ifndef USER_GITHUB_LOGIN
$(error USER_GITHUB_LOGIN is not set)
endif

ifndef USER_GITHUB_KEY
$(error USER_GITHUB_KEY is not set)
endif

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

.PHONY: install-java
install-java:
	mvn clean install -f $(GENERATED_DIR_JAVA)/pom.xml

.PHONY: all
all: clean-openapi generate-openapi install-java

.PHONY: spotless-apply
spotless-apply:
	mvn spotless:apply

.PHONY: spotless-check
spotless-check:
	mvn spotless:check

.PHONY: sonar
sonar:
	mvn clean verify sonar:sonar -Dsonar.branch.name=$(CURRENT_GIT_BRANCH)

.PHONY: docker-build
docker-build:
	docker build -t bankwiz_server .

.PHONY: http-coverage-domain
http-coverage-domain:
	python3 -m http.server 8001 -d domain/target/site/jacoco

.PHONY: http-coverage-infrastructure
http-coverage-infrastructure:
	python3 -m http.server 8002 -d infrastructure/target/site/jacoco