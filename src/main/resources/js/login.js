function invalidPassportData(loginInput) {

    if (loginInput.validity.patternMismatch) {
        loginInput.setCustomValidity('Паспортные данные введены некорректно');
        return false;
    }

    loginInput.setCustomValidity('');
    return true;
}
