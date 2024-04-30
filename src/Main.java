import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Ksiazka> mojeKsiazki = new ArrayList<>();

        try(Socket socket = new Socket("localhost", 8002);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())) {
            System.out.println(bufferedReader.readLine());

            boolean dziala = true;
            while (dziala){
                switch (scanner.nextLine()){
                    case "koniec" -> {
                        dziala = false;
                        printWriter.println(0);
                        System.out.println(bufferedReader.readLine());
                    }
                    case "lista" -> {
                        printWriter.println(1);
                        System.out.println(bufferedReader.readLine());
                    }
                    case "wypozycz" -> {
                        printWriter.println(2);
                        System.out.println(bufferedReader.readLine());
                        printWriter.println(scanner.nextLine());
                        printWriter.println(scanner.nextLine());
                        printWriter.println(scanner.nextLine());
                        mojeKsiazki.add((Ksiazka) objectInputStream.readObject());
                        System.out.println(bufferedReader.readLine());
                    }
                    case "oddaj" -> {
                        printWriter.println(3);
                        System.out.println(bufferedReader.readLine());
                        printWriter.println(scanner.nextLine());
                        printWriter.println(scanner.nextLine());
                        printWriter.println(scanner.nextLine());
                        System.out.println(bufferedReader.readLine());
                    }
                    case "moje" -> mojeKsiazki.forEach(ksiazka -> {
                        System.out.println(ksiazka.getAutor());
                        System.out.println(ksiazka.getTytul());
                        System.out.println(ksiazka.getData());
                        System.out.println(ksiazka.getIndexW());
                        System.out.println();
                    });
                    default -> System.out.println("Nieznana komenda");
                }
            }
        } catch(IOException | ClassNotFoundException e) {
            System.out.println("Błąd: " + e);
        }
    }
}