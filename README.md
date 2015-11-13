# movie-api
Core Movie API

## Setup and Build Notes

### Configuring JRE
movie-api requires JRE1.8. If you have multiple JRE's installed you can configure maven to use the correct one by setting your `~/.m2/settings.xml`. E.g.

```
<settings>
  <profiles>
  <profile>
      <id>compiler</id>
      <properties>
        <JAVA_1_8_HOME>C:\Program Files\Java\jdk1.8.0_60</JAVA_1_8_HOME>
      </properties>
    </profile>
  </profiles>
  <activeProfiles>
    <activeProfile>compiler</activeProfile>
  </activeProfiles>
</settings>
```

The POM expects `JAVA_1_8_HOME` to be defined via `settings.xml`. If Jave 8 is your default JRE or default JRE or if you're building via Eclipse and selecting the JRE from inside Eclipse, you comment out this block in pom.xml. 
```
 	<plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.3</version>
        <configuration>
          <verbose>true</verbose>
          <fork>true</fork>
          <executable>${JAVA_1_8_HOME}/bin/javac</executable>
          <compilerVersion>1.8</compilerVersion>
        </configuration>
    </plugin>	
```

### Custom Katharsis build
This project requires a custom build of katharsis-core and katharsis-servlet (2.0.0-FLIXSTER). I'm in conversation with the maintainers to do an official PR but until then the pom is wired up to use the pre-built custom repo at `https://raw.githubusercontent.com/flixster/mvn-repo/master`. No further action is necessary.

### Set up DB tunnel to RDS DB
Account on jump server required (jump.aws.prod.flixster.com)
    
Edit your `~/.ssh/config`

```    
Host jump
  User peterl 
  HostName jump.aws.prod.flixster.com
  LocalForward 10001 rds-shared-slave.cfzxgxfxhefu.us-west-2.rds.amazonaws.com:3306
``` 
After setting this up, establish your tunnel by connecting to the jump server `ssh jump`

### How to run with Maven
``` 
    $ mvn clean compile
    $ mvn spring-boot:run
```
### How to test with curl
```
    $ curl -v http://localhost:8080/movie/9
```


