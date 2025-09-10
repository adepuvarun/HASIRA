import java.math.BigInteger;
import java.util.*;
import org.json.JSONObject;
public class PolynomialSolver {
    public static void main(String[] args) {
        String input1 = "{\n" +
                "    \"keys\": {\n" +
                "        \"n\": 4,\n" +
                "        \"k\": 3\n" +
                "    },\n" +
                "    \"1\": {\n" +
                "        \"base\": \"10\",\n" +
                "        \"value\": \"4\"\n" +
                "    },\n" +
                "    \"2\": {\n" +
                "        \"base\": \"2\",\n" +
                "        \"value\": \"111\"\n" +
                "    },\n" +
                "    \"3\": {\n" +
                "        \"base\": \"10\",\n" +
                "        \"value\": \"12\"\n" +
                "    },\n" +
                "    \"6\": {\n" +
                "        \"base\": \"4\",\n" +
                "        \"value\": \"213\"\n" +
                "    }\n" +
                "}";

        String input2 = "{\n" +
                "  \"keys\": {\n" +
                "    \"n\": 10,\n" +
                "    \"k\": 7\n" +
                "  },\n" +
                "  \"1\": {\"base\": \"6\", \"value\": \"13444211440455345511\"},\n" +
                "  \"2\": {\"base\": \"15\", \"value\": \"aed7015a346d635\"},\n" +
                "  \"3\": {\"base\": \"15\", \"value\": \"6aeeb69631c227c\"},\n" +
                "  \"4\": {\"base\": \"16\", \"value\": \"e1b5e05623d881f\"},\n" +
                "  \"5\": {\"base\": \"8\", \"value\": \"316034514573652620673\"},\n" +
                "  \"6\": {\"base\": \"3\", \"value\": \"2122212201122002221120200210011020220200\"},\n" +
                "  \"7\": {\"base\": \"3\", \"value\": \"20120221122211000100210021102001201112121\"},\n" +
                "  \"8\": {\"base\": \"6\", \"value\": \"20220554335330240002224253\"},\n" +
                "  \"9\": {\"base\": \"12\", \"value\": \"45153788322a1255483\"},\n" +
                "  \"10\": {\"base\": \"7\", \"value\": \"1101613130313526312514143\"}\n" +
                "}";

        System.out.println("=== FIRST TEST CASE ===");
        solvePolynomial(input1);

        System.out.println("\n=== SECOND TEST CASE ===");
        solvePolynomial(input2);
    }

    public static void solvePolynomial(String jsonInput) {
        JSONObject obj = new JSONObject(jsonInput);
        int n = obj.getJSONObject("keys").getInt("n");
        int k = obj.getJSONObject("keys").getInt("k");

        List<BigInteger> roots = new ArrayList<>();
        for (int i = 1; i <= k; i++) {
            JSONObject rootObj = obj.getJSONObject(String.valueOf(i));
            int base = Integer.parseInt(rootObj.getString("base"));
            String value = rootObj.getString("value");
            BigInteger root = new BigInteger(value, base);
            roots.add(root);
        }

        List<BigInteger> coeffs = new ArrayList<>();
        coeffs.add(BigInteger.ONE);

        for (BigInteger r : roots) {
            List<BigInteger> newCoeffs = new ArrayList<>(Collections.nCopies(coeffs.size() + 1, BigInteger.ZERO));
            for (int i = 0; i < coeffs.size(); i++) {
                newCoeffs.set(i, newCoeffs.get(i).add(coeffs.get(i).negate().multiply(r)));
                newCoeffs.set(i + 1, newCoeffs.get(i + 1).add(coeffs.get(i)));
            }
            coeffs = newCoeffs;
        }

        Collections.reverse(coeffs);
        System.out.println("Polynomial coefficients: " + coeffs);
    }
}
