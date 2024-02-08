# Define variables
DOCKER_IMAGE_NAME = java-app
CONTAINER_NAME = java-app-container

# Default target
all: clean build run check_health tail_logs

# Target to build the Java application Docker image
build:
	./mvnw clean package
	docker build -t $(DOCKER_IMAGE_NAME) .

# Target to run the Docker container
run:
	docker run -d -p 8080:8080 --name $(CONTAINER_NAME) $(DOCKER_IMAGE_NAME)
	sleep 10

# Target to check the health of the application
check_health:
	@echo "Checking health status..."
	@if curl -s http://localhost:8080/actuator/health | grep -q "\"status\":\"UP\""; then \
		echo "Success: Application is up and healthy."; \
	else \
		echo "Error: Application is not healthy. Stopping container."; \
		docker stop $(CONTAINER_NAME);  \
		docker rm $(CONTAINER_NAME) \
		exit 1; \
	fi

# Clean target
clean:
	@echo "Cleaning up..."
	docker stop $(CONTAINER_NAME) || true
	docker rm $(CONTAINER_NAME) || true


tail_logs:
	@echo "Tailing logs of the container $(CONTAINER_NAME)..."
	docker logs -f $(CONTAINER_NAME)