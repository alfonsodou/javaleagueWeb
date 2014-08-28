#!/bin/bash
if [$1 == ""] 
then 
cp ./src/org/javahispano/javaleague/server/resources/templates/* ./war/WEB-INF/classes
$HOME/apps/eclipse/plugins/com.google.appengine.eclipse.sdkbundle_1.9.9/appengine-java-sdk-1.9.9/bin/appcfg.sh update $HOME/git/javaleagueWeb/war
else 
$HOME/apps/eclipse/plugins/com.google.appengine.eclipse.sdkbundle_1.9.9/appengine-java-sdk-1.9.9/bin/appcfg.sh update_indexes $HOME/git/javaleagueWeb/war
$HOME/apps/eclipse/plugins/com.google.appengine.eclipse.sdkbundle_1.9.9/appengine-java-sdk-1.9.9/bin/appcfg.sh vacuum_indexes $HOME/git/javaleagueWeb/war
fi
