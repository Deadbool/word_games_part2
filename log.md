# Log

Nicolas Guégan - ACDC - Jeu de mots - Partie 2

### Bugs manques du modèle:
* On ne peut pas poser de mot qui en croise un autre au scrabble (croisé dans le sens où l'on pose des lettres de chaque côté du mot), ni en rajoutant des lettres au début et à la fin du mot.
* Le calcul du score au Scrabble est erroné
* Le dictionnaire ne respecte pas les probabilités de tirage (stockage en lettre/quantité et tirage sur les lettres, donc autant de chance d'avoir un A qu'un Z)
* Il est possible de poser des mots n'importe où sur le plateau sans les coller aux autres
* Les fonctionnalités de sauvegarde et de reprise de parties ne sont pas implémentées
* Les mots joués ne sont pas vérifié avec le dictionnaire [FIXED]

### Concernant les bugs du modèle de l'application :
Je n'aurai sûrement pas le temps de corriger les nombreux bugs du code que l'on m'a attribué. J'espère que ma note ne sera pas dépendante de ce que j'ai récupéré car je n'ai pas à patir des erreurs (et de la désinvolture) de mon collègue. Si encore il s'agissait de bugs mineurs... mais là ce sont fonctionnalités critiques qui sont défaillantes ou simplement inéxistante.
Je trouve l'attitude de la personne à l'origine de ce coeur applicatif plus qu'obsolète parfaitement inadmissible. Rendre un devoir aussi peu aboutit tout en sachant que ce même devoir impactera le travail d'un collègue est plus qu'irrespectueux et irresponsable. Il est parfaitement injuste de demander de refournir le travail de la partie 1 une nouvelle fois lors de la partie 2.
Mon discours peut paraître violent, mais je suis exaspéré par cette attitude.

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

# 28/12/2016
* Lettres draggable rack->board et board->board
* Ajout d'un bouton reset qui remet toutes les lettres du joeurs dans son rack, il s'agit d'un alternative que je trouve plus adaptée que le drag board->rack
* On peut désormais échanger ses lettres lors de son tour grâce à une boîte de dialogue qui permet de sélectionner les lettres à remettre dans le sac
* Ajout d'un icône représentant le sac de lettres et d'un compteur affichant le nombre de lettres restantes
* Validation d'un tour grâce à au BoardControl qui analyse les lettres posées sur board par le joueur pour déterminer le mot formé, sa position et son orientation