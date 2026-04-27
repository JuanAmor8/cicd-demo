package au.com.equifax.cicddemo;

import java.util.*;

public class TechnicalDebtExample {

    // Code smell: campo público (debería ser privado con getter)
    public String secretKey = "hardcoded-secret-123";

    // Code smell: método demasiado complejo, catch vacío
    public String processData(String input) {
        try {
            if (input == null) {
                return null; // Code smell: retorno nulo
            }
            String result = input.toUpperCase();
            result = result.trim();
            result = result.replace(" ", "_");
            return result;
        } catch (Exception e) {
            // Code smell: excepción ignorada (empty catch block)
        }
        return "";
    }

    // Code smell: código duplicado innecesario
    public int calcularA(int x) { return x * x + 2 * x + 1; }
    public int calcularB(int x) { return x * x + 2 * x + 1; }
}