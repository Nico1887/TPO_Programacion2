package Imple;

import Interfaz.ConjuntoMamushkaTDA;

/*
 * ============================================================================
 * 1) ESTRATEGIA
 * ----------------------------------------------------------------------------
 * Implementamos el multiconjunto con una lista enlazada dinamica propia.
 * Para evitar nodos repetidos, cada nodo guarda un valor distinto (info) junto
 * con la cantidad de acepciones de ese valor (cantidad). De esta forma, agregar
 * o sacar una acepcion de un valor ya presente solo modifica el contador, y la
 * lista nunca contiene dos nodos con el mismo info.
 *
 *   class Nodo { int info; int cantidad; Nodo sig; }
 *
 * - guardar(dato): si el dato ya esta, suma 1 a su cantidad; si no, crea un
 *   nodo nuevo al frente con cantidad 1.
 * - sacar(dato): busca el nodo del dato; si existe, le resta 1 a la cantidad,
 *   y si la cantidad llega a 0 elimina el nodo de la lista (queda eliminada
 *   solo UNA acepcion por llamada).
 * - elegir(): devuelve el info del primer nodo (elemento arbitrario presente).
 * - perteneceCant(dato): recorre la lista y devuelve la cantidad del nodo del
 *   dato, o 0 si no esta.
 * - estaVacio(): la lista esta vacia cuando no hay nodos (c == null). Como todo
 *   nodo se elimina al llegar a cantidad 0, c == null equivale a sin acepciones.
 * ----------------------------------------------------------------------------
 * 3) COSTO
 * ----------------------------------------------------------------------------
 *   inicializar()      -> C
 *   guardar(dato)      -> L  (busca el dato antes de insertar)
 *   sacar(dato)        -> L  (busca el dato para decrementar/eliminar)
 *   elegir()           -> C  (devuelve el primer nodo)
 *   perteneceCant(d)   -> L  (recorre la lista)
 *   estaVacio()        -> C  (mira el puntero de cabecera)
 * ============================================================================
 */
public class ConjuntoMamushka implements ConjuntoMamushkaTDA {

    public class Nodo {
        int info;
        int cantidad;
        Nodo sig;
    }

    Nodo c;

    @Override
    public void inicializar() {
        c = null;
    }

    @Override
    public void guardar(int dato) {
        // Buscamos si el dato ya tiene un nodo
        Nodo aux = c;
        while ((aux != null) && (aux.info != dato)) {
            aux = aux.sig;
        }

        if (aux != null) {
            // Ya existe: sumamos una acepcion
            aux.cantidad = aux.cantidad + 1;
        } else {
            // No existe: creamos un nodo nuevo al frente
            Nodo nuevo = new Nodo();
            nuevo.info = dato;
            nuevo.cantidad = 1;
            nuevo.sig = c;
            c = nuevo;
        }
    }

    @Override
    public void sacar(int dato) {
        Nodo aux = c;
        Nodo ant = null;

        // Buscamos el nodo del dato, recordando el anterior
        while ((aux != null) && (aux.info != dato)) {
            ant = aux;
            aux = aux.sig;
        }

        if (aux != null) {
            // Eliminamos UNA acepcion
            aux.cantidad = aux.cantidad - 1;

            // Si ya no quedan acepciones, sacamos el nodo de la lista
            if (aux.cantidad == 0) {
                if (ant == null) {
                    c = aux.sig;
                } else {
                    ant.sig = aux.sig;
                }
            }
        }
    }

    @Override
    public int elegir() {
        // Devuelve un elemento arbitrario presente (el primero de la lista)
        return c.info;
    }

    @Override
    public int perteneceCant(int dato) {
        Nodo aux = c;
        while ((aux != null) && (aux.info != dato)) {
            aux = aux.sig;
        }

        if (aux != null) {
            return aux.cantidad;
        }
        return 0;
    }

    @Override
    public boolean estaVacio() {
        return c == null;
    }
}
