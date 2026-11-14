package com.enset.metier;

public class SecurityContext {
    private static String username;
    private static String password;
    private static String[] roles;

    public static void authenticate(String u, String p, String[] r) {
        if (u.equals("root") && p.equals("password")) {
           username = u;
           password = p;
           roles = r;
        }else {
            throw new RuntimeException("Access Denied");
        }
    }

    public static boolean hasRole(String role) {
        for (String r : roles) {
            if (role.equals(r)) return true;
        }
        return false;
    }
}
