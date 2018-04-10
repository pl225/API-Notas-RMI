package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import stub.ApiSd;

public class Cliente {

    private Cliente() {}
    
    private static int PORTA = 6011;

    public static void main(String[] args) {

        
        try {
            Registry registry = LocateRegistry.getRegistry(PORTA);
            ApiSd stub = (ApiSd) registry.lookup("api_sd");
            boolean response = stub.cadastrarNota("201378486-7", "IM-204", 9);
            System.out.println("response: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.getMessage());
        }
    }
}
