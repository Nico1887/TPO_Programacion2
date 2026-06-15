package Imple;

import tda.*;
import imple.*;
import Interfaz.DiccionarioSimpleModTDA;

/*
 * ===========================================================================
 * EJERCICIO 4 - DiccionarioSimpleMod (implementacion)
 * ===========================================================================
 *
 * 1) ESTRATEGIA
 * ---------------------------------------------------------------------------
 * Se implementa el diccionario con una lista enlazada simple propia. Cada
 * nodo guarda: clave, valor y factorMod (cantidad de modificaciones del
 * valor). La lista NO se ordena: se inserta al frente como en las
 * implementaciones dinamicas de catedra.
 *
 *   - agregar(clave, valor):
 *       * si la clave NO existe -> se crea un nodo nuevo con factorMod = 0.
 *       * si la clave YA existe -> se actualiza el valor y se incrementa
 *         factorMod (cuenta una modificacion mas).
 *   - eliminar(clave): baja el nodo de la lista (caso primero / caso medio).
 *   - recuperar(clave): devuelve el valor (clave supuesta existente).
 *   - recuperarMod(clave): devuelve el factorMod (clave supuesta existente).
 *   - claves(): construye y devuelve un new imple.Conjunto con las claves.
 *
 * 4) JUSTIFICACION
 * ---------------------------------------------------------------------------
 * Se usa un metodo auxiliar Clave2Nodo que recorre linealmente la lista.
 * Todas las operaciones que buscan una clave son O(n) porque en el peor caso
 * recorren toda la lista. La complejidad se detalla en cada metodo.
 * ===========================================================================
 */
public class DiccionarioSimpleMod implements DiccionarioSimpleModTDA {

    // Celula de la lista de claves.
    private class Nodo {
        int clave;
        int valor;
        int factorMod; // cantidad de veces que se modifico el valor
        Nodo sig;
    }

    private Nodo origen; // referencia a la estructura

    @Override
    public void inicializarDiccionario() {
        // O(1): solo apunta el origen a null.
        origen = null;
    }

    // Devuelve el nodo de la clave o null si no existe.
    // COMPLEJIDAD: O(n) - recorre la lista en el peor caso.
    private Nodo Clave2Nodo(int clave) {
        Nodo aux = origen; // nodo viajero
        while (aux != null && aux.clave != clave) {
            aux = aux.sig;
        }
        return aux;
    }

    @Override
    public void agregar(int clave, int valor) {
        // COMPLEJIDAD: O(n) - por la busqueda de la clave (Clave2Nodo).
        // JUSTIFICACION: en el peor caso la clave no existe y se recorre
        // toda la lista; el alta/actualizacion en si es O(1).
        Nodo nc = Clave2Nodo(clave);
        if (nc == null) {
            // La clave no existe: alta nueva, factorMod arranca en 0.
            nc = new Nodo();
            nc.clave = clave;
            nc.factorMod = 0;
            nc.sig = origen;
            origen = nc; // nuevo origen
        } else {
            // La clave ya existe: se modifica el valor -> cuenta una mod mas.
            nc.factorMod++;
        }
        nc.valor = valor;
    }

    @Override
    public void eliminar(int clave) {
        // COMPLEJIDAD: O(n) - recorre la lista buscando el predecesor.
        if (origen != null) {
            if (origen.clave == clave) { // es el primero
                origen = origen.sig;
            } else { // es algun otro
                Nodo aux = origen; // nodo viajero
                while (aux.sig != null && aux.sig.clave != clave) {
                    aux = aux.sig;
                }
                if (aux.sig != null) {
                    aux.sig = aux.sig.sig;
                }
            }
        }
    }

    @Override
    public int recuperar(int clave) {
        // COMPLEJIDAD: O(n) - por la busqueda de la clave.
        // Se supone la clave existente (consigna).
        Nodo nc = Clave2Nodo(clave);
        return nc.valor;
    }

    @Override
    public int recuperarMod(int clave) {
        // COMPLEJIDAD: O(n) - por la busqueda de la clave.
        // Se supone la clave existente (consigna).
        Nodo nc = Clave2Nodo(clave);
        return nc.factorMod;
    }

    @Override
    public ConjuntoTDA claves() {
        // COMPLEJIDAD: O(n^2) en el peor caso.
        // JUSTIFICACION: se recorre la lista (n) y por cada clave se llama a
        // agregar del Conjunto, que internamente verifica pertenencia O(k).
        ConjuntoTDA c = new imple.Conjunto();
        c.inicializarConjunto();
        Nodo aux = origen; // nodo viajero
        while (aux != null) {
            c.agregar(aux.clave);
            aux = aux.sig;
        }
        return c;
    }
}
