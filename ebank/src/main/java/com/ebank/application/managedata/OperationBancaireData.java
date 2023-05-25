package com.ebank.application.managedata;

import java.util.Date;

// Je met la date des opération en String sinon lors du passage API il change le format de la date or j'aimerai un
// format universel pour pouvoir le convertir facilement
public record OperationBancaireData(Integer idoperationBancaire, Integer idcategorieOperation, String libelle, Date dateOperation, double montantDebit, double montantCredit) { }
