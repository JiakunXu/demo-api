package com.example.demo.qrtz.api.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class Job implements Serializable {

    private static final long serialVersionUID = 3695851816029287371L;

    private String            name;

    private String            group;

    private String            description;

    private String            className;

    private boolean           durability;

    private boolean           shouldRecover;

    private Trigger           trigger;

    @Getter
    @Setter
    public static class Trigger implements Serializable {

        private static final long serialVersionUID = -848475443962692688L;

        private String            description;

        private Date              startTime;

        private Date              endTime;

        private int               misfireInstr;

        private String            cronExpression;

    }

}
