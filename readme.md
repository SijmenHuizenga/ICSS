Deze repo bevat mijn uitwerkting van de casus "ICSS" voor het vak Algoritmes, Programmeertalen en Paradigma’s (APP) op de Hogeschool van Arnhem en Nijmegen (HAN).

Deze implementatie bevat
* Alle functionaliteiten zoals beschreven in de opdrachtbeschrijving
  * Parsen: Implementeer een parser plus listener die AST’s kan maken voor ICSS documenten die eenvoudige opmaak kan parseren, zoals beschreven in de taalbeschrijving.
  * Parsen Breidt je grammatica en listener uit zodat nu ook assignments en het gebruik ervan geparseerd kunnen worden. In level1.icss vindt je voorbeeldcode die je nu zou moeten kunnen.
  * Parsen: Breidt je grammatica en listener uit zodat je nu ook optellen en aftrekken kunt parseren. In level2.icss vindt je voorbeeldcode die je nu zou moeten kunnen.
  * Parsen: Breidt je grammatica en listener uit zodat je ook geneste stijlregels aankunt. In level3.icss vindt je voorbeeldcode die je nu zou moeten kunnen.
  * Checken: Controleer of er geen constantes in declaraties worden gebruikt die nog niet gede?nieerd zijn.
  * Checken: Controleer of er geen constantes worden gede?nieerd die al bestaan.
  * Checken: Controleer of de operanden van de operaties (plus en min) van gelijk type. Je mag geen pixels bij kleuren optellen bijvoorbeeld.
  * Checken: Controleer of bij declaraties het type van de waarde klopt bij de stijlei genschap. Declaraties zoals width: #ff0000 of color: 12px zijn natuurlijk onzin.
  * Transformeren: Implementeer de InlineConstants transformatie. Deze transformatie vervangt alle ConstantReference knopen in de AST door de value knoop waar ze naar wijzen.
  * Transformeren: Implementeer de EvalOperations transformatie. Deze transformatie vervangt iedere Operation knoop in de AST door het resultaat van de operatie (dit is dus een Literal knoop).
  * Genereren: Implementeer de generator in nl.han.ica.icss.generator.Generator voor ICSS zonder geneste stijlregels.
  * Genereren: Breidt de generator uit dat je nu ook de geneste stijlregels aankunt. In CSS bestaat geen nesting, dus in de gegenereerde code staan alle stijlregels op het topniveau. Verder moet je nu “samengestelde” selectors genereren. Een a selector binnen een div selector moet in de output div > a worden.
* AST Tree optimalisatie waarin ConstantReferences altijd een referentie hebben naar de bijbehorende Assignment van de constante. Dit maakt het checken stukke makkelijker
* Volledig Type-checking mechanisme die dus ook controleert of Types van ConstantReferences in Operaties en Declaraties klooppen
* Uitgebreide Operaties (rekensommen):
  * Toevoeging van Delen door met ``/`` en vermenigvuldigen met ``*``
  * Volgorde van berekeningen behouden zoals in normale berekeningen ook wordt gedaan (delen en vermenigvuldigen voor optellen en aftrekken).
* Geautomatiseerde tests voor de kritieke onderdelen:
  * Parser tests (uitgebreide oprations parser test)
  * Declaratie Type Checking tests
  * getType van alle Value types tests (ook getType() op Operaties en ConstantReferences)
  * Cirular Assignments Checker tests (bijvoorbeeld ``$a = $a;`` en ``$a = $b; $b = $a;``)
  * Duplicate Assignment Checker test (bijvoorbeeld ``$a = 10px; $a = 11px``)
  * Null ConstantReference Checker test (bijvoorbeeld ``#a { color: $c; }``)
  * Eval Oprations Transformation test
  * Inline Constant Transformation test
  * Generator tests

Een beschrijving van de casus is hieronder te vinden. Onderstaande text is geciteerd uit "Beroepsproduct-Programmeertalen-Feb-2017" gepubliceerd door ICA Hogeschool Arnhem Nijmegen

Inleiding
=====
Zoals je waarschijnlijk wel weet wordt de opmaak van webpagina’s gespeci?ceerd in Cascading Style Sheets,
oftewel CSS. Ontwerpkeuzes die gemaakt zijn in deze opmaaktaal maken het soms wat omslachtig om op-
maak te beschrijven. Ook krijg je vaak herhalende code. Om deze problemen aan te pakken zijn er verschil-
lende CSS preprocessors zoals SASS 1 en LESS 2 die een eigen CSS “dialect” vertalen naar standaard CSS
code. In deze opdracht ga je zelf een vergelijkbare preprocessor maken.


Opdrachtomschrijving
=====
Je gaat in deze opdracht dus een eigen CSS dialect maken: ICSS-17 (ICA-CSS). Een informele beschrijving
van deze taal is te vinden in appendix A.
Je bouwt in deze opdracht een interactieve Java applicatie: De ICSS tool. Dit is een interactieve compiler. Je
kunt er interactief ICSS in bewerken en deze stapsgewijs compileren naar CSS. Deze CSS kun je vervolgens
kunt exporteren. Het raamwerk voor de ICSS tool krijg je als startcode aangeleverd. De GUI is al gemaakt
en alle onderdelen zijn in minimale vorm aanwezig. De opdracht bestaat uit een aantal deelopdrachten die
samen de volledige compiler vormen.

Licentie
=====
Alle code zoals aangeleverd door ICA HAN is eigendom van ICA HAN. Deze code is te zien in het eerste commit van deze repository. Alle wijzigingen en uitbreidingen ná het eerste commit vallen onder de MIT Licentie.
