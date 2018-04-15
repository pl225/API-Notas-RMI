package server;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import conexao_bd.PostgreSQLJDBC;
import stub.ApiSd;
        
public class Servidor implements ApiSd {
        
    public Servidor() {}
    
    private static int PORTA = 6011;

    @Override
	public boolean cadastrarNota(String matricula, String codDisciplina, float nota) throws RemoteException {
		try {
			return PostgreSQLJDBC.cadastrarNota(matricula, codDisciplina, nota);
		} catch (Exception e) {
			throw new RemoteException(e.getMessage());
		}
	}
  	
	@Override
	public float consultarNota(String matricula, String codDisciplina) throws RemoteException {
		try {
			return PostgreSQLJDBC.consultarNota(matricula, codDisciplina);
		} catch (Exception e) {
			throw new RemoteException(e.getMessage());
		}
	}


	@Override
	public ArrayList<Float> consultarNotas(String matricula) throws RemoteException {
		try {
			return PostgreSQLJDBC.consultarNotas(matricula);
		} catch (Exception e) {
			throw new RemoteException(e.getMessage());
		}
	}


	@Override
	public float consultarCR(String matricula) throws RemoteException {
		try {
			return PostgreSQLJDBC.consultarCR(matricula);
		} catch (Exception e) {
			throw new RemoteException(e.getMessage());
		}
	}
        
    public static void main(String args[]) {
        
        try {
            Servidor obj = new Servidor();
            ApiSd stub = (ApiSd) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.createRegistry(PORTA);
            registry.bind("api_sd", stub);

            System.err.println("Servidor pronto");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
