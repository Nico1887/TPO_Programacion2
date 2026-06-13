package Imple;

import Interfaz.ConjuntoEspecialTDA;

public class ConjuntoEspecial implements ConjuntoEspecialTDA {

    public class Nodo{
        int info;
        Nodo sig;
    }

    Nodo c;

    @Override
    public void inicializarConjunto() {
        c = null;
    }

    @Override
    public Respuesta agregar(int valor) {

        //Instanciamos Respuesta
        Respuesta respuesta = new Respuesta();
        respuesta.error = false;

        //Chequeamos si existe el valor dentro del conjunto
        if(!this.pertenece(valor)){
            Nodo aux = new Nodo();
            aux.info = valor;
            aux.sig = c;
            c = aux;
        }
        else{
            respuesta.error = true;
        }

        return respuesta;
    }

    @Override
    public Respuesta sacar(int valor) {

        //Instanciamos Respuesta
        Respuesta respuesta = new Respuesta();
        respuesta.error = true;

        if(c != null){
            //Aca por si es el primer elemento
            if(c.info == valor){
                c = c.sig;
                respuesta.error = false;
            }
            else{
                Nodo aux = c;
                while((aux.sig != null) && (aux.sig.info != valor)){
                    aux = aux.sig;
                }
                if(aux.sig != null){
                    aux.sig = aux.sig.sig;
                    respuesta.error = false;
                }
            }
        }

        return respuesta;
    }

    @Override
    public Respuesta elegir() {
        //Instanciamos respuesta
        Respuesta respuesta = new Respuesta();
        respuesta.error = true;
        respuesta.rta = 0;

        if(c != null){
            respuesta.error = false;
            respuesta.rta = c.info;
        }

        return respuesta;
    }

    @Override
    public boolean pertenece(int valor) {
        Nodo aux = c;
        while ((aux != null) && (aux.info != valor)) {
            aux = aux.sig;
        }

        return (aux != null);
    }

    @Override
    public boolean conjuntoVacio() {
        return c == null;
    }
}
