# Author: Leon Föckersperger

FROM markhobson/maven-chrome:jdk-21

USER root

# Install Firefox
RUN apt-get update && \
    apt-get install -y firefox && \
    apt-get clean

# Install Microsoft Edge
RUN apt-get update && \
    apt-get install -y wget gnupg && \
    wget -qO- https://packages.microsoft.com/keys/microsoft.asc | gpg --dearmor > microsoft.gpg && \
    mv microsoft.gpg /etc/apt/trusted.gpg.d/microsoft.gpg && \
    echo "deb [arch=amd64] https://packages.microsoft.com/repos/edge stable main" > /etc/apt/sources.list.d/microsoft-edge-dev.list && \
    apt-get update && \
    apt-get install -y microsoft-edge-dev && \
    apt-get clean

# Create a test user
RUN groupadd --system --gid 1000 test && \
    useradd --system --gid test --uid 1000 --shell /bin/bash --create-home test && \
    chown -R test:test /root && \
    chmod -R u+w /root

USER test
WORKDIR /home/test



