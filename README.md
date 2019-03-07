# lthash
A Java implementation of LtHASH (Lattice Hash), a homomorphic hashing algorithm based on lattice cryptography introduced by Mihir Bellare and Daniele Micciancio in [this paper](https://cseweb.ucsd.edu/~mihir/papers/inchash.pdf). This is a simplified Java porting of the [Facebook Folly C++ Library](https://github.com/facebook/folly/tree/master/folly/experimental/crypto) implementation of the algorithm introduced [here](https://code.fb.com/security/homomorphic-hashing/).

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