# Chapitre08 - Gestion d'une bibliothèque de livres en Java

## Description

Ce projet Java  **chapitre08** implémente une classe `BookShelf` permettant de gérer une collection de livres.
Le projet inclut également des tests unitaires réalisés avec **JUnit 5** afin de vérifier le bon fonctionnement des fonctionnalités.

L'objectif principal est de démontrer :

* l'utilisation des collections Java (`List`)
* la programmation orientée objet
* les tests unitaires avec JUnit
* l'immuabilité des données exposées au client

---

## Structure du projet

```text
chapitre08/
│
├── src/
│   └── bookstoread/
│       ├── BookShelf.java
│       └── BookShelfSpec.java
│
└── README.md
```

---

## Fonctionnalités

La classe `BookShelf` permet de :

* Ajouter un ou plusieurs livres
* Consulter la liste des livres
* Empêcher la modification directe de la liste retournée

---

## Classe principale : BookShelf

### Méthodes

#### `add(String... booksToAdd)`

Ajoute un ou plusieurs livres à l'étagère.

Exemple :

```java
shelf.add("Effective Java", "Code Complete");
```

---

#### `books()`

Retourne une liste immuable des livres présents dans l'étagère.

Exemple :

```java
List<String> books = shelf.books();
```
