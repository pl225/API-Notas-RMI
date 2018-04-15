package client;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import stub.ApiSd;

public class Cliente {
    
	private Cliente() {}
    
    private static int PORTA = 6011;
    
    public static void menu(){
        System.out.println("\n\tMENU");
        System.out.println("0. Fim");
        System.out.println("1. Cadastrar nota");
        System.out.println("2. Consultar nota");
        System.out.println("3. Consultar notas");
        System.out.println("4. Consultar CR");
        System.out.println("Opcao:");
    }
    
    public static void main(String[] args) {
        
    	int opcao;
        String matricula; 
        String cod_disc;
        float nota; 
        Scanner entrada = new Scanner(System.in);
        Scanner ler = new Scanner(System.in);
        boolean loop = true; 
        
        do {
            menu();
            opcao = entrada.nextInt();
            
            switch(opcao){
            
            case 0: 
                loop = false; 
                System.out.printf("Encerrado.");
                break;
            case 1:
                System.out.printf("Informe a matricula: ");
                matricula = ler.next();
                System.out.printf("Informe o codigo da disciplina: ");
                cod_disc = ler.next();
                System.out.printf("Informe a nota: ");
                nota = ler.nextFloat(); 
                
                try {
                    Registry registry = LocateRegistry.getRegistry(PORTA);
                    ApiSd stub = (ApiSd) registry.lookup("api_sd");
                    stub.cadastrarNota(matricula, cod_disc, nota);
                    System.out.println("Nota cadastrada com sucesso!"); 
            
                } catch (Exception e) {
                    System.err.println("\nUm erro ocorreu: " + e.getMessage());
                }
                break;
                
            case 2:
                System.out.printf("Informe a matricula: ");
                matricula = ler.next();
                
                System.out.printf("Informe o codigo da disciplina: ");
                cod_disc = ler.next();
                
                try {
                    Registry registry = LocateRegistry.getRegistry(PORTA);
                    ApiSd stub = (ApiSd) registry.lookup("api_sd");
                    float response = stub.consultarNota(matricula, cod_disc);
                    System.out.println("Nota: " + response); 
            
                } catch (Exception e) {
                    System.err.println("\nUm erro ocorreu: " + e.getMessage());
                }
                break;
                
            case 3:
                System.out.printf("Informe a matricula: ");
                matricula = ler.next();
                
                try {
                    Registry registry = LocateRegistry.getRegistry(PORTA);
                    ApiSd stub = (ApiSd) registry.lookup("api_sd");
                    for (Float f: stub.consultarNotas(matricula))
                    	System.out.println("Nota: " + f); 
            
                } catch (Exception e) {
                    System.err.println("\nUm erro ocorreu: " + e.getMessage());
                }
                break;
                
            case 4:
                System.out.printf("Informe a matricula: ");
                matricula = ler.next();
                
                try {
                    Registry registry = LocateRegistry.getRegistry(PORTA);
                    ApiSd stub = (ApiSd) registry.lookup("api_sd");
                    float response = stub.consultarCR(matricula);
                    System.out.println("CR: " + response); 
            
                } catch (Exception e) {
                    System.err.println("\nUm erro ocorreu: " + e.getMessage());
                }
                break;
            
            default:
                System.out.println("Opcao invalida!");
            }
            
            System.out.flush();
            System.err.flush();
        } while(loop);
        
        entrada.close();
        ler.close();
    }
}
