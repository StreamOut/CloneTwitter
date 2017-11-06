#!/bin/bash
mvn package
rm -Rf /opt/apache-tomcat-8.5.23/webapps/cloneTwitter
rm /opt/apache-tomcat-8.5.23/webapps/cloneTwitter.war
cp target/cloneTwitter.war /opt/apache-tomcat-8.5.23/webapps
cd /opt/apache-tomcat-8.5.23/bin
./startup.sh

