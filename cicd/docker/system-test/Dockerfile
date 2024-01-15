# Author: Leon Föckersperger

FROM markhobson/maven-chrome:jdk-21

USER root

# Set SHELL to bash with pipefail option
SHELL ["/bin/bash", "-o", "pipefail", "-c"]

# Replace with your desired locale
ENV LANG de_DE.UTF-8
ENV LANGUAGE de_DE:de

# Install locales and generate desired locale
RUN apt-get update && \
    apt-get install -y locales && \
    sed -i '/de_DE.UTF-8/s/^# //g' /etc/locale.gen && \
    locale-gen

# Install Firefox (Version 121.0)
RUN apt-get update && \
    apt-get install -y firefox && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Install Edge
RUN apt-get update && apt-get install -y wget gnupg libatomic1 alien \
    && wget -nv https://packages.microsoft.com/yumrepos/edge/Packages/m/microsoft-edge-stable-120.0.2210.91-1.x86_64.rpm \
    && alien -i microsoft-edge-stable-120.0.2210.91-1.x86_64.rpm \
    && rm -f microsoft-edge-stable-120.0.2210.91-1.x86_64.rpm \
    && apt-get clean



# Create a test user
RUN groupadd --system --gid 1000 test && \
    useradd --system --gid test --uid 1000 --shell /bin/bash --create-home test && \
    chown -R test:test /home/test

USER test
WORKDIR /home/test
