package Interfaz;

/*
 * ConjuntoMamushkaTDA (multiconjunto / multiset)
 *
 * TDA nuevo basado en ConjuntoTDA, con la particularidad de que se permite
 * mas de una acepcion (ocurrencia) de cada elemento agregado. Igual que en
 * ConjuntoTDA, NO existe orden alguno entre los elementos.
 */
public interface ConjuntoMamushkaTDA {

    void inicializar();             // inicializa el TDA

    void guardar(int dato);         // agrega el elemento dato al TDA (una acepcion mas)

    void sacar(int dato);           // elimina del TDA una acepcion del elemento dato

    int elegir();                   // muestra un elemento agregado al TDA (arbitrario)

    int perteneceCant(int dato);    // devuelve la cantidad de veces que se
                                    // encuentra el elemento dato en el TDA (0 si no esta)

    boolean estaVacio();            // devuelve un boolean que informa si el TDA esta vacio
}
