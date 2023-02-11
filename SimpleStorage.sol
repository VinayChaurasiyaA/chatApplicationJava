//SPDX-License-Identifier: MIT 
pragma solidity 0.8.7; // in solidity firstly we have to write which version we want to use ^ will help to use any version which are greater than or equal to 0.8.7 or we could use >=
// sometimes SPDX are mandatory as some of the complier are weak and they need that

// below is the contract it works as the java class 
contract SimpleStorage {
    // some basic types of primitive data type: boolean, uint : only positive number, int , bytes , address:transaction address/ account
    uint public favoutite = 123; // unit(256) or anything shows the storage capacity in bits, if we provide no values after uint then it considers as 256
    string myName = "vinay";
    address myAddress = 0x179177ad055A9C5647B38b6dE2C3088eE8f79F53;    
    bytes name = "catty"; // bytes maximum1 storage is 32 so we can only type bytes32;
    // here public means the visibility
    function store (uint _favouriteNumber) public {
        favoutite = _favouriteNumber;
    }
}
//0xd9145CCE52D386f254917e481eB44e9943F39138