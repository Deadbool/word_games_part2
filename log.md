# Log

Nicolas Guégan - ACDC - Jeu de mots - Partie 2

### Défaillances du modèle:
* On ne peut pas poser de mot qui croise un autre mot Scrabble [amélioré mais pas corrigé]
* On ne peut pas prolonger un mot ni au Topord ou au Scrabble avec plus d'une lettre [corrigé]
* Le dictionnaire ne respecte pas les probabilités de tirage (stockage en lettre/quantité et tirage sur les lettres, donc autant de chance d'avoir un A qu'un Z)
* Il est possible de poser des mots n'importe où sur le plateau sans les coller aux autres
* Les fonctionnalités de sauvegarde et de reprise de parties ne sont pas implémentées [présentes dans l'IHM mais désactivées]
* Les mots joués ne sont pas vérifié avec le dictionnaire [corrigé]

## 14/12/2016
* Aménagement du code de base pour s'adapter au modèle PAC
* Ajout d'une barre de menu
* Ajout de la liste des joueurs et de son contrôleur

## 21/12/2016
* Création de tous les composants de l'IHM :
	- Board
	- Rack
	- Menu
	- Liste des joueurs + score
* Controle de la liste des joueurs et de leur score
* Lettres draggable (mécanisme obsolète)

## 28/12/2016
* Lettres draggable rack->board et board->board
* Ajout d'un bouton reset qui remet toutes les lettres du joueur dans son rack. Il s'agit d'un alternative que je trouve plus adaptée que le drag board->rack.
* On peut désormais échanger ses lettres lors de son tour grâce à une boîte de dialogue qui permet de sélectionner les lettres à remettre dans le sac
* Ajout d'un icône représentant le sac de lettres et d'un compteur affichant le nombre de lettres restantes
* Création d'un BoardControl qui analyse les lettres posées sur board par le joueur pour déterminer le mot formé, sa position et son orientation. Il est utilisé pour interfacer l'IHM avec le modèle lors de la pose d'un mot.
* Ajout d'un fenêtre de création de partie permettant la saisie des joueurs et le choix du jeu

## 03/12/2016
* Gestion des jokers via une boîte de dialogue (le joker ne compte pas pour zéro mais vaut le nombre de points de la lettre réelle)
* Corrections majeures du BoardControl pour détecter correctement les nouveaux mots
* Implémentations d'un mode overlay pour le drag and drop du topword
* Corrections apportées au modèle
