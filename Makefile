CURRENT_GIT_BRANCH := $(shell git rev-parse --abbrev-ref HEAD)
SONAR_UT_BRANCH_NAME := $(CURRENT_GIT_BRANCH)_UT
SONAR_IT_BRANCH_NAME := $(CURRENT_GIT_BRANCH)_IT
MYSQL_ROOT_PASSWORD := BankwizRootPass2023
DOCKER_BUILDKIT=1

ifndef USER_GITHUB_LOGIN
$(error USER_GITHUB_LOGIN is not set)
endif

ifndef USER_GITHUB_KEY
$(error USER_GITHUB_KEY is not set)
endif

.PHONY: compile
compile:
	mvn compile

.PHONY: package
package:
	mvn package

.PHONY: package-withouttest
package-withouttest:
	mvn package -DskipTests

.PHONY: spotless-apply
spotless-apply:
	mvn spotless:apply

.PHONY: spotless-check
spotless-check:
	mvn spotless:check

.PHONY: docker-build
docker-build:
	docker build --secret id=USER_GITHUB_LOGIN --secret id=USER_GITHUB_KEY -t bankwiz_server .