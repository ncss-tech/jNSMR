/usr/lib/jvm/openlogic-openjdk-8-hotspot-amd64/bin/javac -Xlint:unchecked -cp $CLASSPATH:../java/ -d misc/classes java/org/psu/*/*.java java/org/psu/*/*/*.java
jar cvmf misc/classes/META-INF/MANIFEST.MF inst/java/foo.jar -C misc/classes .

