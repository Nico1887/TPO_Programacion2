package Uso;

import imple.*;
import tda.*;

/*
 * ============================================================================
 * METODOS EXTERNOS DEL TPO (Parte 1, puntos 6 a 15)
 * ----------------------------------------------------------------------------
 * Estos ejercicios NO definen un TDA nuevo: son metodos que reciben las
 * estructuras provistas (Pila, Cola, ABB, Grafo, DiccionarioSimple,
 * DiccionarioMultiple, Conjunto) y devuelven un resultado, preservando
 * siempre las estructuras recibidas.
 *
 * Por eso todos viven en un UNICO Main general (los TDA nuevos -puntos 1 a 5-
 * tienen cada uno su propio Main en su modulo).
 * ============================================================================
 */
public class Main {

    /*6) Se define un metodo que reciba una PilaTDA y devuelva un float (número real) con
    el porcentaje de cantidad de elementos pares de la pila. */
    /* ESTRATEGIA: se desapila hacia una pila auxiliar contando pares y total; al terminar se
       restaura la pila y se devuelve pares/total*100.
       COSTO: L — dos recorridos secuenciales de la pila; las operaciones de pila son C.
       PRESERVACION: se restaura la pila original desde la auxiliar. */
    public static float mostrarPorcentajePares(PilaTDA pila){
        float porcentajePares = 0;
        float cantidadPares = 0;
        float cantidadElementos = 0;
        PilaTDA aux = new Pila();
        aux.inicializarPila();

        while(!pila.pilaVacia()){
            int primero = pila.tope();
            if(primero % 2 == 0){
                cantidadPares++;
            }

            aux.apilar(pila.tope());
            pila.desapilar();
            cantidadElementos++;
        }

        while(!aux.pilaVacia()){
            pila.apilar(aux.tope());
            aux.desapilar();
        }

        return porcentajePares = (cantidadPares / cantidadElementos) * 100;
    }

    /*7)Se define un metodo que reciba una PilaTDA y devuelva un ConjuntoTDA con los
    elementos repetidos de la pila. */
    /* ESTRATEGIA: se desapila hacia una auxiliar; con un conjunto de vistos, si el tope ya
       pertenece se agrega al resultado. Al terminar se restaura la pila.
       COSTO: P — recorrido de la pila con pertenece()/agregar() del conjunto adentro, que
       tambien recorren (un recorrido dentro de otro).
       PRESERVACION: se restaura la pila; el conjunto resultado es nuevo. */
    public static ConjuntoTDA elementosRepetidos(PilaTDA pila){
        ConjuntoTDA repetidos = new Conjunto();
        repetidos.inicializarConjunto();
        ConjuntoTDA auxC =  new Conjunto();
        auxC.inicializarConjunto();
        PilaTDA auxP = new Pila();
        auxP.inicializarPila();

        while(!pila.pilaVacia()){
            if(auxC.pertenece(pila.tope())){
                repetidos.agregar(pila.tope());
            }
            auxC.agregar(pila.tope());
            auxP.apilar(pila.tope());
            pila.desapilar();

        }

        while(!auxP.pilaVacia()){
            pila.apilar(auxP.tope());
            auxP.desapilar();
        }

        return repetidos;
    }

    /*8) Se define un método que reciba una ColaTDA y devuelva una nueva ColaTDA con los
    elementos de la original, sin ninguna repetición. Se debe dejar el primer representante de
    cada uno de los repetidos, respetando el orden en que aparecen todos los elementos en la
    original.*/
    /* ESTRATEGIA: se desacola hacia una auxiliar; con un conjunto de vistos, el primer
       representante de cada valor se acola en la cola resultado. Al terminar se restaura.
       COSTO: P — recorrido de la cola con pertenece()/agregar() del conjunto adentro, que
       tambien recorren (un recorrido dentro de otro).
       PRESERVACION: se restaura la cola original; la cola resultado es nueva. */
    public static ColaTDA colaSinRepetidos(ColaTDA colaOriginal) {
        ConjuntoTDA conjunto = new Conjunto();
        ColaTDA colaAux = new Cola();
        ColaTDA colaFinal = new Cola();
        conjunto.inicializarConjunto();
        colaAux.inicializarCola();
        colaFinal.inicializarCola();

        while (!colaOriginal.colaVacia()) {
            int primero = colaOriginal.primero();
            colaOriginal.desacolar();
            colaAux.acolar(primero);
            if (!conjunto.pertenece(primero)) {
                conjunto.agregar(primero);
                colaFinal.acolar(primero);
            }
        }

        while (!colaAux.colaVacia()) {
            int primero = colaAux.primero();
            colaAux.desacolar();
            colaOriginal.acolar(primero);
        }
        return colaFinal;
    }

    /*9) Se define un metodo que reciba una PilaTDA y una ColaTDA y devuelva un
    ConjuntoTDA con los elementos comunes de la pila y de la cola. */
    /* ESTRATEGIA: por cada tope de la pila se recorre toda la cola buscando coincidencia; si
       esta en ambas se agrega al resultado. Al terminar se restauran pila y cola.
       COSTO: P — recorrido de la cola anidado dentro del recorrido de la pila.
       PRESERVACION: se restauran pila y cola; el conjunto resultado es nuevo. */
    public static ConjuntoTDA obtenerElementosComunes(PilaTDA pila, ColaTDA cola){
        ConjuntoTDA comunes = new Conjunto();
        comunes.inicializarConjunto();
        PilaTDA auxP = new Pila();
        auxP.inicializarPila();
        ColaTDA auxCola = new Cola();
        auxCola.inicializarCola();

        while(!pila.pilaVacia()){
            int tope = pila.tope();
            while(!cola.colaVacia()){
                if(tope == cola.primero()){
                    comunes.agregar(tope);
                }
                auxCola.acolar(cola.primero());
                cola.desacolar();
            }
            auxP.apilar(tope);
            pila.desapilar();

            while(!auxCola.colaVacia()){
                cola.acolar(auxCola.primero());
                auxCola.desacolar();
            }
        }

        while(!auxP.pilaVacia()){
            pila.apilar(auxP.tope());
            auxP.desapilar();
        }

        return comunes;
    }

    /*10) Se define un método que reciba una PilaTDA y devuelva un DiccionarioSimpleTDA, en el cual se guardarán los
    elementos de la pila como claves, y la cantidad de apariciones de dicho elemento en la pila, como valores */
    /* ESTRATEGIA: se desapila hacia una auxiliar; por cada tope, si ya es clave se le suma 1,
       si no se agrega con 1. Al terminar se restaura la pila.
       COSTO: P — recorrido de la pila con operaciones de diccionario/conjunto adentro, que
       tambien recorren (un recorrido dentro de otro).
       PRESERVACION: se restaura la pila; el diccionario resultado es nuevo. */
    public static DiccionarioSimpleTDA contarAparicionesPila(PilaTDA pilaOriginal) {
        DiccionarioSimpleTDA diccFinal = new DiccionarioSimple();
        PilaTDA pilaAux = new Pila();
        diccFinal.inicializarDiccionario();
        pilaAux.inicializarPila();
        while (!pilaOriginal.pilaVacia()) {
            int topeActual = pilaOriginal.tope();
            ConjuntoTDA claves = diccFinal.claves();
            if (claves.pertenece(topeActual)) {
                int cantidad = diccFinal.recuperar(topeActual);
                diccFinal.agregar(topeActual, cantidad + 1);
            } else {
                diccFinal.agregar(topeActual, 1);
            }
            pilaAux.apilar(topeActual);
            pilaOriginal.desapilar();
        }
        while (!pilaAux.pilaVacia()) {
            pilaOriginal.apilar(pilaAux.tope());
            pilaAux.desapilar();
        }
        return diccFinal;
    }

    /*11) Se define un método que reciba un DiccionarioMultipleTDA y devuelva una ColaTDA
    con todos los valores del diccionario, sin repetición. */
    /* ESTRATEGIA: se recorre una copia de las claves y, por cada una, una copia de sus
       valores; con un conjunto de vistos se acola cada valor una sola vez.
       COSTO: P — recorrido de valores anidado dentro del recorrido de claves, con
       pertenece()/agregar() del conjunto adentro.
       PRESERVACION: se trabaja sobre copias; el diccionario no se modifica. */
    public static ColaTDA valoresUnicos(DiccionarioMultipleTDA dicc) {
        ColaTDA resultado = new Cola();
        resultado.inicializarCola();
        ConjuntoTDA vistos = new Conjunto();
        vistos.inicializarConjunto();

        ConjuntoTDA claves = dicc.claves();
        while (!claves.conjuntoVacio()) {
            int clave = claves.elegir();
            claves.sacar(clave);

            ConjuntoTDA valores = dicc.recuperar(clave);
            while (!valores.conjuntoVacio()) {
                int valor = valores.elegir();
                valores.sacar(valor);
                if (!vistos.pertenece(valor)) {
                    vistos.agregar(valor);
                    resultado.acolar(valor);
                }
            }
        }

        return resultado;
    }

    /*12) Se define un método que calcule la suma de los elementos con un valor impar de un
    ABB. */
    /* ESTRATEGIA: recorrido recursivo; si la raiz es impar se acumula y se suma lo de ambos hijos.
       COSTO: L — visita cada nodo una vez; las consultas sobre el arbol son C.
       PRESERVACION: solo consulta el arbol; no lo modifica. */
    public static int sumarImparesABB(ABBTDA arbol) {
        int suma = 0;

        if (arbol.arbolVacio()) {
            return 0;
        }

        int valorRaiz = arbol.raiz();

        if (valorRaiz % 2 != 0) {
            suma = valorRaiz;
        }

        suma = suma + sumarImparesABB(arbol.hijoIzq());
        suma = suma + sumarImparesABB(arbol.hijoDer());

        return suma;
    }

    /*13) Se define un método que reciba un ABBTDA y devuelva la cantidad de hojas (nodos
    sin hijos) cuyo valor es par. */
    /* ESTRATEGIA: recorrido recursivo; una hoja cuenta 1 si es par, y si no es hoja se suman
       las hojas pares de ambos hijos.
       COSTO: L — visita cada nodo una vez; las consultas sobre el arbol son C.
       PRESERVACION: solo consulta el arbol; no lo modifica. */
    public static int hojasPares(ABBTDA arbol) {
        if (arbol.arbolVacio()) {
            return 0;
        }

        ABBTDA izq = arbol.hijoIzq();
        ABBTDA der = arbol.hijoDer();

        if (izq.arbolVacio() && der.arbolVacio()) {
            if (arbol.raiz() % 2 == 0) {
                return 1;
            }
            return 0;
        }

        return hojasPares(izq) + hojasPares(der);
    }

    /*14) Se define un método que reciba un GrafoTDA y dos vértices o (origen) y d (destino)
    y devuelva un ConjuntoTDA con los vértices puente p tales que existe arista (o,p) y
    existe arista (p,d). */
    /* ESTRATEGIA: se recorre una copia de los vertices; cada p es puente si existen las
       aristas (o,p) y (p,d), y se agrega al resultado.
       COSTO: P — recorrido de los vertices con existeArista adentro, que es L (busca los
       vertices); un recorrido dentro de otro.
       PRESERVACION: se trabaja sobre una copia de los vertices; el grafo no se modifica. */
    public static ConjuntoTDA verticesPuente(GrafoTDA g, int o, int d) {
        ConjuntoTDA resultado = new Conjunto();
        resultado.inicializarConjunto();

        ConjuntoTDA vertices = g.vertices();
        while (!vertices.conjuntoVacio()) {
            int p = vertices.elegir();
            vertices.sacar(p);
            if (g.existeArista(o, p) && g.existeArista(p, d)) {
                resultado.agregar(p);
            }
        }

        return resultado;
    }

    /*15) Se define un método que reciba un GrafoTDA y un vértice v y devuelva el grado del
    vértice, calculado como (aristas que salen de v) - (aristas que llegan a v). */
    /* ESTRATEGIA: se recorre una copia de los vertices; por cada u, (v,u) suma salida y (u,v)
       suma entrada. Devuelve salidas - entradas.
       COSTO: P — recorrido de los vertices con existeArista adentro, que es L (busca los
       vertices); un recorrido dentro de otro.
       PRESERVACION: se trabaja sobre una copia de los vertices; el grafo no se modifica. */
    public static int gradoVertice(GrafoTDA g, int v) {
        int salidas = 0;
        int entradas = 0;

        ConjuntoTDA vertices = g.vertices();
        while (!vertices.conjuntoVacio()) {
            int u = vertices.elegir();
            vertices.sacar(u);
            if (g.existeArista(v, u)) {
                salidas++;
            }
            if (g.existeArista(u, v)) {
                entradas++;
            }
        }

        return salidas - entradas;
    }


    /* Metodo auxiliar para imprimir una cola sin modificarla: se desacola hacia una cola
       auxiliar mostrando cada elemento y luego se restaura la cola original. */
    public static void imprimirCola(ColaTDA cola) {
        ColaTDA aux = new Cola();
        aux.inicializarCola();

        System.out.print("[ ");
        while (!cola.colaVacia()) {
            int elemento = cola.primero();
            System.out.print(elemento + " ");

            aux.acolar(elemento);
            cola.desacolar();
        }
        System.out.println("]");

        while (!aux.colaVacia()) {
            cola.acolar(aux.primero());
            aux.desacolar();
        }
    }

    /* Metodo auxiliar para imprimir un conjunto sin modificarlo: se elige y saca cada
       elemento de una copia auxiliar y luego se restaura el conjunto original. */
    public static void imprimirConjunto(ConjuntoTDA conjunto) {
        ConjuntoTDA aux = new Conjunto();
        aux.inicializarConjunto();

        System.out.print("{ ");
        while (!conjunto.conjuntoVacio()) {
            int elemento = conjunto.elegir();
            System.out.print(elemento + " ");
            aux.agregar(elemento);
            conjunto.sacar(elemento);
        }
        System.out.println("}");

        while (!aux.conjuntoVacio()) {
            int elemento = aux.elegir();
            conjunto.agregar(elemento);
            aux.sacar(elemento);
        }
    }

    public static void main(String[] args) {

        /* ===================== PUNTO 6: mostrarPorcentajePares ===================== */
        System.out.println("=== Punto 6: mostrarPorcentajePares ===");
        PilaTDA pila6 = new Pila();
        pila6.inicializarPila();
        pila6.apilar(1);
        pila6.apilar(2);
        pila6.apilar(3);
        pila6.apilar(4); // 2 pares de 4 -> 50%
        System.out.println("Porcentaje de pares (deberia ser 50.0): " + mostrarPorcentajePares(pila6) + "%");
        System.out.println("Preservacion (tope deberia seguir siendo 4): " + pila6.tope());

        /* ===================== PUNTO 7: elementosRepetidos ===================== */
        System.out.println();
        System.out.println("=== Punto 7: elementosRepetidos ===");
        PilaTDA pila7 = new Pila();
        pila7.inicializarPila();
        pila7.apilar(10);
        pila7.apilar(20);
        pila7.apilar(30);
        pila7.apilar(20);
        pila7.apilar(10); // repetidos: 10 y 20
        System.out.print("Repetidos (deberian estar 10 y 20, no el 30): ");
        imprimirConjunto(elementosRepetidos(pila7));
        System.out.println("Preservacion (tope deberia seguir siendo 10): " + pila7.tope());

        /* ===================== PUNTO 8: colaSinRepetidos ===================== */
        System.out.println();
        System.out.println("=== Punto 8: colaSinRepetidos ===");
        ColaTDA cola8 = new Cola();
        cola8.inicializarCola();
        cola8.acolar(9);
        cola8.acolar(10);
        cola8.acolar(7);
        cola8.acolar(10);
        cola8.acolar(5);
        cola8.acolar(7);
        System.out.print("Cola sin repetidos (deberia ser 9 10 7 5): ");
        imprimirCola(colaSinRepetidos(cola8));
        System.out.print("Preservacion (la original sigue 9 10 7 10 5 7): ");
        imprimirCola(cola8);

        /* ===================== PUNTO 9: obtenerElementosComunes ===================== */
        System.out.println();
        System.out.println("=== Punto 9: obtenerElementosComunes ===");
        PilaTDA pila9 = new Pila();
        pila9.inicializarPila();
        pila9.apilar(10);
        pila9.apilar(20);
        pila9.apilar(30);
        ColaTDA cola9 = new Cola();
        cola9.inicializarCola();
        cola9.acolar(20);
        cola9.acolar(40);
        cola9.acolar(10);
        System.out.print("Comunes pila-cola (deberian ser 10 y 20): ");
        imprimirConjunto(obtenerElementosComunes(pila9, cola9));
        System.out.println("Preservacion pila (tope 30): " + pila9.tope()
                + " | cola (primero 20): " + cola9.primero());

        /* ===================== PUNTO 10: contarAparicionesPila ===================== */
        System.out.println();
        System.out.println("=== Punto 10: contarAparicionesPila ===");
        PilaTDA pila10 = new Pila();
        pila10.inicializarPila();
        pila10.apilar(5);
        pila10.apilar(5);
        pila10.apilar(5);
        pila10.apilar(8);
        DiccionarioSimpleTDA frecuencias = contarAparicionesPila(pila10);
        System.out.println("Apariciones del 5 (deberia ser 3): " + frecuencias.recuperar(5));
        System.out.println("Apariciones del 8 (deberia ser 1): " + frecuencias.recuperar(8));
        System.out.println("Preservacion (tope deberia seguir siendo 8): " + pila10.tope());

        /* ===================== PUNTO 11: valoresUnicos ===================== */
        System.out.println();
        System.out.println("=== Punto 11: valoresUnicos ===");
        DiccionarioMultipleTDA dicc = new DiccionarioMultiple();
        dicc.inicializarDiccionario();
        dicc.agregar(1, 10);
        dicc.agregar(1, 20);
        dicc.agregar(2, 20); // 20 repetido respecto de la clave 1
        dicc.agregar(2, 30);
        dicc.agregar(3, 10); // 10 repetido respecto de la clave 1
        dicc.agregar(3, 40);
        System.out.print("Valores unicos (deberian aparecer 10, 20, 30 y 40 sin repetir): ");
        imprimirCola(valoresUnicos(dicc));
        System.out.print("Preservacion (valores de la clave 1 siguen siendo 10 y 20): ");
        imprimirConjunto(dicc.recuperar(1));

        /* ===================== PUNTO 12: sumarImparesABB ===================== */
        System.out.println();
        System.out.println("=== Punto 12: sumarImparesABB ===");
        ABBTDA arbol12 = new ABB();
        arbol12.inicializarArbol();
        arbol12.agregarElem(50);
        arbol12.agregarElem(45);
        arbol12.agregarElem(53);
        arbol12.agregarElem(27);
        arbol12.agregarElem(60);
        arbol12.agregarElem(52);
        arbol12.agregarElem(17);
        // impares: 45 + 53 + 27 + 17 = 142
        System.out.println("Suma de impares (deberia ser 142): " + sumarImparesABB(arbol12));
        System.out.println("Preservacion (la raiz debe seguir siendo 50): " + arbol12.raiz());

        /* ===================== PUNTO 13: hojasPares ===================== */
        System.out.println();
        System.out.println("=== Punto 13: hojasPares ===");
        ABBTDA arbol13 = new ABB();
        arbol13.inicializarArbol();
        arbol13.agregarElem(50);
        arbol13.agregarElem(45);
        arbol13.agregarElem(53);
        arbol13.agregarElem(27);
        arbol13.agregarElem(60);
        arbol13.agregarElem(52);
        arbol13.agregarElem(17);
        // Hojas: 17, 52 y 60 -> pares: 52 y 60 => 2
        System.out.println("Cantidad de hojas pares (deberia ser 2): " + hojasPares(arbol13));
        System.out.println("Preservacion (la raiz debe seguir siendo 50): " + arbol13.raiz());

        /* ===================== PUNTO 14: verticesPuente ===================== */
        System.out.println();
        System.out.println("=== Punto 14: verticesPuente ===");
        GrafoTDA grafo = new Grafo();
        grafo.inicializarGrafo();
        grafo.agregarVertice(1);
        grafo.agregarVertice(2);
        grafo.agregarVertice(3);
        grafo.agregarVertice(4);
        grafo.agregarVertice(5);
        grafo.agregarArista(1, 2, 1); // 1->2
        grafo.agregarArista(2, 5, 1); // 2->5  => 2 es puente
        grafo.agregarArista(1, 3, 1); // 1->3
        grafo.agregarArista(3, 5, 1); // 3->5  => 3 es puente
        grafo.agregarArista(1, 4, 1); // 1->4 (4 no va a 5, no es puente)
        grafo.agregarArista(2, 3, 1); // arista extra que no afecta el resultado
        System.out.print("Vertices puente de 1 a 5 (deberian ser 2 y 3): ");
        imprimirConjunto(verticesPuente(grafo, 1, 5));
        System.out.println("Preservacion (la arista 1->2 debe seguir existiendo): " + grafo.existeArista(1, 2));

        /* ===================== PUNTO 15: gradoVertice ===================== */
        System.out.println();
        System.out.println("=== Punto 15: gradoVertice ===");
        // Reutilizamos el grafo del punto 14.
        System.out.println("Grado del vertice 1 (deberia ser 3): " + gradoVertice(grafo, 1));
        System.out.println("Grado del vertice 5 (deberia ser -2): " + gradoVertice(grafo, 5));
        System.out.println("Grado del vertice 2 (deberia ser 1): " + gradoVertice(grafo, 2));
        System.out.println("Preservacion (la arista 2->5 debe seguir existiendo): " + grafo.existeArista(2, 5));
    }

}
