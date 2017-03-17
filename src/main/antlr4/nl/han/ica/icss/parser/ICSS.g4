grammar ICSS;

stylesheet: stylesheetpart*;

stylesheetpart: constantassignment | declerationblock;

declerationblock: selector DECLERATIONBLOCK_OPEN decleration* DECLERATIONBLOCK_CLOSE;

selector: ((SELECTOR_ELEEMNT | SELECTOR_ID) | SELECTOR_CLASS);

decleration: attribute ATTRIBUTE_VALUE_SEPERATOR (value | constant) LINEEND;

attribute: ATTRIBUTE_COLOR | ATTRIBUTE_BACKGROUND_COLOR | ATTRIBUTE_WIDTH | ATTRIBUTE_HEIGHT;

value: VALUE_COLOR | VALUE_PIXELS | VALUE_PERCENTAGE;

constant: CONSTANT_NAME;

constantassignment: constant CONSTANT_ASSIGNMENT_SEPERATOR (value | constant) LINEEND;

WS: [ \t\r\n]+ -> skip;

DECLERATIONBLOCK_OPEN:  '{';
DECLERATIONBLOCK_CLOSE: '}';

LINEEND: ';';

ATTRIBUTE_COLOR: 'color';
ATTRIBUTE_BACKGROUND_COLOR: 'background-color';
ATTRIBUTE_WIDTH: 'width';
ATTRIBUTE_HEIGHT: 'height';

ATTRIBUTE_VALUE_SEPERATOR: ':';

VALUE_COLOR: '#'[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f];
VALUE_PIXELS: [0-9]+'px';
VALUE_PERCENTAGE: [0-9]+'%';

SELECTOR_ID: '#'[a-zA-Z]+;
SELECTOR_CLASS: '.'[a-zA-Z]+;
SELECTOR_ELEEMNT: [a-zA-Z]+;

CONSTANT_NAME: '$'[a-zA-Z0-9]+;
CONSTANT_ASSIGNMENT_SEPERATOR: '=';