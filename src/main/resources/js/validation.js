function invalidPassportData(textbox) {
    if(textbox.validity.patternMismatch){
        textbox.setCustomValidity('Номер и серия паспорта введены неверно.');
    }
    else {
        textbox.setCustomValidity('');
    }

    return true;
}