# apacheslingkeysmigration
Apache Sling Keys Migration

### build it
gradle clean build

### play
java -cp build/libs/apacheslingkeysmigration-1.0-SNAPSHOT.jar net.marco27.api.sling.SlingKeyMigration /Users/marco27/dev/PFNEO/pf-neo/pf-neo-components/pf-neo-components-package/src/main/jcr_root/apps/pfch/components/i18n/en.xml /Users/marco27/dev/myStash/pf-neo/pf-neo-components/pf-neo-components-package/src/main/jcr_root/apps/pfch/website/i18n/en.xml

###### convert this
    <pfch.pages.homepage.description
            jcr:mixinTypes="[sling:Message]"
            jcr:primaryType="nt:folder"
            sling:message="Description Home Page"/>

##### into this
    <pfch.pages.homepage.description
            jcr:primaryType="sling:MessageEntry"
            sling:key="pfch.pages.homepage.description"
            sling:message="Description Home Page"/>
