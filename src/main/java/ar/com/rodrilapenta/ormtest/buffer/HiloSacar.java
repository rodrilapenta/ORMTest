package ar.com.rodrilapenta.ormtest.buffer;

import java.util.List;
import java.util.Map;

import ar.com.rodrilapenta.ormtest.db.dao.impl.GenericDAOImpl;


public class HiloSacar extends Thread
{
	Buffer mon;
	private int milisegundos;
	//Se le llama as� a la variable ya que representar� la condici�n de si el HiloPoner est� poniendo elementos en el buffer o no.
	private boolean poniendo;
	private GenericDAOImpl dao;
	private List<String> emails;
	
	public HiloSacar(Buffer mon, int milisegundos, GenericDAOImpl dao, List<String> emails)
	{
		this.dao = dao;
		this.poniendo = false;
		this.mon = mon;
		this.milisegundos = milisegundos;
		this.emails = emails;
	}

	@SuppressWarnings("unchecked")
	public void run()
	{
		poniendo = true;
		while (poniendo || mon.elementosEnElBuffer() > 0) //Se toma como condición que el HiloPoner está poniendo elementos en el buffer o que haya elementos en el buffer para sacar. Mientras alguno de los dos ocurra, este hilo seguirá sacando.
		{
			sleep(milisegundos);
			String file = mon.sacar();
			
			Map<String, Object> map = Wrapper.wrap(file);
			String tabla = map.get("tabla").toString();
			List<String> registros = (List<String>) map.get("registros");
			try {
				dao.insertElements(tabla, registros, file, emails);
			}
			catch(Exception e) {
				System.out.println("ERROR AL GRABAR TABLA");
				e.printStackTrace();
			}
		}
	}
	
	private void sleep(int milliseconds) 
	{
		try 
		{
			Thread.sleep(milliseconds);
		} 
		catch (InterruptedException e) 
		{
			
		}
	}
	
	public void puedeTerminar() 
	{
		//Este método será llamado cuando termine la ejecución del HiloPoner para indicarle al HiloSacar que luego de que termine de sacar todos los elementos del buffer
		//puede terminar de ejecutarse porque el HiloPoner ya no pondrá más elementos en el buffer.
		poniendo = false;
	}
}

