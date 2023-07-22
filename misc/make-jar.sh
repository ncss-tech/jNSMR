/usr/lib/jvm/java-8-openjdk/bin/javac -Xlint:unchecked -cp $CLASSPATH:../java/ -d misc/classes java/org/psu/*/*.java java/org/psu/*/*/*.java
jar cvmf misc/classes/META-INF/MANIFEST.MF inst/java/foo.jar -C misc/classes .

