# apacheslingkeysmigration
Apache Sling Keys Migration

# Prerequisites
- Java 8
- Gradle >= 6.1

# Build
gradle clean test
gradle clean build

# Deploy
-

# Play
java -cp build/libs/apacheslingkeysmigration-1.0.0-SNAPSHOT.jar net.marco27.api.sling.SlingKeyMigration /Users/marcoguastalli/temp/old.xml /Users/marcoguastalli/temp/new.xml
java -cp build/libs/apacheslingkeysmigration-1.0.0-SNAPSHOT.jar net.marco27.api.sling.SlingKeyFromXmlToJson /Users/marcoguastalli/dev/PFNEO/pf-neo/pf-neo-components/pf-neo-components-package/src/main/jcr_root/apps/pfch/website/i18n/en.xml /Users/marcoguastalli/dev/PFNEO/pf-neo/pf-neo-components/pf-neo-components-package/src/main/jcr_root/apps/pfch/website/i18n/en.json
java -cp build/libs/apacheslingkeysmigration-1.0.0-SNAPSHOT.jar net.marco27.api.sling.OrderJsonKeys /Users/marcoguastalli/dev/PFNEO/pf-neo/pf-neo-components/pf-neo-components-package/src/main/jcr_root/apps/pfch/website/de.json /Users/marcoguastalli/dev/PFNEO/pf-neo/pf-neo-components/pf-neo-components-package/src/main/jcr_root/apps/pfch/website/de2.json
