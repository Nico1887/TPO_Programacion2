package Uso;
import Interfaz.ConjuntoEspecialTDA;
import Imple.ConjuntoEspecial;
import imple.*;
import tda.*;

public class Main {

    /*6) Se define un metodo que reciba una PilaTDA y devuelva un float (número real) con
    el porcentaje de cantidad de elementos pares de la pila. */
    /* ESTRATEGIA: se recorre la pila desapilando hacia una pila auxiliar, contando
       cuantos elementos son pares y la cantidad total de elementos. Luego se restaura
       la pila original devolviendo los elementos desde la auxiliar. El porcentaje se
       calcula como (cantidadPares / cantidadElementos) * 100.
       COMPLEJIDAD: O(n), donde n es la cantidad de elementos de la pila.
       JUSTIFICACION: se recorre la pila una vez para contar (O(n)) y una vez para
       restaurarla (O(n)); 2*O(n) = O(n). Cada operacion de pila es O(1).
       PRESERVACION: la pila original queda con los mismos elementos y orden ya que
       todo lo desapilado se vuelve a apilar desde la auxiliar. */
    public static float mostrarPorcentajePares(PilaTDA pila){
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

        return (cantidadPares / cantidadElementos) * 100;
    }

    /*7)Se define un metodo que reciba una PilaTDA y devuelva un ConjuntoTDA con los
    elementos repetidos de la pila. */
    /* ESTRATEGIA: se recorre la pila desapilando hacia una pila auxiliar. Se usa un
       conjunto auxiliar (auxC) para registrar los elementos ya vistos; si un elemento
       ya pertenece a auxC significa que esta repetido y se agrega al conjunto resultado.
       Al finalizar se restaura la pila original desde la pila auxiliar.
       COMPLEJIDAD: O(n^2) en el peor caso, donde n es la cantidad de elementos.
       JUSTIFICACION: por cada uno de los n elementos se consultan/agregan elementos en
       conjuntos implementados con lista enlazada, cuyas operaciones pertenece/agregar
       son O(n) en el peor caso.
       PRESERVACION: la pila original se restaura desde auxP con el mismo orden. */
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
    /* ESTRATEGIA: se desacola la cola original hacia una cola auxiliar (para luego
       restaurarla) y se usa un conjunto para detectar elementos ya vistos. La primera
       vez que aparece un elemento se acola en la cola final, conservando asi el orden
       de aparicion y el primer representante. Al final se restaura la cola original.
       COMPLEJIDAD: O(n^2) en el peor caso, donde n es la cantidad de elementos.
       JUSTIFICACION: se recorre la cola una vez (O(n)), pero por cada elemento se hace
       pertenece/agregar sobre un conjunto con lista enlazada, que es O(n).
       PRESERVACION: la cola original se restaura desde colaAux con el mismo orden. */
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
    /* ESTRATEGIA: por cada elemento de la pila (recorrida con una pila auxiliar para
       restaurarla) se recorre toda la cola comparando contra el tope actual; si
       coinciden, el elemento se agrega al conjunto de comunes. La cola se restaura con
       una cola auxiliar en cada vuelta, y la pila se restaura al final.
       COMPLEJIDAD: O(n*m), donde n es la cantidad de elementos de la pila y m la de la
       cola.
       JUSTIFICACION: por cada uno de los n elementos de la pila se recorre la cola
       completa (m elementos), de ahi el producto n*m.
       PRESERVACION: la cola se reconstruye en cada iteracion desde auxCola y la pila se
       reconstruye desde auxP al terminar; ambas quedan intactas. */
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
    /* ESTRATEGIA: se recorre la pila desapilando hacia una pila auxiliar. Para cada
       elemento se consulta el conjunto de claves del diccionario: si la clave ya existe
       se recupera su contador y se reemplaza por contador+1; si no existe se agrega con
       valor 1. Al final se restaura la pila original desde la auxiliar.
       COMPLEJIDAD: O(n^2) en el peor caso, donde n es la cantidad de elementos.
       JUSTIFICACION: se recorre la pila una vez (O(n)), pero por cada elemento se
       construye/consulta el conjunto de claves y se hacen operaciones de diccionario
       sobre listas enlazadas, que son O(n).
       PRESERVACION: la pila original se restaura desde pilaAux con el mismo orden. */
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
        while (!pilaAux.pilaVacia()) { //
            pilaOriginal.apilar(pilaAux.tope()); //
            pilaAux.desapilar(); //
        }
        return diccFinal;
    }

    /*11) Se define un método que reciba un DiccionarioMultipleTDA y devuelva una ColaTDA
    con todos los valores del diccionario, sin repetición. */
    /* ESTRATEGIA: se obtiene el conjunto de claves del diccionario y se recorre
       eligiendo y sacando cada clave de una copia auxiliar (para no modificar nada del
       diccionario). Para cada clave se recupera su conjunto de valores y se recorren
       de la misma forma, usando un conjunto auxiliar (vistos) para deduplicar: solo
       cuando un valor no pertenece a vistos se lo agrega a vistos y se lo acola en la
       cola resultado.
       COMPLEJIDAD: O(k * v) en cantidad de operaciones de recorrido, donde k es la
       cantidad de claves y v la cantidad de valores por clave; cada chequeo de
       pertenencia/agregar sobre conjuntos con lista enlazada agrega un factor lineal.
       JUSTIFICACION: se visita cada par (clave, valor) una sola vez; la deduplicacion
       se apoya en el conjunto auxiliar para no acolar valores repetidos.
       PRESERVACION: nunca se modifica el diccionario; claves() y recuperar() devuelven
       conjuntos nuevos que se consumen sobre esas copias, dejando el diccionario intacto. */
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
    /* ESTRATEGIA: recorrido recursivo del arbol. Si el arbol esta vacio aporta 0. Si la
       raiz es impar la suma parte de su valor; luego se suma el resultado recursivo del
       hijo izquierdo y del hijo derecho.
       COMPLEJIDAD: recursivo; se visita cada nodo exactamente una vez.
       JUSTIFICACION: por ser recursivo se aclara que cada nodo del arbol se procesa una
       unica vez, acumulando los valores impares.
       PRESERVACION: solo se realizan consultas (raiz, hijoIzq, hijoDer, arbolVacio); no
       se modifica la estructura del arbol. */
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
    /* ESTRATEGIA: recorrido recursivo del arbol. Si el arbol esta vacio aporta 0. Un
       nodo es hoja cuando su hijo izquierdo y su hijo derecho estan vacios; en ese caso
       cuenta 1 si su raiz es par, 0 si no. Si no es hoja, se suma la cantidad de hojas
       pares del hijo izquierdo y del hijo derecho.
       COMPLEJIDAD: recursivo; se visita cada nodo exactamente una vez.
       JUSTIFICACION: por ser recursivo se aclara que cada nodo se evalua una sola vez;
       las consultas hijoIzq/hijoDer/arbolVacio/raiz son O(1).
       PRESERVACION: solo se realizan consultas sobre el arbol; no se modifica. */
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
    /* ESTRATEGIA: se recorren todos los vertices del grafo usando una copia del conjunto
       devuelto por g.vertices() (se elige y se saca cada vertice de esa copia para no
       perderlo). Para cada vertice p se verifica si existe la arista (o,p) y la arista
       (p,d); si ambas existen, p es un vertice puente y se agrega al conjunto resultado.
       COMPLEJIDAD: O(V) consultas de aristas, donde V es la cantidad de vertices; cada
       agregar al conjunto resultado agrega un factor lineal en el peor caso.
       JUSTIFICACION: se recorre el conjunto de vertices una sola vez y por cada uno se
       hacen dos consultas existeArista que son O(1)/O(log) segun la implementacion.
       PRESERVACION: no se modifica el grafo; se trabaja sobre la copia del conjunto de
       vertices devuelto por vertices(), que es una estructura nueva. */
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
    /* ESTRATEGIA: se recorren todos los vertices u del grafo usando una copia del
       conjunto devuelto por g.vertices(). Por cada u, si existe la arista (v,u) se suma
       una salida; si existe la arista (u,v) se suma una entrada. El grado resultante es
       salidas - entradas.
       COMPLEJIDAD: O(V) consultas de aristas, donde V es la cantidad de vertices.
       JUSTIFICACION: se recorre una sola vez el conjunto de vertices y por cada uno se
       hacen dos consultas existeArista de costo O(1)/O(log) segun la implementacion.
       PRESERVACION: no se modifica el grafo; se consume una copia del conjunto de
       vertices devuelto por vertices(). */
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
        /*Testeos para el punto 1*/

        /*ConjuntoEspecialTDA conjunto = new ConjuntoEspecial();
        conjunto.inicializarConjunto();
        ConjuntoEspecialTDA.Respuesta res;

        System.out.println("Aca se prueba si el conjunto recien inicializado esta vacio (deberia ser true):");
        System.out.println(conjunto.conjuntoVacio());

        System.out.println("Aca se prueba elegir en un conjunto vacio (el error deberia ser true):");
        res = conjunto.elegir();
        System.out.println(res.error);

        System.out.println("Aca se prueba agregar un valor nuevo (el error deberia ser false):");
        res = conjunto.agregar(10);
        System.out.println(res.error);

        System.out.println("Aca se prueba agregar otro valor nuevo (el error deberia ser false):");
        res = conjunto.agregar(20);
        System.out.println(res.error);

        System.out.println("Aca se prueba agregar un valor repetido (el error deberia ser true):");
        res = conjunto.agregar(10);
        System.out.println(res.error);

        System.out.println("Aca se prueba si el conjunto ya no esta vacio (deberia ser false):");
        System.out.println(conjunto.conjuntoVacio());

        System.out.println("Aca se prueba elegir en un conjunto con elementos (el error deberia ser false y mostrar un numero):");
        res = conjunto.elegir();
        System.out.println("Error: " + res.error + " - Valor: " + res.rta);

        System.out.println("Aca se prueba sacar un elemento que existe (el error deberia ser false):");
        res = conjunto.sacar(10);
        System.out.println(res.error);

        System.out.println("Aca se prueba sacar un elemento que ya no existe (el error deberia ser true):");
        res = conjunto.sacar(10);
        System.out.println(res.error);*/

        /*Punto 6*/
        /*PilaTDA pila1 = new Pila();
        pila1.inicializarPila();
        pila1.apilar(1);
        pila1.apilar(2);
        pila1.apilar(3);
        pila1.apilar(4);
        System.out.println("La cantidad de elementos pares de la pila es: " + mostrarPorcentajePares(pila1) + "%");
        */

        /*Punto 7*/
        /*PilaTDA miPila = new Pila();
        miPila.inicializarPila();

        System.out.println("Aca se prueba buscar repetidos en una pila vacia (deberia dar true al consultar si el conjunto esta vacio):");
        ConjuntoTDA resultado1 = elementosRepetidos(miPila);
        System.out.println(resultado1.conjuntoVacio());

        miPila.apilar(10);
        miPila.apilar(20);
        miPila.apilar(30);

        System.out.println("Aca se prueba una pila sin repetidos (deberia dar true al consultar si el conjunto esta vacio):");
        ConjuntoTDA resultado2 = elementosRepetidos(miPila);
        System.out.println(resultado2.conjuntoVacio());

        miPila.apilar(20);
        miPila.apilar(40);
        miPila.apilar(10);

        System.out.println("Aca se prueba una pila con elementos repetidos (el 10 y el 20 deberian pertenecer, el 30 no):");
        ConjuntoTDA resultado3 = elementosRepetidos(miPila);
        System.out.println("Pertenece el 10: " + resultado3.pertenece(10));
        System.out.println("Pertenece el 20: " + resultado3.pertenece(20));
        System.out.println("Pertenece el 30: " + resultado3.pertenece(30));

        System.out.println("Aca se prueba que la pila original no haya perdido elementos revisando el tope actual (deberia ser 10):");
        System.out.println(miPila.tope());*/

        /*Punto 8*/
        ColaTDA colaOriginal = new Cola();
        colaOriginal.inicializarCola();
        colaOriginal.acolar(9);
        colaOriginal.acolar(10);
        colaOriginal.acolar(7);
        colaOriginal.acolar(10);
        colaOriginal.acolar(5);
        colaOriginal.acolar(7);
        ColaTDA colaFinal = colaSinRepetidos(colaOriginal);
        imprimirCola(colaFinal);

        /*Punto 9*/
        /*
        PilaTDA miPila = new Pila();
        miPila.inicializarPila();
        ColaTDA miCola = new Cola();
        miCola.inicializarCola();

        System.out.println("Aca se prueba con pila y cola vacias (deberia dar true al consultar si el conjunto esta vacio):");
        ConjuntoTDA resultado1 = obtenerElementosComunes(miPila, miCola);
        System.out.println(resultado1.conjuntoVacio());

        miPila.apilar(10);
        miPila.apilar(20);
        miPila.apilar(30);

        miCola.acolar(40);
        miCola.acolar(50);

        System.out.println("Aca se prueba sin elementos en comun (deberia dar true al consultar si el conjunto esta vacio):");
        ConjuntoTDA resultado2 = obtenerElementosComunes(miPila, miCola);
        System.out.println(resultado2.conjuntoVacio());

        miCola.acolar(20);
        miCola.acolar(10);
        miCola.acolar(60);

        System.out.println("Aca se prueba con elementos en comun (el 10 y el 20 deberian pertenecer, el 30 no):");
        ConjuntoTDA resultado3 = obtenerElementosComunes(miPila, miCola);
        System.out.println("Pertenece el 10: " + resultado3.pertenece(10));
        System.out.println("Pertenece el 20: " + resultado3.pertenece(20));
        System.out.println("Pertenece el 30: " + resultado3.pertenece(30));

        System.out.println("Aca se prueba que la pila y la cola no hayan perdido elementos (deberian ser 30 y 40 respectivamente):");
        System.out.println("Tope pila: " + miPila.tope());
        System.out.println("Primero cola: " + miCola.primero());*/

        /*PUNTO 12*/
        ABBTDA arbol = new ABB();
        arbol.inicializarArbol();
        arbol.agregarElem(50);
        arbol.agregarElem(45);
        arbol.agregarElem(53);
        arbol.agregarElem(27);
        arbol.agregarElem(60);
        arbol.agregarElem(52);
        arbol.agregarElem(17);
        int resultadoABB = sumarImparesABB(arbol);
        System.out.println(resultadoABB);

        /*PUNTO 11 - valoresUnicos*/
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

        ColaTDA valores = valoresUnicos(dicc);
        System.out.print("Valores unicos (deberian aparecer 10, 20, 30 y 40 sin repetir): ");
        imprimirCola(valores);

        System.out.println("Preservacion del diccionario (las claves 1, 2 y 3 deben seguir presentes):");
        ConjuntoTDA clavesDicc = dicc.claves();
        System.out.println("Pertenece la clave 1: " + clavesDicc.pertenece(1));
        System.out.println("Pertenece la clave 2: " + clavesDicc.pertenece(2));
        System.out.println("Pertenece la clave 3: " + clavesDicc.pertenece(3));
        System.out.print("Valores de la clave 1 (deberian ser 10 y 20): ");
        imprimirConjunto(dicc.recuperar(1));

        /*PUNTO 13 - hojasPares*/
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
        // Hojas del arbol: 17, 52 y 60 -> pares: 52 y 60 => 2
        System.out.println("Cantidad de hojas pares (deberia ser 2): " + hojasPares(arbol13));
        System.out.println("Preservacion del arbol (la raiz debe seguir siendo 50): " + arbol13.raiz());

        /*PUNTO 14 - verticesPuente*/
        System.out.println();
        System.out.println("=== Punto 14: verticesPuente ===");
        GrafoTDA grafo = new Grafo();
        grafo.inicializarGrafo();
        grafo.agregarVertice(1);
        grafo.agregarVertice(2);
        grafo.agregarVertice(3);
        grafo.agregarVertice(4);
        grafo.agregarVertice(5);
        // Buscamos puentes de o=1 a d=5
        grafo.agregarArista(1, 2, 1); // 1->2
        grafo.agregarArista(2, 5, 1); // 2->5  => 2 es puente
        grafo.agregarArista(1, 3, 1); // 1->3
        grafo.agregarArista(3, 5, 1); // 3->5  => 3 es puente
        grafo.agregarArista(1, 4, 1); // 1->4
        // 4 NO va a 5, asi que no es puente
        grafo.agregarArista(2, 3, 1); // arista extra que no afecta el resultado

        ConjuntoTDA puentes = verticesPuente(grafo, 1, 5);
        System.out.print("Vertices puente de 1 a 5 (deberian ser 2 y 3): ");
        imprimirConjunto(puentes);

        System.out.println("Preservacion del grafo (la arista 1->2 debe seguir existiendo): " + grafo.existeArista(1, 2));
        System.out.print("Vertices del grafo siguen intactos: ");
        imprimirConjunto(grafo.vertices());

        /*PUNTO 15 - gradoVertice*/
        System.out.println();
        System.out.println("=== Punto 15: gradoVertice ===");
        // Reutilizamos el grafo del punto 14.
        // Vertice 1: salidas a 2, 3, 4 (3 salidas), entradas: ninguna => grado 3
        System.out.println("Grado del vertice 1 (deberia ser 3): " + gradoVertice(grafo, 1));
        // Vertice 5: salidas: ninguna, entradas desde 2 y 3 (2 entradas) => grado -2
        System.out.println("Grado del vertice 5 (deberia ser -2): " + gradoVertice(grafo, 5));
        // Vertice 2: salidas a 5 y 3 (2 salidas), entradas desde 1 (1 entrada) => grado 1
        System.out.println("Grado del vertice 2 (deberia ser 1): " + gradoVertice(grafo, 2));

        System.out.println("Preservacion del grafo (la arista 2->5 debe seguir existiendo): " + grafo.existeArista(2, 5));

    }

}
