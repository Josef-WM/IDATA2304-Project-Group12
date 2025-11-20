# IDATA2304-Project-Group12

To run the project, change directory into the StudentMadeGadgets
folder where the pom.xml file is located. Then, run the command:

```shell
mvn exec:java
```

Note that it is possible to run the Main class directly from an IDE
such as IntelliJ IDEA, but in these cases the ansi escape codes does
not always get interpreted correctly. (We use the ansi escape codes
to do things such as clear the screen and change text colours.)


When the program is running, you'll get a menu where you can choose to either start the server, or run the control panel.
The control panel needs to connect to a server, so start the server if you didn't already.

The default way to test the program is run the server, and then run the control panel in another terminal (all on the same machine). When the control panel asks for the host IP, leave it empty as it will set it to localhost (127.0.0.1)


Do note that the server can also be ran on another machine in the same network; if this is the case, the control panel will need the ip address of the server machine in the network.
