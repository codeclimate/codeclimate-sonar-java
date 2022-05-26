.PHONY: image test

IMAGE_NAME ?= codeclimate/codeclimate-sonar-java

image:
	docker build --rm -t $(IMAGE_NAME) .

analyze-fixtures:
	docker run --rm -v "$(PWD)/fixtures/java_lib/main/java":/code -v "$(PWD)/fixtures/java_source_version/config_15.json":/config.json $(IMAGE_NAME)

test: image
	docker run --rm -ti -w /usr/src/app -u root $(IMAGE_NAME) gradle clean test
