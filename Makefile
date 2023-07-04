CURRENT_GIT_BRANCH := $(shell git rev-parse --abbrev-ref HEAD)
SONAR_UT_BRANCH_NAME := $(CURRENT_GIT_BRANCH)_UT
SONAR_IT_BRANCH_NAME := $(CURRENT_GIT_BRANCH)_IT
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
sonar: sonar-unittest sonar-integrationtest

.PHONY: sonar-unittest
sonar-unittest:
	mvn -Dtest="fr/bankwiz/server/unittest/**/*" clean verify sonar:sonar -Dsonar.branch.name=$(SONAR_UT_BRANCH_NAME)

.PHONY: sonar-integrationtest
sonar-integrationtest:
	mvn -Dtest="fr/bankwiz/server/integrationtest/**/*" clean verify sonar:sonar -Dsonar.branch.name=$(SONAR_IT_BRANCH_NAME)


.PHONY: docker-build
docker-build:
	docker build --secret id=USER_GITHUB_LOGIN --secret id=USER_GITHUB_KEY -t bankwiz_server .