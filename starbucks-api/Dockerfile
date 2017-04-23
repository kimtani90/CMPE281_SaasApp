FROM openjdk


# Install Grails
ENV GRAILS_VERSION 3.0.6
WORKDIR /usr/lib/jvm
RUN wget https://github.com/grails/grails-core/releases/download/v${GRAILS_VERSION}/grails-${GRAILS_VERSION}.zip && \
    unzip grails-${GRAILS_VERSION}.zip && \
    rm -rf grails-${GRAILS_VERSION}.zip && \
    ln -s grails-${GRAILS_VERSION} grails

ENV GRAILS_HOME /usr/lib/jvm/grails
ENV PATH $GRAILS_HOME/bin:$PATH

CMD gradle bootRun


