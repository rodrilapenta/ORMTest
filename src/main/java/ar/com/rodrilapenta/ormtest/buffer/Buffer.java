package ar.com.rodrilapenta.ormtest.buffer;

public class Buffer
{
    private Object noLleno = new Object();
    private Object noVacio = new Object();
    
    private int cabeza, cola, elementos, espacios, capacidad; //espacios y elementos ser�an los dos monitores
	private String[] buffer;
    
    public Buffer(int i)
    {
        cola = cabeza = elementos = 0;
        capacidad = i;
        espacios = i;
        buffer = new String[i];
        for(int j = 0; j<i; j++)
		{
			buffer[j] = null;
		}
    }
    
    public void poner(String elem)
    {
        synchronized(noLleno) //synchronized es lo que maneja la exclusi�n m�tua
        {
        	System.out.println("Poner    >> Verifica condici�n de sincronizaci�n -> while(espacios<=0)");
            while(espacios<=0) //esto verifica la condici�n de sincronizaci�n: si hay espacios, se puede insertar, sino no.
            {
                try
                {
                	System.out.println("Poner    >> No hay espacio (espacios <= 0) -> noLleno.wait()");
                	noLleno.wait();
                }
                catch(InterruptedException e)
                {
                	
                }
            }
            buffer[cola] = elem;
            cola = (cola+1)%capacidad;
        }
        synchronized(noVacio)
        {
        	espacios--;
            elementos++;
            System.out.println("Poner    >> Elemento " + elem + " puesto.\n");
            System.out.println("Cantidad de elementos en el buffer: " + elementos);
            System.out.println("Cantidad de espacios en el buffer: " + espacios + "\n");
            if(elementos>0)
            {
            	System.out.println("Poner   >> Avisa que termin� de poner -> noVacio.notifyAll()\n");
            	System.out.println("**************************************************************\n");
            	noVacio.notifyAll();
            }
                
        }
    }
    
    public String sacar()
    {
        String valor;
        synchronized(noVacio)
        {
        	System.out.println("Sacar    >> Verifica condici�n de sincronizaci�n -> while(elementos<=0)");
            while(elementos<=0)
            {
                try
                {
                	System.out.println("Sacar    >> No hay elementos (elementos < 0) -> noVacio.wait()");
                	noVacio.wait();
                }
                catch(InterruptedException e)
                {
                	
                }
            }
            valor = buffer[cabeza];
        	buffer[cabeza] = null; //Est� programado para que, si el valor en una posici�n del buffer es -1, entonces ese lugar est� vac�o
            cabeza = (cabeza+1)%capacidad;
       
        }
        synchronized(noLleno)
        {	
            espacios++;
            elementos--;
            System.out.println("Sacar    >> Elemento " + valor + " sacado.\n");
            System.out.println("Cantidad de elementos en el buffer: " + elementos);
            System.out.println("Cantidad de espacios en el buffer: " + espacios + "\n");
            if(espacios>0)
            {
            	System.out.println("Sacar   >> Avisa que termin� de sacar -> noLleno.notifyAll()\n");
            	System.out.println("**************************************************************\n");
            	noLleno.notifyAll();
            }
                
            return valor;
        }
    }
    
    public int elementosEnElBuffer() 
	{
		int cont = 0;
		for(int i = 0; i < buffer.length; i++)
		{
			if(buffer[i] != null)
			{
				cont++;
			}
		}
		return cont;
	}
}

