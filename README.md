# SynthLab Project - Group C

Branch dev : [![Build Status](https://travis-ci.com/Kwodhan/SynthLabC.svg?token=6zdxF4MUzxXu29Z8EQzC&branch=dev)](https://travis-ci.com/Kwodhan/SynthLabC) Branch master : [![Build Status](https://travis-ci.com/Kwodhan/SynthLabC.svg?token=yEvcBv9NYXmyDfvx1xFm&branch=master)](https://travis-ci.com/Kwodhan/SynthLabC)

## Description
SynthLabC is a graphical application dealing with sound synthesis. It allows the synthesis of audio streams by using a set of audio processing modules. It is possible to interconnect several modules and to set them.

## Usage 
In terminal, in the root directory of the project : ``` java -jar SynthLabC.jar ```

## Documentation
#### Javadoc
The javadoc has already been generated and is located in the ```javadoc``` repository (Opening the ```index.html``` file in a browser). 

It can also be generated directly from the project with the following instructions.
In terminal, in the root directory of the project, run ```mvn javadoc:javadoc```.
The html files of the javadoc will be generated in the target/site/apidocs repository. Open the ```index.html``` file in a browser.

## Testing

To perform the tests, you need to run ```mvn test``` in the root directory of the project. 

## Coverage 
The coverage report has already been generated and is located in the ```cobertura``` repository (Opening the ```index.html``` file in a browser).

To generate the coverage report from the project, run ```mvn cobertura:cobertura``` in the root directory of the project.


## Authors

Chakib Benkebir,
June Benvegnu-Sallou,
Antoine Ferey,
Emmanuel Loisance,
Christophe Planchais,
Youssouf Roudani
