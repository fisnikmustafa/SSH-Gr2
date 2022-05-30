package FileTransfer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class FileTransfer {

    static ArrayList<MyFile> myFiles=new ArrayList<>();

    public static void main(String[] args) throws IOException {

        final File[] fileToSend= new File[1];
        JFrame jFrame = new JFrame("Dergo Files");
        jFrame.setSize(450,450);
        jFrame.setLayout(new BoxLayout(jFrame.getContentPane(), BoxLayout.Y_AXIS));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel jlFileName = new JLabel("Zgjedh");
        jlFileName.setFont(new Font("Arial", Font.BOLD,25));
        jlFileName.setBorder(new EmptyBorder(50,0,0,0));
        jlFileName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel jpButton = new JPanel();
        jpButton.setBorder(new EmptyBorder(75,0,10,0));
        JButton jbSendFile = new JButton("Dergo");
        jbSendFile.setPreferredSize(new Dimension(150,75));
        jbSendFile.setFont(new Font("Arial",Font.BOLD,20));
        JButton jbChooseFile = new JButton("Zgjedh file");
        jbChooseFile.setPreferredSize(new Dimension(150,75));
        jbChooseFile.setFont(new Font("Arial",Font.BOLD,20));

        jpButton.add(jbSendFile);
        jpButton.add(jbChooseFile);
        jbChooseFile.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setDialogTitle("Zgjedh file");

                if(jFileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
                    fileToSend[0]=jFileChooser.getSelectedFile();
                    jlFileName.setText("File qe dergohet ---> "+fileToSend[0].getName());
                }
            }
        });

        jbSendFile.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(fileToSend[0]==null){
                    jlFileName.setText("Nuk keni zgjedhur asnje file");
                }

                else{
                    try{


                        FileInputStream fileInputStream=new FileInputStream(fileToSend[0].getAbsolutePath());
                        Socket socket = new Socket("192.168.177.211",1000);
                        DataOutputStream dataOutputStream= new DataOutputStream(socket.getOutputStream());
                        String fileName=fileToSend[0].getName();
                        byte[] fileNameBytes= fileName.getBytes();
                        byte[] fileContentBytes= new byte[(int)fileToSend[0].length()];
                        fileInputStream.read(fileContentBytes);
                        dataOutputStream.writeInt(fileNameBytes.length);
                        dataOutputStream.write(fileNameBytes);
                        dataOutputStream.writeInt(fileContentBytes.length);
                        dataOutputStream.write(fileContentBytes);
                    }catch(IOException err){
                        err.printStackTrace();

                    }
                }
            }

        });

//////////////////////////////////////////////////////////////////////////

        int fileId=0;

        JPanel jpanel=new JPanel();
        jpanel.setLayout(new BoxLayout(jpanel,BoxLayout.Y_AXIS));

        JScrollPane jscrollpane= new JScrollPane(jpanel);
        jscrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JLabel jltitle=new JLabel("File te pranuar");
        jltitle.setFont(new Font("Arial",Font.BOLD,25));
        jltitle.setBorder(new EmptyBorder(20,0,10,0));
        jltitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        jFrame.add(jlFileName);
        jFrame.add(jpButton);
        jFrame.add(jltitle);
        jFrame.add(jscrollpane);
        jFrame.setVisible(true);

        ServerSocket serverSocket = new ServerSocket(2000);

        while(true){

            try{
                Socket socket = serverSocket.accept();
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                int fileNameLength = dataInputStream.readInt();

                if(fileNameLength>0){
                    byte[] fileNameBytes= new byte[fileNameLength];
                    dataInputStream.readFully(fileNameBytes, 0, fileNameBytes.length);
                    String fileName= new String(fileNameBytes);

                    int fileContentLength= dataInputStream.readInt();

                    if(fileContentLength>0){
                        byte[] fileContentBytes = new byte[fileContentLength];
                        dataInputStream.readFully(fileContentBytes,0,fileContentLength);

                        JPanel jpfrow = new JPanel();
                        jpfrow.setLayout(new BoxLayout(jpfrow,BoxLayout.Y_AXIS));

                        JLabel JLabelFileName= new JLabel(fileName);
                        JLabelFileName.setFont(new Font("Arial",Font.BOLD,20));
                        JLabelFileName.setBorder(new EmptyBorder(10,0,10,0));
                        JLabelFileName.setAlignmentX(Component.CENTER_ALIGNMENT);

                        if(getFileExtention(fileName).equalsIgnoreCase("txt")){
                            jpfrow.setName(String.valueOf(fileId));
                            jpfrow.addMouseListener(getMyMouseListener());
                            jpfrow.add(JLabelFileName);
                            jpanel.add(jpfrow);
                            jFrame.validate();
                        }else {
                            jpfrow.setName(String.valueOf(fileId));
                            jpfrow.addMouseListener(getMyMouseListener());
                            jpfrow.add(JLabelFileName);
                            jpanel.add(jpfrow);
                            jFrame.validate();

                        }
                        myFiles.add(new MyFile(fileId,fileName,fileContentBytes,getFileExtention(fileName)));

                        fileId++;
                    }

                }
            }catch(IOException error){
                error.printStackTrace();

            }
        }


    }

    public static MouseListener getMyMouseListener(){
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JPanel jPanel= (JPanel) e.getSource();
                int fileId= Integer.parseInt(jPanel.getName());

                for(MyFile myFile: myFiles){
                    if(myFile.getId()==fileId){
                        File fileToBeDownloaded= new File(myFile.getName());
                        try{
                            FileOutputStream fileOutputStream= new FileOutputStream(fileToBeDownloaded);
                            fileOutputStream.write(myFile.getData());
                            fileOutputStream.close();

                        }catch(IOException error){
                            error.printStackTrace();

                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
    }

    public static String getFileExtention(String fileName){
        int i = fileName.lastIndexOf('.');
        if(i>0){
            return fileName.substring(i+1);
        }else{
            return "Nuk u gjet extension";
        }
    }
}
