package ChatServer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.sound.sampled.*;
import javax.xml.crypto.Data;

public class AudioPeer {

    private int sendPort;
    private int acceptPort;
    private String address;
    private final byte audioBuffer[] = new byte[10000];
    private TargetDataLine targetDataLine;

    AudioInputStream audioInputStream;
    SourceDataLine sourceDataLine;

    public AudioPeer(String address, int sendPort, int acceptPort){
        this.address = address;
        this.sendPort = sendPort;
        this.acceptPort = acceptPort;

        setupAudio();
        new SendAudio(this.sendPort, this.address).start();
        new ReceiveAudio(this.acceptPort, this.address).start();
    }

    public static void main(String[] args) {
        String address = "172.16.0.71";
        int sendport = 9786;
        int acceptport=9787;

        new AudioPeer(address, sendport, acceptport);
    }

    private AudioFormat getAudioFormat() {
        float sampleRate = 16000F;
        int sampleSizeInBits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;
        return new AudioFormat(sampleRate, sampleSizeInBits,
                channels, signed, bigEndian);
    }

    public void setupAudio() {
        try {
            AudioFormat audioFormat = getAudioFormat();
            DataLine.Info dataLineInfo =
                    new DataLine.Info(TargetDataLine.class,
                            audioFormat);
            targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
            targetDataLine.open(audioFormat);
            targetDataLine.start();

        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }

    class SendAudio extends Thread{
        DatagramSocket socket;
        InetAddress inetAddress;

        public SendAudio(int sendPort, String address){
            try{
                this.socket = new DatagramSocket(sendPort);
                inetAddress = InetAddress.getByName(address);
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }

        @Override
        public void run(){
            try {
                System.out.println("Detecting audio to send...!");
                while (true) {
                    int count = targetDataLine.read(
                            audioBuffer, 0, audioBuffer.length);

                    if (count > 0) {
                        DatagramPacket packet = new DatagramPacket(
                                audioBuffer, audioBuffer.length, inetAddress, sendPort);
                        socket.send(packet);
                    }
                }
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }

    }

    class ReceiveAudio extends Thread {
        DatagramSocket socket;
        InetAddress inetAddress;

        byte[] audioBuffer = new byte[10000];

        public ReceiveAudio(int acceptPort, String address){
            try{
                this.socket = new DatagramSocket(acceptPort);
                this.inetAddress = InetAddress.getByName(address);
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }

        @Override
        public void run(){
            try {
                while (true) {
                    DatagramPacket packet
                            = new DatagramPacket(audioBuffer, audioBuffer.length);//,inetAddress,9787
                    socket.receive(packet);
                    try {
                        byte audioData[] = packet.getData();
                        InputStream byteInputStream =
                                new ByteArrayInputStream(audioData);
                        AudioFormat audioFormat = getAudioFormat();
                        audioInputStream = new AudioInputStream(
                                byteInputStream,
                                audioFormat, audioData.length /
                                audioFormat.getFrameSize());
                        DataLine.Info dataLineInfo = new DataLine.Info(
                                SourceDataLine.class, audioFormat);
                        sourceDataLine = (SourceDataLine)
                                AudioSystem.getLine(dataLineInfo);
                        sourceDataLine.open(audioFormat);
                        sourceDataLine.start();
                        playAudio();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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


    }
}
