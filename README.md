## Build project

mvn clean install -Dcheckstyle.skip
## Run project

### Sample input

A column datatype pair on each line separated by spaces. For example:

```
w_id_col    int,
w_char_col0 char(20),
w_char_col1 char(20),
w_char_col2 char(20),
w_char_col3 char(20),
w_char_col4 char(20),
w_char_col5 char(20),
w_char_col6 char(20),
w_char_col7 char(20),
w_char_col8 char(20),
```

### Command
cat /path/to/input.txt | java -cp target/datagenerator-1.0-SNAPSHOT.jar com.github.zabetak.datagenerator.Generator 1000
