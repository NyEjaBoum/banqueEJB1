package com.change;

import jakarta.ejb.Remote;
import java.time.LocalDate;
import java.util.List;

@Remote
public interface IChangeService {
    double getCours(String devise, LocalDate date) throws Exception;
    double convertirEnAriary(String devise, double montant, LocalDate date) throws Exception;
    List<String> listerDevises() throws Exception;
}