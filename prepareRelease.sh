#!/bin/sh
read -sp "Enter passphrase: " pass
cp pom.xml target/agdt-java-generic-1.0.0-pom.xml
cd target/
echo "${pass}" | gpg --batch --no-tty --yes --passphrase-fd 0 --symmetric -o agdt-java-generic-1.0.0-pom.xml.asc agdt-java-generic-1.0.0-pom.xml
echo "${pass}" | gpg --batch --no-tty --yes --passphrase-fd 0 --symmetric -o agdt-java-generic-1.0.0.jar.asc agdt-java-generic-1.0.0.jar
echo "${pass}" | gpg --batch --no-tty --yes --passphrase-fd 0 --symmetric -o agdt-java-generic-1.0.0-javadoc.jar.asc agdt-java-generic-1.0.0-javadoc.jar
echo "${pass}" | gpg --batch --no-tty --yes --passphrase-fd 0 --symmetric -o agdt-java-generic-1.0.0-sources.jar.asc agdt-java-generic-1.0.0-sources.jar
