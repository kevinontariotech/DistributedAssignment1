import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MathServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server is running and waiting for connections...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            

            out.println("Welcome to the Math Server! You can type 'SEND' to request a math question or 'CALCULATE' to calculate a math question. Use command 'QUIT' to exit.");

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.equalsIgnoreCase("SEND")) {
                    sendMathQuestion();
                } else if (inputLine.equalsIgnoreCase("CALCULATE")) {
                    calculateQuestion();
                } else if (inputLine.equalsIgnoreCase("QUIT")) {
                    break;
                } else {
                    out.println("Invalid command. Type 'SEND' or 'QUIT'.");
                }
                out.println("Welcome to the Math Server! You can type 'SEND' to request a math question or 'QUIT' to exit.");     
            }
            

            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // private void sendMathQuestion() {
    //     // Generate a math question (you can modify this logic as needed)
    //     int num1 = (int) (Math.random() * 100) + 1;
    //     int num2 = (int) (Math.random() * 100) + 1;
    //     String question = num1 + " + " + num2 + " = ?";
        
    //     out.println("Math Question: " + question);
    // }



    // Generate a math question
    private void sendMathQuestion() {
        
        int num1 = (int) (Math.random() * 10);
        int num2 = (int) (Math.random() * 10);
        int operation = (int) (Math.random() * 2);
        
        String question = "";
        int answer;
        switch (operation){
            case 0:
                question = num1 + " + " + num2 + " = ?";
                answer = num1 + num2;
                break;
            case 1:
                question = num1 + " - " + num2 + " = ?";
                answer = num1 - num2;
                break;
            default:
                System.out.println("Something went wrong");
                return;
        }
        out.println("Math Question: " + question);
        out.println(answer);
    }

    private void calculateQuestion() {
        try {
            String input = in.readLine();
            System.out.println("Received: " + input);
            
            String[] tokens = input.split(" ");
            if (tokens.length == 3 && tokens[0].equalsIgnoreCase("ADD")) {
                try {
                    int num1 = Integer.parseInt(tokens[1]);
                    int num2 = Integer.parseInt(tokens[2]);
                    int result = num1 + num2;
                    out.println("Result: " + result);
                } catch (NumberFormatException e) {
                    out.println("Invalid numbers provided.");
                }
                return;
            }

            if (tokens.length == 3 && tokens[0].equalsIgnoreCase("SUBTRACT")) {
                try {
                    int num1 = Integer.parseInt(tokens[1]);
                    int num2 = Integer.parseInt(tokens[2]);
                    int result = num1 - num2;
                    out.println("Result: " + result);
                } catch (NumberFormatException e) {
                    out.println("Invalid numbers provided.");
                }
                return;
            }

            out.println("Invalid input. Usage: <operation> <number1> <number2>");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}