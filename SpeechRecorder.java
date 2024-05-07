import javax.sound.sampled.*;
import java.io.*;

public class SpeechRecorder {
    public static void main(String[] args) {
        try {
            AudioFormat format = new AudioFormat(16000, 16, 1, true, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                System.exit(0);
            }
            TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();
            System.out.println("Start speaking...");
            
            // Capture audio until "end" is typed
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            boolean isEnd = false;
            while (!isEnd) {
                bytesRead = line.read(buffer, 0, buffer.length);
                outputStream.write(buffer, 0, bytesRead);
                
                // Check for "end" input
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String input = reader.readLine();
                if (input.equalsIgnoreCase("end")) {
                    isEnd = true;
                }
            }
            
            // Stop recording
            line.stop();
            line.close();
            
            System.out.println("Recording finished.");
            
            // Save audio to file or process it further
            byte[] audioData = outputStream.toByteArray();
            // Do something with the audio data...
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
}
