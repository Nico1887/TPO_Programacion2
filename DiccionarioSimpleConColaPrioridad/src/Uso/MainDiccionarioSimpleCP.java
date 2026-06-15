package Uso;

import Imple.DiccionarioSimpleCP;
import tda.*;
import imple.*;

/*
 * 5) CASOS DE PRUEBA - Driver del DiccionarioSimpleCP
 * ---------------------------------------------------
 * Demuestra: agregar, recuperar, actualizar el valor de una clave existente,
 * eliminar y claves(). Tambien verifica que la operacion recuperar() y claves()
 * NO modifican la estructura interna (las claves siguen presentes despues).
 */
public class MainDiccionarioSimpleCP {

    // Imprime el conjunto de claves de forma legible, sin modificarlo de manera
    // permanente (lo reconstruye al terminar).
    public static void imprimirClaves(ConjuntoTDA c) {
        ConjuntoTDA aux = new Conjunto();
        aux.inicializarConjunto();

        System.out.print("{ ");
        while (!c.conjuntoVacio()) {
            int e = c.elegir();
            System.out.print(e + " ");
            aux.agregar(e);
            c.sacar(e);
        }
        System.out.println("}");

        while (!aux.conjuntoVacio()) {
            int e = aux.elegir();
            c.agregar(e);
            aux.sacar(e);
        }
    }

    public static void main(String[] args) {
        DiccionarioSimpleTDA dicc = new DiccionarioSimpleCP();
        dicc.inicializarDiccionario();

        System.out.println("=== Agregar entradas ===");
        dicc.agregar(1, 100); // clave 1 -> valor 100
        dicc.agregar(2, 200); // clave 2 -> valor 200
        dicc.agregar(3, 300); // clave 3 -> valor 300
        System.out.println("Agregadas claves 1, 2, 3");

        System.out.println("\n=== Recuperar ===");
        System.out.println("Valor de clave 1 (esperado 100): " + dicc.recuperar(1));
        System.out.println("Valor de clave 2 (esperado 200): " + dicc.recuperar(2));
        System.out.println("Valor de clave 3 (esperado 300): " + dicc.recuperar(3));

        System.out.println("\n=== Claves actuales ===");
        imprimirClaves(dicc.claves());

        System.out.println("\n=== Actualizar valor de una clave existente ===");
        dicc.agregar(2, 999); // actualiza clave 2 -> 999 (no debe duplicar)
        System.out.println("Valor de clave 2 tras actualizar (esperado 999): " + dicc.recuperar(2));
        System.out.print("Claves tras actualizar (deben seguir siendo 1,2,3, sin duplicados): ");
        imprimirClaves(dicc.claves());

        System.out.println("\n=== Eliminar ===");
        dicc.eliminar(1);
        System.out.println("Eliminada clave 1");
        System.out.print("Claves tras eliminar la 1 (esperado 2,3): ");
        imprimirClaves(dicc.claves());
        System.out.println("Recuperar clave 1 eliminada (esperado -1): " + dicc.recuperar(1));

        System.out.println("\n=== Verificacion de PRESERVACION ===");
        // recuperar() y claves() no deben alterar el contenido del diccionario.
        dicc.recuperar(2);
        dicc.claves();
        System.out.println("Tras varias consultas, valor de clave 2 (esperado 999): " + dicc.recuperar(2));
        System.out.println("Tras varias consultas, valor de clave 3 (esperado 300): " + dicc.recuperar(3));
        System.out.print("Claves finales (esperado 2,3): ");
        imprimirClaves(dicc.claves());
    }
}
