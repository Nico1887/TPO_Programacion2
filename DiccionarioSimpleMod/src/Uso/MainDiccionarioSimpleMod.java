package Uso;

import tda.*;
import Interfaz.DiccionarioSimpleModTDA;
import Imple.DiccionarioSimpleMod;

/*
 * ===========================================================================
 * EJERCICIO 4 - MainDiccionarioSimpleMod (casos de prueba)
 * ---------------------------------------------------------------------------
 * 5) CASOS DE PRUEBA: driver ejecutable que demuestra:
 *      - agregar claves NUEVAS (factorMod = 0).
 *      - re-agregar las MISMAS claves (factorMod sube).
 *      - recuperar (valor) y recuperarMod (cantidad de modificaciones).
 *      - eliminar una clave.
 *      - claves() devuelve un imple.Conjunto con las claves.
 *
 * 6) PRESERVACION: el Conjunto devuelto por claves() es una estructura NUEVA
 *    (new imple.Conjunto), por lo que consumirlo NO afecta al diccionario.
 *    Se verifica al final volviendo a pedir las claves luego de vaciar la
 *    copia obtenida.
 * ===========================================================================
 */
public class MainDiccionarioSimpleMod {

    public static void main(String[] args) {
        DiccionarioSimpleModTDA d = new DiccionarioSimpleMod();
        d.inicializarDiccionario();

        System.out.println("=== Alta de claves NUEVAS (factorMod debe ser 0) ===");
        d.agregar(1, 100);
        d.agregar(2, 200);
        d.agregar(3, 300);
        System.out.println("clave 1 -> valor=" + d.recuperar(1) + " mod=" + d.recuperarMod(1));
        System.out.println("clave 2 -> valor=" + d.recuperar(2) + " mod=" + d.recuperarMod(2));
        System.out.println("clave 3 -> valor=" + d.recuperar(3) + " mod=" + d.recuperarMod(3));

        System.out.println();
        System.out.println("=== Re-agregar MISMAS claves (factorMod debe subir) ===");
        d.agregar(1, 111); // mod 1
        d.agregar(1, 112); // mod 2
        d.agregar(2, 222); // mod 1
        System.out.println("clave 1 -> valor=" + d.recuperar(1) + " mod=" + d.recuperarMod(1) + " (esperado valor=112 mod=2)");
        System.out.println("clave 2 -> valor=" + d.recuperar(2) + " mod=" + d.recuperarMod(2) + " (esperado valor=222 mod=1)");
        System.out.println("clave 3 -> valor=" + d.recuperar(3) + " mod=" + d.recuperarMod(3) + " (esperado valor=300 mod=0)");

        System.out.println();
        System.out.println("=== claves() devuelve un Conjunto con las claves ===");
        ConjuntoTDA cl = d.claves();
        imprimirConjunto(cl);

        System.out.println();
        System.out.println("=== Eliminar clave 2 ===");
        d.eliminar(2);
        ConjuntoTDA cl2 = d.claves();
        imprimirConjunto(cl2);
        System.out.println("clave 1 sigue -> valor=" + d.recuperar(1) + " mod=" + d.recuperarMod(1));
        System.out.println("clave 3 sigue -> valor=" + d.recuperar(3) + " mod=" + d.recuperarMod(3));

        System.out.println();
        System.out.println("=== PRESERVACION: consumir la copia de claves() no afecta al diccionario ===");
        ConjuntoTDA copia = d.claves();
        // Vaciamos la copia por completo.
        while (!copia.conjuntoVacio()) {
            copia.sacar(copia.elegir());
        }
        System.out.println("copia vaciada -> conjuntoVacio? " + copia.conjuntoVacio());
        // El diccionario debe seguir intacto.
        ConjuntoTDA verif = d.claves();
        System.out.print("claves del diccionario tras vaciar la copia: ");
        imprimirConjunto(verif);
    }

    // Imprime un Conjunto sin destruirlo: trabaja sobre una copia auxiliar.
    private static void imprimirConjunto(ConjuntoTDA c) {
        // Copiamos a un auxiliar para no modificar el conjunto recibido.
        ConjuntoTDA aux = new imple.Conjunto();
        aux.inicializarConjunto();
        String salida = "{ ";
        while (!c.conjuntoVacio()) {
            int x = c.elegir();
            salida = salida + x + " ";
            c.sacar(x);
            aux.agregar(x);
        }
        salida = salida + "}";
        // Restauramos el conjunto original.
        while (!aux.conjuntoVacio()) {
            int x = aux.elegir();
            c.agregar(x);
            aux.sacar(x);
        }
        System.out.println(salida);
    }
}
