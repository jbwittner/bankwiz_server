CURRENT_GIT_BRANCH := $(shell git rev-parse --abbrev-ref HEAD)
DOCKER_BUILDKIT=1

ifndef USER_GITHUB_LOGIN
$(error USER_GITHUB_LOGIN is not set)
endif

ifndef USER_GITHUB_KEY
$(error USER_GITHUB_KEY is not set)
endif

.PHONY: compile
compile:
	mvn clean compile

.PHONY: package
package:
	mvn clean package

.PHONY: package-withouttest
package-withouttest:
	mvn package -DskipTests

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
	docker build --secret id=USER_GITHUB_LOGIN --secret id=USER_GITHUB_KEY -t bankwiz_server .

.PHONY: http-coverage-domain
http-coverage-domain:
	python3 -m http.server 8001 -d domain/target/site/jacoco

.PHONY: http-coverage-infrastructure
http-coverage-infrastructure:
	python3 -m http.server 8002 -d infrastructure/target/site/jacoco