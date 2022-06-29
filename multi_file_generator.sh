#!/bin/bash

mkdir data
for i in {1..1000} ; do
    cat samples/input2.txt | java -cp target/datagenerator-1.0-SNAPSHOT.jar -Ddgen.string.length=4 com.github.zabetak.datagenerator.Generator 59164 > data/file$i
done
