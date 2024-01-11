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
    apt-get install -y --no-install-recommends firefox=121.0 && \
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
    rm -rf /var/lib/apt/lists/* \

RUN wget -O /usr/lib/firefox/browser/extensions/langpack-de@firefox.mozilla.org.xpi "https://addons.mozilla.org/firefox/downloads/file/xxxxxx/deutsch-de-language-pack-latest.xpi"

RUN echo 'pref("intl.locale.requested", "de_DE");' >> /usr/lib/firefox/defaults/pref/autoconfig.js

# Create a test user
RUN groupadd --system --gid 1000 test && \
    useradd --system --gid test --uid 1000 --shell /bin/bash --create-home test && \
    chown -R test:test /home/test

USER test
WORKDIR /home/test
