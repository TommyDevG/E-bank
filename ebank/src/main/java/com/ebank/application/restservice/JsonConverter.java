package com.ebank.application.restservice;

import com.ebank.application.managedata.OperationBancaire;
import com.ebank.application.managedata.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonConverter {

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

    public static String ListOfOperationToJSON(List<OperationBancaire> listOperation)  {
        ObjectMapper mapper = new ObjectMapper();

        String jsonListOperation = "";

        try {
            jsonListOperation = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(listOperation);
        } catch(JsonProcessingException jsonProcessingException) {
            System.out.println(jsonProcessingException.toString());
        }

        return jsonListOperation;
    }

    public static String UserToJSON(User currentUser) {
        ObjectMapper mapper = new ObjectMapper();

        String jsonUser = "";

        try {
            jsonUser = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(currentUser);
        } catch(JsonProcessingException jsonProcessingException) {
            System.out.println(jsonProcessingException.toString());
        }

        return jsonUser;
    }

}
