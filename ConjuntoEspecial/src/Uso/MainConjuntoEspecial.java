package Uso;

import Interfaz.ConjuntoEspecialTDA;
import Imple.ConjuntoEspecial;

/*
 * ============================================================================
 * 1) CASOS DE PRUEBA - ConjuntoEspecialTDA
 * ----------------------------------------------------------------------------
 * Driver que demuestra el funcionamiento del ConjuntoEspecial: cada operacion
 * devuelve una Respuesta cuyo campo error indica si la operacion fue valida.
 *   - inicializar / conjuntoVacio
 *   - elegir sobre conjunto vacio  -> error = true
 *   - agregar valor nuevo          -> error = false
 *   - agregar valor repetido       -> error = true
 *   - elegir sobre conjunto lleno  -> error = false, rta = valor
 *   - sacar valor existente        -> error = false
 *   - sacar valor inexistente      -> error = true
 * ============================================================================
 */
public class MainConjuntoEspecial {

    public static void main(String[] args) {

        ConjuntoEspecialTDA conjunto = new ConjuntoEspecial();
        conjunto.inicializarConjunto();
        ConjuntoEspecialTDA.Respuesta res;

        System.out.println("Conjunto recien inicializado esta vacio (esperado true):");
        System.out.println(conjunto.conjuntoVacio());

        System.out.println("Elegir en un conjunto vacio (error esperado true):");
        res = conjunto.elegir();
        System.out.println(res.error);

        System.out.println("Agregar un valor nuevo (error esperado false):");
        res = conjunto.agregar(10);
        System.out.println(res.error);

        System.out.println("Agregar otro valor nuevo (error esperado false):");
        res = conjunto.agregar(20);
        System.out.println(res.error);

        System.out.println("Agregar un valor repetido (error esperado true):");
        res = conjunto.agregar(10);
        System.out.println(res.error);

        System.out.println("El conjunto ya no esta vacio (esperado false):");
        System.out.println(conjunto.conjuntoVacio());

        System.out.println("Elegir en un conjunto con elementos (error false y un valor):");
        res = conjunto.elegir();
        System.out.println("Error: " + res.error + " - Valor: " + res.rta);

        System.out.println("Sacar un elemento que existe (error esperado false):");
        res = conjunto.sacar(10);
        System.out.println(res.error);

        System.out.println("Sacar un elemento que ya no existe (error esperado true):");
        res = conjunto.sacar(10);
        System.out.println(res.error);
    }
}
