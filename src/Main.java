import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static PrintWriter printWriter;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Ksiazka> mojeKsiazki = new ArrayList<>();

        try(Socket socket = new Socket("localhost", 8002)){
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            System.out.println(bufferedReader.readLine());

            boolean dziala = true;
            while (dziala){
                switch (scanner.nextLine()){
                    case "koniec" -> {
                        dziala = false;
                        print(Integer.toString(0));
                        System.out.println(bufferedReader.readLine());
                    }
                    case "lista" -> {
                        print(Integer.toString(1));
                        System.out.println(bufferedReader.readLine());
                    }
                    case "wypozycz" -> {
                        print(Integer.toString(2));
                        System.out.println(bufferedReader.readLine());
                        print(scanner.nextLine());
                        print(scanner.nextLine());
                        print(scanner.nextLine());
                        mojeKsiazki.add((Ksiazka) objectInputStream.readObject());
                        System.out.println(bufferedReader.readLine());
                    }
                    case "oddaj" -> {
                        print(Integer.toString(3));
                        System.out.println(bufferedReader.readLine());
                        print(scanner.nextLine());
                        print(scanner.nextLine());
                        print(scanner.nextLine());
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

    private static void print(String s){
        printWriter.println(s);
        printWriter.flush();
    }
}