create_image:
	mvn clean package
	docker build -t zhenyria/telegram-consulting-plugin:${tag} .

run:
	docker run -d -p 8080:8080 --name telegram-consulting-plugin zhenyria/telegram-consulting-plugin:${tag}
