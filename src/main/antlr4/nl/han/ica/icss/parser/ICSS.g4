/*
 * Alle code zoals aangeleverd door ICA HAN is eigendom van ICA HAN. Deze code is te zien in het eerste commit van deze repository. Alle wijzigingen en uitbreidingen ná het eerste commit vallen onder de MIT Licentie:
 *
 * Copyright 2017 Sijmen Huizenga
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

grammar ICSS;

stylesheet: stylesheetpart*;

stylesheetpart: constantassignment | stylerule;

stylerule: selector STYLERULE_OPEN declerationpart* STYLERULE_CLOSE;

selector: ((SELECTOR_ELEEMNT | SELECTOR_ID) | SELECTOR_CLASS);

declerationpart: decleration | stylerule;
decleration: attribute ATTRIBUTE_VALUE_SEPERATOR calculatedvalue LINEEND;

attribute: ATTRIBUTE_COLOR | ATTRIBUTE_BACKGROUND_COLOR | ATTRIBUTE_WIDTH | ATTRIBUTE_HEIGHT;

value: literal | constantreference;

literal: LITERAL_COLOR | LITERAL_PIXELS | LITERAL_PERCENTAGE;

calculatedvalue: value (|moreCalculatedValues);
moreCalculatedValues: calcoperator calculatedvalue;

calcoperator: CALCOPERATOR_ADD | CALCOPERATOR_SUB | CALCOPERATOR_MUL | CALCOPERATOR_DEV;

constantreference: CONSTANT_NAME;

constantassignment: constantreference CONSTANT_ASSIGNMENT_SEPERATOR calculatedvalue LINEEND;

WS: [ \t\r\n]+ -> skip;

STYLERULE_OPEN:  '{';
STYLERULE_CLOSE: '}';

LINEEND: ';';

ATTRIBUTE_COLOR: 'color';
ATTRIBUTE_BACKGROUND_COLOR: 'background-color';
ATTRIBUTE_WIDTH: 'width';
ATTRIBUTE_HEIGHT: 'height';

ATTRIBUTE_VALUE_SEPERATOR: ':';

LITERAL_COLOR: '#'[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f];
LITERAL_PIXELS: [0-9]+'px';
LITERAL_PERCENTAGE: [0-9]+'%';

SELECTOR_ID: '#'[a-zA-Z0-9]+;
SELECTOR_CLASS: '.'[a-zA-Z0-9]+;
SELECTOR_ELEEMNT: [a-zA-Z0-9]+;

CONSTANT_NAME: '$'[a-zA-Z0-9]+;
CONSTANT_ASSIGNMENT_SEPERATOR: '=';

CALCOPERATOR_ADD: '+';
CALCOPERATOR_SUB: '-';
CALCOPERATOR_MUL: '*';
CALCOPERATOR_DEV: '/';