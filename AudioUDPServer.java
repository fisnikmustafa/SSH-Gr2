package ChatServer;
//per me komuniku AULONI me BENIN duhet qe njoni me kriju qito serverin edhe klientin
//ip adresen e BENIT qe po don me komuniku edhe sendportin dhe acceptportin porte te ndryshme
//beni duhet me pas ip e te AULONIT
//sendport i AULONIT osht acceptporti i BENIT dhe e kunderta
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import javax.sound.sampled.*;


public class AudioUDPServer extends Thread{
    private int sendport;
    private String address;
    private final byte audioBuffer[] = new byte[10000];
    private TargetDataLine targetDataLine;
    public AudioUDPServer(String address,int sendport) {
        this.address=address;
        this.sendport=sendport;
        setupAudio();
        broadcastAudio();

    }
    public static void main(String[] args) {

            String address = "172.16.0.67";
            int sendport = 9786;
            int acceptport=9787;

            new AudioUDPServer(address,sendport);



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
    public void broadcastAudio() {
        try {
            DatagramSocket socket = new DatagramSocket(8000);
            InetAddress inetAddress =
                    InetAddress.getByName(address);
            while (true) {
                int count = targetDataLine.read(
                        audioBuffer, 0, audioBuffer.length);


                if (count > 0) {
                    DatagramPacket packet = new DatagramPacket(
                            audioBuffer, audioBuffer.length,inetAddress, sendport);
                    socket.send(packet);


                }

            }
        } catch (Exception ex) {
         ex.printStackTrace();
        }
    }

}



