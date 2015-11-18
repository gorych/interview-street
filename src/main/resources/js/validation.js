function invalidPassportData(textbox) {
    if(textbox.validity.patternMismatch){
        textbox.setCustomValidity('Invalid data');
    }
    else {
        textbox.setCustomValidity('');
    }

    return true;
}