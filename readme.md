# Jeux de lettres

Projet ACDC - 2016

## Jouer un mot

Pour jouer un mot il faut préciser les valeurs suivantes, sur la même ligne, séparées par des virgules :

* le mot entier que vous souhaiter former,
* la coordonnée X de la première lettre du mot,
* la coordonnée Y de la première lettre du mot,
* le sens du mot (line ou column, 'l' ou 'c' pour allez plus vite).

## Etat du projet

Deux implémentations incomplètes sont disponibles, un TopWord et un Scrabble.

Ce qui marche (mais est surement à revoir) :

* Initialisation des jeux
* Ajout d'un mot
* Fin de partie

Ce qui marche et qui est à revoir :

* Validation d'un coup
* Comptage des points

Ce qui manque :

* utilisation d'un joker au Scrabble

## Structure du projet

Le projet projet est découpé de la manière suivante :

* Un série d'interfaces
* Une série d'implémentations communes
* Des décorateurs pour étendre les fonctionnalités des implémentations communes

![](/struct_uml.png)

La boucle de jeu utilise une implémentation de l'interface Wordgame et WordgameFactory, ici un décorateur de BasicWordgame.  
**Le code n'est pas commenté mais prendre connaissance de la boucle de jeu et des deux interfaces qu'elle utilise (Wordgame et WordgameFactory) devrait suffire !**

![](/usage.png)

## Autres précisions

* C'est un projet Maven
* Ils y a quelques tests unitaires
* Les fichiers spécifiant les données propres à un jeu sont au format Json. Ils sont dans le dossier src/java/main/ressources.
