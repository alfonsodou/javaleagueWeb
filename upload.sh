#!/bin/sh
cp ./src/org/javahispano/javaleague/server/resources/templates/* ./war/WEB-INF/classes
$HOME/apps/eclipse/plugins/com.google.appengine.eclipse.sdkbundle_1.9.9/appengine-java-sdk-1.9.9/bin/appcfg.sh update $HOME/git/javaleagueWeb/war
