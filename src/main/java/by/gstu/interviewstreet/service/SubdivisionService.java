package by.gstu.interviewstreet.service;

import by.gstu.interviewstreet.domain.Subdivision;

import java.util.List;

public interface SubdivisionService {

    Subdivision getById(Integer id);

    List<Subdivision> getAll();

    List<Subdivision> getSubdivisionsByInterview(String hash);

}
