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

## Custom column generators

The generated values for a column can be fully controlled by using custom column generators.
If the existing column generators are not sufficient for your needs you can create your own by implementing the `ColumnGenerator` interface.
The following sub-sections outline a few useful custom generators with sample input and output.

### [EnumColumnGenerator](src/main/java/com/github/zabetak/datagenerator/EnumColumnGenerator.java)

#### Config file
```
department custom com.github.zabetak.datagenerator.EnumColumnGenerator COMPUTER_SCIENCE MATH PHYSICS LITERATURE
```
#### Command
```
 cat samples/input3.txt | java -cp target/datagenerator-1.0-SNAPSHOT.jar com.github.zabetak.datagenerator.Generator 10
```
#### Output
```
LITERATURE
COMPUTER_SCIENCE
LITERATURE
PHYSICS
MATH
COMPUTER_SCIENCE
COMPUTER_SCIENCE
PHYSICS
MATH
MATH
```