import java.util.InputMismatchException;
import java.util.Scanner;

class NodoArbol{
	private NodoArbol nodoIzq;
	private int dato;
	private NodoArbol nodoDer;
	
	public NodoArbol(int dato) {
		this.dato = dato;
	}
	public NodoArbol(){
	}
	
	public NodoArbol getNodoIzq() {
		return nodoIzq;
	}
	public void setNodoIzq(NodoArbol nodoIzq) {
		this.nodoIzq = nodoIzq;
	}
	public int getDato() {
		return dato;
	}
	public void setDato(int dato) {
		this.dato = dato;
	}
	public NodoArbol getNodoDer() {
		return nodoDer;
	}
	public void setNodoDer(NodoArbol nodoDer) {
		this.nodoDer = nodoDer;
	}
	
	@Override
	public String toString() {
		return "NodoArbol [nodoIzq=" + nodoIzq + ", dato=" + dato + ", nodoDer=" + nodoDer + "]";
	}
	
}

class ArbolBinario{
	NodoArbol nodoRaiz;
	
	//1) Crear arbol
	public ArbolBinario(){
		nodoRaiz=null;
	}
	
	//2) Agregar
	public void agregarElemento(int dato){
		NodoArbol nuevoNodo = new NodoArbol(dato);
		if(nodoRaiz==null) {
			nodoRaiz = nuevoNodo;
		}else {
			NodoArbol aux = nodoRaiz;
			NodoArbol nodoAnterior;
			while(aux!=null) {
				nodoAnterior = aux;
				if(dato>=aux.getDato()) {
					aux = aux.getNodoIzq();
					if(aux==null)
						nodoAnterior.setNodoIzq(nuevoNodo);
				}else {
					aux = aux.getNodoDer();
					if(aux==null)
						nodoAnterior.setNodoDer(nuevoNodo);
				}
			}
		}
	}//metodo agergarElemento
	
	//3) Eliminar
	public boolean eliminarElemento(int dato) {
		
		if(!(nodoRaiz==null)) {
			
			NodoArbol anterior = nodoRaiz;
			NodoArbol aux = nodoRaiz;
			String ladoArbol ="";
			
			//proceso de busqueda-----------------------------------
			while(aux.getDato() != dato) {
				anterior = aux;
				if(dato<=aux.getDato()) { //izquierda
					aux = aux.getNodoIzq();
					ladoArbol = "IZQ";
				}else {//Derecha
					aux = aux.getNodoDer();
					ladoArbol = "DER";
				}
				
				if(aux==null) {
					System.out.println("buscado y no encontrdo");
					return false;
				}
			}//while
			
			System.out.println("encontrado");

			
			//proceso de eliminacion  (se encontro el dato)---------------------
			
			//Escenario 1: El nodo a eliminar es HOJA
			if(aux.getNodoIzq()==null && aux.getNodoDer()==null) { //verficar si es hoja
				if(aux==nodoRaiz)
					nodoRaiz = null;
				else if(ladoArbol.equals("IZQ"))
					anterior.setNodoIzq(null);
				else
					anterior.setNodoDer(null);
			}else if(aux.getNodoIzq()==null) { //verificar si tiene un solo hijo a la IZQ
				if(aux==nodoRaiz)
					nodoRaiz = aux.getNodoIzq();
				else if(ladoArbol.equals("IZQ"))
					anterior.setNodoIzq(aux.getNodoIzq());
				else
					anterior.setNodoDer(aux.getNodoIzq());
			}else if(aux.getNodoDer()==null) { //verificar si tiene un solo hijo a la DER
				if(aux==nodoRaiz)
					nodoRaiz = aux.getNodoDer();
				else if(ladoArbol.equals("IZQ"))
					anterior.setNodoIzq(aux.getNodoDer());
				else
					anterior.setNodoDer(aux.getNodoDer());
			
			
			}else { // de lo contrario TIENE DOS HIJOS -----------------------------------
				
				//----------------------------------------------------------------
				NodoArbol reemplazo = reemplazar(aux);

				if(aux==nodoRaiz)
					nodoRaiz = reemplazo;
				else if(ladoArbol.equals("IZQ"))
					anterior.setNodoIzq(reemplazo);
				else
					anterior.setNodoDer(reemplazo);
				
				reemplazo.setNodoIzq(aux.getNodoIzq());
			}
			return true;
				//----------------------------------------------------------------
			
		}else {
			System.out.println("Arbol vacio");
			return false;
		}
	}
	
	
	public NodoArbol reemplazar(NodoArbol nodo){
		NodoArbol reemplazarPadre = nodo;
		NodoArbol reemplazo = nodo;
		NodoArbol auxiliar = nodo.getNodoDer();
		
		while(auxiliar != null){
			reemplazarPadre = reemplazo;
			reemplazo = auxiliar;
			auxiliar= auxiliar.getNodoIzq();
		}
		if(reemplazo!=nodo.getNodoDer()){
			reemplazarPadre.setNodoIzq(reemplazo.getNodoDer());
			reemplazo.setNodoDer(nodo.getNodoDer());
		}
		return reemplazo;		

	}//metodo reemplazar		
		
	
	//R-I-D  7-3-1-14-9-37
	public void recorridoPreorden(NodoArbol nodoRaiz) {
		if(!(nodoRaiz==null)) {
			System.out.print(nodoRaiz.getDato() + " => ");
			recorridoPreorden(nodoRaiz.getNodoIzq());
			recorridoPreorden(nodoRaiz.getNodoDer());
		}
	}
	
	//I-R-D  1-3-7-9-14-37
	public void recorridoEnorden(NodoArbol nodoRaiz) {
		if(!(nodoRaiz==null)) {
			recorridoEnorden(nodoRaiz.getNodoIzq());
			System.out.print(nodoRaiz.getDato() + " => ");
			recorridoEnorden(nodoRaiz.getNodoDer());
		}
	}
	//I-D-R => 1-3-9-37-14-7
	public void recorridoPostorden(NodoArbol nodoRaiz) {
		if(!(nodoRaiz==null)) {
			recorridoPostorden(nodoRaiz.getNodoIzq());
			recorridoPostorden(nodoRaiz.getNodoDer());
			System.out.print(nodoRaiz.getDato() + " => ");
		}
	}
	
	
	public void mostrarDatoMayor() {
		if(nodoRaiz==null) {
			System.out.println("el arbol esta vacio");
		}else {
			NodoArbol aux = nodoRaiz;
			NodoArbol nodoAnterior;
			
			while(aux!=null) {
				nodoAnterior = aux;
				aux = aux.getNodoIzq();
				if(aux==null) {
					System.out.println("el numero mayor es: "+nodoAnterior.getDato());
				}
			}
		}
	}
	
	public void mostrarDatoMenor() {
		if(nodoRaiz==null) {
			System.out.println("el arbol esta vacio");
		}else {
			NodoArbol aux = nodoRaiz;
			NodoArbol nodoAnterior;
			
			while(aux!=null) {
				nodoAnterior = aux;
				aux = aux.getNodoDer();
				if(aux==null) {
					System.out.println("el numero menor es: "+nodoAnterior.getDato());
				}
			}
		}
	}
	public int buscarDato(NodoArbol nodo, int dato,int encontrado) {
		if(nodo!=null) {
			if(dato==nodo.getDato()) {
				encontrado+=1;
			}
			encontrado+=buscarDato(nodo.getNodoIzq(),dato,encontrado);
			encontrado+=buscarDato(nodo.getNodoDer(),dato,encontrado);
		}
		return encontrado;
	}
	
	
}


public class ArbolBinarioBusqueda {

	public static void main(String[] args) {
		
		Scanner ent = new Scanner(System.in);
		
		ArbolBinario ab = new ArbolBinario();	
		int opc=0;
		int opc2=0;
		int dato;
		int num;
		boolean salir=false;
		boolean salir1=false;
		
		
		do {
			System.out.println("1)Crear árbol");
			System.out.println("2)Insertar nodo");
			System.out.println("3)Eliminar nodo");
			System.out.println("4)Mostrar nodos");
			System.out.println("5)Mostrar dato mayor");
			System.out.println("6)Mostrar dato menor");
			System.out.println("7)Buscar dato");
			System.out.println("8)Salir");
			
			try {
				System.out.println("Escribe una de las opciones");
				opc = ent.nextInt();
				switch (opc) {
				case 1:
					ab = new ArbolBinario();
					System.out.println("Se creó el árbol");
					break;
				case 2:
					System.out.println("Ingresa el dato a insertar: ");
					dato = ent.nextInt();
					ab.agregarElemento(dato);
					break;
				case 3:
					System.out.println("Ingresa el dato a eliminar: ");
					dato = ent.nextInt();
					ab.eliminarElemento(dato);
					break;
				case 4:
					do {
						salir=false;
						System.out.println("1)Preorden");
						System.out.println("2)Inorden");
						System.out.println("3)Postorden");
						System.out.println("4)Salir");
						try {
							
							System.out.println("Escribe una de las opciones");
							opc2 = ent.nextInt();
							
							switch (opc2) {
							case 1:
								ab.recorridoPreorden(ab.nodoRaiz);
								break;
							case 2:
								ab.recorridoEnorden(ab.nodoRaiz);
								break;
							case 3:
								ab.recorridoPostorden(ab.nodoRaiz);
								break;
							case 4:
								salir=true;
								break;
							default:
								System.out.println("Opcion inválida");
								break;
							}
							
						} catch (InputMismatchException e) {
							System.out.println("Debes ingresar un número");
			                ent.next();
						}
						
					} while (!salir);
					System.out.println();
					break;
				case 5:
					ab.mostrarDatoMayor();
					break;
				case 6:
					ab.mostrarDatoMenor();
					break;
				case 7:
					System.out.println("Ingresa el dato a buscar: ");
					dato = ent.nextInt();
					System.out.println(ab.buscarDato(ab.nodoRaiz, dato, 0)>0?"Se encontró el dato":"No se encontró el dato");
					break;
				case 8:
					salir1=true;break;
				default:
					System.out.println("Opcion inválida");break;
				}
				
			}catch (InputMismatchException e) {
				System.out.println("Debes ingresar un número");
                ent.next();
				
			}
			
		} while (!salir1);		
		
		
		
	
	}

}
