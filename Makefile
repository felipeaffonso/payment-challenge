start-stack: package
	docker-compose up --build -d
stop-stack:
	docker-compose down
package:
	mvn clean package