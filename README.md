# lthash
A Java implementation of LtHASH (Lattice Hash), a homomorphic hashing algorithm based on lattice cryptography introduced by Mihir Bellare and Daniele Micciancio in [this paper](https://cseweb.ucsd.edu/~mihir/papers/inchash.pdf). This is a simplified Java porting of the [Facebook Folly C++ Library](https://github.com/facebook/folly/tree/master/folly/experimental/crypto) implementation of the algorithm introduced [here](https://code.fb.com/security/homomorphic-hashing/).

## Homomorphic Hashing
A homomorphic hash can simplistically be defined as a hash function such that one can compute the hash of a composite block from the hashes of the individual blocks or rather being *f1* and *f2* two hash functions and *op1*, *op2* two operations it is true that:

```math
f1(a op1 b) = f2(a) op2 f2(b)
```

One of the main building blocks of a homomorphic hashing function is therefore an underlying hash function (our *f2*).<br />
This project depends on [this](https://github.com/alphazero/Blake2b) Java implementation of the [BLAKE2b](https://blake2.net/) cryptographic hash function.

## Example
```Java

LtHash32 ltHash = new LtHash32();

// Create an initial checksum of the values in input
ltHash.add("apple".getBytes(), "orange".getBytes());
byte[] checksum = ltHash.getChecksum();

// Remove the hash of "apple" from the checksum and check
// if the 2 checksums are equals
ltHash.remove("apple".getBytes());
boolean isEqual = ltHash.checksumEquals(checksum);

// Update the hash of "orange" with the new value "apple"
// and check if the 2 checksums are equals
ltHash.update("orange".getBytes(), "apple".getBytes());
isEqual = ltHash.checksumEquals(checksum);

// Adding again the missing "orange" and check if the
// checksum has gotten back to the initial status
ltHash.add("orange".getBytes());
isEqual = ltHash.checksumEquals(checksum);

```