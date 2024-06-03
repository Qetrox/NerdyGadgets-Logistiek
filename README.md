# NerdyGadgets-Logistiek
## KBS-B NerdyGadgets 2

**Welke oplossing is het beste voor het bepalen van de beste route bij een bezorging?**
- Welke algoritme is het meest geschikt om routes te bepalen voor bezorgingen?
-	Hoe bepaal je de meest optimale route? 
-	Met welke externe omstandigheden moeten we rekening houden bij het bepalen van een route?
-	Welke transportmethodes worden er gebruikt bij het bezorgen?
-	Hoe bepalen we welke transportmethode we gebruiken bij het bezorgen van een pakket?
-	Welke oplossing voor routebepaling is het meest kost efficiënt?
-	Welke oplossing voor routebepaling is het meest tijd efficiënt?
-	Welke oplossing voor routebepaling is het handigst om te implementeren?
-	Op welke tijden en dagen moet de oplossing beschikbaar zijn? 
-	Op welke manier optimaliseren we welke pakketjes met een bezorger mee gaan?
-	Is het voordelig om samen te werken met andere bedrijven en die pakketjes ook leveren? 

Gebouwd uit 4 java applicaties
- Backend
- Frontend: bezorger
- Frontend: manager
- Frontend: retour

### C4 diagram
![image](https://github.com/Qetrox/NerdyGadgets-Logistiek/assets/85188865/150bab9d-4886-4daa-b1e7-15566ef68f3f)

## Backend
De applicatie die alle data verwerkt en op slaat. Staat als enige in connectie met de database.
Via een REST api kunnen de rest van de applicaties data ophalen en opslaan.

## Frontend: bezorger
De applicatie die de bezorger de route laat zien en de mogelijkheid bied om de bezorger de status van pakketten aan te laten passen.

## Frontend: manager
De applicatie die de managers kunnen gebruiken om alle relevante informatie betreft orders in te zien.

## Frontend: retour
De applicatie de de bezorgers kunnen gebruiken om retours aan te melden of bestaande in te zien en aanpassen.

### Database Entity relation diagram
![image](https://github.com/Qetrox/NerdyGadgets-Logistiek/assets/85188865/fe6233b8-ed35-40a3-be0e-df8efa4c2663)
    
