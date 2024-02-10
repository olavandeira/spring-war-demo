DOCKER_IMAGE_NAME = java-app
CONTAINER_NAME = java-app-container

run-local: clean build run check_health test tail_logs

build:
	./mvnw clean package
	docker build -t $(DOCKER_IMAGE_NAME) .

run:
	docker run -d -p 8080:8080 --name $(CONTAINER_NAME) $(DOCKER_IMAGE_NAME)

check_health:
	sleep 10
	@echo "Checking health status..."
	@if curl -s http://localhost:8080/actuator/health | grep -q "\"status\":\"UP\""; then \
		echo "Success: Application is up and healthy."; \
	else \
		echo "Error: Application is not healthy. Stopping container."; \
		docker stop $(CONTAINER_NAME);  \
		docker rm $(CONTAINER_NAME) \
		exit 1; \
	fi

clean:
	@echo "Cleaning up..."
	docker stop $(CONTAINER_NAME) || true
	docker rm $(CONTAINER_NAME) || true

test:
	curl -X GET "http://localhost:8080/prices?time=2020-06-14T10:00:00&productId=35455&brandId=1"
	curl -X GET "http://localhost:8080/prices?time=2020-06-14T16:00:00&productId=35455&brandId=1"
	curl -X GET "http://localhost:8080/prices?time=2020-06-14T21:00:00&productId=35455&brandId=1"
	curl -X GET "http://localhost:8080/prices?time=2020-06-15T10:00:00&productId=35455&brandId=1"
	curl -X GET "http://localhost:8080/prices?time=2020-06-16T21:00:00&productId=35455&brandId=1"


tail_logs:
	@echo "Tailing logs of the container $(CONTAINER_NAME)..."
	docker logs -f $(CONTAINER_NAME)

kill:
	@echo "Killing the container $(CONTAINER_NAME)..."
	docker stop $(CONTAINER_NAME) || true
	docker rm $(CONTAINER_NAME) || true

