# Low Level System Design - Parking lot 

### Problem Statement 
[Check here](problem-statment.md)

Please write the code for a parking app under the following conditions:

1. When the car comes in, check if there is a space and allocate space if available, otherwise give message saying full.   Let us assume the car park size is 100.
2. When the car leaves, calculate the time spent and charge £2 per hour.  We don’t need the payment module but just returning the amount is enough.
3. Handle multiple cars coming at the same time.

### Assumptions
* Currently parking lot supports single threaded operations
* After one minute of parking, full hour charge is applicable.
* Slots are allocated on sequential basis and no selections are supported
* Multiple cars are accepted at entry but slot allocation is still sequential.

### Project Technology Requirements
* JDK 1.8
* Maven
* For Unit Tests:  
  * Junit 4
  * Mockito

### Compiling/Building and running the unit tests
Go to the project root folder and then run: ./bin/setup.sh

### Runing the project
NOTE: Before running, please make sure you do the above setup step. Otherwise it will not run. 
The project can be run as follows in one of the two ways:

1) **Using Interactive based input:**: This will run the program in the interactive shell mode where commands can be typed in.
./bin/parking_lot.sh or run com.detillens.Main and input commands manually from file file_input.txt

2) **Using file based input:**: It accepts a filename as a parameter at the command prompt and read the commands from that file.   
  ./bin/parking_lot.sh  <input_filepath>  
 Example: ./bin/parking_lot.sh  ./file_input.txt
  

### Further Enhancements:

* Dependency injection: Currently dependencies are injected manually. We can use some 
dependency injection framework like spring. 
* Currently system is single threaded but can be made multi threaded to simulate actual 
environment but then need to make sure slots are being locked, while parking & leaving.
* Exit command: Exit command is currently coupled with interactive mode only which makes
it non-reusable.
* Parking strategy: Parking strategy is currently associated with `ParkingLotService`. 
Instead of that, it makes more sense to associate it with `ParkingLot`.
* Mode: Mode checking is currently done in main function directly. There could be a
factory for that.
