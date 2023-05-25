package bankmanager.restservice;

import bankmanager.datamanagement.OperationBancaire;
import java.util.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

public class JSONToObject {

    public static List<OperationBancaire> JSONToListOfOperation(String JSONFileToConvert)  {
        ObjectMapper mapper = new ObjectMapper();

        OperationBancaire[] arrayOperationBancaire = {};
        List<OperationBancaire> listOperationBancaire = new ArrayList<>();

        try {
            // Je doit passer par un tableau sinon j'ai le problème suivant avec une liste : (tiré de stackOverflow
            // The issue's coming from Jackson. When it doesn't have enough information on what class to deserialize to, it uses LinkedHashMap.
            // Je me retrouve donc avec un LinkedHashMap ou les clés sont les nom des rubriques et je ne peux pas parcourir ma liste d'objet correctement
            arrayOperationBancaire = mapper.readValue(JSONFileToConvert, OperationBancaire[].class);
        } catch (JsonProcessingException jsonProcessingException) {
            System.out.println(jsonProcessingException.toString());
        }
        if (arrayOperationBancaire.length > 0) {
            // Je convertis par la suite mon tableau en liste d'opération
            listOperationBancaire = Arrays.asList(arrayOperationBancaire);
        }

        return listOperationBancaire;
    }

}
