## Info

This contains java code to read H264 files, and send them using the
BPP packet format.
The receiver can print data or reconstruct a valid H264 stream to
save.

These programs can be used with an external virtualisation platform
which can enact BPP behaviours to drop chunks under certain network
conditions.\
Examples include: [VLSP](https://github.com/stuartclayman/VLSP) The
Very Lightweight Network & Service Platform.


### Packages

**app**  Application entry points\
**bpp**  BPP definitions\
**chunk**  Chunk processing\
**h264**  H264 definitions, parsing NALs, and input stream\
**net**  BPP packetizing and UDP netowrking\
**processor**  NALs --> chunks and chunks --> NALs\
**terminal**  Terminal output functions\
**util** Util functions


## Useful interations

H264Parse  _Prints out info on an H264 file_

BPPSend  -->   BPPListen   _Prints out BPP received info_

BPPSend  -->   H264Listen  _Reconstructs H264 video from BPP packets, and outputs to a file_

UDPSend  -->   UDPListen   _Outputs data from UDP packets into a file_


## Usage

All apps execute from the entry package: cc.clayman.app







Some examples, plus arguments.


`java cc.clayman.app.H264Parse -f ~/tmp/foreman.264`

 H264Parse [-q] [-f filename]\
 _Print out details of an H264 file, as NALs_\
**-f** filename Read data from file.  Use - for stdin.  Default: stdin\
**-q** Print qualityLayer info.  Default: false



`java cc.clayman.app.BPPListen -p 6798`

BPPListen [-p port] [-c cols]\
_Listen for BPP packets_\
**-p** port Listen port.  Default: 6799\
**-c** cols No of columns for output.  Default: 80


`java cc.clayman.app.H264Listen  -f ~/tmp/sc_0.6mbps.264 -q -p 6798`

H264Listen [-f filename] [-p port] [-q]\
_Listen for BPP packets and construct an H264 file_\
**-f** filename Send data to file.  Use - for stdout.  Default: stdout\
**-p** port Listen port.  Default: 6799\
**-q** Print qualityLayer info.  Default: false


`java cc.clayman.app.BPPSend -f filename`

BPPSend [-s sleep|-r rate] [-f filename] [-p port]  [-z packetSize] [-Pe|-Pd|-Pi|-Pf]\
_Send a video file over BPP_\
**-f** filename Read data from file.  Use - for stdin.  Default: stdin\
**-p** port Send port.  Default: 6799\
**-z** Packet size.  Default: 1500\
**-r** rate No of packets per second\
**-s** inter packet sleep (in milliseconds).  Default: 7\
**-a** Do adaptive sleep.  Based on chunk size and packet size.  Default: false\
**-c** cols No of columns for output.  Default: 80\
**-Pe** Even packing strategy.  Default: EvenSplit\
**-Pd** Dynamic packing strategy\
**-Pi** In Order packing strategy\
**-Pf** In Order Fully packed packing strategy


`java cc.clayman.app.UDPListen -p 6798`

UDPListen [-f filename] [-p port]\
_Listen for UDP packets_\
**-f** filename Send data to file.  No Default\
**-p** port Listen port.  Default: 6799\
**-c** cols No of columns for output.  Default: 80


`java cc.clayman.app.UDPSend  -f filename`

UDPSend [-s sleep] [-f filename] [-p port] [-z packetSize]\
_Send a video file over UDP_\
**-s** inter packet sleep (in milliseconds).  Default: 7\
**-f** filename Read data from file.  Use - for stdin.  Default: stdin\
**-p** port Send port.  Default: 6799\
**-z** Packet size.  Default: 1500\
**-c** cols No of columns for output.  Default: 80





All commands support verbose output, at different levels:\
**-v** Verbose level 1\
**-vv** Verbose level 2\
**-vvv** Verbose level 3
