# GooseChain: Learning DSA and Proof of Work

## How to Run This
Import the project into your favorite IDE. This was developed and tested in IntelliJ and should run out of the box with the default import settings. It should work in Eclipse as well.

You required JDK 1.8 or greater. You also need to have gradle (which is the build tool) installed.

This project uses the GSON library to serialize data to JSON files for storage. It will store all output JSON data in a folder called `metadata/`.

It also uses JUnit5 for Unit Testing. Both these dependencies should be installed by gradle when you open the project in an IDE for the first time.

To run it simply navigate to `goosechain.core.GooseChain` and run the main function.

For running the Unit Tests which include the validation of parameters and a sample DSA routine to show off our algorithm, please refer to the section below.

## The Source Directory Structure
If you ran the linux `tree` command on the `src/` folder you would see:

```
    src
    ├── main
    │   ├── java
    │   │   └── goosechain
    │   │       ├── core
    │   │       │   ├── Block.java
    │   │       │   ├── GooseChain.java
    │   │       │   └── User.java
    │   │       ├── crypto
    │   │       │   ├── DSA
    │   │       │   │   ├── DSAKCalculator.java
    │   │       │   │   ├── DSAKeyPair.java
    │   │       │   │   ├── DSAKeyPairGenerator.java
    │   │       │   │   ├── DSAParameters.java
    │   │       │   │   ├── DSASignature.java
    │   │       │   │   └── DSASigner.java
    │   │       │   └── digests
    │   │       │       ├── Digest.java
    │   │       │       ├── DigestFactory.java
    │   │       │       ├── ExtendedDigest.java
    │   │       │       ├── KeccakDigest.java
    │   │       │       └── SHA3Digest.java
    │   │       └── utils
    │   │           ├── Arrays.java
    │   │           ├── DSAUtils.java
    │   │           ├── Hex.java
    │   │           ├── Pack.java
    │   │           └── SHA3Utils.java
    │   └── resources
    │       └── aesops_fables.txt
    └── test
        ├── java
        │   └── goosechain
        │       ├── crypto
        │       │   ├── DSA
        │       │   │   └── DSAParametersTest.java
        │       │   └── digests
        │       │       └── KeccakDigestTest.java
        │       └── utils
        │           ├── DSAUtilsTest.java
        │           ├── HexTest.java
        │           └── SHA3UtilsTest.java
        └── resources
```
There are 3 distinct packages under the goosechain package:

1. **core**: contains all the code that ties everything together. The main() function can be found in GooseChain.java. Running this will start a REPL that runs the project and produces an output.
2. **crypto**: contains our DSA implementation as well as the code for SHA3
3. **utils**: contains all the utility helper classes in the project.

There is also a `test/` folder. The DSAParamatersTest.java file performs a Unit Test to validate the given System Parameters.

The DSAUtilsTest and SHA3UtilsTest show off the the DSA and SHA3 functionality in this project.
