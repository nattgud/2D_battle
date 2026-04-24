# Turbaserat strategispel

Ett rutnätsbaserat strategispel byggt i Java med Swing.

---

## Om spelet

Ett turbaserat taktikspel där spelaren styr ett lag med fantasymonster och krigare mot en datorkontrollerad motståndare. Enheterna rör sig på en rutnätskarta med terränghinder. Varje tur får en sida flytta och attackera, sedan byter kontrollen sida. Spelet slutar när ett av lagen inte har några levande enheter kvar.

---

## Funktioner

- **Procedurella kartor** – Hinder och gräsbakgrunder slumpas fram för varje nytt spel.
- **Varierade enhetstyper** – Enheter har "raser" (magi, ohelig m.fl.), när-/distansattack och styrkor och svagheter.
- **AI-motståndare** – Datorn väger skada, räckvidd och hälsa för att välja bästa möjliga drag varje tur.
- **Spara & ladda** – Upp till 5 sparslottar lagras i en databas. Pausmenyn nås med Escape-tangenten.
- **Animationer & partiklar** – Pilar, magiska projektiler, blodstänk och skadeanimationer visas i realtid.
- **Enhetskonfiguration** – Innan striden börjar kan spelaren höger-/vänsterklicka för att byta enhetstyper i sitt lag.

---

## Hur man spelar

1. Ange ditt spelarnamn när spelet startar.
2. Konfigurera dina enheter med vänster-/högerklick, klicka sedan **BEGIN**.
3. **Vänsterklicka** på en egen enhet för att välja den.
4. **Högerklicka** på en tom ruta för att flytta, eller på en fiende för att attackera.
5. Tryck **Escape** för att öppna pausmenyn (spara, ladda, starta om).

---

## Teknisk stack

- Java
- Swing / JPanel
- A\*-pathfinding
- MySQL (sparfiler och monster)
