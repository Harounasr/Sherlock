# Author: Leon Föckersperger

FROM maven:3.9.5-eclipse-temurin-21

RUN apt-get update && apt-get install -y --no-install-recommends zip=3.0-12build2 && rm -rf /var/lib/apt/lists/*

RUN groupadd --system --gid 1000 test
RUN useradd --system --gid test --uid 1000 --shell /bin/bash --create-home test
RUN chown -R test:test /root
RUN chmod -R u+w /root

USER test
WORKDIR /home/test
