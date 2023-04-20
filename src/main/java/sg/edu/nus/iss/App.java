package sg.edu.nus.iss;

import java.io.*;
import java.net.Socket;

public class App {
    public static void main(String[] args) throws IOException {
        Integer port = Integer.parseInt(args[0]);

        Socket socket = new Socket("localhost", port);

        Console con = System.console();
        String keyboardInput = "";
        String msgReceived = "";

        try (InputStream is = socket.getInputStream()) {
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);

            try (OutputStream os = socket.getOutputStream()) {
                BufferedOutputStream bos = new BufferedOutputStream(os);
                DataOutputStream dos = new DataOutputStream(bos);
                
                while(!keyboardInput.equals("close")){
                    keyboardInput = con.readLine("Enter a command: ");

                    dos.writeUTF(keyboardInput);
                    dos.flush();

                    msgReceived = dis.readUTF();
                    System.out.println(msgReceived);
                }

                dos.close();
                bos.close();
            } catch (EOFException e) {}
            
            dis.close();
            bis.close();
            socket.close();

        } catch (EOFException e) {}

    }
}
