# moba-server
My moba game server implementation

This is a MOBA(Multiplayer Online Battle Arena) game server implementation to showcase my skills and 
a way to integrate new things I will learn in the future to keep improving the product.

The server will be implemented mostly in java, but different components communicate using TCP/IP, 
so performance critical parts can be implemented using C/C++/Golang/whatever that fits.

Tech spec now includes:
netty: to handle connection management and network io
talky: to automate rpc related code generation
mysql: current choice of datastore
redis: in memory datastore for performance

There are several key elements in the dev process:
unittest: 
	as part of the HEALTHY dev process, unittest will be carried out the entire process 
monitoring: 
	monitorable is a key feature provided by this implementaion
data visualization: 
	data visualization will be a first class citizen in the dev process, since this is the effect way to comminicate info among team members
cost analysis:
	based on the implementation, cost to run this project will be provided(estimated) to view the game from a business' point of way
CI:
	travis will be used for CI
build:
	gradle will be used


Stategy to allocate ports for different servers are:
db server: [3000, 3999]
login server: [4000, 4999]
match server: battle[5000, 5499] login [5500, 5999]
battle server: [6000, 6999]