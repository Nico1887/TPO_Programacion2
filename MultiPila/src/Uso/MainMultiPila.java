package Uso;

import Interfaz.MultiPilaTDA;
import Imple.MultiPila;
import tda.PilaTDA;
import imple.Pila;

/*
 * CASOS DE PRUEBA del Ejercicio 3 - MultiPilaTDA.
 *
 * Reproduce los ejemplos exactos del enunciado (TPO.md) y verifica:
 *   - apilar: (tope) 3-5-7 + pila (tope) 1-9  =>  (tope) 1-9-3-5-7
 *   - desapilar coincidente: (tope) 7-2-8-9 desapilando (tope) 7-2 => (tope) 8-9
 *   - desapilar NO coincidente: (tope) 7-2-8-9 desapilando (tope) 7-2-3 => sin cambios
 *   - tope: (tope) 4-2-9-7 con 2 => (tope) 4-2 ; con 5 => (tope) 4-2-9-7
 *
 * Además, verifica explícitamente la PRESERVACION de las pilas recibidas por
 * parámetro tras cada operación.
 */
public class MainMultiPila {

    public static void main(String[] args) {

        System.out.println("===== Ejercicio 3 - MultiPila =====");

        // ---------- Caso 1: apilar ----------
        System.out.println("\n--- Caso 1: apilar (3-5-7) + (1-9) => 1-9-3-5-7 ---");
        MultiPilaTDA mp = new MultiPila();
        mp.inicializarPila();

        // Construimos la multipila como (tope) 3 - 5 - 7 apilando una pila 3-5-7.
        PilaTDA base = construirPila(new int[]{7, 5, 3}); // se apila 7, luego 5, luego 3 => tope 3
        mp.apilar(base);
        System.out.print("MultiPila inicial: ");
        imprimirMultiPila(mp);

        PilaTDA p1 = construirPila(new int[]{9, 1}); // tope 1 - 9
        System.out.print("Pila a apilar:     ");
        imprimirPila(p1);

        mp.apilar(p1);
        System.out.print("MultiPila result:  ");
        imprimirMultiPila(mp); // esperado: 1 - 9 - 3 - 5 - 7

        System.out.print("Pila p1 preservada (esperado 1 - 9): ");
        imprimirPila(p1);

        // ---------- Caso 2: desapilar coincidente ----------
        System.out.println("\n--- Caso 2: desapilar coincidente (7-2-8-9) quitando (7-2) => 8-9 ---");
        MultiPilaTDA mp2 = new MultiPila();
        mp2.inicializarPila();
        mp2.apilar(construirPila(new int[]{9, 8, 2, 7})); // tope 7-2-8-9
        System.out.print("MultiPila inicial: ");
        imprimirMultiPila(mp2);

        PilaTDA quitar = construirPila(new int[]{2, 7}); // tope 7 - 2
        System.out.print("Pila a desapilar:  ");
        imprimirPila(quitar);

        mp2.desapilar(quitar);
        System.out.print("MultiPila result:  ");
        imprimirMultiPila(mp2); // esperado: 8 - 9

        System.out.print("Pila quitar preservada (esperado 7 - 2): ");
        imprimirPila(quitar);

        // ---------- Caso 3: desapilar NO coincidente ----------
        System.out.println("\n--- Caso 3: desapilar NO coincidente (7-2-8-9) quitando (7-2-3) => sin cambios ---");
        MultiPilaTDA mp3 = new MultiPila();
        mp3.inicializarPila();
        mp3.apilar(construirPila(new int[]{9, 8, 2, 7})); // tope 7-2-8-9
        System.out.print("MultiPila inicial: ");
        imprimirMultiPila(mp3);

        PilaTDA quitarNo = construirPila(new int[]{3, 2, 7}); // tope 7 - 2 - 3
        System.out.print("Pila a desapilar:  ");
        imprimirPila(quitarNo);

        mp3.desapilar(quitarNo);
        System.out.print("MultiPila result:  ");
        imprimirMultiPila(mp3); // esperado: 7 - 2 - 8 - 9 (sin cambios)

        System.out.print("Pila quitarNo preservada (esperado 7 - 2 - 3): ");
        imprimirPila(quitarNo);

        // ---------- Caso 4: tope ----------
        System.out.println("\n--- Caso 4: tope sobre (4-2-9-7) ---");
        MultiPilaTDA mp4 = new MultiPila();
        mp4.inicializarPila();
        mp4.apilar(construirPila(new int[]{7, 9, 2, 4})); // tope 4-2-9-7
        System.out.print("MultiPila inicial: ");
        imprimirMultiPila(mp4);

        System.out.print("tope(2) (esperado 4 - 2):         ");
        imprimirPila(mp4.tope(2));
        System.out.print("tope(5) (esperado 4 - 2 - 9 - 7): ");
        imprimirPila(mp4.tope(5));
        System.out.print("tope(0) (esperado vacia):         ");
        imprimirPila(mp4.tope(0));

        System.out.print("MultiPila tras tope (no modificada, esperado 4 - 2 - 9 - 7): ");
        imprimirMultiPila(mp4);

        // ---------- Caso 5: pilas vacias ----------
        System.out.println("\n--- Caso 5: casos borde con pila vacia ---");
        MultiPilaTDA mp5 = new MultiPila();
        mp5.inicializarPila();
        System.out.println("pilaVacia() recien inicializada (esperado true): " + mp5.pilaVacia());

        PilaTDA vacia = new Pila();
        vacia.inicializarPila();
        mp5.apilar(vacia); // apilar pila vacia no cambia nada
        System.out.println("pilaVacia() tras apilar pila vacia (esperado true): " + mp5.pilaVacia());

        mp5.apilar(construirPila(new int[]{2, 1})); // tope 1 - 2
        mp5.desapilar(vacia); // desapilar pila vacia: coincide trivialmente, no quita nada
        System.out.print("MultiPila tras desapilar vacia (esperado 1 - 2): ");
        imprimirMultiPila(mp5);
    }

    // Construye una PilaTDA apilando en orden los valores del arreglo.
    // El ULTIMO elemento del arreglo queda como tope.
    private static PilaTDA construirPila(int[] valores) {
        PilaTDA p = new Pila();
        p.inicializarPila();
        for (int i = 0; i < valores.length; i++) {
            p.apilar(valores[i]);
        }
        return p;
    }

    // Imprime una pila desde el tope al fondo SIN modificarla (la restaura).
    private static void imprimirPila(PilaTDA p) {
        PilaTDA aux = new Pila();
        aux.inicializarPila();
        String salida = "(tope) ";
        boolean primero = true;
        while (!p.pilaVacia()) {
            if (!primero) {
                salida = salida + " - ";
            }
            salida = salida + p.tope();
            aux.apilar(p.tope());
            p.desapilar();
            primero = false;
        }
        while (!aux.pilaVacia()) {
            p.apilar(aux.tope());
            aux.desapilar();
        }
        if (primero) {
            salida = salida + "(vacia)";
        }
        System.out.println(salida);
    }

    // Imprime una multipila desde el tope al fondo usando tope() (no la modifica).
    private static void imprimirMultiPila(MultiPilaTDA mp) {
        // tope con un numero muy grande devuelve todos los elementos.
        PilaTDA todos = mp.tope(1000000);
        imprimirPila(todos);
    }
}
