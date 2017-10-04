.PHONY: image test

IMAGE_NAME ?= codeclimate/codeclimate-sonar-java
DOCKER_RUN_MOUNTED = docker run --rm -w /usr/src/app -v $(PWD):/usr/src/app

image:
	docker build --rm -t $(IMAGE_NAME) .

test: image
	docker run -ti -v $(PWD)/fixtures:/code $(IMAGE_NAME) 

