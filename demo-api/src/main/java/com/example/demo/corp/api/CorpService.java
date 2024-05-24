package com.example.demo.corp.api;

import com.example.demo.corp.api.bo.Corp;

import java.util.List;

/**
 * @author JiakunXu
 */
public interface CorpService {

    int countCorp();

    List<Corp> listCorps();

    Corp getCorp();

    Corp updateCorp();

}
