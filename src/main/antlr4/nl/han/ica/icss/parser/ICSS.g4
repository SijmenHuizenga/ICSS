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