CURRENT_GIT_BRANCH := $(shell git rev-parse --abbrev-ref HEAD)
MYSQL_ROOT_PASSWORD := BankwizRootPass2023
DOCKER_BUILDKIT=1


ifndef USER_GITHUB_LOGIN
$(error USER_GITHUB_LOGIN is not set)
endif

ifndef USER_GITHUB_KEY
$(error USER_GITHUB_KEY is not set)
endif

.PHONY: restore-system
restore-system:
	@docker exec -i bankwiz_mysql sh -c 'exec mysql -uroot -p"$(MYSQL_ROOT_PASSWORD)"' < sql/prepare.sql

.PHONY: restore-table
restore-table: restore-system
	@docker exec -i bankwiz_mysql sh -c 'exec mysql -uroot -p"$(MYSQL_ROOT_PASSWORD)" bankwiz_db' < sql/databases.sql

.PHONY: restore-data
restore-data:
	@docker exec -i bankwiz_mysql sh -c 'exec mysql -uroot -p"$(MYSQL_ROOT_PASSWORD)" bankwiz_db' < sql/data.sql

.PHONY: restore
restore: restore-system restore-table restore-data

.PHONY: dump-table
dump-table:
	@docker exec bankwiz_mysql sh -c 'exec mysqldump --no-data -uroot -p"$(MYSQL_ROOT_PASSWORD)" --single-transaction bankwiz_db' > sql/databases.sql

.PHONY: dump-data
dump-data:
	@docker exec bankwiz_mysql sh -c 'exec mysqldump --no-create-info -uroot -p"$(MYSQL_ROOT_PASSWORD)" bankwiz_db' > sql/data.sql

.PHONY: dump
dump: dump-table dump-data

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