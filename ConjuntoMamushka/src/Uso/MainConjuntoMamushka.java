package Uso;

import Interfaz.ConjuntoMamushkaTDA;
import Imple.ConjuntoMamushka;

/*
 * ============================================================================
 * 5) CASOS DE PRUEBA
 * ----------------------------------------------------------------------------
 * Driver que demuestra el funcionamiento del ConjuntoMamushka:
 *   - guardar repetidos (varias acepciones del mismo dato)
 *   - perteneceCant (incluye el caso de un dato inexistente -> 0)
 *   - sacar UNA acepcion por llamada
 *   - estaVacio
 *   - elegir (elemento arbitrario presente)
 *
 * Nota sobre PRESERVACION (punto 6): el ConjuntoMamushka es el TDA que estamos
 * implementando, no un parametro recibido. Los metodos de consulta del TDA
 * (perteneceCant, elegir, estaVacio) NO modifican la estructura interna; abajo
 * se verifica que invocarlos repetidamente no altera las cantidades guardadas.
 * ============================================================================
 */
public class MainConjuntoMamushka {

    public static void main(String[] args) {

        ConjuntoMamushkaTDA m = new ConjuntoMamushka();
        m.inicializar();

        System.out.println("== Estado inicial ==");
        System.out.println("estaVacio() = " + m.estaVacio() + " (esperado true)");

        System.out.println();
        System.out.println("== guardar repetidos ==");
        // 7 -> 3 acepciones, 5 -> 2 acepciones, 9 -> 1 acepcion
        m.guardar(7);
        m.guardar(5);
        m.guardar(7);
        m.guardar(9);
        m.guardar(5);
        m.guardar(7);

        System.out.println("estaVacio() = " + m.estaVacio() + " (esperado false)");
        System.out.println("perteneceCant(7) = " + m.perteneceCant(7) + " (esperado 3)");
        System.out.println("perteneceCant(5) = " + m.perteneceCant(5) + " (esperado 2)");
        System.out.println("perteneceCant(9) = " + m.perteneceCant(9) + " (esperado 1)");
        System.out.println("perteneceCant(100) = " + m.perteneceCant(100) + " (esperado 0, no esta)");

        System.out.println();
        System.out.println("== sacar UNA acepcion de 7 ==");
        m.sacar(7);
        System.out.println("perteneceCant(7) = " + m.perteneceCant(7) + " (esperado 2)");

        System.out.println();
        System.out.println("== sacar hasta agotar el 5 ==");
        m.sacar(5);
        System.out.println("perteneceCant(5) = " + m.perteneceCant(5) + " (esperado 1)");
        m.sacar(5);
        System.out.println("perteneceCant(5) = " + m.perteneceCant(5) + " (esperado 0, ya no esta)");

        System.out.println();
        System.out.println("== sacar un dato inexistente (no debe romper) ==");
        m.sacar(12345);
        System.out.println("perteneceCant(12345) = " + m.perteneceCant(12345) + " (esperado 0)");

        System.out.println();
        System.out.println("== elegir (elemento arbitrario presente) ==");
        int elegido = m.elegir();
        System.out.println("elegir() = " + elegido + " (debe ser uno de los datos presentes)");
        System.out.println("perteneceCant(" + elegido + ") = " + m.perteneceCant(elegido) + " (debe ser > 0)");

        System.out.println();
        System.out.println("== Verificacion: las consultas NO modifican el TDA ==");
        int antes7 = m.perteneceCant(7);
        m.elegir();
        m.elegir();
        m.estaVacio();
        m.perteneceCant(7);
        int despues7 = m.perteneceCant(7);
        System.out.println("perteneceCant(7) antes = " + antes7 + ", despues de consultas = " + despues7
                + " -> preservado: " + (antes7 == despues7));

        System.out.println();
        System.out.println("== Vaciar el multiconjunto ==");
        // Quedan: 7 -> 2, 9 -> 1
        m.sacar(7);
        m.sacar(7);
        m.sacar(9);
        System.out.println("perteneceCant(7) = " + m.perteneceCant(7) + " (esperado 0)");
        System.out.println("perteneceCant(9) = " + m.perteneceCant(9) + " (esperado 0)");
        System.out.println("estaVacio() = " + m.estaVacio() + " (esperado true)");
    }
}
