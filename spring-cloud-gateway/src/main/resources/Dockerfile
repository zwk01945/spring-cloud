FROM java:8
VOLUME /tmp
ADD *.jar gateway.jar
EXPOSE 9015
ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/gateway.jar" ]