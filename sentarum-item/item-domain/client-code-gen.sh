#!/usr/bin/env bash
mvn package -DskipTests=true -P client-gen
mvn source:jar -DskipTests=true -P client-gen
mvn deploy:deploy-file -Durl=https://maven.pkg.github.com/martinmao/sentarum  -Dfile=target/item-client-0.0.1-SNAPSHOT.jar -Dsources=target/item-client-0.0.1-SNAPSHOT-sources.jar -DgroupId=org.scleropages.sentarum -DartifactId=item-client -Dversion=0.0.1-SNAPSHOT -DrepositoryId=github