package Imple;
import Interfaz.ConjuntoMamushkaTDA;

public class ConjuntoMamushka implements ConjuntoMamushkaTDA{
    // Clase interna para el Nodo
    private class Nodo {
        int info;       // El número guardado
        int cantidad;   // Cuántas acepciones/capas tiene
        Nodo sig;       // Puntero al siguiente elemento
    }

    private Nodo origen; // Atributo principal del TDA

    @Override
    public void inicializar() {
        origen = null;
    }

    @Override
    public boolean estaVacio() {
        return (origen == null);
    }

    @Override
    public void guardar(int dato) {
        Nodo aux = buscarNodo(dato);

        // Si ya existe, solo sumamos una acepción (una capa más)
        if (aux != null) {
            aux.cantidad++;
        } else {
            // Si no existe, creamos un nodo nuevo y lo insertamos al principio (sin orden)
            Nodo nuevo = new Nodo();
            nuevo.info = dato;
            nuevo.cantidad = 1;
            nuevo.sig = origen;
            origen = nuevo;
        }
    }

    @Override
    public void sacar(int dato) {
        if (origen == null) return;

        // Caso especial: si es el primer nodo
        if (origen.info == dato) {
            origen.cantidad--;
            if (origen.cantidad == 0) {
                origen = origen.sig; // Si llegó a 0, se elimina el nodo
            }
            return;
        }

        // Caso general: buscar en el resto de la lista
        Nodo viajero = origen;
        while (viajero.sig != null && viajero.sig.info != dato) {
            viajero = viajero.sig;
        }

        // Si lo encontramos en viajero.sig
        if (viajero.sig != null) {
            viajero.sig.cantidad--;
            if (viajero.sig.cantidad == 0) {
                viajero.sig = viajero.sig.sig; // Desenganchamos el nodo
            }
        }
    }

    @Override
    public int elegir() {
        // Al no tener orden, podemos devolver directamente la info del primer nodo
        if (origen != null) {
            return origen.info;
        }
        return -1; // O lanzar una excepción según cómo manejen errores en tu cátedra
    }

    @Override
    public int perteneceCant(int dato) {
        Nodo aux = buscarNodo(dato);
        if (aux != null) {
            return aux.cantidad;
        }
        return 0; // Si no está, tiene 0 acepciones
    }

    // Método privado auxiliar para evitar repetir código de búsqueda
    private Nodo buscarNodo(int dato) {
        Nodo viajero = origen;
        while (viajero != null) {
            if (viajero.info == dato) {
                return viajero;
            }
            viajero = viajero.sig;
        }
        return null;
    }

}
