This project consists of JAVA_STUBS which can be used to provide xml responses to any xml requests.
-This is a JAVA based project
-I have created 3 sample stubs
- The main usage of this project is to read XML messages from the input queue and store them in your local 
system and provide the xml responses to those XML requests
-User has to provide their project-specific  below MQ details:
       Host, QueueManager, Port ,Channel ,InputQueue, OutputQueue
-Also user has to add their own project-specific XML response in the variable OUT
-User has to configure their local system folder path where they want to store their XML requests


NOTE: I have used dummy MQ data in the code, hence it will not work. User has to configure their own project specific
MQ details in this code in order to see the output
