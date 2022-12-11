# Replace zhenyria by name of actual docker hub account

create_image:
	mvn clean package -Plocal
	docker build -t zhenyria/monro-consulting-bot:${tag} .

run:
	docker run -d -p 8080:8080 --name monro-consulting-bot zhenyria/monro-consulting-bot:${tag}
