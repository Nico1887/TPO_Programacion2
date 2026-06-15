package Interfaz;

import tda.ConjuntoTDA;

/*
 * ===========================================================================
 * EJERCICIO 4 - DiccionarioSimpleModTDA
 * ---------------------------------------------------------------------------
 * Interfaz NUEVA basada en tda.DiccionarioSimpleTDA.
 *
 * Idea: ademas de almacenar una entrada (clave -> valor), el diccionario
 * registra un "factor de modificacion" (factorMod) que cuenta cuantas veces
 * se modifico el valor de una clave ya existente.
 *
 *   - La PRIMERA vez que se agrega una clave: factorMod = 0.
 *   - Cada vez POSTERIOR que se re-agrega la misma clave (modificando su
 *     valor): factorMod++.
 * ===========================================================================
 */
public interface DiccionarioSimpleModTDA {

    // Deja el diccionario vacio y listo para usar.
    void inicializarDiccionario();

    // Agrega/modifica la entrada para la clave dada.
    // 1ra vez: factorMod = 0; cada re-agregado posterior: factorMod++.
    void agregar(int clave, int valor);

    // Elimina la clave (y su valor y factorMod).
    void eliminar(int clave);

    // Devuelve el valor asociado a la clave (se supone clave existente).
    int recuperar(int clave);

    // Devuelve la cantidad de modificaciones (factorMod) de la clave.
    int recuperarMod(int clave);

    // Devuelve un imple.Conjunto con todas las claves del diccionario.
    ConjuntoTDA claves();
}
