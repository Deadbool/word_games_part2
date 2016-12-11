# Wordgame TODO

- Init
	- [X] Installer le plateau
	- [X] Préparer le sac de lettre
	- [X] Chaque joueur prend un rack et le rempli de lettres
- Premier coup
	- [ ] Le joueur qui a la lettre la plus proche de A commence
	- [X] Le premier mot doit passer par la/une case centrale
- Chaque tour
	- [X] Rempli son rack
	- [ ] Place un mot
		- [X] Emplacement valide (raccordé à un autre, forme un ou plusieurs mot)
		- [X] Comptage des points
	- [ ] Echange des lettres et passe son tour
- Fin de partie
	- [X] Plus de lettres dans le sac et dans le rack d'un joueur
	- [ ] Plus de lettres dans le sac et tout le monde passe son tour

Général :

- [ ] Rendre le tout insensible à la casse
- [ ] Les erreurs doivent être géré via des exceptions et non des booléens
- [X] Indiquer au joueur les erreurs qu'il génère
