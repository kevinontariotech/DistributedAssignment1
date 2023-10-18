import java.io.*;
import java.net.Socket;

public class MathClient {

    PrintWriter out;
    BufferedReader in, consoleInput;
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            String serverResponse;
            while ((serverResponse = in.readLine()) != null) {
                System.out.println(serverResponse);
                String userInput = consoleInput.readLine();
                out.println(userInput);

                if (userInput.equalsIgnoreCase("QUIT")) {
                    break;
                }  

                if (userInput.equalsIgnoreCase("SEND")) {
                    String mathQuestion = in.readLine();
                    System.out.println("Server: " + mathQuestion);
                    int answer = Integer.parseInt(in.readLine());
                    int userAnswer = Integer.parseInt(consoleInput.readLine());
                    if (userAnswer == answer) {
                        System.out.println("Congratulations, you got the answer right");
                    } else {
                        System.out.println("Sorry you got the answer wrong");
                    }
                    continue;
                } 

                if (userInput.equalsIgnoreCase("CALCULATE")) {
                    System.out.println("Usage: ADD <number1> <number2>");
                    userInput = consoleInput.readLine();
                    out.println(userInput);
                    String result = in.readLine();
                    System.out.println(result);
                    continue;
                }
            }

            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void checkAnswer(int userAnswer, int answer) {
        
        
        System.out.println("Server: " + answer);
    }
}


