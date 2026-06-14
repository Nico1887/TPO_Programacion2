package Uso;
import Interfaz.ConjuntoEspecialTDA;
import Imple.ConjuntoEspecial;
import imple.Cola;
import imple.Conjunto;
import imple.DiccionarioSimple;
import imple.Pila;
import tda.ColaTDA;
import tda.ConjuntoTDA;
import tda.DiccionarioSimpleTDA;
import tda.PilaTDA;

public class Main {

    /*6) Se define un metodo que reciba una PilaTDA y devuelva un float (número real) con
    el porcentaje de cantidad de elementos pares de la pila. */
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

        



    }

}
