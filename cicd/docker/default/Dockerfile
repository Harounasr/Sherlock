# Author: Leon Föckersperger

FROM maven:3.9.5-eclipse-temurin-21

RUN apt-get update && \
    apt-get install -y --no-install-recommends zip=3.0-13 && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

RUN groupadd --system --gid 1000 test && \
    useradd --system --gid test --uid 1000 --shell /bin/bash --create-home test && \
    chown -R test:test /home/test

USER test
WORKDIR /home/test
