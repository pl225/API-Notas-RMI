package stub;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ApiSd extends Remote {
  	
  	boolean cadastrarNota(String matricula, String codDisciplina, float nota) throws RemoteException;
	float consultarNota(String matricula, String codDisciplina) throws RemoteException;	
	ArrayList<Float> consultarNotas(String matricula) throws RemoteException; 
	float consultarCR (String matricula) throws RemoteException;
  
}