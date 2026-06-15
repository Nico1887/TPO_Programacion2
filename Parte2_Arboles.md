# Parte 2 — Árboles balanceados (Ejercicios 16 a 20)

Desarrollo **teórico** paso a paso. No hay código: se trata de la resolución conceptual con
diagramas ASCII, nombrando en cada paso la operación de balanceo / reestructuración que
interviene.

Convenciones usadas en este documento:

- **AVL (16, 17, 18):** se anota junto a cada nodo su *factor de balance* (FB) cuando es
  relevante. Se define **FB = altura(subárbol izquierdo) − altura(subárbol derecho)**.
  Un nodo está balanceado si FB ∈ {−1, 0, +1}. Cuando |FB| = 2 hay que rotar.
  - **LL** → rotación simple a derecha.
  - **RR** → rotación simple a izquierda.
  - **LR** → rotación doble izquierda-derecha (primero izq, luego der).
  - **RL** → rotación doble derecha-izquierda (primero der, luego izq).
- **Árbol B (19, 20):** orden `m` ⇒ cada nodo tiene como **máximo `m−1` claves** y como
  mínimo `⌈m/2⌉−1` claves (salvo la raíz). Al desbordar (m claves) se hace **split (división)**;
  al quedar por debajo del mínimo al eliminar (underflow) se hace **préstamo/redistribución
  con un hermano** o, si no es posible, **fusión (merge)**.
- En los diagramas, los nodos se muestran indentados: el hijo izquierdo arriba y el derecho
  abajo respecto del padre, o bien con la notación `padre → (izq, der)` para los casos compactos.

---

## Ejercicio 16 — AVL

**Operaciones:** a) +20  b) +29  c) +21  d) +12  e) +7  f) −20

### a) Agregar 20

```
20   (FB=0)
```

Árbol vacío → 20 pasa a ser la raíz. Balanceado.

### b) Agregar 29

29 > 20 → va a la derecha.

```
20   (FB=-1)
  \
   29 (FB=0)
```

FB(20) = 0 − 1 = −1. Balanceado, no se rota.

### c) Agregar 21

21 > 20 → derecha; 21 < 29 → izquierda de 29.

```
20  (FB=-2)   <-- DESBALANCE
  \
   29 (FB=+1)
  /
 21 (FB=0)
```

FB(20) = −2 y el desbalance viene por la **derecha** del nodo, y dentro de esa rama el nuevo
nodo entró por la **izquierda** (29 es hijo derecho, 21 es hijo izquierdo de 29).
Patrón **RL** ⇒ **rotación doble derecha-izquierda**.

1. Rotación simple a derecha sobre 29: 21 sube, 29 baja a su derecha.
2. Rotación simple a izquierda sobre 20: 21 sube a la raíz.

Resultado:

```
      21 (FB=0)
     /  \
   20    29
 (FB=0)(FB=0)
```

### d) Agregar 12

12 < 21 → izquierda; 12 < 20 → izquierda de 20.

```
        21 (FB=+1)
       /  \
   20      29
  (FB=+1)
   /
 12 (FB=0)
```

FB(20) = +1, FB(21) = +1. Todos en rango, balanceado.

### e) Agregar 7

7 < 21 → izq; 7 < 20 → izq; 7 < 12 → izquierda de 12.

```
            21 (FB=+2)   <-- DESBALANCE
           /  \
        20     29
      (FB=+2)
       /
     12 (FB=+1)
     /
    7 (FB=0)
```

El primer nodo desbalanceado subiendo desde la inserción es **20** (FB = +2). El desbalance
es por la **izquierda** y, dentro de esa rama, el nuevo nodo entró también por la **izquierda**
(12 hijo izquierdo de 20, 7 hijo izquierdo de 12).
Patrón **LL** ⇒ **rotación simple a derecha** sobre 20.

12 sube a ocupar el lugar de 20; 20 baja como hijo derecho de 12.

```
        21 (FB=0)
       /  \
     12    29
    /  \
   7    20
 (FB=0)(FB=0)
```

FB(21) = altura(izq=2) − altura(der=1) = ... revisamos: subárbol izq de 21 tiene altura 2
(12 → 7,20), subárbol der altura 1 (29). FB(21) = +1. Balanceado.

### f) Eliminar 20

20 es una hoja → se elimina directamente.

```
        21 (FB=+1)
       /  \
     12    29
    /
   7
```

Tras la baja revisamos FB ascendiendo:
- FB(12) = 1 − 0 = +1 (ok).
- FB(21) = altura(izq=2) − altura(der=1) = +1 (ok).

No se requiere rotación. **Árbol final del Ej. 16:**

```
        21
       /  \
     12    29
    /
   7
```

---

## Ejercicio 17 — AVL

**Operaciones:** a) +30  b) +36  c) +10  d) +15  e) +12  f) −30

### a) Agregar 30

```
30 (FB=0)
```

### b) Agregar 36

36 > 30 → derecha.

```
30 (FB=-1)
  \
   36 (FB=0)
```

Balanceado.

### c) Agregar 10

10 < 30 → izquierda.

```
   30 (FB=0)
  /  \
 10   36
```

FB(30) = 1 − 1 = 0. Balanceado.

### d) Agregar 15

15 < 30 → izq; 15 > 10 → derecha de 10.

```
     30 (FB=+1)
    /  \
   10   36
    \
     15 (FB=0)
```

FB(10) = −1, FB(30) = +1. Todos en rango.

### e) Agregar 12

12 < 30 → izq; 12 > 10 → der; 12 < 15 → izquierda de 15.

```
        30 (FB=+2)   <-- DESBALANCE
       /  \
     10    36
   (FB=-2)
      \
       15 (FB=+1)
      /
    12 (FB=0)
```

El primer nodo desbalanceado desde la inserción es **10** (FB = −2). El desbalance es por la
**derecha** (15 es hijo derecho de 10) y el nuevo nodo entró por la **izquierda** de esa rama
(12 hijo izquierdo de 15).
Patrón **RL** ⇒ **rotación doble derecha-izquierda** sobre 10.

1. Rotación simple a derecha sobre 15: 12 sube, 15 baja a su derecha.
2. Rotación simple a izquierda sobre 10: 12 sube por encima de 10.

Subárbol resultante (raíz local 12):

```
       12
      /  \
    10    15
```

Insertado de nuevo bajo 30:

```
        30 (FB=+1)
       /  \
     12    36
    /  \
   10   15
```

FB(12) = 0, FB(30) = altura(izq=2) − altura(der=1) = +1. Balanceado.

### f) Eliminar 30

30 es la raíz y tiene dos hijos. Se reemplaza por su **sucesor inorden** (mínimo del subárbol
derecho). El subárbol derecho es solo `36`, así que el sucesor es **36**. 36 sube a la raíz y se
elimina su nodo original.

```
        36 (FB=+2)   <-- DESBALANCE
       /
     12
    /  \
   10   15
```

Tras la baja, FB(36) = altura(izq=2) − altura(der=0) = +2. Desbalance por la **izquierda**;
el subárbol izquierdo (raíz 12) tiene FB = 0 (se trata como caso **LL** por convención cuando el
hijo está balanceado).
⇒ **rotación simple a derecha** sobre 36. 12 sube y 36 baja a su derecha; el 15 (que era
hijo derecho de 12) se reubica como hijo izquierdo de 36:

```
        12
       /  \
     10    36
          /
        15
```

Verificación de FB: FB(36) = 1 − 0 = +1, FB(12) = 1 − 2 = −1. Balanceado.

**Árbol final del Ej. 17:**

```
        12
       /  \
     10    36
          /
        15
```

---

## Ejercicio 18 — AVL

**Operaciones:** a) +8  b) +5  c) +6  d) +3  e) +1  f) −3

### a) Agregar 8

```
8 (FB=0)
```

### b) Agregar 5

5 < 8 → izquierda.

```
   8 (FB=+1)
  /
 5 (FB=0)
```

Balanceado.

### c) Agregar 6

6 < 8 → izq; 6 > 5 → derecha de 5.

```
      8 (FB=+2)   <-- DESBALANCE
     /
    5 (FB=-1)
     \
      6 (FB=0)
```

Primer nodo desbalanceado: **8** (FB = +2). Desbalance por la **izquierda** (5 hijo izq) y el
nuevo nodo entró por la **derecha** de esa rama (6 hijo der de 5).
Patrón **LR** ⇒ **rotación doble izquierda-derecha** sobre 8.

1. Rotación simple a izquierda sobre 5: 6 sube, 5 baja a su izquierda.
2. Rotación simple a derecha sobre 8: 6 sube a la raíz.

```
      6 (FB=0)
     /  \
    5    8
  (FB=0)(FB=0)
```

### d) Agregar 3

3 < 6 → izq; 3 < 5 → izquierda de 5.

```
       6 (FB=+1)
      /  \
     5    8
    /
   3 (FB=0)
```

FB(5) = +1, FB(6) = +1. Balanceado.

### e) Agregar 1

1 < 6 → izq; 1 < 5 → izq; 1 < 3 → izquierda de 3.

```
          6 (FB=+2)   <-- DESBALANCE
         /  \
        5    8
      (FB=+2)
       /
      3 (FB=+1)
     /
    1 (FB=0)
```

Primer nodo desbalanceado subiendo desde la inserción: **5** (FB = +2). Desbalance por la
**izquierda** y el nuevo nodo entró por la **izquierda** (3 hijo izq de 5, 1 hijo izq de 3).
Patrón **LL** ⇒ **rotación simple a derecha** sobre 5. 3 sube, 5 baja a su derecha.

```
        6 (FB=+1)
       /  \
      3    8
     / \
    1   5
```

FB(3) = 0, FB(6) = altura(izq=2) − altura(der=1) = +1. Balanceado.

### f) Eliminar 3

3 tiene dos hijos (1 y 5). Se reemplaza por su **sucesor inorden** = mínimo del subárbol
derecho = **5**. 5 ocupa el lugar de 3 y se elimina su nodo original (era hoja).

```
        6 (FB=0)
       /  \
      5    8
     /
    1
```

Verificación de FB: FB(5) = 1 − 0 = +1, FB(6) = altura(izq=2) − altura(der=1) = +1.
Balanceado, no se requiere rotación.

**Árbol final del Ej. 18:**

```
        6
       /  \
      5    8
     /
    1
```

---

## Ejercicio 19 — Árbol B de orden 4

**Orden 4** ⇒ máximo **3 claves** por nodo, mínimo (no raíz) **1 clave**.
Política de split: al llegar a 4 claves, la clave **mediana** (la 2.ª de las 4 ordenadas) sube
al padre y el nodo se divide en dos.

**Operaciones:** a) +82  b) +12  c) +102  d) +36  e) +61  f) −82  g) −36  h) −102

### a) Agregar 82

```
[82]
```

### b) Agregar 12

```
[12, 82]
```

### c) Agregar 102

```
[12, 82, 102]
```

El nodo tiene 3 claves = máximo permitido. Todavía no desborda.

### d) Agregar 36

Insertando 36 ordenado: 12, 36, 82, 102 → **4 claves ⇒ desbordamiento (overflow)**.
Se aplica **split (división) de nodo**. Mediana = 2.ª clave de `[12, 36, 82, 102]` = **36**.
36 sube y crea una nueva raíz; el resto se reparte en dos hojas.

```
            [36]
           /    \
       [12]      [82, 102]
```

### e) Agregar 61

61 > 36 → va a la hoja derecha `[82, 102]`. Insertado ordenado: `[61, 82, 102]` (3 claves, ok).

```
            [36]
           /    \
       [12]      [61, 82, 102]
```

### f) Eliminar 82

82 está en la hoja `[61, 82, 102]`. Es una clave de una hoja con 3 claves; quitarla deja
`[61, 102]` (2 claves ≥ mínimo 1). **No hay underflow.**

```
            [36]
           /    \
       [12]      [61, 102]
```

### g) Eliminar 36

36 está en un **nodo interno** (la raíz). Se reemplaza por su **predecesor inorden** (máximo
del subárbol izquierdo) o el **sucesor inorden** (mínimo del subárbol derecho).
Usamos el **sucesor**: mínimo de la hoja derecha `[61, 102]` = **61**. 61 sube a la raíz y se
borra de la hoja.

```
            [61]
           /    \
       [12]      [102]
```

La hoja `[102]` queda con 1 clave = mínimo, sin underflow.

### h) Eliminar 102

102 está en la hoja derecha `[102]`. Al quitarla, la hoja queda **vacía ⇒ underflow**
(0 claves < mínimo 1).

Se intenta **préstamo/redistribución con un hermano**: el único hermano es `[12]`, que tiene
solo 1 clave (el mínimo), por lo que **no puede prestar**.
⇒ Se hace **fusión (merge)**: se combinan el hermano izquierdo `[12]`, la clave separadora de
la raíz `61` y la hoja vacía, formando un único nodo `[12, 61]`. Como la raíz se queda sin
claves, el nodo fusionado pasa a ser la **nueva raíz** (la altura del árbol disminuye en 1).

```
[12, 61]
```

**Árbol final del Ej. 19:**

```
[12, 61]
```

---

## Ejercicio 20 — Árbol B de orden 5

**Orden 5** ⇒ máximo **4 claves** por nodo, mínimo (no raíz) **2 claves** (⌈5/2⌉−1 = 2).
Política de split: al llegar a 5 claves, la clave **mediana** (la 3.ª de las 5 ordenadas) sube
al padre.

**Operaciones:** a) +53  b) +62  c) +31  d) +105  e) +85  f) +55  g) −105  h) −62

### a) Agregar 53

```
[53]
```

### b) Agregar 62

```
[53, 62]
```

### c) Agregar 31

```
[31, 53, 62]
```

### d) Agregar 105

```
[31, 53, 62, 105]
```

4 claves = máximo permitido. Aún no desborda.

### e) Agregar 85

Insertando 85 ordenado: 31, 53, 62, 85, 105 → **5 claves ⇒ desbordamiento**.
Se aplica **split (división)**. Mediana = 3.ª clave de `[31, 53, 62, 85, 105]` = **62**.
62 sube y crea una nueva raíz.

```
              [62]
            /      \
   [31, 53]         [85, 105]
```

### f) Agregar 55

55 < 62 → va a la hoja izquierda `[31, 53]`. Insertado ordenado: `[31, 53, 55]`
(3 claves ≤ 4, ok).

```
              [62]
            /      \
   [31, 53, 55]     [85, 105]
```

### g) Eliminar 105

105 está en la hoja derecha `[85, 105]`. Quitarla deja `[85]` ⇒ **1 clave < mínimo 2 ⇒
underflow**.

Se intenta **préstamo/redistribución con un hermano**: el hermano izquierdo es `[31, 53, 55]`,
que tiene 3 claves (> mínimo 2), por lo que **sí puede prestar**.
Redistribución vía el padre: la clave separadora de la raíz `62` baja a la hoja derecha, y la
clave **mayor** del hermano izquierdo (`55`) sube a la raíz como nuevo separador.

```
              [55]
            /      \
   [31, 53]         [62, 85]
```

La hoja derecha queda con 2 claves (= mínimo). Resuelto sin fusión.

### h) Eliminar 62

62 está en la hoja derecha `[62, 85]`. Quitarla deja `[85]` ⇒ **1 clave < mínimo 2 ⇒
underflow** otra vez.

Se intenta **préstamo con el hermano** izquierdo `[31, 53]`: tiene exactamente 2 claves
(el mínimo), por lo que **no puede prestar**.
⇒ Se hace **fusión (merge)**: se combinan el hermano izquierdo `[31, 53]`, la clave separadora
de la raíz `55` y la hoja `[85]`, formando `[31, 53, 55, 85]`. La raíz se queda sin claves, así
que el nodo fusionado pasa a ser la **nueva raíz** (la altura disminuye en 1).

```
[31, 53, 55, 85]
```

**Árbol final del Ej. 20:**

```
[31, 53, 55, 85]
```

---

## Resumen de operaciones de balanceo utilizadas

| Ej. | Estructura | Reestructuraciones aplicadas |
|-----|-----------|------------------------------|
| 16 | AVL | RL (insertar 21), LL (insertar 7) |
| 17 | AVL | RL (insertar 12), LL/simple derecha (eliminar 30) |
| 18 | AVL | LR (insertar 6), LL (insertar 1) |
| 19 | Árbol B (orden 4) | split (insertar 36), merge (eliminar 102) |
| 20 | Árbol B (orden 5) | split (insertar 85), préstamo (eliminar 105), merge (eliminar 62) |
