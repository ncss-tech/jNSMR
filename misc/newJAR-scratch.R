# rJava setup
rJava::.jinit()
# rJava::.jpackage(pkgname, lib.loc = libname)

# newhall JAR setup: add to class path
rJava::.jaddClassPath("/home/andrew/R/x86_64-pc-linux-gnu-library/4.1/jNSMR/java/newhall-1.6.2.jar")
rJava::.jclassPath()

nh <- rJava::.jnew("org/psu/newhall/Newhall")
nh$NSM_VERSION
