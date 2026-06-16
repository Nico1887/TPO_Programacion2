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
 * Implementamos la interfaz tda.DiccionarioSimpleTDA (la del jar) usando como
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
 * 3) COMPLEJIDAD TEMPORAL
 * -----------------------
 * Sea N la cantidad de entradas (claves) del diccionario.
 *   - inicializarDiccionario : C (constante)
 *   - recuperar(clave)       : L (lineal)  (un recorrido completo + restauracion)
 *   - eliminar(clave)        : L (lineal)
 *   - agregar(clave,valor)   : L (lineal)  (eliminar previo L + acolarPrioridad L)
 *   - claves()               : P (polinomico) en el peor caso por el pertenece()
 *                              del Conjunto al insertar cada clave; el recorrido
 *                              es L pero anidado con un pertenece L da P (~N^2).
 *   Referencia: C = constante, L = lineal (crece con N), P = polinomico (~N^2).
 *
 * 4) JUSTIFICACION
 * ----------------
 * La ColaPrioridad es una estructura lineal sin acceso por clave, por lo que
 * cualquier consulta obliga a recorrerla entera (L). El metodo auxiliar
 * 'acolarTodo' mueve los nodos de una cola a otra preservando info y prioridad,
 * lo que nos permite restaurar la cola original sin perder datos. acolarPrioridad
 * de la implementacion del jar inserta ordenado, por eso es L cada insercion.
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
