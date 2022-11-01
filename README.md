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

## [FixedDigitColumnGenerator](src/main/java/com/github/zabetak/datagenerator/FixedDigitColumnGenerator)

#### Config file
```
postal_code custom com.github.zabetak.datagenerator.FixedDigitColumnGenerator 3 PO_78 _FR
```
#### Command
```
 cat samples/input4.txt | java -cp target/datagenerator-1.0-SNAPSHOT.jar com.github.zabetak.datagenerator.Generator 10
```
#### Output
```
PO_78232_FR
PO_78156_FR
PO_78584_FR
PO_78470_FR
PO_78523_FR
PO_78149_FR
PO_78129_FR
PO_78479_FR
PO_78716_FR
PO_78740_FR
```

## [SkewedIntColumnGenerator](src/main/java/com/github/zabetak/datagenerator/SkewedIntColumnGenerator)

### Example with custom weights

#### Config file
```
integers custom com.github.zabetak.datagenerator.SkewedIntColumnGenerator 4 0.9 0.05 0.03 0.02
```
#### Command
```
 cat samples/input5.txt | java -cp target/datagenerator-1.0-SNAPSHOT.jar com.github.zabetak.datagenerator.Generator 10
```
#### Output
```
3232
3232
3232
2584
3232
3232
3232
2584
3232
3232
```

### Example with default weights [0.8 0.1 0.05 0.03 0.02]

#### Config file
```
integers custom com.github.zabetak.datagenerator.SkewedIntColumnGenerator 4
```
#### Command
```
 cat samples/input5.txt | java -cp target/datagenerator-1.0-SNAPSHOT.jar com.github.zabetak.datagenerator.Generator 10
```
#### Output
```
3232
3232
3232
3232
8156
3232
3232
3232
3232
3232
```