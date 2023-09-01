## Problem Statement

Please write the code for a parking app under the following conditions:

1. When the car comes in, check if there is a space and allocate space if available, otherwise give message saying full.   Let us assume the car park size is 100.
2. When the car leaves, calculate the time spent and charge £2 per hour.  We don’t need the payment module but just returning the amount is enough.
3. Handle multiple cars coming at the same time.
   
### Example: Interactive
Assuming a parking lot with 6 slots, the following commands should be run in
sequence by typing them in at a prompt and should produce output as described
below the command. Note that `exit` terminates the process and returns control to
the shell.

$ create_parking_lot 6  
Created a parking lot with 6 slots  

$ park KA-01-HH-1234 White  
Allocated slot number: 1  

$ park KA-01-HH-9999 White KA-01-BB-0001 Black KA-01-HH-7777 Red
Allocated slot number: 4  

$ park KA-01-HH-2701 Blue KA-01-HH-3141 Black 
Allocated slot number: 6  

$ leave 4  
Slot number 4 is free & charges are 0.0  

$ status  
Slot No. Registration No Colour  
1 KA-01-HH-1234 White  
2 KA-01-HH-9999 White  
3 KA-01-BB-0001 Black  
5 KA-01-HH-2701 Blue  
6 KA-01-HH-3141 Black  

$ park KA-01-P-333 White  
Allocated slot number: 4  

$ park DL-12-AA-9999 White  
Sorry, parking lot is full  

$ slot_number_for_registration_number KA-01-HH-3141  
6  

$ slot_number_for_registration_number MH-04-AY-1111  
Not found  

$ exit
