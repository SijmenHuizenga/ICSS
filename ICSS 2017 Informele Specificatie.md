Onderstaande text is geciteerd uit "Beroepsproduct-Programmeertalen-Feb-2017" gepubliceerd door ICA Hogeschool Arnhem Nijmegen

---


ICSS is een opmaaktaal vergelijkbaar met Cascading Stylesheets (CSS). Het is tegelijkertijd een subset, als
ook een superset van CSS2. Dat wil zeggen, het heeft niet alle mogelijkheden van CSS, maar tegelijkertijd
ook een aantal features die CSS niet heeft.
Dit document beschrijft op informele wijze mogelijkheden van de 2017 versie van ICSS.

Eenvoudige opmaak
=======
ICSS gebruikt net als CSS regels om de opmaak van HTML elementen aan te geven. Een stylesheet bestaat
uit een aantal regels die na elkaar worden toegepast op een HTML document.
Regels hebben de vorm ``<selector> { <declaraties> }``. Hierin is de selector ofwel een speci?ek type tag
geselecteerd kan worden, ofwel een element met een unieke id, ofwel elementen van een bepaalde class.
Elementen met een uniek id worden aangegeven door identi?er beginnend met een hekje (``#``) en elementen
in een klass worden aangegeven door de klassenamen voorafgegaan door een punt (``.``). Declaraties zijn
naam/waarde paren van de vorm ``<attribuutnaam>: <waarde>;``. Sommige waardes kunnen ook een eenheid
bevatten zoals ``px`` of ``\%``.
Hier volgen een aantal voorbeelden van eenvoudige ICSS regels:

``
a {
    color: #ff0000;
    background -color: #eeeeee;
}
#menu {
    width: 100%;
    height: 50px;
}
.active {
    color: #00ffff;
}
``

Beperkingen
=======
ICSS is beperkter dan CSS. Dit zijn de beperkingen:
* Selectoren selecteren maar op één ding tegelijk. Combinaties zoals ``a.active`` zijn niet toegestaan.
* Selectoren voor het selecteren van kinderen uit CSS zoals ``div > a`` zijn niet toegestaan
* Alleen de stijlattributen ``color``, ``background-color``, ``width`` en ``height`` zijn toegestaan.
* Voor kleuren (color en background-color) moet de waarde als een hexadecimale waarde van zes tekens opgegeven worden. (Bijvoorbeeld: ``#00ff00``)
* Voor groottes mag of een waarde in pixels (bijvoorbeeld: ``100px``) of een percentage (bijvoorbeeld ``50%``) gespeci?eerd worden.

Constantes
=======
Een feature die CSS niet heeft, maar ICSS wel is de de?nitie van constante waardes. In ICSS kun je constantes
declareren die je dan op meerdere plaatsen waar je anders een waarde zou invullen. Een assignment ziet er
als volgt uit:
``$myconstant = 100px;``
Het gebruik ervan is dan:
``width: $myconstant;``
Je kunt ook constantes de?nieren op basis van een andere constante:
``$textcolor = $bgcolor``

Je mag in ICSS een constante maar één keer de?niëren. Dit mag tussen de stijlregels door, maar niet in de
body van een stijlregel. De scope van constantes is globaal. Je mag constantes gebruiken die pas verderop
in het document gede?nieerd worden.

Berekende waardes
=======
Een andere uitbreiding in ICSS is de mogelijkheid om eenvoudige berekeningen te doen met waardes. In ICSS
mag je pixelwaardes en percentages optellen en aftrekken. Dit mag zowel in stijldeclaraties van attributen als
in de assignment van constantes.
Bijvoorbeeld:
``
div {
    width: 50px + 50px;
}
#menu {
    height: 20px + $myheight + 5px;
}
``
of
``
$menusize = $headersize - 20%;
``
Je mag alleen pixelwaardes bij pixelwaardes optellen en percentages bij percentages.

Geneste regels
=======
Hoewel ICSS geen samengestelde selectoren voor het beschrijven van kinderen van elementen ondersteund
(bijv ``div a > span {...}``), kun je toch regels maken die van toepassing zijn op de kinderen van een HTML-
element. Dit doe je door regels in elkaar te ”nesten”. Door een nieuwe regel te de?nieren binnen de accolades
van een bestaande regel kun je aangeven dat die regel van toepassing is op de directe kinderen van de HTML-
elementen waarop de oorspronkelijke regel van toepassing was.
Bijvoorbeeld:
``
div {
    background -color: #000000;
    a {
        width: 200px;
        span {
            color: #ff00ff;
        }
    }
}
``