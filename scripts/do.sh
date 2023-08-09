#!/bin/bash

CURRENT_GIT_BRANCH=$(git rev-parse --abbrev-ref HEAD)
SONAR_UT_BRANCH_NAME="${CURRENT_GIT_BRANCH}_UT"
SONAR_IT_BRANCH_NAME="${CURRENT_GIT_BRANCH}_IT"
MYSQL_ROOT_PASSWORD="BankwizRootPass2023"
DOCKER_BUILDKIT=1

if [[ -z "$USER_GITHUB_LOGIN" ]]; then
  echo "USER_GITHUB_LOGIN is not set"
  exit 1
fi

if [[ -z "$USER_GITHUB_KEY" ]]; then
  echo "USER_GITHUB_KEY is not set"
  exit 1
fi

restore-system() {
  docker exec -i bankwiz_mysql sh -c 'exec mysql -uroot -p"$MYSQL_ROOT_PASSWORD"' < sql/prepare.sql
}

restore-table() {
  docker exec -i bankwiz_mysql sh -c 'exec mysql -uroot -p"$MYSQL_ROOT_PASSWORD" bankwiz_db' < sql/databases.sql
}

restore-data() {
  docker exec -i bankwiz_mysql sh -c 'exec mysql -uroot -p"$MYSQL_ROOT_PASSWORD" bankwiz_db' < sql/data.sql
}

restore() {
  restore-system
  restore-table
  restore-data
}

dump-table() {
  docker exec bankwiz_mysql sh -c 'exec mysqldump --no-data -uroot -p"$MYSQL_ROOT_PASSWORD" --single-transaction bankwiz_db' > sql/databases.sql
  docker exec bankwiz_mysql sh -c 'exec mysqldump --no-data -uroot -p"$MYSQL_ROOT_PASSWORD" --single-transaction bankwiz_db' > src/test/resources/databases.sql
}

dump-data() {
  docker exec bankwiz_mysql sh -c 'exec mysqldump --no-create-info -uroot -p"$MYSQL_ROOT_PASSWORD" bankwiz_db' > sql/data.sql
}

dump() {
  dump-table
  dump-data
}

compile() {
  mvn compile
}

test-unit() {
  mvn test -Dtest="fr/bankwiz/server/unittest/**/*"
}

test-integration() {
  mvn test -Dtest="fr/bankwiz/server/integrationtest/**/*"
}

package() {
  mvn package
}

package-withouttest() {
  mvn package -DskipTests
}

spotless-apply() {
  mvn spotless:apply
}

spotless-check() {
  mvn spotless:check
}

sonar-all() {
  sonar-unittest
  sonar-integrationtest
}

sonar-unittest() {
  mvn -Dtest="fr/bankwiz/server/unittest/**/*" clean verify sonar:sonar -Dsonar.branch.name="$SONAR_UT_BRANCH_NAME"
}

sonar-integrationtest() {
  mvn -Dtest="fr/bankwiz/server/integrationtest/**/*" clean verify sonar:sonar -Dsonar.branch.name="$SONAR_IT_BRANCH_NAME"
}

docker-build() {
  docker build --secret id=USER_GITHUB_LOGIN --secret id=USER_GITHUB_KEY -t bankwiz_server .
}

# Handle command-line arguments
for i in "$@"; do
    "$i"
done
