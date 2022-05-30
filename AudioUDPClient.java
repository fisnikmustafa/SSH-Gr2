package ChatServer;

import java.io.*;
import java.net.*;

import javax.sound.sampled.*;

import static java.lang.System.in;
import static java.lang.System.lineSeparator;


public class AudioUDPClient extends Thread {
    private String address;
    private int acceptport;
    AudioInputStream audioInputStream;
    SourceDataLine sourceDataLine;
    public AudioUDPClient(String address,int acceptport) {

        this.address=address;
        this.acceptport=acceptport;
        initiateAudio();
    }
    public static void main(String[] args) {
       String address="192.168.1.9";
       int acceptport=9787;
        new AudioUDPClient(address,acceptport);
    }
    private void initiateAudio() {
        try {
            DatagramSocket socket = new DatagramSocket(acceptport);
            byte[] audioBuffer = new byte[10000];
            InetAddress inetAddress =
                    InetAddress.getByName(address);
            try {
                while (true) {
                    DatagramPacket packet
                            = new DatagramPacket(audioBuffer, audioBuffer.length);//,inetAddress,9787
                    socket.receive(packet);
                    byte audioData[] = packet.getData();
                    InputStream byteInputStream =
                            new ByteArrayInputStream(audioData);
                    AudioFormat audioFormat = getAudioFormat();
                    audioInputStream = new AudioInputStream(
                            byteInputStream,
                            audioFormat, audioData.length / audioFormat.getFrameSize());
                    DataLine.Info dataLineInfo = new DataLine.Info(
                            SourceDataLine.class, audioFormat);
                    sourceDataLine = (SourceDataLine)
                            AudioSystem.getLine(dataLineInfo);
                    sourceDataLine.open(audioFormat);
                    sourceDataLine.start();
                    playAudio();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void playAudio() {
        byte[] buffer = new byte[10000];
        try {
            int count;
                        while ((count = audioInputStream.read(
                    buffer, 0, buffer.length)) != -1) {
                if (count > 0) {
                    sourceDataLine.write(buffer, 0, count);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private AudioFormat getAudioFormat() {
        float sampleRate = 16000F;
        int sampleSizeInBits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }
}
