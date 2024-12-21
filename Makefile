build-app: ## Executa build da aplicação
	./gradlew clean build -x test

run-deps: ## Sobe compose das dependências (MySQL e Frontend)
	cd docker && docker-compose -f docker-compose.yml up

run-mysql: ## Sobe compose das dependências (MySQL)
	cd docker && docker-compose -f docker-compose.yml up mysql

run-front: ## Sobe compose das dependências (Frontend)
	cd docker && docker-compose -f docker-compose.yml up frontend

test: ## Executa os testes e relatório de cobertura do backend
	./gradlew clean test jacocoTestReport

run-frontend: ## Instala dependências e roda apenas o frontend
	cd frontend && yarn install && yarn dev
