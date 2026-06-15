package Interfaz;

import tda.PilaTDA;

/*
 * EJERCICIO 3 - MultiPilaTDA (basado en PilaTDA)
 *
 * ESTRATEGIA GENERAL:
 * Se define un nuevo TDA "MultiPila" que se comporta como una pila de enteros,
 * pero cuyas operaciones de inserción y borrado trabajan con una PilaTDA
 * completa por parámetro:
 *   - apilar(valores): apila la pila recibida ENCIMA de la multipila,
 *     preservando el orden de la pila recibida (su tope queda como nuevo tope).
 *   - desapilar(valores): solo desapila si el tope de la multipila coincide,
 *     elemento a elemento, con la pila recibida; en caso contrario no hace nada.
 *   - tope(cantidad): devuelve una PilaTDA con los primeros 'cantidad' elementos
 *     del tope de la multipila, preservando el orden (si cantidad supera el
 *     tamaño, devuelve todos).
 *
 * Las pilas recibidas por parámetro NUNCA deben quedar modificadas al terminar
 * cada método (se restauran), y tope() no debe alterar la multipila.
 */
public interface MultiPilaTDA {

    /** Inicializa la multipila (la deja vacía). */
    public void inicializarPila();

    /**
     * Inserta la pila recibida en el tope de la multipila.
     * Si la multipila actualmente es: (tope) 3 - 5 - 7
     * Y la pila que se recibe es:      (tope) 1 - 9
     * La multipila debe quedar:        (tope) 1 - 9 - 3 - 5 - 7
     * La pila recibida no debe quedar modificada.
     */
    public void apilar(PilaTDA valores);

    /**
     * Desapila la pila recibida por parámetro de la multipila,
     * solo si el tope de la multipila coincide con la pila recibida.
     * Si la multipila actualmente es: (tope) 7 - 2 - 8 - 9
     * Y la pila que se recibe es:      (tope) 7 - 2
     * La multipila debe quedar:        (tope) 8 - 9
     * Si en cambio la pila que se recibe es: (tope) 7 - 2 - 3
     * No deben realizarse cambios en la multipila,
     * dado que no coincide con la pila recibida.
     * La pila recibida no debe quedar modificada.
     */
    public void desapilar(PilaTDA valores);

    /**
     * Devuelve una pila con los valores que estén en el tope de la multipila.
     * La cantidad de valores a devolver se define por parámetro y debe
     * preservarse el orden. Si la cantidad es mayor al tamaño actual de la
     * multipila, se devuelven todos los valores.
     * Si la multipila actualmente es: (tope) 4 - 2 - 9 - 7
     * Y se recibe por parámetro un 2, debe devolverse la pila: (tope) 4 - 2
     * Si se recibe por parámetro un 5, debe devolverse la pila: (tope) 4 - 2 - 9 - 7
     * La multipila no debe quedar modificada.
     */
    public PilaTDA tope(int cantidad);

    /** Devuelve un booleano que indica si la multipila está vacía. */
    public boolean pilaVacia();
}
