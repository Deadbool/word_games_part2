# Bilan du module IPIPIP

Nicolas Guégan - ACDC - Jeu de mots

## Phase 1 : le coeur applicatif
* Conception d'une architecture modulaire pour gérer plusieurs types de jeux de lettres
* Implémentation de deux jeux de lettres: Scrabble et Topword
* Rédaction d'une Javadoc accompagnant le code source

* Points positifs :
	- Facilement utilisable avec une IHM de type PAC en utilisant comme modèle la classe WordGame et en implémentant une nouvelle sous-classe de Player avec une méthode askForAWord() adaptée
	- Simplicité et clarté de l'architecture
	- Documentation
	- Tests fonctionnels passés avec succès
	
* Points négatifs :
	- Pas de gestion des jokers
	- Processus de jeu terminal très fragile car pas de gestion des incohérences de saisie
	
* Difficultés rencontrées :
	- De nombreuses problématiques que je n'avais pas anticipées (algorithmes de détection des mots sur le plateau)
	- La gestion des jokers en tant que tel m'a parue beaucoup trop complexe à gérer, j'ai donc choisit de le gérer en tant que lettre normale déterminée par le joueur à l'utilisation
	
## Phase 2 : l'Interface Homme-Machine
* Conception d'une IHM agréable visuellement et ergonomique proposant toutes les interactions nécessaires aux jeux de Scrabble et Topword
* Implémentation de cette IHM et connexion au modèle en respectant la méthode PAC
* Gestion d'un mécanisme de Drag & Drop pour ce qui est des interractions avec les tuiles (lettres)

* Points positifs :
	- Le Drag & Drop offre un confort d'utilisation
	- Une réflexion aboutie sur la manière de présenter les différentes informations (compteur de lettres restantes par exemple)
	- Une charte graphique globale
	
* Points négatifs :
	- La gestion des jokers ne respecte pas celle des jeux réels: après sélection de la lettre voulue par le joueur, le joker vaut le score de cette lettre et non zéro
	- La fin de partie affiche simplement une boîte de dialogue indiquant le nom et le score du vainqueur et non un classement général avec la possibilité de recommencer la partie comme voulu dans ma conception (manque de temps).
	
* Difficultés rencontrées :
	- Le drag & drop: choix de la méthode à employer, mise en place, gestion de l'overlay pour le topword
	- L'adaptation du modèle aux normes PAC: changement de l'interface Wordgame en classe abstraite pour pouvoir la faire hériter d'Observable, ajout d'une méthode newTurn() pour actualiser l'IHM (appelée au début d'une partie et à chaque appelle de skipTurn())
	