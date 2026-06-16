package Imple;
import Interfaz.ConjuntoMamushkaTDA;

/*
 * ===========================================================================
 * EJERCICIO 2 - ConjuntoMamushka (implementacion)
 * ===========================================================================
 *
 * 1) ESTRATEGIA
 * ---------------------------------------------------------------------------
 * Multiconjunto implementado con una lista enlazada dinamica propia. Para no
 * repetir nodos, cada Nodo guarda un valor distinto (info) junto con la cantidad
 * de acepciones de ese valor (cantidad). Asi, agregar o sacar una acepcion de un
 * valor ya presente solo modifica el contador, y la lista nunca tiene dos nodos
 * con el mismo info. Se apoya en el metodo privado buscarNodo para no repetir el
 * codigo de busqueda.
 *   - guardar(dato): si el dato ya esta, suma 1 a su cantidad; si no, crea un
 *     nodo nuevo al frente con cantidad 1.
 *   - sacar(dato): resta 1 a la cantidad del dato y, si llega a 0, elimina el
 *     nodo (se saca solo UNA acepcion por llamada).
 *   - elegir(): devuelve el info del primer nodo (-1 si esta vacio).
 *   - perteneceCant(dato): devuelve la cantidad del nodo del dato, o 0 si no esta.
 *   - estaVacio(): true cuando no hay nodos (origen == null).
 *
 * 3) COMPLEJIDAD temporal
 * ---------------------------------------------------------------------------
 *   inicializar()      -> C (constante)
 *   guardar(dato)      -> L (lineal)  (busca el dato antes de insertar)
 *   sacar(dato)        -> L (lineal)  (busca el dato para decrementar/eliminar)
 *   elegir()           -> C (constante)
 *   perteneceCant(d)   -> L (lineal)
 *   estaVacio()        -> C (constante)
 *   siendo n la cantidad de valores DISTINTOS guardados.
 *   Referencia: C = constante, L = lineal, P = polinomico.
 *
 * 4) JUSTIFICACION
 * ---------------------------------------------------------------------------
 * Al mantener un solo nodo por valor con un contador, los recorridos (guardar,
 * sacar, perteneceCant via buscarNodo) son L en la cantidad de valores distintos.
 * elegir() y estaVacio() son C porque solo consultan el primer nodo / la cabecera.
 * ===========================================================================
 */
public class ConjuntoMamushka implements ConjuntoMamushkaTDA{
    // Clase interna para el Nodo
    private class Nodo {
        int info;       // El número guardado
        int cantidad;   // Cuántas acepciones/capas tiene
        Nodo sig;       // Puntero al siguiente elemento
    }

    private Nodo origen; // Atributo principal del TDA

    @Override
    public void inicializar() {
        origen = null;
    }

    @Override
    public boolean estaVacio() {
        return (origen == null);
    }

    @Override
    public void guardar(int dato) {
        Nodo aux = buscarNodo(dato);

        // Si ya existe, solo sumamos una acepción (una capa más)
        if (aux != null) {
            aux.cantidad++;
        } else {
            // Si no existe, creamos un nodo nuevo y lo insertamos al principio (sin orden)
            Nodo nuevo = new Nodo();
            nuevo.info = dato;
            nuevo.cantidad = 1;
            nuevo.sig = origen;
            origen = nuevo;
        }
    }

    @Override
    public void sacar(int dato) {
        if (origen == null) return;

        // Caso especial: si es el primer nodo
        if (origen.info == dato) {
            origen.cantidad--;
            if (origen.cantidad == 0) {
                origen = origen.sig; // Si llegó a 0, se elimina el nodo
            }
            return;
        }

        // Caso general: buscar en el resto de la lista
        Nodo viajero = origen;
        while (viajero.sig != null && viajero.sig.info != dato) {
            viajero = viajero.sig;
        }

        // Si lo encontramos en viajero.sig
        if (viajero.sig != null) {
            viajero.sig.cantidad--;
            if (viajero.sig.cantidad == 0) {
                viajero.sig = viajero.sig.sig; // Desenganchamos el nodo
            }
        }
    }

    @Override
    public int elegir() {
        // Al no tener orden, podemos devolver directamente la info del primer nodo
        if (origen != null) {
            return origen.info;
        }
        return -1; // O lanzar una excepción según cómo manejen errores en tu cátedra
    }

    @Override
    public int perteneceCant(int dato) {
        Nodo aux = buscarNodo(dato);
        if (aux != null) {
            return aux.cantidad;
        }
        return 0; // Si no está, tiene 0 acepciones
    }

    // Método privado auxiliar para evitar repetir código de búsqueda
    private Nodo buscarNodo(int dato) {
        Nodo viajero = origen;
        while (viajero != null) {
            if (viajero.info == dato) {
                return viajero;
            }
            viajero = viajero.sig;
        }
        return null;
    }

}
