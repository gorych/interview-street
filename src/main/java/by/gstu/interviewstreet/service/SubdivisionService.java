package by.gstu.interviewstreet.service;

import by.gstu.interviewstreet.domain.Subdivision;

import java.util.List;

public interface SubdivisionService {

    List<Subdivision> getAll();

    List<Subdivision> getSubdivisionsByInterview(String hash);

}
