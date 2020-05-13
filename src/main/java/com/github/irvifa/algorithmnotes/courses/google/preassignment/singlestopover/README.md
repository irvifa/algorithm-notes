Google use case: Google Flights.

-----

There are airports with direct flights between them. Find all flights from an origin to a destination airports with exactly one stopover between them and print all found stopovers sorted lexicographically from smallest to largest.

Input
-----
The fist line contains N, the number of direct flights. 0 < N < 1000000. Then the subsequent N lines contain two words per line, both ASCII-only and case sensitive. The first word is the origin airport and the second word is the destination airport of the flight. Please note that flights are one-way, maybe there is no flight in the opposite direction. Flights can be listed in any order, but without duplicates.

The next line contains M, the number of trips. 0 < M < 1000000. Then the subsequent M lines contain two words per line, both ASCII-only and case sensitive. The first word is the origin airport and the second word is the final destination airport of the trip. In addition to these airports, the trip contains a single stopover airport in-between. Trip origin and destination can be the same.

Each airport name is at most 4 characters long. it is ASCII only, it may not contain whitespace, and it is case sensitive.

Output
------
There is one output line for each trip. The line contains a space-separated list of possible stopover airports, in ASCII code lexicographical order.

Example
-------
Input: 
 
8
AMS ZRH
SFO NYC
NYC ZRH
SFO ZRH
SFO AMS
NYC AMS
NYC MUC
MUC ZRH
6
SFO ZRH
ZRH SFO
AMS ZRH
x ZRH  
SFO y  
NYC ZRH

Output:

AMS NYC




AMS MUC

Explanation:

For the SFO --> ? --> ZRH trips, AMS and NYC are stopovers. It's also possible to fly SFO --> NYC --> MUC --> ZRH, but those trips contain more than 1 stopover, thus they are ignored.

There is no ZRH --> ? --> SFO trip, even though there is one in the opposite direction.

There is no AMS --> ? --> ZRH trip, even though there is an AMS
--> ZRH flight.

The airports x and y don't exist, thus there are no trips with them.

For the NYC --> ? --> ZRH trips, AMS and MUC are stopovers.