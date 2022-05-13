## Build project
```
mvn clean install
```
## Run project

The directory `samples` contains various input files that can be used to
generate data.

Generate data using a simple file and default parameters.
```
cat samples/input1.txt | java -cp target/datagenerator-1.0-SNAPSHOT.jar com.github.zabetak.datagenerator.Generator
```

Generate data using a simple file and specifying the total number of rows to.
```
cat samples/input1.txt | java -cp target/datagenerator-1.0-SNAPSHOT.jar com.github.zabetak.datagenerator.Generator 10
```

Generate data using a simple file and overriding the default properties.
```
cat samples/input1.txt | java -cp target/datagenerator-1.0-SNAPSHOT.jar -Ddgen.string.length=2 com.github.zabetak.datagenerator.Generator
```

Generate data using a file combining standard and custom column generators.
```
cat samples/input2.txt | java -cp target/datagenerator-1.0-SNAPSHOT.jar com.github.zabetak.datagenerator.Generator
```