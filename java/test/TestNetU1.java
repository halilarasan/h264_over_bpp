package test;

import java.nio.ByteBuffer;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.List;

import cc.clayman.h264.*;
import cc.clayman.chunk.*;
import cc.clayman.processor.*;
import cc.clayman.net.*;

// A first test of the UDPSender
// With a Raw packetizer
public class TestNetU1 {

    static UDPSender sender = null;
    static int sleep = 7;       // default sleep (in milliseconds) between chunks
    static ChunkPacketizer packetizer = null;

    public static void main(String[] args) {
        if (args.length == 1) {
            String filename = args[0];
            
            try {
                processFile(filename);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } else if (args.length >= 2) {
            // have flags too

            int argc = 0;

            while (argc < args.length-1) {
                String arg0 = args[argc];

            
                if (arg0.equals("-s")) {
                    // Sleep (in milliseconds) between chunks
                    argc++;

                    String val = args[argc];
                    sleep = Integer.parseInt(val);
                    argc++;
             
                } else {
                    usage();
                }
            }
            
            String filename = args[argc];
            
            try {
                processFile(filename);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } else {
            usage();
        }
    }

    static void usage() {
        System.err.println("TestNetU1 [-s sleep] filename");
        System.exit(1);
    }


    protected static void processFile(String filename) throws IOException {
        // Setup UDP Sender
        sender = new UDPSender("localhost", 6799);
        sender.start();
        

        int count = 0;
        int total = 0;
        SVCChunkInfo chunk = null;
        
        // Configure ChunkPacketizer
        packetizer = new RawPacketizer(1500);

        // Open a H264InputStream
        H264InputStream str = new H264InputStream(new FileInputStream(filename));
        // MultiNALProcessor - payload size of 1468
        MultiNALProcessor nalProcessor = new MultiNALProcessor(str, packetizer.getPayloadSize(), 1);

        while (nalProcessor.hasNext()) {

            chunk = nalProcessor.next();
            count++;

            total += chunk.offset();

            printChunk(chunk, count, total);

            // now send it
            sender.sendPayload(packetizer.convert(count, chunk));
    
            // fix sleep from student code - awaiting proper algorithm
            try { 
                Thread.sleep(sleep);
            } catch (InterruptedException ie) {
            }
        }

        // close the stream
        str.close();

        // stop sender
        sender.stop();

    }
    
    protected static void printChunk(SVCChunkInfo chunk, int count, int total) {

        System.out.printf("%-8d", count);               // N
        System.out.printf("%-10s", chunk.getNALType());               // type
        System.out.printf(" %-5d", chunk.offset());         // no of bytes
        System.out.printf(" %-10d", total);             // total bytes

        System.out.println();
    }
    
}
