# Author: Leon Föckersperger

FROM markhobson/maven-chrome:jdk-21

USER root

# Install Firefox
RUN apt-get update && \
    apt-get install -y --no-install-recommends firefox && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Install Microsoft Edge
RUN apt-get update && \
    apt-get install -y --no-install-recommends wget gnupg && \
    wget -qO- https://packages.microsoft.com/keys/microsoft.asc | gpg --dearmor > /etc/apt/trusted.gpg.d/microsoft.gpg && \
    echo "deb [arch=amd64] https://packages.microsoft.com/repos/edge stable main" > /etc/apt/sources.list.d/microsoft-edge-dev.list && \
    apt-get update && \
    apt-get install -y --no-install-recommends microsoft-edge-dev && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Create a test user
RUN groupadd --system --gid 1000 test && \
    useradd --system --gid test --uid 1000 --shell /bin/bash --create-home test && \
    chown -R test:test /home/test

USER test
WORKDIR /home/test



