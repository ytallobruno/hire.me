build-app: ## Executa build da aplicação
	./gradlew clean build -x test

run-deps: ## Sobe compose das dependências (MySQL e SQS)
	cd docker && docker-compose -f docker-compose.yml up

test: ## Executa os testes e relatório de cobertura
	./gradlew clean test jacocoTestReport