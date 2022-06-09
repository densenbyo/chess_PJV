# Základní informace
- Vytvořil : Mukan Atazhanov
- Téma : Šachy
- Deadline : 20.05.2022

# Téma
V jazyce Java s využitím grafického uživatelského rozhraní (GUI) napište program pro hru šachů pro dva hráče.

# Technology
- [Java](https://en.wikipedia.org/wiki/Java_(programming_language))
- [Maven](https://en.wikipedia.org/wiki/Apache_Maven)
- [Swing](https://en.wikipedia.org/wiki/Swing_(Java))
- [JUnit](https://en.wikipedia.org/wiki/JUnit)
- [Lombok](https://projectlombok.org)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/)

# Сíl
1) Naimplementovat funkční šachy
2) Vytvořit příjemné grafické prostředí
3) Otestovat hru při pomoci JUnit testu
4) Šachy musí být hratelné pro dva hráče (PvP, PvC)
5) Partie budou zapisované a zdokumentované

# Požadavky
- hra dvou hráčů `DONE`
- člověk vs. člověk (PvP) `DONE`
- příprava pro hru člověk vs. počítač (PvC) `DONE`
- kompletní kontrola pravidel hry `DONE`
- možnost uložení a načtení rozehrané partie `DONE`
- možnost manuálního umístění figur před zahájením hry `DONE`
- šachové hodiny (využití vláken - bez použití Timeru nebo obdobné třídy s implementovaným časovačem). Správnou synchronizaci času si každý student musí naprogramovat pomocí vláken sám s vědomím toho, že “sleep(1000)” neznamená, že program púoběží opravdu za jednu sekundu! `DONE`
- Každý musí implementovat minimálně jednu z následujících vlastností/úkolů:
  - síťová hra
  - načítání, ukládání a prohlížení partií ve standardním PGN formátu `NOT DONE`

# TODO
1. GUI pro manualni umisteni
2. Fix nacteni a ulozeni (neuklada ci tah byl)
3. PGN nacitani a prohlizeni
4. Nevim proc po pawn promotion hra nevidi sach :)