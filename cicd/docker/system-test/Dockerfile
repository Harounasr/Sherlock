# Author: Leon Föckersperger

FROM markhobson/maven-chrome:jdk-21

USER root

# Set SHELL to bash with pipefail option
SHELL ["/bin/bash", "-o", "pipefail", "-c"]

# Install Firefox (Version 120.0.1-1)
RUN apt-get update && \
    apt-get install -y --no-install-recommends firefox=121.0 && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Install Microsoft Edge (Version 119.0.2151.97-1)
RUN apt-get update && \
    apt-get install -y --no-install-recommends wget=2.1.0-2 gnupg=2.4.3 && \
    wget -qO- https://packages.microsoft.com/keys/microsoft.asc | gpg --dearmor > /etc/apt/trusted.gpg.d/microsoft.gpg && \
    echo "deb [arch=amd64] https://packages.microsoft.com/repos/edge stable main" > /etc/apt/sources.list.d/microsoft-edge-dev.list && \
    apt-get update && \
    apt-get install -y --no-install-recommends microsoft-edge-dev=120.0.2210.121 && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Install locales and generate desired locales
RUN apt-get update && \
    apt-get install -y --no-install-recommends locales && \
    echo "en_US.UTF-8 UTF-8" >> /etc/locale.gen && \
    echo "de_DE.UTF-8 UTF-8" >> /etc/locale.gen && \
    locale-gen


# Create a test user
RUN groupadd --system --gid 1000 test && \
    useradd --system --gid test --uid 1000 --shell /bin/bash --create-home test && \
    chown -R test:test /home/test

USER test
WORKDIR /home/test
