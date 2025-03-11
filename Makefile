# Replace zhenyria by name of actual dockerhub account

build:
	mvn clean package
	docker build -t zhenyria/monro-consulting-bot:${tag} .
