.PHONY: image test release

IMAGE_NAME ?= codeclimate/codeclimate-sonar-java
RELEASE_REGISTRY ?= codeclimate

ifndef RELEASE_TAG
override RELEASE_TAG = latest
endif

image:
	docker build --rm -t $(IMAGE_NAME) .

analyze-fixtures:
	docker run --rm -v "$(PWD)/fixtures/java_lib/main/java":/code -v "$(PWD)/fixtures/java_source_version/config_15.json":/config.json $(IMAGE_NAME)

test: image
	docker run --rm -ti -w /usr/src/app -u root $(IMAGE_NAME) gradle clean test

release:
	docker tag $(IMAGE_NAME) $(RELEASE_REGISTRY)/codeclimate-sonar-java:$(RELEASE_TAG)
	docker push $(RELEASE_REGISTRY)/codeclimate-sonar-java:$(RELEASE_TAG)
