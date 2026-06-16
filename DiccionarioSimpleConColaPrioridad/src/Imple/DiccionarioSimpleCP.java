package Imple;

import tda.*;
import imple.*;

/*
 * ===========================================================================
 * EJERCICIO 5 - DiccionarioSimple implementado SOLO con una ColaPrioridad
 * ===========================================================================
 *
 * 1) ESTRATEGIA
 * -------------
 * Implementamos la interfaz tda.DiccionarioSimpleTDA usando como
 * UNICO almacenamiento interno una imple.ColaPrioridad (campo 'cola').
 *
 * El mapeo clave -> valor se logra guardando cada par con:
 *      cola.acolarPrioridad(valor, prioridad = clave)
 * es decir, la PRIORIDAD del nodo es la CLAVE y el INFO del nodo es el VALOR.
 * De esta forma, recorriendo la cola podemos identificar cada entrada por su
 * prioridad (clave) y leer su info (valor).
 *
 * Como la cola de prioridad NO ofrece busqueda directa, para todas las
 * operaciones de consulta/modificacion recorremos la cola desacolando hacia
 * una cola de prioridad AUXILIAR y, al terminar, restauramos TODO el contenido
 * en la cola original (respetando la regla de oro: la estructura no debe quedar
 * modificada salvo cuando la operacion lo requiera explicitamente, como agregar
 * o eliminar).
 *
 * Para devolver el conjunto de claves usamos un new imple.Conjunto (permitido
 * por el enunciado solo para el resultado de claves()).
 *
 * NO se usan arreglos ni nodos propios para almacenar: solo la ColaPrioridad.
 *
 *
 * 3) COSTO
 * -----------------------
 *   - inicializarDiccionario : C
 *   - recuperar(clave)       : L  (recorre toda la cola y la restaura)
 *   - eliminar(clave)        : L  (recorre toda la cola y la restaura)
 *   - agregar(clave,valor)   : L  (eliminar previo + acolar, cada uno L)
 *   - claves()               : P  (recorre la cola y por cada clave agregar() al
 *                                  conjunto tambien recorre: un recorrido dentro de otro)
 */
public class DiccionarioSimpleCP implements DiccionarioSimpleTDA {

    // UNICO almacenamiento interno permitido: una ColaPrioridad.
    private ColaPrioridadTDA cola;

    @Override
    public void inicializarDiccionario() {
        cola = new ColaPrioridad();
        cola.inicializarCola();
    }

    /*
     * Metodo auxiliar: vacia 'origen' acolando todos sus nodos (info, prioridad)
     * en 'destino'. Sirve para mover/restaurar contenido entre colas.
     */
    private void acolarTodo(ColaPrioridadTDA origen, ColaPrioridadTDA destino) {
        while (!origen.colaVacia()) {
            int valor = origen.primero();
            int clave = origen.prioridad();
            destino.acolarPrioridad(valor, clave);
            origen.desacolar();
        }
    }

    @Override
    public void agregar(int clave, int valor) {
        // Si la clave ya existe, la eliminamos primero para evitar duplicados
        // (esto tambien sirve para ACTUALIZAR el valor de una clave existente).
        eliminar(clave);
        // Guardamos el par: prioridad = clave, info = valor.
        cola.acolarPrioridad(valor, clave);
    }

    @Override
    public void eliminar(int clave) {
        // Reconstruimos la cola sin la entrada cuya prioridad sea 'clave'.
        ColaPrioridadTDA aux = new ColaPrioridad();
        aux.inicializarCola();

        while (!cola.colaVacia()) {
            int v = cola.primero();
            int k = cola.prioridad();
            cola.desacolar();
            if (k != clave) {
                aux.acolarPrioridad(v, k);
            }
            // si k == clave, NO la copiamos: queda eliminada.
        }

        // Restauramos el contenido (ya sin la clave) en la cola original.
        acolarTodo(aux, cola);
    }

    @Override
    public int recuperar(int clave) {
        ColaPrioridadTDA aux = new ColaPrioridad();
        aux.inicializarCola();
        int valor = -1; // valor por defecto si la clave no existe

        // Recorremos toda la cola buscando la prioridad == clave.
        while (!cola.colaVacia()) {
            int v = cola.primero();
            int k = cola.prioridad();
            if (k == clave) {
                valor = v;
            }
            aux.acolarPrioridad(v, k);
            cola.desacolar();
        }

        // Restauramos TODO al original (no se modifica la estructura).
        acolarTodo(aux, cola);

        return valor;
    }

    @Override
    public ConjuntoTDA claves() {
        ConjuntoTDA claves = new Conjunto();
        claves.inicializarConjunto();

        ColaPrioridadTDA aux = new ColaPrioridad();
        aux.inicializarCola();

        // Recorremos toda la cola agregando cada prioridad (clave) al conjunto.
        while (!cola.colaVacia()) {
            int v = cola.primero();
            int k = cola.prioridad();
            claves.agregar(k);
            aux.acolarPrioridad(v, k);
            cola.desacolar();
        }

        // Restauramos TODO al original.
        acolarTodo(aux, cola);

        return claves;
    }
}
