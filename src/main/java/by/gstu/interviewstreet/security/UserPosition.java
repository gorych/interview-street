package by.gstu.interviewstreet.security;

public enum UserPosition {

    DEFAULT(new String[]{UserRoleConstants.VIEWER}),
    VIEWER(new String[]{UserRoleConstants.VIEWER}),
    RESPONDENT(new String[]{UserRoleConstants.VIEWER, UserRoleConstants.RESPONDENT}),
    EDITOR(new String[]{UserRoleConstants.VIEWER, UserRoleConstants.RESPONDENT, UserRoleConstants.EDITOR});

    private final String[] ROLES;

    UserPosition(String[] roles) {
        this.ROLES = roles;
    }

    public String[] getRole() {
        return ROLES;
    }

}
