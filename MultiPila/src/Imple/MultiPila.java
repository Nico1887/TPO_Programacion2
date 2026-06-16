package Imple;

import Interfaz.MultiPilaTDA;
import tda.PilaTDA;
import imple.Pila;

/*
 * IMPLEMENTACION de MultiPilaTDA con estructura dinámica propia (lista enlazada
 * con inner class Nodo). El tope de la multipila es la CABEZA de la lista (m),
 * de modo que apilar/desapilar en la cabeza es O(1).
 *
 * Representación interna:
 *   m -> [tope] -> ... -> [fondo] -> null
 *
 * Las pilas recibidas por parámetro se recorren apoyándose en una pila auxiliar
 * (imple.Pila) y SIEMPRE se restauran antes de retornar, de modo que el llamador
 * no perciba modificación alguna (REGLA DE ORO de la cátedra).
 */
public class MultiPila implements MultiPilaTDA {

    // Lista dinámica propia. El tope de la multipila es 'm' (la cabeza).
    public class Nodo {
        int info;
        Nodo sig;
    }

    Nodo m;

    @Override
    public void inicializarPila() {
        m = null;
    }

    @Override
    public boolean pilaVacia() {
        return m == null;
    }

    /*
     * apilar: coloca la pila recibida ENCIMA de la multipila, preservando su orden.
     *
     * Ejemplo: multipila (tope) 3 - 5 - 7 ; pila recibida (tope) 1 - 9
     *          resultado (tope) 1 - 9 - 3 - 5 - 7
     *
     * Para lograr que el tope de la pila recibida (1) quede como nuevo tope de la
     * multipila, debemos insertar los elementos de la pila recibida empezando por
     * el FONDO (9) y terminando por el tope (1). Como una PilaTDA solo da acceso
     * al tope, la "damos vuelta" a una pila auxiliar para luego recorrerla desde
     * el fondo hacia el tope, apilando cada elemento en la cabeza de la multipila.
     *
     * COMPLEJIDAD: O(k), siendo k la cantidad de elementos de la pila recibida.
     * JUSTIFICACION: se recorre la pila recibida un número constante de veces
     * (volcado a auxiliar, inserción en la multipila y restauración), cada uno O(k).
     */
    @Override
    public void apilar(PilaTDA valores) {
        // Pila auxiliar con el orden invertido de 'valores' (su fondo queda en tope).
        PilaTDA invertida = new Pila();
        invertida.inicializarPila();

        // Volcamos 'valores' a 'invertida'. Al hacerlo, el fondo de 'valores'
        // (9 en el ejemplo) queda en el tope de 'invertida'.
        while (!valores.pilaVacia()) {
            invertida.apilar(valores.tope());
            valores.desapilar();
        }

        // Recorremos 'invertida' (fondo->tope original) insertando en la cabeza de
        // la multipila. Así el tope original (1) termina siendo el último insertado
        // y queda como tope de la multipila. A la vez restauramos 'valores'.
        while (!invertida.pilaVacia()) {
            int valor = invertida.tope();

            // Insertar 'valor' en la cabeza de la multipila.
            Nodo nuevo = new Nodo();
            nuevo.info = valor;
            nuevo.sig = m;
            m = nuevo;

            // Restaurar 'valores' a su estado original.
            valores.apilar(valor);
            invertida.desapilar();
        }
    }

    /*
     * desapilar: quita de la multipila los elementos de la pila recibida, pero
     * SOLO si el tope de la multipila coincide, elemento a elemento, con la pila
     * recibida (mismo orden). Si no coincide (o la multipila tiene menos elementos
     * que la pila recibida), no realiza ningún cambio.
     *
     * Ejemplo: multipila (tope) 7 - 2 - 8 - 9 ; pila recibida (tope) 7 - 2
     *          resultado (tope) 8 - 9
     *          pila recibida (tope) 7 - 2 - 3  ->  no hay cambios.
     *
     * COMPLEJIDAD: O(k), siendo k la cantidad de elementos de la pila recibida.
     * JUSTIFICACION: se compara recorriendo la pila recibida y la cima de la
     * multipila a lo sumo k posiciones; la pila recibida se restaura recorriéndola
     * otra vez, todo lineal en k.
     */
    @Override
    public void desapilar(PilaTDA valores) {
        // Auxiliar para volcar 'valores' y poder restaurarla luego.
        PilaTDA aux = new Pila();
        aux.inicializarPila();

        boolean coincide = true;
        Nodo recorre = m; // cima actual de la multipila.

        // Comparamos el tope de 'valores' contra la cima de la multipila.
        while (!valores.pilaVacia()) {
            int valor = valores.tope();
            aux.apilar(valor);
            valores.desapilar();

            if (coincide) {
                if (recorre == null || recorre.info != valor) {
                    // La multipila se agotó antes, o un valor no coincide.
                    coincide = false;
                } else {
                    recorre = recorre.sig;
                }
            }
        }

        // Restauramos 'valores' a su estado original (no debe quedar modificada).
        while (!aux.pilaVacia()) {
            valores.apilar(aux.tope());
            aux.desapilar();
        }

        // Si todo coincidió, recortamos la cima de la multipila hasta 'recorre'.
        // 'recorre' apunta justo al primer nodo que NO se desapila.
        if (coincide) {
            m = recorre;
        }
    }

    /*
     * tope: devuelve una PilaTDA con los primeros 'cantidad' elementos del tope de
     * la multipila, preservando el orden (el tope de la multipila queda como tope
     * de la pila devuelta). Si 'cantidad' supera el tamaño, devuelve todos.
     * No modifica la multipila.
     *
     * Ejemplo: multipila (tope) 4 - 2 - 9 - 7 ; cantidad 2 -> (tope) 4 - 2
     *          cantidad 5 -> (tope) 4 - 2 - 9 - 7
     *
     * COMPLEJIDAD: O(c), siendo c = min(cantidad, tamaño de la multipila).
     * JUSTIFICACION: se recorren a lo sumo 'c' nodos de la multipila y se realizan
     * dos pasadas sobre una pila auxiliar de tamaño c para preservar el orden.
     */
    @Override
    public PilaTDA tope(int cantidad) {
        // Tomamos los primeros 'cantidad' elementos en una auxiliar; al recorrer
        // la multipila desde el tope, la auxiliar queda con el orden invertido.
        PilaTDA aux = new Pila();
        aux.inicializarPila();

        Nodo recorre = m;
        int tomados = 0;
        while (recorre != null && tomados < cantidad) {
            aux.apilar(recorre.info);
            recorre = recorre.sig;
            tomados++;
        }

        // Volcamos 'aux' a la pila resultado para recuperar el orden correcto
        // (el tope de la multipila vuelve a quedar como tope del resultado).
        PilaTDA resultado = new Pila();
        resultado.inicializarPila();
        while (!aux.pilaVacia()) {
            resultado.apilar(aux.tope());
            aux.desapilar();
        }

        return resultado;
    }
}
